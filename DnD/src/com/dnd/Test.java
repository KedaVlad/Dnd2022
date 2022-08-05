package com.dnd;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.dnd.creatingCharacter.CharacterDnd;
import com.dnd.creatingCharacter.bagDnd.*;
import com.dnd.creatingCharacter.classDnd.*;
import com.dnd.creatingCharacter.raceDnd.*;
import com.dnd.creatingCharacter.spells.*;


public class Test {

		public static void main(String[] args) throws IOException, ClassNotFoundException,FileNotFoundException {
			

			Rogue rogue = new Rogue(1);
			System.out.println(rogue.getMyArchetypeClass());
		
			DwarfHil dwarf = new DwarfHil(73,44,4);
			
			CharacterDnd some = new CharacterDnd("billy",20);
			some.setRaceDnd(dwarf);
			some.setClassDnd(rogue);
			some.showSkills();
			
			System.out.println("***************************Character 2 ***************************");
			
			Rogue rogue2 = new Rogue(2);
			System.out.println(rogue.getMyArchetypeClass());
		
			DwarfHil dwarf2 = new DwarfHil(73,44,4);
			
			CharacterDnd some2 = new CharacterDnd("billy",20);
			some2.setRaceDnd(dwarf2);
			some2.setClassDnd(rogue2);
			some2.showSkills();
			
		
			System.out.println("***************************spells ***************************");
			some2.setSomeSpell(new MageHand());
			some2.showSpells();
		

	}

}

