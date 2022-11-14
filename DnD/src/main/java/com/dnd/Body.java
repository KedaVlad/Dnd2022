package com.dnd;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dnd.dndTable.creatingDndObject.Stats;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;

public class Body {

	private Map<String, Weapon> prepearedWepons;
	
	private Map<String, Bag> myBags;

	private Stats myStats;
	
	public Body()
	{
		myStats = new Stats();
		prepearedWepons = new LinkedHashMap<>();
		myBags = new LinkedHashMap<>();
		myBags.put("Bag", new Bag("Bag"));
	}
	
	public Map<String, Weapon> getPrepearedWepons() {
		return prepearedWepons;
	}

	public void setPrepearedWepons(Weapon weapon) {
		
		for(WeaponProperties properties: weapon.getProperties())
		{
			if(properties.equals(weapon))
		}
		
	}

	public Map<String, Bag> getMyBags() {
		return myBags;
	}

	public void setMyBags(Map<String, Bag> myBags) {
		this.myBags = myBags;
	}

	public Stats getMyStats() {
		return myStats;
	}
}
