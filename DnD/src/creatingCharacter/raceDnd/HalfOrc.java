package creatingCharacter.raceDnd;


public class HalfOrc extends RaceDnd {

	public HalfOrc(int age, int weight, int growth) {
		super(age, weight, growth);
	
		if(age<1 || age >80) System.out.println("Your age dosen`t corect!");
		if(weight<55 || weight > 160) System.out.println("Your weigth dosen`t corect!");
		if(growth<4 || growth >7) System.out.println("Your growth dosen`t corect!");
	}
	public boolean getDarkVision() {
		return true;
	}
	public int getStr(int str) {
		return str+2;
	}
	public int getDex(int dex) {
		return dex+1;
	}
	public String toString() {
		return "Half-Orc";
	}

}
