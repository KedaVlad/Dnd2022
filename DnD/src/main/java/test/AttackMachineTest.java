package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dnd.botTable.actions.BotAction;
import com.dnd.dndTable.creatingDndObject.AttackMachine;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.Weapons;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;


public class AttackMachineTest {

	AttackMachine machine;
	List<AttackModification> target;
	List<AttackModification> need;
	Weapon weapon;
	AttackModification attack;
	@Before
	public void setUp() throws Exception {
		machine = new AttackMachine(null);
		target = new ArrayList<>();
		need = new ArrayList<>();
		weapon = new Weapon(Weapons.DAGGER);
		attack = weapon.getAttacksTypes()[0];
		target.add(AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6))
				.requirement(WeaponProperties.FENCING));
		need.add(attack);
		need.addAll(target);
		
	}

	@Test
	public void testGetAttacks() 
	{
	}

}
