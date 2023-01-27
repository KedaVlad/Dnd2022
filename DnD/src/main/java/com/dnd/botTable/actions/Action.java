package com.dnd.botTable.actions;
import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.factory.inerComands.InerComand;

public class Action extends BaseAction implements Cloneable
{
	public enum Location
	{
		BOT, FACTORY, CHARACTER
	}
	
	private static final long serialVersionUID = 1L;
	String[][] buttons;
	String[] answers;
	ObjectDnd objectDnd;
	InerComand comand;

	Action(){}

	
	public static ActionBuilder builder()
	{
		return new ActionBuilder();
	}
	
	@Override
	public String[][] buildButtons()
	{
		return buttons;
	}
	
	@Override
	public Action continueAction(String key) 
	{
		Action answer = this.clone();
		if(answer.answers != null && answer.answers.length > 0)
		{
			String[] newAnswers  = new String[answer.answers.length + 1];
			for(int i = 0; i < answer.answers.length; i++)
			{
				newAnswers[i] = answer.answers[i];
			}
			newAnswers[newAnswers.length - 1] = key;
			answer.answers = newAnswers;
		}
		else
		{
			answer.answers = new String[]{key};
		}
		return answer;
	}
	
	@Override
	public boolean hasButtons() 
	{
		return buttons != null && buttons[0].length > 0;
	}

	@Override
	public boolean replyContain(String string) {
		
		for(String[] line: buttons)
		{
			for(String button: line)
			{
				if(button.equals(string))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected Action clone()
	{
		Action answer = new Action();
		answer.name = this.name;
		answer.objectDnd = this.objectDnd;
		answer.answers = this.answers;
		answer.location = this.getLocation();
		answer.key = this.getKey();
		return answer;
	}
	
	public InerComand getComand() 
	{
		return comand;
	}

	public void setComand(InerComand comand)
	{
		this.comand = comand;
	}

	public ObjectDnd getObjectDnd() 
	{
		return objectDnd;
	}

	public void setObjectDnd(ObjectDnd objectDnd) 
	{
		this.objectDnd = objectDnd;
	}

	public String[] getAnswers()
	{
		return answers;
	}

	public void setAnswers(String[] answers) 
	{
		this.answers = answers;
	}

	public String[][] getButtons() 
	{
		return buttons;
	}

	public void setButtons(String[][] buttons) 
	{
		this.buttons = buttons;
	}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}
