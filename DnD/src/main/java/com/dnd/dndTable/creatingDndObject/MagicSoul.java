package com.dnd.dndTable.creatingDndObject;

import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.WrappAction;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.modification.Matrix;
import com.dnd.dndTable.creatingDndObject.modification.pool.SimplePool;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("MAGIC_SOUL")
public class MagicSoul implements Refreshable, ObjectDnd{

	private Matrix cells;
	private SimplePool<Spell> poolCantrips;
	private Stat depends;
	private SimplePool<Spell> pool;
	private Spell targetSpell;

	private Time time;

	public MagicSoul duplicate()
	{
		 MagicSoul answer = new MagicSoul();
		 answer.cells = new Matrix();
		 answer.cells.setMatrix(this.cells.getMatrix());
		 answer.poolCantrips = new SimplePool<Spell>();
		 answer.poolCantrips.setActiveMaxSize( this.poolCantrips.getActiveMaxSize());
		 answer.pool = new SimplePool<Spell>();
		 answer.pool.setActiveMaxSize( this.poolCantrips.getActiveMaxSize());
		 answer.depends = this.depends;
		 answer.time = this.time;
		 return answer;
	}
	
	public Action cast(int target, int cell)
	{
		return null;
	}

	public Action cast(int target)
	{
		return null;
	}

	public SimplePool<Spell> getPool() 
	{
		return pool;
	}

	public void setPool(SimplePool<Spell> pool) 
	{
		this.pool = pool;
	}

	@Override
	public void refresh(Time time) 
	{
		if(this.getTime() == time)
		{
			getCells().refresh();
		}

	}

	
	
	public Action getSpellMenu() 
	{
		String name = "SPELLS";
		String text = "This is your spells. Choose some for more infotmation";
		Action[][] buttons = new Action[pool.getActive().size()][0];
		for(int i = 0; i < pool.getActive().size(); i++)
		{
			Spell spell = pool.getActive().get(i);
			buttons[i][0] = RegistrateAction.create(spell.getName(),spell);
		}
		return WrappAction.create(name, key(), text, buttons);
	}

	public Action spellCase(Spell object) 
	{
		String name = object.getName();
		String text = name + "\n" + object.getDescription();
		return WrappAction.create(name, key(), text, null);
	}

	@Override
	public long key() 
	{
		return MAGIC_SOUL;
	}

	public String info() {
		String answer = "Magic cells:";
		int lvlOfCell = 1;
		for(boolean[] cells: this.getCells().getMatrix())
		{
			answer += "\nlvl" + lvlOfCell + ": ";
			int active = 0;
			for(int i = 0; i < cells.length; i++)
			{
				if(cells[i] == false)
				{
					active = i;
					break;
				}
				else 
				{
					active = i+1;
				}
			}
			answer += active + "|" + cells.length;
			
		}
		
		return answer;
	}


	public Stat getDepends() {
		return depends;
	}

	public void setDepends(Stat depends) {
		this.depends = depends;
	}


	public SimplePool<Spell> getPoolCantrips() {
		return poolCantrips;
	}


	public void setPoolCantrips(SimplePool<Spell> poolCantrips) {
		this.poolCantrips = poolCantrips;
	}

	
	public Matrix getCells() {
		return cells;
	}

	public void setCells(Matrix cells) {
		this.cells = cells;
	}

	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}




}
