package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dnd.botTable.GameTable;
import com.dnd.botTable.actions.factoryAction.FactoryAction;
import com.dnd.botTable.actions.factoryAction.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.factory.ControlPanel;

public class ControlPanelTest {

	GameTable gameTable;
	ControlPanel panel;
	CharacterDnd character;
	List<String> answers;
	FactoryAction action;
	FinalAction finalAct;
	@Before
	public void setUp() throws Exception {
		
		gameTable = new GameTable();
		panel = new ControlPanel();
		character = CharacterDnd.create("Name");
		answers = new ArrayList<>();
		answers.add("Rogue");
		answers.add("Assasin");
		answers.add(""+12);
		action = FactoryAction.create("", 432543654, false, null, null);
		action.getLocalData().addAll(answers);
		gameTable.setActualGameCharacter(character);
		finalAct = FinalAction.create(action);
		panel.finish(finalAct,gameTable);
	}

	@Test
	public void test() {

		Assert.assertEquals("Must be 2", 2, character.getAttackMachine().getAfterAttak().size());
	}

}
