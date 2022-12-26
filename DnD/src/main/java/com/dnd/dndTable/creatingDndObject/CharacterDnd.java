package com.dnd.dndTable.creatingDndObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.AttackAction;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.botTable.actions.PreRoll;
import com.dnd.botTable.actions.RegistrateAction;
import com.dnd.botTable.actions.RollAction;
import com.dnd.botTable.actions.StartTreeAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.Rolls.MainStat;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.rolls.Article;
import com.dnd.dndTable.rolls.Formula;


public class CharacterDnd implements Serializable, Refreshable, DndKeyWallet
{

	private static final long serialVersionUID = -7781627593661723428L;

	private String name;
	private boolean fighting;
	private int lvl;
	private HP hp;
	private String nature;
	private int speed;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private Rolls rolls;
	private AttackMachine attackMachine;
	private Workmanship myWorkmanship;
	private СhoiceCloud cloud;
	private MagicSoul magicSoul;
	private Body myBody;
	private List<String> permanentBuffs;
	private List<String> timesBuffs;
	private List<String> myMemoirs;

	public Action act(Action action)
	{
		Log.add(action);
		Log.add("START ACT IN CHARACTER");
		long key = action.getKey();
		Action answer = null;

		if(action instanceof StartTreeAction)
		{
			return startTreeAction((StartTreeAction) action);
		}
		if(action instanceof RegistrateAction)
		{
			return registAction((RegistrateAction) action);
		}


		if(key == AttackMachine.key)
		{
			if(action instanceof PreRoll)
			{
				PreRoll roll = (PreRoll) action;
				if(roll.getAction() instanceof AttackAction)
				{
				answer = attackMachine.postAttack(preRoll(roll));
				}
				else
				{
					answer = preRoll(roll).rebuild();
				}
			}
			else if(action instanceof AttackAction)
			{
				return PreRoll.create((AttackAction) action);
			}
			else if(action instanceof RollAction)
			{
				return roll((RollAction) action);
			}
			else
			{
				return action;
			}
		}
		Log.add(action);
		return answer;
	}

	private Action startTreeAction(StartTreeAction action) {

		Log.add(action + "START TREE ACTION");
		long key = action.getKey();
		if(key == FEATURE)
		{
			return myWorkmanship.getFeatureMenu();
		}
		else if(key == POSSESSION)
		{
			return myWorkmanship.getPossessionMenu();
		}
		else if(key == SPELL)
		{
			return magicSoul.getSpellMenu();	
		}
		else if(key == BODY)
		{
			return myBody.getBodyMeny();
		}
		else if(key == ROLLS)
		{
			return rolls.getRollsMenu();
		}
		else if(key == REST)
		{
			return rest();
		}

		return null;	
	}
	
	private Action registAction(RegistrateAction action)
	{
		ObjectDnd object = action.getTarget();
		if(isFighting())
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
			else if(object instanceof Bag)
			{
				return myBody.getBagMeny((Bag) object);
			}
			else if(object instanceof Items)
			{
				return myBody.getItemMenu((Items) object);
			}
			else if(object instanceof MainStat)
			{
				return rolls.getStatCase((MainStat) object);
			}
			else if(object instanceof Article)
			{
				return rolls.getArticleCase((Article) object);
			}
		}
		return null;
	}

	private Action rest() 
	{
		String name = "Rest";
		String text = "You are resting... How many hours did you have time to rest?\n"
				+ "Long rest - if 8 or more.\n"
				+ "Short rest - if more than 1.5 and less than 8.";
		Action[][] buttons = {{RegistrateAction.create("Long rest", Time.LONG),RegistrateAction.create("Short rest", Time.SHORT)}};
		return HeroAction.create(name, CHARACTER, text, buttons);
	}

	private Action rest(Time time) 
	{
		refresh(time);
		String name = "EndRest";
		if(time == Time.SHORT)
		{
			String text = "Everything that depended on a short rest is reset.\n"
					+ "You have "+lvl+" Hit Dice available to restore your health.";
			HeroAction.create(name, CHARACTER, text, null);
		}
		else if(time == Time.LONG)
		{
			String text = "You are fully rested and recovered!";
			HeroAction.create(name, CHARACTER, text, null);
		}
		return null;
	}
	
	private PreRoll preRoll(PreRoll attack)
	{
		String text;
		if(attack.getAction() instanceof AttackAction)
		{
			AttackAction action = (AttackAction) attack.getAction();
			if(action.getAttack().isPermanentCrit())
			{
				text = "PERMANENT CRIT";
			}
		}
		else
		{
			Formula formula = rolls.execute(attack.getAction());
			int status = attack.getStatus();
			if(status == 1)
			{
				text = formula.execute(true);
			}
			else if(status == 3)
			{
				text = formula.execute(false);
			}
			else 
			{
				text = formula.execute();
			}
		attack.setCriticalMiss(formula.isNatural1());
		attack.setCriticalHit(formula.isNatural20());
		attack.setText(text);
		}
		return attack;
	}
	
	private HeroAction roll(RollAction action)
	{
		Formula formula = rolls.execute(action);
		return HeroAction.create("finish", 0, formula.execute(), null);
	}

	private CharacterDnd(String name) 
	{
		this.name = name;
		myWorkmanship = new Workmanship();
		myMemoirs = new ArrayList<>();
		myBody = new Body();
		cloud = new СhoiceCloud();
		rolls = new Rolls();
		hp = new HP();
		permanentBuffs = new ArrayList<>();
		timesBuffs = new ArrayList<>();


	}

	public void lvlUp(CharacterDnd character) 
	{
		myClass.setLvl(myClass.getLvl()+1);

	}

	public ClassDnd getClassDnd() 
	{
		return myClass;
	}

	public int getLvl() 
	{
		return lvl;
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
		setLvl();
	}

	public List<String> getMyMemoirs() 
	{
		return myMemoirs;
	}

	public void addMyMemoirs(String memoirs) 
	{
		myMemoirs.add(memoirs);
	}
	
	public void setLvl() 
	{
		if(multiClass != null)
		{
			lvl = myClass.getLvl() + multiClass.getLvl();
		}
		else
		{
			lvl = myClass.getLvl();
		}
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

		if(lvl>16) 
		{
			result = 5;
		} 
		else if(lvl>9) 
		{
			result = 4;
		} 
		else if(lvl>4) 
		{
			result = 3;
		} 
		else
		{
			result = 2;
		}
		if(result != rolls.getProficiency().getBuff())
		{
			rolls.getProficiency().setBuff(result);
		}

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

	public СhoiceCloud getCloud() 
	{
		return cloud;
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
		magicSoul.refresh(time);
		hp.refresh(time);
	}	

	public String getStatus()
	{
		return "Status";
	}

	public String getMenu()
	{
		return "Menu";
	}

	public HP getHp() 
	{
		return hp;
	}

	public MagicSoul getMagicSoul() 
	{
		return magicSoul;
	}

	public void setMagicSoul(MagicSoul magicSoul) 
	{
		this.magicSoul = magicSoul;
	}

	public Action getMemoirsAct() 
	{
		String name = "MEMOIRS";
		String text = "MEMOIRS\n";
		int i = 1;
		for(String string: myMemoirs)
		{
			text = i + ". " + string + "\n";
		}
		
		return HeroAction.create(name, NO_ANSWER, text, null);
	}
}
