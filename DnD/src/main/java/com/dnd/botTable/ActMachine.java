package com.dnd.botTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;


class ActMachine implements Serializable 
{
	private static final long serialVersionUID = 5473026731995501761L;
	private List<Action> mainTree = new ArrayList<>();
	private List<Action> datached = new ArrayList<>();
	private List<Integer> prepeared = new ArrayList<>();
	private List<Integer> trash = new ArrayList<>();

	Integer getMenuId()
	{
		if(mainTree.get(2).name.equals("Menu"))
		{
			return mainTree.get(2).getMessageId();
		}
		else
		{
			Log.add("Error menu Id");
			return 0;
		}
	}
	
	Action findReply()
	{
		for(int i = mainTree.size() - 1; i > 0; i--)
		{
			if(mainTree.get(i).isReplyButtons())
			{
				return mainTree.get(i);
			}
		}
		return null;
	}
	
	void finishLast()
	{
		Log.add("END " + mainTree.get(mainTree.size() - 1));
		Log.add("*****************************************");
		trash.addAll(mainTree.get(mainTree.size() - 1).end());
		mainTree.remove(mainTree.size() - 1);
	}

	void dateche(String name)
	{
		for(Action dateche: getDatached())
		{
			if(dateche.name.equals(name)) {

				trash.addAll(dateche.end());
				getDatached().remove(dateche);
				break;
			}
		}
	}

	boolean isMediator()
	{
		if(mainTree.get(mainTree.size() - 1).mediator)
		{
			Log.add("MEDIATOR WORK LAST");
			return true;
		}
		else if(mainTree.size() > 1 && mainTree.get(mainTree.size() - 2).mediator)
		{
			Log.add("MEDIATOR WORK pre LAST");
			finishLast();
			return true;
		}
		else
		{
			Log.add("MEDIATOR doesnt WORK");
			return false;
		}
	}

	void beackTo(String name)
	{
		Log.add("START BEACKING to "+name);
		for(int i = 0; i < mainTree.size(); i++)
		{
			Log.add("CHECK ACTION #" + i + " - " + mainTree.get(i));
			if(mainTree.get(i).name.equals(name))
			{
				Log.add("FIND " + name + " = " + mainTree.get(i));
				for(int j = mainTree.size() - 1; j > i; j--)
				{
					Log.add("FINISH #" + j + mainTree.get(j));
					trash.addAll(mainTree.get(j).end());
					mainTree.remove(j);
				}
			}
		}
	}

	void beackTo(long key)
	{
		for(Action action: mainTree)
		{
			if(action.key == key)
			{
				beackTo(action.name);
			}
		}
	}

	void up(Action action)
	{
		Log.add("Up " + action);
		this.mainTree.add(action);
	}

	void start(Action act)
	{
		getDatached().add(act);
	}

	List<Integer> throwAwayTrash() {

		List<Integer> answer = new ArrayList<>();
		answer.addAll(trash);
		getTrash().clear();
		getTrash().addAll(prepeared);
		prepeared.clear();
		return answer;
	}

	void toAct(Integer act)
	{
		mainTree.get(mainTree.size()-1).toCircle(act);
	}

	void prepare(Integer prepared) 
	{
		this.prepeared.add(prepared);
	}

	List<Integer> getTrash() 
	{
		return trash;
	}

	Action getAction()
	{
		return mainTree.get(mainTree.size() - 1);
	}

	void toDatached(String name, Integer messageId) {
		for(Action action: getDatached())
		{
			if(action.name == name)
			{
				action.toCircle(messageId);
				break;
			}
		}
	}

	
	public String toString()
	{
		String answer = "MAIN TREE  ";
		
		for(Action action: mainTree)
		{
			answer += action + "[>]";
		}
		
		return answer;
	}

	/**
	 * @return the datached
	 */
	public List<Action> getDatached() {
		return datached;
	}
}


