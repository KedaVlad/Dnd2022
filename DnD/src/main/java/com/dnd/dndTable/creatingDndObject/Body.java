package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.ArrayAction;
import com.dnd.botTable.actions.dndAction.HeroAction;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;

public class Body implements ObjectDnd, Serializable 
{

	private static final long serialVersionUID = 1L;

	private int armor;
	
	private List<String> status;
	
	private List<Bag> myBags;
	//private List<Clothes> weared;
	private List<Weapon> prepearedWepons;
	
	


	
	public Body()
	{
		 status = new ArrayList<>();
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

	public Action getBagMeny(Bag bag)
	{
		String name = bag.getName();
		String text = "Choose item";
		List<Items> insideBag = bag.getInsideBag();
		Action[][] buttons = new Action[insideBag.size()][0];
		for(int i = 0; i < insideBag.size(); i++)
		{
			buttons[i][0] = RegistrateAction.create(insideBag.get(i).getName(),insideBag.get(i));
		}
		return HeroAction.create(name, key(), text, buttons);
	}
	
	public Action getBodyMeny() 
	{
		String name = "BodylMenu";
		String status = "Active statuses";
		for(String string: this.status)
		{
			status += string + "\n";
		}
		
		if(myBags.size() > 1)
		{
			String text = "Choose bag";
			Action[][] buttons = new Action[myBags.size()][0];
			for(int i = 0; i < myBags.size(); i++)
			{
				buttons[i][0] = RegistrateAction.create(myBags.get(i).getName(),myBags.get(i));
			}
			return ArrayAction.create("BodyArray", BODY, new Action[]
					{
							HeroAction.create(name, NO_ANSWER, status, null),
							HeroAction.create(name, key(), text, buttons)
					});
		}
		else
		{
			return ArrayAction.create("BodyArray", BODY, new Action[]
					{
							HeroAction.create(name, NO_ANSWER, status, null),
							getBagMeny(myBags.get(0))
					});
		}
	}
	
	public Action getItemMenu(Items item) 
	{
		String name = item.getName();
		String text = name;
		return HeroAction.create(name, item.key(), text, null);
	}


	@Override
	public long key() {
		// TODO Auto-generated method stub
		return BODY;
	}

	
	
}
