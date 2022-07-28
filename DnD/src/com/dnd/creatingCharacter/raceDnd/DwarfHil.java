package com.dnd.creatingCharacter.raceDnd;


public class DwarfHil extends Dwarf {

	public DwarfHil(int age, int weight, int growth) {
		super(age, weight, growth);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "Hil Dwarf";
	}
	public int getWis(int wis) {
		return wis+1;
	}


}
