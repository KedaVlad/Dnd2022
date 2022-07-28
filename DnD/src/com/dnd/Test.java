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
			
			
			CharacterDnd x = new CharacterDnd("Vovan",new GnomeForest(84,5,5), new Barbarian(12));
			x.setStats(13,15,13,14,15,15);
			x.setNature(0, 0);
			System.out.println(x.getClassDnd());
			x.getBag().getInsideBag().add(new Greatsword());
			x.getBag().whatInTheBag();
			x.showSkills();
			x.showStats();
			System.out.println("*********************************************************************");
			CharacterDnd y = new CharacterDnd("Andro");
			y.setNature(2, 2);
			y.getBag().whatInTheBag();
			System.out.println(y.getClassDnd());
			y.showSkills();
			y.showStats();
			
			x.castSomeSpell("Prestidigitation");
			
			

	}

}
