package creatingCharacter.raceDnd;

public class GnomeRock extends Gnome {

	public GnomeRock(int age, int weight, int growth) {
		super(age, weight, growth);
	}
		
	public int getDex(int agl) {
		return agl+1;
	}
	public String toString() {
		return "Rocky Gnome";
	}
}
