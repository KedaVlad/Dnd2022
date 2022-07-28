package com.dnd.creatingCharacter.raceDnd;



public class HalflingStout extends Halfling {

	public HalflingStout(int age, int weight, int growth) {
		super(age, weight, growth);
	}
	public String toString() {
		return "Stout Halfling";
	}
	public int getDex(int dex) {
		return dex+1;
	}
}
