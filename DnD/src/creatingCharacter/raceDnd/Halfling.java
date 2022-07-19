package creatingCharacter.raceDnd;


public class Halfling extends RaceDnd {

	public Halfling(int age, int weight, int growth) {
		super(age, weight, growth);
		if(age<1 || age >150) System.out.println("Your age dosen`t corect!");
		if(weight<5 || weight > 100) System.out.println("Your weigth dosen`t corect!");
		if(growth<2 || growth >5) System.out.println("Your growth dosen`t corect!");
	}
	public int getSpeed() {
		return speed-5;
	}

	public int getAgl(int agl) {
		return agl+2;
	}
	public String toString() {
		return "Halfling";
	}
}
