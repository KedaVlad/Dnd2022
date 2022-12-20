package com.dnd.dndTable.creatingDndObject;

import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.magicEffects.Damage;
import com.dnd.dndTable.creatingDndObject.workmanship.magicEffects.Effect;
import com.dnd.dndTable.creatingDndObject.workmanship.mechanics.Matrix;
import com.dnd.dndTable.creatingDndObject.workmanship.mechanics.SimplePool;

public class MagicSoul implements Refreshable{

	private Matrix cells;
	private int sizeNoCellsSpells;
	private Stat depends;
	private boolean[] serializer = new boolean[3];
	private SimplePool<Spell> pool;

	private Time time;

	public Effect cast(int target, int cell)
	{
		cells.use(cell);
		Spell spell = pool.getActive().get(target);
		if(spell.getCast() instanceof Damage)
		{
		Damage damage = (Damage) spell.getCast();	
		damage.getAction().getStepOne().setDepends(depends);
		return damage;
		}
		
		return spell.getCast();
	}
	
	public Effect cast(int target)
	{
		Spell spell = pool.getActive().get(target);
		cells.use(spell.getLvlSpell());
		if(spell.getCast() instanceof Damage)
		{
		Damage damage = (Damage) spell.getCast();	
		damage.getAction().getStepOne().setDepends(depends);
		return damage;
		}
		
		return spell.getCast();
	}

	public SimplePool<Spell> getPool() {
		return pool;
	}

	public void setPool(SimplePool<Spell> pool) {
		this.pool = pool;
	}

	@Override
	public void refresh(Time time) {
		if(this.time == time)
		{
			cells.refresh();
		}
		
	}

	
	

}
