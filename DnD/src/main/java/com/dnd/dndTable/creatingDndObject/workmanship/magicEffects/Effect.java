package com.dnd.dndTable.creatingDndObject.workmanship.magicEffects;

import java.io.Serializable;


public abstract class Effect implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
