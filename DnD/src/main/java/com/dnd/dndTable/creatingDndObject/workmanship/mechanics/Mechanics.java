package com.dnd.dndTable.creatingDndObject.workmanship.mechanics;

import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.rolls.AttackModification;
import com.dnd.dndTable.rolls.Dice;

public class Mechanics extends Feature
{
	private static final long serialVersionUID = 7027811790314988798L;

	private SimplePool<Feature> pool;
	private Matrix matrix;
	private Dice dice;
	private long key;
	private AttackModification[] modification;
	
	public static Mechanics build(Feature feature)
	{
		Mechanics answer = new Mechanics();
		answer.setName(feature.getName());
		answer.setDescription(feature.getDescription());
		return answer;
	}
	
	public Mechanics pool(SimplePool<Feature> pool)
	{
		this.pool = pool;
		return this;
	}
	
	public Mechanics modification(AttackModification... modification)
	{
		this.modification = modification;
		return this;
	}
	
	public Mechanics key(long key)
	{
		this.key = key;
		return this;
	}
	
	public Mechanics matrix(Matrix matrix)
	{
		this.matrix = matrix;
		return this;
	}
	
	public Mechanics dice(Dice dice)
	{
		this.dice = dice;
		return this;
	}
	
	
	public boolean isCellsCheck() 
	{
		return matrix != null;
	}


	public boolean isDiceCheck() 
	{
		return dice != null;
	}

	public boolean isPoolCheck() 
	{
		return pool != null;
	}

	public Dice getDice() 
	{
		return dice;
	}

	public void setDice(Dice dice) 
	{
		this.dice = dice;
	}

	public SimplePool<Feature> getPool() 
	{
		return pool;
	}

	public void setPool(SimplePool<Feature> pool) 
	{
		this.pool = pool;
	}

	public Matrix getMatrix() 
	{
		return matrix;
	}

	public void setMatrix(Matrix matrix) 
	{
		this.matrix = matrix;
	}

	public long getKey() {
		return key;
	}

}



