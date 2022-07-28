package com.dnd.creatingCharacter.raceDnd;

public class DwarfMountain extends Dwarf {

	public DwarfMountain(int age, int weight, int growth) {
		super(age, weight, growth);
		// TODO Auto-generated constructor stub
	}
	public int getStr(int str) {
		return str+1;
	}
	
	public String toString() {
		return "Mountain Dwarf";
	}

}
