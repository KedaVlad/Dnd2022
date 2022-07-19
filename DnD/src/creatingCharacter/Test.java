package creatingCharacter;

import java.io.FileNotFoundException;
import java.io.IOException;

import creatingCharacter.bagDnd.*;
import creatingCharacter.classDnd.*;
import creatingCharacter.raceDnd.*;

public class Test {
	

	public static void main(String[] args) throws IOException, ClassNotFoundException,FileNotFoundException {
		
		
		Character x = new Character("Vovan",new GnomeForest(75,7,4), new Barbarian(12));
		x.setStats(13,15,13,14,15,15);
		x.setNature(0, 0);
		x.showStats();
		
		
		System.out.println(x.getHp());
		System.out.println(x.getProfisiency());
		System.out.println(x.getHp());
		
		
		x.bag.whatInTheBag();
		x.bag.insideBag.add(new Greatsword());
		x.bag.whatInTheBag();
		
		
			
			
			
			
			}
	}


	

