package com.dnd.creatingCharacter.raceDnd;


public class DwarfHil extends Dwarf {
	
	private String[] skillsRace = {"Dwarven endurance","Dark Vision","Dwarven Resilience","Stone Knowledge"};

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

	public String[] getSkillsRace() {
		return skillsRace;
	}


}
