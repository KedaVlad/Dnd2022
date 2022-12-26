package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.dnd.botTable.GameTable;
import com.dnd.botTable.actions.BotAction;
import com.dnd.dndTable.rolls.Dice.Roll;

public class GameTableTest {

	BotAction action = BotAction.create("Menu", 2, false, false, null, null);
	GameTable game = new GameTable();
	
	@Test
	public void test() {
		Assert.assertSame(action, game.);
	}

}
