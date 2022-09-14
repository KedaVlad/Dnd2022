package com.dnd.creatingCharacter.bagDnd;

import java.io.Serializable;

public abstract class Items implements Serializable{
	
	private static final long serialVersionUID = -1353539867889183740L;
	
	private int weigth;
	private int cost;
	private String name;
	
	

	public int getWeigth() {
		return weigth;
	}
	public int getCost() {
		return cost;
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return getName() + "\n cost "+getCost() + "\n weigth " + getWeigth();
	}
}
