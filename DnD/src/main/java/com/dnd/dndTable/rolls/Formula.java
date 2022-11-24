package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Formula implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name = "";
	private List<Dice> formula;

	public Formula(Dice... formula)
	{
		this.formula = new ArrayList<>();
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

	public String execute()
	{
		String answer = this.name;

		for(Dice dice: this.getFormula())
		{
			answer += dice.execute() + "\n";	
		}
		return answer + "Final result: " + roll();
	}

	public int roll()
	{
		int result = 0;
		for(Dice dice: this.getFormula())
		{
			result += dice.roll();
		}
		return result;
	}

	
	public List<Dice> getFormula() {
		return formula;
	}

}