package com.dnd.dndTable.creatingDndObject.classDnd;

import java.util.List;

public class Barbarian extends ClassDnd {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3283672555053391371L;





	public Barbarian(int lvl, String arhcetype) {
		super(lvl);
		// TODO Auto-generated constructor stub
	}


	private static final int hits = 12;

	

	

	public String toString() {
		return "Barbarian";
	}





	@Override
	public int getDiceHits() {
		// TODO Auto-generated method stub
		return 0;
	}





	@Override
	public List<String> getPermanentBuffs() {
		// TODO Auto-generated method stub
		return null;
	}
	



}
