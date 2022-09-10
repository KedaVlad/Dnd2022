package com.dnd;

import java.util.HashMap;
import java.util.Map;

import com.dnd.creatingCharacter.CharacterDnd;
import com.dnd.factory.CharacterFactory;

public class Test {
	private Map<String, String> map = new HashMap<>();
	public static void main(String[] args) {


		Test test = new Test();
		test.getMap().put("Some key", "Some info");
		
		
		System.out.println(test.getMap().get("Some key"));
		
			
		
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
