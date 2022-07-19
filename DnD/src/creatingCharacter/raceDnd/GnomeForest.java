package creatingCharacter.raceDnd;

public class GnomeForest extends Gnome {
	
	public GnomeForest(int age, int weight, int growth) {
		super(age,weight,growth);
		
	}
	
	public int getAgl(int agl) {
		return agl+1;
	}
	public String toString() {
		return "Forest Gnome";
	}

}
