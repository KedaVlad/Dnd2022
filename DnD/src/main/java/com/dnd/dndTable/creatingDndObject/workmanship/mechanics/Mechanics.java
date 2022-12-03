package com.dnd.dndTable.creatingDndObject.workmanship.mechanics;

import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.magicEffects.Effect;
import com.dnd.dndTable.rolls.Dice;

public class Mechanics extends Feature
{
	private static final long serialVersionUID = 7027811790314988798L;

	private boolean cellsCheck;
	private boolean diceCheck;
	private boolean poolCheck;
	private SimplePool<Feature> pool;
	private Matrix matrix;
	private Dice dice;
	
	public Mechanics(boolean cells, boolean dice, boolean pool)
	{
		this.setCellsCheck(cells);
		this.setDiceCheck(dice);
		this.setPoolCheck(pool);
	}
	
	
	
	public Effect cast()
	{
		if(isCellsCheck() == true)
		{
		
		}
		
		
		return this.getEffect();
	}



	public boolean isCellsCheck() {
		return cellsCheck;
	}



	public void setCellsCheck(boolean cellsCheck) {
		this.cellsCheck = cellsCheck;
	}



	public boolean isDiceCheck() {
		return diceCheck;
	}



	public void setDiceCheck(boolean diceCheck) {
		this.diceCheck = diceCheck;
	}



	public boolean isPoolCheck() {
		return poolCheck;
	}



	public void setPoolCheck(boolean poolCheck) {
		this.poolCheck = poolCheck;
	}



	public Dice getDice() {
		return dice;
	}



	public void setDice(Dice dice) {
		this.dice = dice;
	}
	
	
	
	

}
