package com.dnd.creatingCharacter.raceDnd;

public class GnomeForest extends Gnome {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GnomeForest(int age, int weight, int growth) {
		super(age,weight,growth);
		
		
	}
	
	public GnomeForest() {
		// TODO Auto-generated constructor stub
	}

	public int getAgl(int agl) {
		return agl+1;
	}
	public String toString() {
		return "Forest Gnome";
	}

}
