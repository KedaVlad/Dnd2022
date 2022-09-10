package com.dnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import telegrambots.telegrambots.bots.DefaultAbsSender;

import com.dnd.creatingCharacter.CharacterDnd;
import com.dnd.creatingCharacter.classDnd.Rogue;

public class DndCharacterBot extends DefaultAbsSender {
	private List<CharacterDnd> myCharacters = new ArrayList<>();
	
	public void showMyCharacters() {
		if(myCharacters.isEmpty()) {
			System.out.println("You got no character.");
		} else {
			for(CharacterDnd character: myCharacters) {
				System.out.println(character.getName());
			}
		}
	}
	
	public static void createCharacter() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("What name?");
		CharacterDnd someCharacter = new CharacterDnd(scanner.nextLine());
		System.out.println("What Lvl?");
		
		
		
	}
	public void showClasses() {
		
	}
	

	public static void main(String[] args) {
		
		
	}

}
