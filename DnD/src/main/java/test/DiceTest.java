package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.ClassDnd;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;



public class DiceTest {

	CharacterDnd character = new CharacterDnd("Bober");
	ClassDnd clazz = new ClassDnd();
	int a;
	int s;
	int d;
	int f;
	int g;
	int h;
	
	

	@Before
	public void setUp() throws Exception {
		a = 10;
		s = 10;
		f = 16;
		d = 10;
		g = 10;
		h = 10;
		clazz.setFirstHp(8);
		clazz.setLvl(2);
		clazz.setDiceHp(Roll.D8);
		character.setClassDnd(clazz);
		character.getRolls().setStats(s, d, f, d, s, a);
	}
	
	
	@Test
	public void testRandom() 
	{
		int answer = 8;
		Assert.assertEquals(answer, Dice.randomStartHp(character));
	}
	
	@Test
	public void testStable() {
		int answer = 19;
		Assert.assertEquals("8 + 3 + 5 + 3 must be equals 19", answer, Dice.stableStartHp(character));
	}
	

}
