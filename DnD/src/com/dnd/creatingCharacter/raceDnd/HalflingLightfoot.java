package com.dnd.creatingCharacter.raceDnd;


public class HalflingLightfoot extends Halfling {

	public HalflingLightfoot(int age, int weight, int growth) {
		super(age, weight, growth);
		// TODO Auto-generated constructor stub
	}
	public int getCha(int cha) {
		return cha+1;
	}
	public String toString() {
		return "Lightfoot Halfling";
	}
}
