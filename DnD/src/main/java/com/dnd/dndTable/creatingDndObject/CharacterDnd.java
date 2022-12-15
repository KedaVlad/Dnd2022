package com.dnd.dndTable.creatingDndObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Body;
import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.AttackAction;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.botTable.actions.PreAttack;
import com.dnd.botTable.actions.RollAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.dndTable.rolls.Formula;
import com.dnd.dndTable.rolls.Rolls;


public class CharacterDnd implements Serializable, ActionObject
{

	private static final long serialVersionUID = -7781627593661723428L;

	private String name;
	private boolean fighting;
	private int lvl;
	private int hp;
	private String nature;
	private int speed;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private Rolls rolls;
	private AttackMachine attackMachine;
	private Workmanship myWorkmanship;
	private СhoiceCloud cloud;
	private Body body;
	private List<String> permanentBuffs;
	private List<String> timesBuffs;
	private List<String> myMemoirs;

	public Action registAction(ActionObject object)
	{
		if(isFighting())
		{
			if(object instanceof Weapon)
			{
				Weapon weapon = (Weapon) object;
				return attackMachine.startAction(weapon);
				
			}
		}

		return null;
	}

	private PreAttack preAttack(PreAttack attack)
	{
		Formula formula = rolls.execute(attack.getAction());
		int status = attack.getStatus();
		String text;
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
		return attack;
	}
	
	private HeroAction roll(RollAction action)
	{
		Formula formula = rolls.execute(action);
		return HeroAction.create("finish", 0, formula.execute(), null);
	}
	
	public Action act(HeroAction action)
	{
		long key = action.getKey();
		Action answer = null;
		
		if(key == AttackMachine.key)
		{
			if(action instanceof PreAttack)
			{
				answer = attackMachine.postAttack(preAttack((PreAttack) action));
			}
			else if(action instanceof AttackAction)
			{
				return PreAttack.create((AttackAction) action);
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
		
		if(action instanceof AttackAction)
		{
			
		
		}
		else if(action instanceof RollAction)
		{
			
		}
		else
		{
			
		}
		
		return answer;
	}







	public CharacterDnd(String name) 
	{
		this.name = name;
		myWorkmanship = new Workmanship();
		myMemoirs = new ArrayList<>();	
		cloud = new СhoiceCloud();
		rolls = new Rolls();
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

	public String getMyMemoirs() 
	{

		String answer = "";
		for(String string: myMemoirs)
		{
			answer += string + "\n";
		}

		return answer;
	}

	public void setMyMemoirs(String memoirs) 
	{

		myMemoirs.add(memoirs);
	}

	public String getMenu() 
	{

		return name;
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

	public int getHp() 
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
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

	public List<String> getTimesBuffs() {
		return timesBuffs;
	}

	public AttackMachine getAttackMachine() {
		return attackMachine;
	}

	public void setAttackMachine(AttackMachine attackMachine) {
		this.attackMachine = attackMachine;
	}

	public boolean isFighting() {
		return fighting;
	}

	public void setFighting(boolean fighting) {
		this.fighting = fighting;
	}

	@Override
	public String objectKey() 
	{
		// TODO Auto-generated method stub
		return null;
	}	

}
