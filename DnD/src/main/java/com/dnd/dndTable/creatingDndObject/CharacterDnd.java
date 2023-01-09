package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.BotAction;
import com.dnd.botTable.actions.CloudAction;
import com.dnd.botTable.actions.WrappAction;
import com.dnd.botTable.actions.dndAction.AttackAction;
import com.dnd.botTable.actions.dndAction.ChangeAction;
import com.dnd.botTable.actions.dndAction.PreRoll;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.botTable.actions.dndAction.RollAction;
import com.dnd.botTable.actions.dndAction.StartTreeAction;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.Rolls.MainStat;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Wallet;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.rolls.Article;
import com.dnd.dndTable.rolls.Formula;

public class CharacterDnd implements Serializable, Refreshable, DndKeyWallet
{

	private static final long serialVersionUID = -7781627593661723428L;

	private String name;
	private boolean fighting;
	private LVL lvl;
	private int speed;
	private String nature;
	private HP hp;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private Rolls rolls;
	private AttackMachine attackMachine;
	private Workmanship myWorkmanship;
	private Body myBody;
	private List<CloudAction> cloud;
	private List<String> permanentBuffs;
	private List<String> timesBuffs;
	private List<String> myMemoirs;

	private CharacterDnd(String name) 
	{
		this.name = name;
		myWorkmanship = new Workmanship();
		rolls = new Rolls(myWorkmanship.getPossessions());
		attackMachine = new AttackMachine(myWorkmanship.getPossessions());
		myMemoirs = new ArrayList<>();
		cloud = new ArrayList<>();
		myBody = new Body(rolls.getStats());
		hp = new HP();
		permanentBuffs = new ArrayList<>();
		timesBuffs = new ArrayList<>();
	}

	public Action act(Action action)
	{
		if(action instanceof StartTreeAction)
		{
			return startTreeAction((StartTreeAction) action);
		}
		else if(action instanceof PreRoll) 
		{
			PreRoll roll = (PreRoll) action;
			if(roll.getAction() instanceof AttackAction)
			{
				return attackMachine.postAttack(postRoll(roll));
			}
			else
			{
				return postRoll(roll).rebuild();
			}
		}
		else if(action instanceof RollAction)
		{
			if(action instanceof AttackAction)
			{
				return PreRoll.create(action.getName() + "PreRoll", (AttackAction) action);
			}
			return WrappAction.create("EndRoll", NO_ANSWER, rolls.execute((RollAction) action).execute(), null);
		}
		Log.add("CHARACTER ACT ERROR");
		return null;
	}

	private Action startTreeAction(StartTreeAction action) {

		if(action instanceof RegistrateAction)
		{
			return registAction((RegistrateAction) action);
		}
		else
		{
			long key = action.getKey();
			if(key == ABILITY)
			{
				return myWorkmanship.ability().returnTo("Menu");
			}
			else if(key == CHARACTERISTIC)
			{
				return rolls.characteristic().returnTo("Menu");
			}
			else if(key == STUFF)
			{
				return myBody.stuff().returnTo("Menu");
			}
			else if(key == MEMOIRS)
			{
				return getMemoirsAct().returnTo("Menu");
			}
			else if(key == REST)
			{
				return rest().returnTo("Menu");
			}
			else if(key == STAT)
			{
				return rolls.statMenu().returnTo("Characteristic");
			}
			else if(key == SAVE_ROLL)
			{
				return rolls.saveRollMenu().returnTo("Characteristic");
			}
			else if(key == SKILL)
			{
				return rolls.skillMenu().returnTo("Characteristic");
			}
			else if(key == PREPEARED)
			{
				return myBody.getPrepearedMenu().returnTo("STUFF");
			}
			else if(key == BAG)
			{
				return myBody.getBagMeny().returnTo("STUFF");
			}
			else if(key == WALLET)
			{
				return myBody.getWalletMenu().returnTo("STUFF");
			}
			Log.add("TREE ACTION ERROR");
			return null;	
		}
	}

	private Action registAction(RegistrateAction action)
	{

		if(action instanceof ChangeAction)
		{
			return changeAction((ChangeAction) action);
		}
		else
		{
			ObjectDnd object = action.getTarget();
			if(action.getKey() == ATTACK_MACHINE)
			{
				if(object instanceof Weapon)
				{
					Weapon weapon = (Weapon) object;
					return attackMachine.startAction(weapon);
				}
			}
			else
			{
				if(object instanceof Feature)
				{
					return myWorkmanship.featureCase((Feature)object);
				}
				else if(object instanceof Time)
				{
					return rest((Time)object);
				}
				else if(object instanceof Items)
				{
					if(action.getKey() == PREPEARED)
					{
						return myBody.getPrepearedMenu(action);
					}

					return myBody.getItemMenu(action);
				}
				else if(object instanceof MainStat)
				{
					return rolls.getStatCase(action);
				}
				else if(object instanceof Article)
				{
					return rolls.getArticleCase(action);
				}
			}
			Log.add("ERROR REGISTRATE ACTION");
			return null;
		}
	}

	private Action changeAction(ChangeAction action)
	{
		long key = action.getKey();
		if(key == STAT)
		{
			return rolls.changeStat(action);
		}
		else if(key == ARTICLE)
		{
			return rolls.changeArticle(action);
		}
		else if(key == PREPEARED)
		{
			Action answer = myBody.changePrepeared(action);
			if(answer.getKey() == ATTACK_MACHINE)
			{
				return registAction((RegistrateAction)answer);
			}
			return answer;

		}
		else if(key == ITEM)
		{
			return myBody.change(action);
		}
		else if(key == WALLET)
		{
			return myBody.changeWallet(action);
		}
		Log.add("ERROR CHANGE ACTION IN CHARACTER");
		return null;
	}

