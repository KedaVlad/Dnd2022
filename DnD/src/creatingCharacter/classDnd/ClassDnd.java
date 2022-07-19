package creatingCharacter.classDnd;

import java.io.Serializable;

public class ClassDnd implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 13L;
	private int DiceHits;
	private int lvl;
	
	public ClassDnd(int lvl){
		if(lvl >20) {
			System.out.println("Your lvl can`t be aboe lvl 20");
		}
		this.lvl=lvl;
		
	}

	public int getDiceHits() {
		return DiceHits;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		if(lvl >20) {
			System.out.println("Your lvl can`t be aboe lvl 20");
		}
		this.lvl = lvl;
	}
	
	
}
