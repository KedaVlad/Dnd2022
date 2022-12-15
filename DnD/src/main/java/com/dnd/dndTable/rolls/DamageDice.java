package com.dnd.dndTable.rolls;

import com.dnd.Names.TypeDamage;
import com.dnd.dndTable.rolls.Dice.Roll;

public class DamageDice extends Dice
{
	private static final long serialVersionUID = 1L;
	private TypeDamage typeDamage;
	
	public DamageDice(String name, int buff, TypeDamage typeDamage, Roll... combo) {
		super(name, buff, combo);
		this.typeDamage = typeDamage;
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
		for(int i = 0; i < this.getCombo().size(); i++)
		{
				this.getResults()[i] = roll(this.getCombo().get(i));				
		}

		this.getResults()[this.getResults().length-1] = this.getBuff();
		 answer += summ() + "(";
		 
		 boolean start = true;
		 for(int i = 0; i < getResults().length; i++)
			{
				int target = getResults()[i];
				if(start && (target != 0))
				{
					answer += "" + target;
					start = false;
				}
				else if(target < 0)
				{
					answer += " - " + target*-1;
				}
				else if(target > 0)
				{
					answer += " + " + target;
				}
				else
				{
					continue;
				}
				
			}
		 
		 return answer + ")";
		 
	}

	

}
