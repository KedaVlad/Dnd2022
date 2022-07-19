package creatingCharacter.classDnd;

public class Barbarian extends ClassDnd  {
	
	private static int diceHits = 12;

	
	public Barbarian(int lvl){
		super(lvl);
	}

	public int getDiceHits() {
		return diceHits;
	}
	
	public String toString() {
		return "Barbarian";
	}

	
	
}
