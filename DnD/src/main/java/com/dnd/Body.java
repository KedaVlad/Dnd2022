package com.dnd;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;

public class Body {

	private int armor;
	
	private List<String> status;
	
	private List<Bag> myBags;
	//private List<Clothes> weared;
	private List<Weapon> prepearedWepons;
	
	


	
	public Body()
	{
		
		prepearedWepons = new ArrayList<>();
		myBags = new ArrayList<>();
		myBags.add(new Bag("Bag"));
	}
	
	public List<Weapon> getPrepearedWepons() {
		return prepearedWepons;
	}

	public void setPrepearedWepons(Weapon weapon) {
		
	
		
	}

	public List<Bag> getMyBags() {
		return myBags;
	}

	public void setMyBags(List<Bag> myBags) {
		this.myBags = myBags;
	}

	
}
