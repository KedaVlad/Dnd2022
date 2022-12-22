package com.dnd.dndTable.creatingDndObject.workmanship.features;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.factory.inerComands.InerComand;
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("INER_FEATURE")
public class InerFeature extends Feature
{
	private static final long serialVersionUID = 1L;
	
	private InerComand[] comand;
	
	public static InerFeature create(String name, String text, InerComand... comand)
	{
		InerFeature answer = new InerFeature();
		answer.setName(name);
		answer.setDescription(text);
		answer.comand =  comand;
		return answer;
	}

	public InerComand[] getComand() {
		return comand;
	}
	
	public static InerFeature build(Feature feature)
	{
		InerFeature answer = new InerFeature();
		answer.setName(feature.getName());
		answer.setDescription(feature.getDescription());
		return answer;
	}
	
	public InerFeature comand(InerComand...comands)
	{
		this.comand = comands;
		return this;
	}
}
