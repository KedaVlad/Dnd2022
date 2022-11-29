package com.dnd.dndTable.creatingDndObject.workmanship.mechanics;

import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;

public class MagicSoul {

	private Matrix cells;
	private int sizeNoCellsSpells;
	private int sizeSpells;
	private Stat depends;
	private boolean[] serializer = new boolean[3];

	private List<List<Spell>> spellActivePool;

	public String add(Spell spell)
	{
		if(spellActivePool.get(0).size() <= sizeSpells)
		{
			spellActivePool.get(0).add(spell);
			return "added";
		}
		else if(spellActivePool.get(1) != null)
		{
			spellActivePool.get(1).add(spell);
			return "added to your book but uour hend hes no free space";
		}
		else
		{
			return "no free space";
		}
	}
	
	public void remove(int target)
	{
		if(spellActivePool.get(1) != null)
		{
			spellActivePool.get(0).add(spellActivePool.get(0).get(target));
		}
		spellActivePool.get(0).remove(target);
	}

	public Spell cast(int target, int cell)
	{
		
	}
	
	public Spell cast()

}
