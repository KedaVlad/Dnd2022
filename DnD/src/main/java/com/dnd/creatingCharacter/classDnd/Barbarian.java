package com.dnd.creatingCharacter.classDnd;

public class Barbarian extends ClassDnd {

	public Barbarian(int lvl, String arhcetype) {
		super(lvl, arhcetype);
		// TODO Auto-generated constructor stub
	}

	

	private static final int diceHits = d12;

	private static final int hits = 12;

	

	public int getDiceHits() {
		return diceHits;
	}

	public String toString() {
		return "Barbarian";
	}
	



}
