package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.ArrayAction;
import com.dnd.botTable.actions.BotAction;


public class ArrayActionTest {

	ArrayAction mainAct;
	BotAction answer;
	BotAction actionw2;
	@Before
	public void setUp() throws Exception {
		answer = BotAction.create("B", 222222222, false, false, "text", new String[][]
				{{"aaaa","b"},{"c", "d"}});
		
		mainAct = ArrayAction.create("A", 111111111, new Action[] {
				BotAction.create("B", 2, false, false, "text", new String[][]
						{{"aaaa","b"},{"c", "d"}}),
				BotAction.create("C", 3, false, false, "text", new String[][]
						{{"a","b"},{"c", "d"}}),
				
				
				
		});
		actionw2 = (BotAction)mainAct.continueAction("2a");
	}

	@Test
	public void testObj() {
		Assert.assertSame(answer.continueAction("a"), mainAct.continueAction("2a"));
	}
	
	@Test
	public void testStr() {
		
		BotAction action1 = (BotAction)answer.continueAction("a");
		BotAction action2 = (BotAction)mainAct.continueAction("2a");
		Assert.assertEquals(action1.getAnswer(), action2.getAnswer());
	}


}
