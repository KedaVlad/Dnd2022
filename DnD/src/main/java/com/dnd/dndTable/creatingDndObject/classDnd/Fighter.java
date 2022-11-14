package com.dnd.dndTable.creatingDndObject.classDnd;

import java.util.List;

public class Fighter extends ClassDnd {

	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3225247974567573138L;

	public Fighter(int lvl, String arhcetype) {
		super(lvl);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "Fighter";
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
