package com.dnd.factory;

import com.dnd.creatingCharacter.classDnd.ClassDnd;
import com.dnd.creatingCharacter.classDnd.ClassSource;

public class ClassFactory implements Factory,ClassSource {

	public static ClassSource create(String classDnd, int lvl, String archetype) {
		
		return new Rogue(lvl, archetype);
	

	@Override
	public void whatICanTake() {
		// TODO Auto-generated method stub
		
	}

}
