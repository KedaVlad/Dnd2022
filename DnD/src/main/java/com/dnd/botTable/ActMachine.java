package com.dnd.botTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;


class ActMachine implements Serializable {

	private static final long serialVersionUID = 5473026731995501761L;
	private List<Act> mainMap = new ArrayList<>();
	private List<Act> undependet = new ArrayList<>();

	private List<Integer> prepared = new ArrayList<>();
	private List<Integer> trash = new ArrayList<>();


	void finish()
	{
		getTrash().addAll(mainMap.get(mainMap.size() - 1).end());
		mainMap.remove(mainMap.size() - 1);
	}

	void finish(String act)
	{
		for(Act undep: undependet)
		{
			if(undep.getName().equals(act)) {

				getTrash().addAll(undep.end());
				undependet.remove(undep);
			}
		}
	}

	void goTo(String base, String act)
	{
		goTo(base, true);
		goTo(act, true);
	}

	void goTo(String act, boolean depend)
	{
		if(depend == true)
		{
			
				for(int i = 0; i < mainMap.size(); i++)
				{
					if(mainMap.get(i).getName().equals(act))
					{
						down(act);
						depend = false;
						break;
					}
				}
				if(depend == true)
				{
					up(act);
				}
			
		} 
		else
		{
			start(act);
		}
	}

	private void start(String act)
	{
		undependet.add(Act.create(act));
	}

	private void down(String act)
	{
		for(int i = mainMap.size() - 1; i >= 0; i--)
		{
			if(!mainMap.get(i).getName().equals(act))
			{
				Log.add("Down " + act + trash);
				getTrash().addAll(mainMap.get(i).end());
				mainMap.remove(i);
			}
			else
			{
				break;
			}
		}
	}

	private void up(String act)
	{
		Log.add("Up " + act + trash);
		this.mainMap.add(Act.create(act));
	}

	public List<Integer> throwAwayTrash() {

		Log.add("Throw 1 " + trash);
		List<Integer> answer = new ArrayList<>();
		answer.addAll(trash);
		Log.add("Throw 2 " + answer);
		getTrash().clear();
		Log.add("Throw 21 " + answer);
		Log.add("Throw 3 " + trash);
		getTrash().addAll(prepared);
		Log.add("Throw 23 " + answer);
		Log.add("Throw 4 " + trash);
		prepared.clear();
		Log.add("Throw 5 " + answer);
		return answer;
	}

	public void toAct(Integer act)
	{
		mainMap.get(mainMap.size()-1).setActCircle(act);
	}

	public void prepare(Integer prepared) {
		this.prepared.add(prepared);
	}

	public List<Integer> getTrash() {
		return trash;
	}

	public String toString()
	{
		String answer = "----------------------------------------------\n";

		for(Act act: mainMap)
		{
			answer += (act.toString()) + "\n";
		}

		return answer + "----------------------------------------\n";
	}
}

class Act implements Serializable{


	private static final long serialVersionUID = 2479864067932090569L;

	private String name;

	private List<Integer> actCircle = new ArrayList<>();

	List<Integer> end()
	{
		return actCircle;
	}

	void setActCircle(Integer act) 
	{
		this.actCircle.add(act);
	}

	static Act create(String name)
	{
		Act act = new Act();
		act.setName(name);
		return act;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public String toString()
	{


		String answer = name + "|";

		for(Integer inter: actCircle)
		{
			answer += inter + "|";
		}
		return answer;
	}

}