	private Action rest() 
	{
		String name = "Rest";
		String text = "You are resting... How many hours did you have time to rest?\n"
				+ "Long rest - if 8 or more.\n"
				+ "Short rest - if more than 1.5 and less than 8.";
		Action[][] buttons = {{RegistrateAction.create("Long rest", Time.LONG),RegistrateAction.create("Short rest", Time.SHORT)},
				{BotAction.create("RETURN TO MENU", NO_ANSWER, true, false, null, null)}};
		return WrappAction.create(name, CHARACTER, text, buttons).replyButtons();
	}

	private Action rest(Time time) 
	{
		refresh(time);
		String name = "EndRest";
		if(time == Time.SHORT)
		{
			String text = "Everything that depended on a short rest is reset.\n"
					+ "You have "+lvl+" Hit Dice available to restore your health.";
			return WrappAction.create(name, NO_ANSWER, text, null);
		}
		else if(time.equals(Time.LONG))
		{ 

			String text = "You are fully rested and recovered!";
			return WrappAction.create(name, NO_ANSWER, text, null);
		}
		return null;
	}

	private PreRoll postRoll(PreRoll roll)
	{
		String text = "BED BED BED";
		if(roll.getAction() instanceof AttackAction)
		{
			AttackAction attack = (AttackAction) roll.getAction();
			if(attack.getAttack().isPermanentCrit())
			{
				text = "PERMANENT CRIT";
				roll.setCriticalHit(true);
				roll.setText(text);
				return roll;
			}
		}

		Formula formula = rolls.execute(roll.getAction());
		String status = roll.getStatus();
		if(status.equals("ADVENTURE"))
		{
			text = formula.execute(true);
		}
		else if(status.equals("DISADVENTURE"))
		{
			text = formula.execute(false);
		}
		else if(status.equals("BASIC"))
		{
			text = formula.execute();
		}
		else 
		{
			Log.add("ERROR STATUS PRE ROLL");
		}
		roll.setCriticalMiss(formula.isNatural1());
		roll.setCriticalHit(formula.isNatural20());
		roll.setText(text);
		return roll;
	}

	private Action getMemoirsAct() 
	{
		String name = "MEMOIRS";
		String text = "MY MEMOIRS\n";
		int i = 1;
		for(String string: myMemoirs)
		{
			text += i + ". " + string + "\n";
			i++;
		}

		return WrappAction.create(name, NO_ANSWER, text,  new Action[][]
				{{
					BotAction.create("RETURN TO MENU", NO_ANSWER, true, false, null, null)
				}}).replyButtons();
	}

	public void lvlUp(CharacterDnd character) 
	{
		myClass.setLvl(myClass.getLvl()+1);
	}

	public ClassDnd getClassDnd() 
	{
		return myClass;
	}

	public String getName() 
	{
		return name;
	}

	public RaceDnd getMyRace() 
	{
		return myRace;
	}

	public ClassDnd getMultiClass() 
	{
		return multiClass;
	}

	public void setMultiClass(ClassDnd multiClass) 
	{
		this.multiClass = multiClass;
	}

	public void setRaceDnd(RaceDnd raceDnd) 
	{
		this.myRace = raceDnd;
	}

	public void setClassDnd(ClassDnd classDnd) 
	{
		this.myClass = classDnd;
		this.lvl = new LVL(classDnd.getLvl());
	}

	public List<String> getMyMemoirs() 
	{
		return myMemoirs;
	}

	public void addMemoirs(String memoirs) 
	{
		myMemoirs.add(memoirs);
	}

	public String getNature() 
	{
		return nature;
	}

	public void setNature(String nature) 
	{
		this.nature = nature;
	}

	private void setProfisiency() 
	{
		int result = 0;

		if(lvl.lvl>16) 
		{
			result = 6;
		} 
		else if(lvl.lvl>12) 
		{
			result = 5;
		} 
		else if(lvl.lvl>8) 
		{
			result = 4;
		} 
		else if(lvl.lvl>4) 
		{
			result = 3;
		} 
		else
		{
			result = 2;
		}
		
		myWorkmanship.getPossessions().setProfisiency(result);
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}

	public void setMyStat(int str, int dex, int con, int intl, int wis, int cha) 
	{
		rolls.setStats(str, dex, con, intl, wis, cha); 
		setProfisiency();
	}

	public Rolls getRolls()
	{
		return rolls;
	}

	public Workmanship getWorkmanship() 
	{
		return myWorkmanship;
	}

	public static CharacterDnd create(String name)
	{
		return new CharacterDnd(name);
	}

	public List<String> getTimesBuffs() 
	{
		return timesBuffs;
	}

	public AttackMachine getAttackMachine() 
	{
		return attackMachine;
	}

	public boolean isFighting() 
	{
		return fighting;
	}

	public void setFighting(boolean fighting) 
	{
		this.fighting = fighting;
	}

	public Body getBody() 
	{
		return myBody;
	}

	public List<String> getPermanentBuffs() 
	{
		return permanentBuffs;
	}

	@Override
	public void refresh(Time time) 
	{
		myWorkmanship.refresh(time);
		hp.refresh(time);
	}	

	public String getMenu()
	{
		return "Menu Some information of hero";
	}

	public HP getHp() 
	{
		return hp;
	}

	public List<CloudAction> getCloud() 
	{
		return cloud;
	}

}
