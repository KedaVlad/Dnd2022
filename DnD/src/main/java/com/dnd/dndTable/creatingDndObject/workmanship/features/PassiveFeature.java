package com.dnd.dndTable.creatingDndObject.workmanship.features;

public class PassiveFeature extends Feature {

	private static final long serialVersionUID = 1L;
	
	private String passive;

	public static PassiveFeature create(String name, String text, String passive)
	{
		PassiveFeature answer = new PassiveFeature();
		answer.setName(name);
		answer.setDescription(text);
		answer.passive = passive;
		return answer;
	}

	public String getPassive() {
		return passive;
	}

}
