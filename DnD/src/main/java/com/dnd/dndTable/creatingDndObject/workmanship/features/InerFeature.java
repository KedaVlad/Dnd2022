package com.dnd.dndTable.creatingDndObject.workmanship.features;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.factory.inerComands.InerComand;

public class InerFeature extends Feature
{
	private static final long serialVersionUID = 1L;
	
	private List<InerComand> comand;
	
	public static InerFeature create(String name, String text, InerComand... comand)
	{
		InerFeature answer = new InerFeature();
		answer.setName(name);
		answer.setDescription(text);
		answer.comand = new ArrayList<>();
		for(InerComand target: comand)
		{
			answer.comand.add(target);
		}
		return answer;
	}

	public List<InerComand> getComand() {
		return comand;
	}
}
