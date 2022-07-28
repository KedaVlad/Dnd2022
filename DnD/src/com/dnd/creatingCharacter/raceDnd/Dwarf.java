package com.dnd.creatingCharacter.raceDnd;


public class Dwarf extends RaceDnd {

	public Dwarf(int age, int weight, int growth) {
		super(age,weight,growth);
		if(age<1 || age >350) System.out.println("Your age dosen`t corect!");
		if(weight<35 || weight > 135) System.out.println("Your weigth dosen`t corect!");
		if(growth<4 || growth >5) System.out.println("Your growth dosen`t corect!");
	}
	public int getSpeed() {
		return speed-5;
	}
	public boolean getDarkVision() {
		return true;
	}
	public int getDex(int dex) {
		return dex+2;
	}
	public String toString() {
		return "Dwarf";
	}

}
