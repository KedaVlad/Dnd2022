package com.dnd.dndTable.rolls;

import com.dnd.Names.TypeDamage;
import com.dnd.dndTable.rolls.Dice.Roll;

public class DamageDice extends Dice
{
	private static final long serialVersionUID = 1L;
	private TypeDamage typeDamage;
	
	public DamageDice(String name, int buff, Roll[] combo, TypeDamage typeDamage) {
		super(name, buff, combo);
		
	}

	public TypeDamage getTypeDamage() {
		return typeDamage;
	}

	public void setTypeDamage(TypeDamage typeDamage) {
		this.typeDamage = typeDamage;
	}
	
	public String execute()
	{
		String answer = this.getName() + "(" + typeDamage.toString() + "): ";
		boolean start = true;
		for(Roll roll: this.getCombo())
		{
			if(start == true)
			{
				answer = "" + roll(roll);
				start = false;
			}
			else 
			{
				answer += " + " + roll(roll);
			}	
		}

		if(this.getBuff() < 0)
		{
			answer += " - " + this.getBuff();
		}
		else if(this.getBuff() > 0);
		{
			answer += " + " + this.getBuff();
		}


		return answer + " = " + roll();
	}

	

}
