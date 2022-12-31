package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.ArrayAction;
import com.dnd.botTable.actions.dndAction.ChangeAction;
import com.dnd.botTable.actions.dndAction.ComplexChenge;
import com.dnd.botTable.actions.dndAction.HeroAction;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.botTable.actions.factoryAction.FactoryAction;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.Rolls.MainStat;
import com.dnd.dndTable.creatingDndObject.bagDnd.Ammunition;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.ClassArmor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.Armors;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Wallet;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;

public class Body implements ObjectDnd, Serializable 
{

	private static final long serialVersionUID = 1L;

	private boolean barbarian;

	private List<String> status;
	private List<Bag> myBags;
	private List<Items> prepearedWeapons;
	private Armor[] weared = new Armor[2];
	private List<MainStat> stats;



	public Action getBodyMeny() 
	{
		String name = "BodyArray";
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
			return ArrayAction.create("BodyArray", NO_ANSWER, new Action[]
					{
							getPrepeared(),
							HeroAction.create(name, NO_ANSWER, status, null),
							HeroAction.create(name, key(), text, buttons)
					});
		}
		else
		{
			return ArrayAction.create("BodyArray", NO_ANSWER, new Action[]
					{
							getPrepeared(),
							HeroAction.create(name, NO_ANSWER, status, null),
							getBagMeny(RegistrateAction.create("BAG", myBags.get(0)))
					});
		}
	}


	public Action walletMenu(RegistrateAction action)
	{
		Wallet wallet = (Wallet) action.getTarget();
		return ChangeAction.create(action, wallet.toMenu() + "\n Choose currency..." , 
				new String[][] {{"CP", "SP", "GP", "PP"}});
	}

	private Action getPrepeared()
	{
		String name = "Prepeared";
		String text;
		if(prepearedWeapons.size() > 0)
		{
			Action[][] buttons = new Action[prepearedWeapons.size()][1];
			text = "PREPEARED";
			for(int i = 0; i < prepearedWeapons.size(); i++)
			{
				buttons[i][0] = RegistrateAction.create(prepearedWeapons.get(i).getName(),prepearedWeapons.get(i)).key(PREPEARED);
			}
			return HeroAction.create(name, PREPEARED, text, buttons);
		}
		text = "NO PREPEARED WEAPONS or ARMORS\nFor prepearing you shood choose some weapon in your bag and choose \"PREPEAR\"";
		return HeroAction.create(name, PREPEARED, text, null);
	}

	public Action getPrepearedMenu(RegistrateAction action) 
	{
		Items item = (Items) action.getTarget();
		if(item instanceof Weapon)
		{
			return ChangeAction.create(action, "Return to bag?" , new String[][] {{"RETURN", "ATTACK"}});
		}
		return ChangeAction.create(action, "Return to bag?" , new String[][] {{"RETURN"}});
	}
	
	public Action changePrepeared(ChangeAction action) 
	{
		String answer = action.getAnswer();
		Items item = (Items)action.getTarget();
		if(answer.contains("RETURN"))
		{
		prepearedWeapons.remove(item);
		myBags.get(0).add(item);
		return action.beckKey("Menu").beckCall(100000002+"BAG");
		}
		else if(answer.contains("ATTACK"))
		{
			return RegistrateAction.create("ATTACK", (Weapon)item).key(ATTACK_MACHINE);
		}
		return null;
	}

	public Action getBagMeny(RegistrateAction action)
	{
		Bag bag = (Bag)action.getTarget();
		String name = "BagMeny";
		if(bag.getInsideBag().size() == 0)
		{
			String text = bag.getWallet().toString() + "\nYor bag is empty";
			Action[][] buttons = new Action[1][2];
			buttons[0] = new Action[] {
					FactoryAction.create("ADD ITEM", 623478675, false, null, null),
					RegistrateAction.create("WALLET", bag.getWallet())};
			return HeroAction.create(name, key(), text, buttons);
		}
		else
		{	
			String text = bag.getWallet().toString() + "\nChoose item";
			List<Items> insideBag = bag.getInsideBag();
			Action[][] buttons = new Action[insideBag.size()+1][];
			buttons[0] = new Action[] {
					FactoryAction.create("ADD ITEM", 623478675, false, null, null),
					RegistrateAction.create("WALLET", bag.getWallet())};
			for(int i = 1; i < buttons.length; i++)
			{
				buttons[i] = new Action[] {RegistrateAction.create(insideBag.get(i-1).getName(),insideBag.get(i-1))};
			}

			return HeroAction.create(name, key(), text, buttons);
		}
	}

	public Action getItemMenu(RegistrateAction action) 
	{
		Items item = (Items) action.getTarget();
		if(item instanceof Armor)
		{
			return armorMenu(action);
		}
		else if(item instanceof Weapon)
		{
			return weaponMenu(action);
		}
		else if(item instanceof Ammunition)
		{
			return ammunitionMenu(action);
		}
		else if(item instanceof Ammunition)
		{
			return ammunitionMenu(action);
		}
		else
		{
			String name = item.getName();
			String text = item.getDescription();
			if(text == null || text == "") text = name;
			return HeroAction.create(name, item.key(), text, null);
		}
	}

	public Action changeWallet(ChangeAction action)
	{
		Wallet wallet = (Wallet) action.getTarget();
		if(action instanceof ComplexChenge)
		{
			ComplexChenge target = (ComplexChenge) action;
			if(target.getPoolAnswer().size() == 2)
			{
				return target.addQuestion("How much?(Write)", null).setMediator();
			}
			else if(target.getPoolAnswer().size() == 3)
			{
				int value = 0;
				Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
				Matcher matcher = pat.matcher(target.getPoolAnswer().get(2));
				while (matcher.find()) 
				{
					value = ((Integer) Integer.parseInt(matcher.group()));
				}
				if(target.getPoolAnswer().get(1).contains("+"))
				{
					wallet.addCoin(target.getPoolAnswer().get(0), value);
					return action.beckKey("Menu").beckCall(100000002+"BAG");
				}
				else if(target.getPoolAnswer().get(1).contains("-"))
				{
					if(wallet.lostCoin(target.getPoolAnswer().get(0), value))
					{
						return action.beckKey("Menu").beckCall(100000002+"BAG");
					}
					else
					{
						return HeroAction.create("NoMoney", NO_ANSWER, "You don`t have enough coins for that ;(", null).beckKey("BodyArray");
					}
				}
			}
		}
		else
		{
			return ComplexChenge.create(action, "Earned or spent?", new String[][]{{"+","-"}});
		}
		return null;
	}

	public Action change(ChangeAction action)
	{
		ObjectDnd target = action.getTarget();
		String answer = action.getAnswer();

		if(answer.equals("THROW OUT"))
		{
			myBags.get(0).getInsideBag().remove((Items)target);
			return action.beckKey("Menu").beckCall(100000002+"BAG");
		}
		else if(answer.equals("WEAR"))
		{
			wear((Armor)target);
			prepearedWeapons.add((Armor) target);
			myBags.get(0).getInsideBag().remove((Items)target);
			return action.beckKey("Menu").beckCall(100000002+"BAG");
		}
		else if(answer.equals("PREPEAR"))
		{
			prepearedWeapons.add((Weapon) target);
			myBags.get(0).getInsideBag().remove(target);
			return action.beckKey("Menu").beckCall(100000002+"BAG");
		}
		else if(answer.equals("TOP UP"))
		{
			if(action instanceof ComplexChenge)
			{
				ComplexChenge targetAction = (ComplexChenge) action;
				Ammunition ammunition = (Ammunition) target;
				String valueInString = targetAction.getPoolAnswer().get(1);
				int value = 0;
				Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
				Matcher matcher = pat.matcher(valueInString);
				while (matcher.find()) 
				{
					value = ((Integer) Integer.parseInt(matcher.group()));
				}
				ammunition.addValue(value);
				return action.beckKey("Menu").beckCall(100000002+"BAG");
			}
			else
			{
				return ComplexChenge.create(action, "How many?(Write)", null).setMediator();
			}

		}
		return null;
	}

	private void wear(Armor armor)
	{
		if(armor.getType().getClazz().equals(ClassArmor.SHIELD))
		{
			weared[1] = armor;
		}
		else
		{
			weared[0] = armor;
		}
	}

	private Action armorMenu(RegistrateAction action)
	{
		Armor armor = (Armor) action.getTarget();
		return ChangeAction.create(action, armor.toString() + ": " + armor.getType().getClazz() , new String[][] {{"WEAR", "THROW OUT"}});
	}

	private Action ammunitionMenu(RegistrateAction action)
	{
		Ammunition ammunition = (Ammunition) action.getTarget();
		return ChangeAction.create(action, ammunition.toString() , new String[][] {{"TOP UP", "THROW OUT"}});
	}

	private Action weaponMenu(RegistrateAction action)
	{
		Weapon weapon = (Weapon) action.getTarget();
		return ChangeAction.create(action, weapon.toString() , new String[][] {{"PREPEAR", "THROW OUT"}});
	}

	public Body(List<MainStat> stats)
	{
		this.stats = stats;
		status = new ArrayList<>();		 
		prepearedWeapons = new ArrayList<>();
		myBags = new ArrayList<>();
		myBags.add(new Bag("Bag"));
	}

	public List<Items> getPrepearedWepons() 
	{
		return prepearedWeapons;
	}

	public void setPrepearedWepons(Weapon weapon) {



	}

	public List<Bag> getMyBags() {
		return myBags;
	}

	public void setMyBags(List<Bag> myBags) {
		this.myBags = myBags;
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return BODY;
	}

	public void setBarbarian(boolean barbarian) {
		this.barbarian = barbarian;
	}

	public String getAC() 
	{
		String answer = "AC:";
		if(weared[0] == null)
		{
			if(barbarian)
			{
				answer += (10 + stats.get(2).getModificator() + stats.get(1).getModificator());
			}
			else
			{
				answer += (10 + stats.get(1).getModificator());
			}
		}
		else
		{
			Armors type = weared[0].getType();
			int armor;
			if(type.getStatDependBuff() > type.getBaseArmor())
			{
				armor = type.getBaseArmor() + stats.get(1).getModificator();
				if(armor > type.getStatDependBuff())
				{
					answer += type.getStatDependBuff();
				}
				else
				{
					answer += armor;
				}
			}
			else
			{
				armor = type.getBaseArmor();
			}

			if(weared[1] == null)
			{
				return answer;
			}
			else
			{
				answer += "(+" + weared[1].getType().getBaseArmor() + ")";
			}
		}
		return answer;
	}




}
