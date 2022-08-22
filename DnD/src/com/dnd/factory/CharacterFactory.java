package com.dnd.factory;


import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import com.dnd.creatingCharacter.CharacterDnd;
import com.dnd.creatingCharacter.classDnd.ClassDnd;
import com.dnd.creatingCharacter.raceDnd.RaceDnd;
import com.dnd.creatingCharacter.skills.SkillsSource;

public class CharacterFactory implements Factory,SkillsSource{
	
	private Map<String, CharacterDnd> readyCharacters = new LinkedHashMap<>();

	
	public CharacterDnd create(String name, RaceDnd raceDnd, ClassDnd classDnd) {
		
		if(!readyCharacters.containsKey(name)) {
		readyCharacters.put(name, new CharacterDnd(name,raceDnd,classDnd));
		readyCharacters.get(name).setFilePool(new File(skillsSource,possessionSourse,spellsSource,));
		} 
		return readyCharacters.get(name);
	}

	@Override
	public void whatICanTake() {
		// TODO Auto-generated method stub
		
	}

}
