package com.dnd.dndTable.creatingDndObject;


import java.util.ArrayList;
import java.util.List;

import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.botTable.actions.PoolActions;
import com.dnd.botTable.actions.PreRoll;
import com.dnd.botTable.actions.RollAction;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.bagDnd.Body;
import com.dnd.dndTable.creatingDndObject.characteristic.CharactersStat;
import com.dnd.dndTable.creatingDndObject.workmanship.Workmanship;
import com.dnd.dndTable.rolls.Formula;

public class CharacterDnd implements Refreshable, Executor
{

	private static final long serialVersionUID = -7781627593661723428L;

	private String name;
	private LVL lvl;
	private int speed = 25;
	private String nature;
	private HP hp;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private CharactersStat rolls;
	private AttackMachine attackMachine;
	private Workmanship myWorkmanship;
	private Body myBody;
	private List<Act> cloud;
	private List<String> myMemoirs;

	private CharacterDnd(String name) 
	{
		this.name = name;
		this.myWorkmanship = new Workmanship();
		this.rolls = new CharactersStat(myWorkmanship.getPossessions());
		this.attackMachine = new AttackMachine(myWorkmanship.getPossessions());
		this.myMemoirs = new ArrayList<>();
		this.cloud = new ArrayList<>();
		this.myBody = new Body();
		this.myBody.getCarrying().setStats(rolls.getStats());
		this.hp = new HP();
	}

	public Act executeAction(BaseAction action)
	{
		if(action instanceof Action)
		{
			Action target = (Action) action;
			long key = action.getKey();
			if(key == myWorkmanship.key())
			{
				return myWorkmanship.execute(target);
			}
			else if(key == rolls.key())
			{
				return rolls.execute(target);
			}
			else if(key == myBody.key())
			{
				return myBody.execute(target);
			}
			else if(key == attackMachine.key())
			{
				return attackMachine.execute(target);
			}
			else
			{
				return this.execute(target);
			}
		}
		else if(action instanceof PreRoll)
		{
			PreRoll target = (PreRoll) action;
			if(action.getKey() == attackMachine.key())
			{
				return attackMachine.execute(postRoll(target));
			}
			else
			{
				return Act.builder()
						.name("EndRoll")
						.text(postRoll(target).getAnswers()[0])
						.build();
			}
		}
		else if(action instanceof RollAction)
		{
			return Act.builder()
					.name("EndRoll")
					.text(rolls.execute((RollAction) action).execute())
					.build();
		}
		else
		{
			return null;
		}
	}

	@Override
	public Act execute(Action action) 
	{
		long key = action.getKey();
		if(key == MEMOIRS)
		{
			return getMemoirsAct();
		}
		else if(key == DEBUFF)
		{
			return debuff();
		}
		else if(key == REST)
		{
			if(action.getObjectDnd() != null && action.getObjectDnd() instanceof Time)
			{
				return rest((Time)action.getObjectDnd());
			}
			return startRest();
		}
		return null;	
	}

	private Act debuff() 
	{
		return Act.builder()
				.name("Debuff")
				.text("(Write) What is effect on you? After it will end ELIMINATE this...")
				.returnTo("Menu")
				.action(Action.builder()
						.key(DEBUFF)
						.mediator()
						.replyButtons()
						.location(Location.BOT)
						.buttons(new String[][] {{"RETURN TO MENU"}})
						.build())
				.build();
	}

	private Act startRest() 
	{
		return Act.builder()
				.name(name)
				.returnTo("Menu")
				.action(PoolActions.builder()
						.replyButtons()
						.actionsPool(new BaseAction[][] 
								{{Action.builder()
									.name("Long rest")
									.key(REST)
									.objectDnd(Time.LONG)
									.location(Location.CHARACTER)
									.build(),
									Action.builder()
									.name("Short rest")
									.key(REST)
									.objectDnd(Time.SHORT)
									.location(Location.CHARACTER)
									.build()},
							{Action.builder()
										.name("RETURN TO MENU")
										.key(NO_ANSWER)
										.location(Location.BOT)
										.build()}})
						.build())
				.text("You are resting... How many hours did you have time to rest?\n"
						+ "Long rest - if 8 or more.\n"
						+ "Short rest - if more than 1.5 and less than 8.")
				.build();
	}

	private Act rest(Time time) 
	{
		refresh(time);
		if(time == Time.SHORT)
		{
			return Act.builder()
					.name("EndRest")
					.text("Everything that depended on a short rest is reset.\n"
							+ "You have "+ getLvl() +" Hit Dice available to restore your health.")
					.build();
		}
		else if(time.equals(Time.LONG))
		{ 
			return Act.builder()
					.name("EndRest")
					.text("You are fully rested and recovered!")
					.build();
		}
		return null;
	}

	private Action postRoll(PreRoll roll)
	{
		String text = "BED BED BED";
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
		
		Action answer = Action.builder().build();
		if(formula.isNatural1())
		{
			answer.setAnswers(new String[]{text, DELETE_B});
		}
		else if(formula.isNatural20())
		{
			answer.setAnswers(new String[]{text, CREATE_B});
		}
		else
		{
			answer.setAnswers(new String[]{text});
		}
		return answer;
	}

	private Act getMemoirsAct() 
	{
		String name = "MEMOIRS";
		String text = "MY MEMOIRS\n";
		int i = 1;
		for(String string: myMemoirs)
		{
			text += i + ". " + string + "\n";
			i++;
		}

		return Act.builder()
				.name(name)
				.text(text)
				.returnTo("Menu")
				.action(Action.builder()
						.buttons(new String[][]{{"RETURN TO MENU"}})
						.replyButtons()
						.build())
				.build();
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

		if(getLvl().lvl>16) 
		{
			result = 6;
		} 
		else if(getLvl().lvl>12) 
		{
			result = 5;
		} 
		else if(getLvl().lvl>8) 
		{
			result = 4;
		} 
		else if(getLvl().lvl>4) 
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

	public CharactersStat getRolls()
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

	public AttackMachine getAttackMachine() 
	{
		return attackMachine;
	}

	public Body getBody() 
	{
		return myBody;
	}
	
	@Override
	public void refresh(Time time) 
	{
		myWorkmanship.refresh(time);
		hp.refresh(time);
	}	

	public HP getHp() 
	{
		return hp;
	}

	public List<Act> getCloud() 
	{
		return cloud;
	}


	public LVL getLvl() 
	{
		return lvl;
	}

	@Override
	public long key() 
	{
		return CHARACTER;
	}

	public String info() 
	{
		String answer = name + "\n\n";
		answer += hp.info();
		answer += lvl.info();
		answer += myBody.info();
		answer += myWorkmanship.info();
		return answer;
	}
}
