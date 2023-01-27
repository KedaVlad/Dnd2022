package com.dnd.dndTable.creatingDndObject.workmanship;

import java.util.ArrayList;
import java.util.List;

import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class Possessions implements Executor
{
	private static final long serialVersionUID = 1L;
	private Dice proficiency;	
	private List<Possession> possessions;
	
	public void setProfisiency(int value)
	{
		this.proficiency.setBuff(value);
	}
	
	public List<Possession> getPossessions() 
	{
		return possessions;
	}

	public Possessions()
	{
		possessions = new ArrayList<>();
		proficiency = new Dice("Proficiency", 2, Roll.NO_ROLL);
	}
	
	public Dice getDice(Proficiency prof)
	{
		Dice answer = new Dice("Proficiency", 0, Roll.NO_ROLL);
		switch(prof)
		{
		case HALF:
			answer.setBuff(proficiency.getBuff()/2);
			break;
		case BASE:
			answer.setBuff(proficiency.getBuff());
			break;
		case COMPETENSE:
			answer.setBuff(proficiency.getBuff()*2);
			break;
		}
		return answer;
	}
	
	public Proficiency getProf(String name)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().contains(name))
			{
				return target.getProf();
			}
		}
		return null;
	}

	public void add(Possession possession)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().contains(possession.getName()))
			{
				target.setProf(upOrStay(target.getProf(), possession.getProf()));
				return;
			}
		}
		getPossessions().add(possession);
	}
	
	private Proficiency upOrStay(Proficiency first, Proficiency second)
	{
		if(second.equals(Proficiency.COMPETENSE))
		{
			return Proficiency.COMPETENSE;
		}
		else if(first.equals(Proficiency.BASE))
		{
			return Proficiency.BASE;
		}
		else
		{
			return second;
		}
	}
	
	public void add(String name)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().equalsIgnoreCase(name))
			{
				return;
			}
		}
		getPossessions().add(new Possession(name));
	}
	
	public boolean holded(String name)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().contains(name))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Act execute(Action action)
	{
		int condition = Executor.condition(action);
		switch(condition)
		{
		case 1:
			return possessionMenu(action);
		case 2:
			return addPossession(action);
		case 3:
			return answerForPossessionCall(action);
			default:
				return Act.builder().returnTo(ABILITY_B, ABILITY_B).build();
		}
	}

	private Act possessionMenu(Action action) 
	{
		action.setButtons(new String[][]{{"Add possession"}});
		return Act.builder()
				.name(POSSESSION_B)
				.text(info())
				.action(action)
				.returnTo(ABILITY_B)
				.build();
	}

	private Act addPossession(Action action)
	{
		String text = "If you want to add possession of some Skill/Save roll from characteristics - you can do it right by using this pattern (CHARACTERISTIC > SKILLS > Up to proficiency)\n"
				+ "If it`s possession of Weapon/Armor you shood to write ritht the type(correct spelling in Hint list)\n"
				+ "But if it concerns something else(language or metier) write as you like.";
		action.setButtons( new String[][] {{"Hint list"},{"Return to abylity"}});
		action.setMediator();
		action.setReplyButtons();
		return Act.builder()
				.name("addPossession")
				.text(text)
				.action(action)
				.build();
	}

	private Act answerForPossessionCall(Action action)
	{
		if(action.getAnswers()[2].equals("Return to abylity"))
		{
			return Act.builder().returnTo(ABILITY_B, ABILITY_B).build();
		}
		else if(action.getAnswers()[2].equals("Hint list"))
		{
			return Act.builder().name("Hint list").text(Possession.hintList()).build();
		}
		else
		{
			add(action.getAnswers()[2]);
			return Act.builder().returnTo(ABILITY_B, POSSESSION_B).build();
		}
	}

	@Override
	public long key() 
	{
		return ABILITY;
	}


	public String info() {
		
		String text = "This is your possessions. \n";
		for(Possession possession: possessions)
		{
			text += possession.toString() + "\n";
		}
		return  text;
	}

	
}
