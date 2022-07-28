package com.dnd.creatingCharacter.raceDnd;

public class Dragonborn extends RaceDnd {

	public Dragonborn(int age, int weight, int growth) {
		super(age, weight, growth);
		if(age<1 || age >90) System.out.println("Your age dosen`t corect!");
		if(weight<65 || weight > 160) System.out.println("Your weigth dosen`t corect!");
		if(growth<4 || growth >8) System.out.println("Your growth dosen`t corect!");
	}

	public int getStr(int str) {
		return str+2;
	}
	public int getCha(int cha) {
		return cha+1;
	}
	public String toString() {
		return "Dragonborn";
	}

}
