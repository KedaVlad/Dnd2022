package creatingCharacter.raceDnd;


public class Gnome extends RaceDnd {
	

	public Gnome(int age, int weight, int growth) {
		super(age,weight,growth);
		if(age<1 || age >500) System.out.println("Your age dosen`t corect!");
		if(weight<5 || weight > 100) System.out.println("Your weigth dosen`t corect!");
		if(growth<3 || growth >5) System.out.println("Your growth dosen`t corect!");
	}
	public int getSpeed() {
		return speed-5;
	}
	public boolean getDarkVision() {
		return true;
	}

	public int getIntl(int intl) {
		return intl+2;
	}
	public String toString() {
		return "Gnome";
	}

}