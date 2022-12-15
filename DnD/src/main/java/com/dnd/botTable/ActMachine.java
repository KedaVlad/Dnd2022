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

	void finishLast()
	{
		trash.addAll(mainTree.get(mainTree.size() - 1).end());
		mainTree.remove(mainTree.size() - 1);
	}

	void dateche(String name)
	{
		for(Action dateche: datached)
		{
			if(dateche.name.equals(name)) {

				trash.addAll(dateche.end());
				datached.remove(dateche);
				break;
			}
		}
	}

	void goToMediator()
	{
		for(int i = mainTree.size() - 1; i >= 0; i--)
		{		
			if(mainTree.get(i).mediator == true)
			{
				break;
			}
			else
			{
				trash.addAll(mainTree.get(i).end());
				mainTree.remove(i);
			}
		}
	}

	void goTo(Action action)
	{
		if(action.mainAct == true)
		{
			if(beackTo(action.name))
			{
				finishLast();
			}
			up(action);
		} 
		else
		{
			start(action);
		}
	}

	boolean beackTo(String key)
	{
		for(int i = 0; i < mainTree.size(); i++)
		{
			if(mainTree.get(i).name == key)
			{
				for(int j = mainTree.size() - 1; j != i; j--)
				{
					trash.addAll(mainTree.get(j).end());
					mainTree.remove(j);
				}
				return true;
			}
		}
		return false;
	}

	boolean beackTo(long key)
	{

		for(Action action: mainTree)
		{
			if(action.key == key)
			{
				return beackTo(action.name);
			}
		}
		return false;
	}


	private void up(Action action)
	{
		this.mainTree.add(action);
	}


	private void start(Action act)
	{
		datached.add(act);
	}


	public List<Integer> throwAwayTrash() {

		List<Integer> answer = new ArrayList<>();
		answer.addAll(trash);
		getTrash().clear();
		getTrash().addAll(prepeared);
		prepeared.clear();
		return answer;
	}


	public void toAct(Integer act)
	{
		mainTree.get(mainTree.size()-1).toCircle(act);
	}


	public void prepare(Integer prepared) 
	{
		this.prepeared.add(prepared);
	}


	public List<Integer> getTrash() 
	{
		return trash;
	}


	public Action getAction()
	{
		return mainTree.get(mainTree.size() - 1);
	}

	
	public void toDatached(String name, Integer messageId) {
		for(Action action: datached)
		{
			if(action.name == name)
			{
				action.toCircle(messageId);
				break;
			}
		}
	}

}


