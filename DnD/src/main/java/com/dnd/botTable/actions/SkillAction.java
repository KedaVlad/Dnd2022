package com.dnd.botTable.actions;

public class SkillAction extends AttackAction 
{
	private static final long serialVersionUID = 1L;
	private boolean competense;
	private boolean half1prof;
	
	public boolean isCompetense() {
		return competense;
	}
	public void setCompetense(boolean competense) {
		this.competense = competense;
	}
	public boolean isHalf1prof() {
		return half1prof;
	}
	public void setHalf1prof(boolean half1prof) {
		this.half1prof = half1prof;
	}
}
