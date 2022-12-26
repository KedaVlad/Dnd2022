package com.dnd.dndTable.creatingDndObject;

import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.botTable.actions.RegistrateAction;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.modification.Matrix;
import com.dnd.dndTable.creatingDndObject.modification.pool.SimplePool;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;

public class MagicSoul implements Refreshable, ObjectDnd{

	private Matrix cells;
	private int sizeNoCellsSpells;
	private Stat depends;
	private boolean[] serializer = new boolean[3];
	private SimplePool<Spell> pool;

	private Time time;

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
		if(this.time == time)
		{
			cells.refresh();
		}
		
	}

	public Action getSpellMenu() 
	{
		String name = "SpellMenu";
		String text = "This is your spells. Choose some for more infotmation";
		Action[][] buttons = new Action[pool.getActive().size()][0];
		for(int i = 0; i < pool.getActive().size(); i++)
		{
			Spell spell = pool.getActive().get(i);
			buttons[i][0] = RegistrateAction.create(spell.getName(),spell);
		}
		return HeroAction.create(name, key(), text, buttons);
	}
	
	public Action spellCase(Feature object) 
	{
		String name = object.getName();
		String text = name + "\n" + object.getDescription();
		return HeroAction.create(name, key(), text, null);
	}

	@Override
	public long key() 
	{
		return MAGIC_SOUL;
	}

	
	

}
