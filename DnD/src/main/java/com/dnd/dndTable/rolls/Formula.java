package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;

public class Formula implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name = "";
	private List<Dice> formula;

	private boolean natural20;
	private boolean natural1;

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

	
	public String execute(boolean advanture)
	{
		String answer = this.name;
		int first;
		boolean crit20;
		boolean crit1;
		
		this.name = "First roll\n";
		answer += this.execute() + "\n";
		first = this.summ();
		crit20 = this.natural20;
		crit1 = this.natural1;
		
		this.name = "Second roll\n";
		answer += this.execute() + "\n";
		
		if(advanture == true)
		{
			if(this.summ() > first)
			{
				answer += "Final result: " + this.summ();
			}
			else
			{
				answer += "Final result: " + first;
				this.natural1 = crit1;
				this.natural20 = crit20;
			}
		}
		else
		{
			if(this.summ() < first)
			{
				answer += "Final result: " + this.summ();
			}
			else
			{
				answer += "Final result: " + first;
				this.natural1 = crit1;
				this.natural20 = crit20;
			}
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
		if(getFormula().get(0).getResults()[0] == 20)
		{
			natural20 = true;
		}
		else if(getFormula().get(0).getResults()[0] == 1)
		{
			natural1 = true;
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