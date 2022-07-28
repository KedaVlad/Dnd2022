package com.dnd.creatingCharacter.spells;

import java.util.HashMap;
import java.util.Map;

public class TestSpels {

	public static void main(String[] args) {
		
			
		
	Map<String,Spells> map = new HashMap<>();
	map.put(new  Prestidigitation().getName(), new  Prestidigitation());
	System.out.println(map.get("Prestidigitation"));
	
	
		

	}

}
