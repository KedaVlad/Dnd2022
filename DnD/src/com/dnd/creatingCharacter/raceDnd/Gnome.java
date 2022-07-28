package com.dnd.creatingCharacter.raceDnd;


public class Gnome extends RaceDnd {
	
	
	private static final long serialVersionUID = 1L;

	public Gnome(int age, int weight, int growth) {
		super(age,weight,growth);
		if(age<1 || age >500) System.out.println("Your age dosen`t corect!");
		if(weight<5 || weight > 100) System.out.println("Your weigth dosen`t corect!");
		if(growth<3 || growth >5) System.out.println("Your growth dosen`t corect!");
		
	}
	
	public Gnome() {
		// TODO Auto-generated constructor stub
	}

	public int getSpeed() {
		return speed-5;
	}
	

	public int getIntl(int intl) {
		return intl+2;
	}
	public String toString() {
	return "Gnome";
	}
	public void setSkillsRace() {
		getSkillsRace().add("Gnome cunning");
		getSkillsRace().add("Darck vision");

}
	/*public String[] getRaceSkill() {
		return raceSkill;
	}*/
	

}