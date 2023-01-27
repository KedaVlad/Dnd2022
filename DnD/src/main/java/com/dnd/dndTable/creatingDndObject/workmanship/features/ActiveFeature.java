package com.dnd.dndTable.creatingDndObject.workmanship.features;

import com.dnd.botTable.Act;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.Refreshable.Time;
import com.dnd.dndTable.creatingDndObject.workmanship.Caster;
import com.dnd.dndTable.creatingDndObject.workmanship.casts.Cast;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ACTIVE_FEATURE")
public class ActiveFeature extends Feature implements Refreshable
{

	private static final long serialVersionUID = 1L;
	private int charges;
	private int targetCells;
	private Time time;
	private Cast cast;
	@Override
	public void refresh(Time time) 
	{
		if(this.time.equals(Time.SHORT) || time.equals(Time.LONG))
		{
			targetCells = charges;
		}
	}
	
	public Act cast()
	{
		targetCells--;
		if(targetCells < 0)
		{
			targetCells = 0;
			return Act.builder().name("EndTree").text("You ran out of charges... You need " + time + " rest for use it again.").build();
		}
		return Caster.cast(cast);
	}
	
	
	public int getCharges() {
		return charges;
	}
	public void setCharges(int charges) {
		this.charges = charges;
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
	/**
	 * @return the cast
	 */
	public Cast getCast() {
		return cast;
	}
	/**
	 * @param cast the cast to set
	 */
	public void setCast(Cast cast) {
		this.cast = cast;
	}

	/**
	 * @return the targetCells
	 */
	public int getTargetCells() {
		return targetCells;
	}

	/**
	 * @param targetCells the targetCells to set
	 */
	public void setTargetCells(int targetCells) {
		this.targetCells = targetCells;
	}

}
