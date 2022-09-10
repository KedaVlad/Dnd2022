package com.dnd.creatingCharacter.bagDnd;

public abstract class Items{
	
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
