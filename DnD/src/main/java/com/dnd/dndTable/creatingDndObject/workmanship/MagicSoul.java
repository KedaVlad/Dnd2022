package com.dnd.dndTable.creatingDndObject.workmanship;

import com.dnd.Executor;
import com.dnd.botTable.actions.PoolActions;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.characteristic.Stat.Stats;
import com.dnd.dndTable.creatingDndObject.modification.Matrix;
import com.dnd.dndTable.creatingDndObject.modification.pool.SimplePool;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("MAGIC_SOUL")
public class MagicSoul implements Refreshable, Executor, ObjectDnd
{

	private static final long serialVersionUID = 1L;
	private Matrix cells;
	private SimplePool<Spell> poolCantrips;
	private Stats depends;
	private SimplePool<Spell> pool;
	private Spell targetSpell;

	private Time time;

	@Override
	public Act execute(Action action)
	{
		if(action.getObjectDnd() == null)
		{
			return spellMenu(); 
		}
		else
		{
			int condition = Executor.condition(action);
			switch(condition)
			{
			case 0:
				return featureCase(action);
			default:
				return Act.builder().returnTo(ABILITY_B, ABILITY_B).build();
			}
		}
	}
	
	

	private Act spellMenu() 
	{
		BaseAction[][] pool = new BaseAction[this.poolCantrips.getActive().size() + this.pool.getActive().size()][1];
		int i = 0;
		for(Spell spell: this.poolCantrips.getActive())
		{
			pool[i][0] = Action.builder().name(spell.getName()).key(key()).objectDnd(spell).build();
			i++;
		}
		for(Spell spell: this.pool.getActive())
		{
			pool[i][0] = Action.builder().name(spell.getName()).key(key()).objectDnd(spell).build();
			i++;
		}
		return Act.builder()
				.name(FEATURE_B)
				.text("This is your feature. Choose some for more infotmation")
				.action(PoolActions.builder()
						.actionsPool(pool)
						.build())
				.returnTo(ABILITY_B)
				.build();
	}

	private Act featureCase(Action action) 
	{
		Spell spell = (Spell) action.getObjectDnd();
		String name = spell.getName();
		String text = name + "\n" + spell.getDescription();
	
			action.setButtons(new String[][] {{"Cast"}});
			return Act.builder()
					.name(name)
					.text(text)
					.action(action)
					.build();
		
	}
	
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
	
	public Act cast(int target, int cell)
	{
		return null;
	}

	public Act cast(int target)
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

	@Override
	public long key() 
	{
		return ABILITY;
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


	public Stats getDepends() {
		return depends;
	}

	public void setDepends(Stats depends) {
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
