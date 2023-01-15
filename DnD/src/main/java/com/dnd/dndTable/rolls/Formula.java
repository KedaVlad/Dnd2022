package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.dndTable.rolls.Dice.Roll;

public class Formula implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name = "";
	private List<Dice> formula;

	private boolean natural20;
	private boolean natural1;

	private enum CritCheck { NONE, CRIT20, CRIT1 }
	
	public String toString()
	{
		String answer = "";
		for(Dice dice: formula)
		{
			for(Roll roll:dice.getCombo())
			{
			answer += roll.toString();	
			}
			answer += dice.getBuff() + " " + dice.getName() + " |||";
		}
		return answer;
	}
	
	public Formula(Dice first, Dice... formula)
	{
		this.formula = new ArrayList<>();
		this.getFormula().add(first);
		for(Dice dice: formula)
		{
			this.getFormula().add(dice);
		}
	}

	public Formula(List<Dice> formula)
	{
		this.formula = new ArrayList<>();
		this.getFormula().addAll(formula);

	}

	public Formula(String name, Dice... formula)
	{
		this.name = name + "\n";
		this.formula = new ArrayList<>();
		for(Dice dice: formula)
		{
			this.getFormula().add(dice);
		}
	}

	public Formula(String name, List<Dice> formula)
	{
		this.name = name + "\n";
		this.formula = new ArrayList<>();
		this.getFormula().addAll(formula);
	}

	private CritCheck critCheck(Dice dice)
	{
		if(dice.getCombo()[0].equals(Roll.D20))
		{
			if(dice.getResults()[0] == 20)
			{
				return CritCheck.CRIT20;
			}
			else if(dice.getResults()[0] == 1)
			{
				return CritCheck.CRIT1;
			}
			else
			{
				return CritCheck.NONE;
			}
		}
		else
		{
			return CritCheck.NONE;
		}
	}
	
	public String execute(boolean advanture)
	{
		String answer = this.name;
		int first;
		 CritCheck critFirst;
		 CritCheck critSecond;
		 CritCheck crit;
		
		this.name = "First roll\n\n";
		answer += this.execute() + "\n";
		first = this.summ();
		critFirst = critCheck(this.formula.get(0));
		
		this.name = "\nSecond roll\n\n";
		answer += this.execute() + "\n";
		critSecond = critCheck(this.formula.get(0));
		
		if(advanture == true)
		{
			if(this.summ() > first)
			{
				answer += "\nFinal result: " + this.summ();
				crit = critSecond;
			}
			else
			{
				answer += "\nFinal result: " + first;
				crit = critFirst;
			}
		}
		else
		{
			if(this.summ() < first)
			{
				answer += "\nFinal result: " + this.summ();
				crit = critSecond;
			}
			else
			{
				answer += "\nFinal result: " + first;
				crit = critFirst;
			}
		}
		if(crit.equals(CritCheck.CRIT1))
		{
			this.natural1 = true;
			answer += " !CRITICAL 1!";
		}
		else if(crit.equals(CritCheck.CRIT20))
		{
			this.natural20 = true;
			answer += " !NATURAL 20!";
		}
		return answer;
	}


	public String execute()
	{
		String answer = name;

		for(Dice dice: getFormula())
		{
			answer += dice.execute() + "\n";	
		}
		answer += "Result: " + summ();
		if(getFormula().get(0).getCombo()!= null&& getFormula().get(0).getCombo()[0].equals(Roll.D20))
		{
		if(critCheck(getFormula().get(0)).equals(CritCheck.CRIT20))
		{
			this.natural20 = true;
			answer += " !NATURAL 20!";
		}
		else if(critCheck(getFormula().get(0)).equals(CritCheck.CRIT1))
		{
			this.natural1 = true;
			answer += " !CRITICAL 1!";
		}
		}
		return answer;
	}

	public int summ()
	{
		int result = 0;
		for(Dice dice: getFormula())
		{
			result += dice.summ();
		}
		return result;
	}


	public List<Dice> getFormula() {
		return formula;
	}

	public boolean isNatural20() {
		return natural20;
	}

	public boolean isNatural1() {
		return natural1;
	}

}