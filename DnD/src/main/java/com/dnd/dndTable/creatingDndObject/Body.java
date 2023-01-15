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
import com.dnd.botTable.actions.BotAction;
import com.dnd.botTable.actions.WrappAction;
import com.dnd.botTable.actions.dndAction.ChangeAction;
import com.dnd.botTable.actions.dndAction.ComplexChenge;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.botTable.actions.dndAction.StartTreeAction;
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


	Action stuff() 
	{
		String instruction = "Some instruction for STUFF";

		return WrappAction.create("STUFF", BODY, instruction, new Action[][]
				{{
					StartTreeAction.create("PREPEARED", PREPEARED),
					StartTreeAction.create("BAG", BAG),
					StartTreeAction.create("WALLET", WALLET),
				},
			{
					BotAction.create("RETURN TO MENU", NO_ANSWER, true, false, null, null)
			}}).replyButtons();
	}

	Action getPrepearedMenu()
	{
		String name = "Prepeared";
		String text;
		if(prepearedWeapons.size() > 0)
		{
			Action[][] buttons = new Action[prepearedWeapons.size()][1];
			text = "Items in quick access (dressed armor, sword in scabbard, potion on belt, and so on...).";
			for(int i = 0; i < prepearedWeapons.size(); i++)
			{
				buttons[i][0] = RegistrateAction.create(prepearedWeapons.get(i).getName(),prepearedWeapons.get(i)).key(PREPEARED);
			}
			return WrappAction.create(name, PREPEARED, text, buttons);
		}
		text = "No prepeared or weared items yeat...\nFor prepearing you shood choose some item in your bag and choose \"PREPEAR\"";
		return WrappAction.create(name, PREPEARED, text, null);
	}

	Action getBagMeny()
	{
		Bag bag = myBags.get(0);
		String name = "BagMeny";
		if(bag.getInsideBag().size() == 0)
		{
			String text = bag.getWallet().toString() + "\nYor bag is empty";
			Action[][] buttons = new Action[1][2];
			buttons[0] = new Action[] {
					FactoryAction.create("ADD ITEM", 623478675, false, null, null)};
			return WrappAction.create(name, key(), text, buttons);
		}
		else
		{	
			String text = bag.getWallet().toString() + "\nChoose item";
			List<Items> insideBag = bag.getInsideBag();
			Action[][] buttons = new Action[insideBag.size()+1][];
			buttons[0] = new Action[] {
					FactoryAction.create("ADD ITEM", 623478675, false, null, null)};
			for(int i = 1; i < buttons.length; i++)
			{
				buttons[i] = new Action[] {RegistrateAction.create(insideBag.get(i-1).getName(),insideBag.get(i-1))};
			}

			return WrappAction.create(name, key(), text, buttons);
		}
	}

	Action getWalletMenu()
	{
		return ChangeAction.create(RegistrateAction.create("Wallet", myBags.get(0).getWallet()), myBags.get(0).getWallet().toMenu() + "\n Choose currency..." , 
				new String[][] {{"CP", "SP", "GP", "PP"}});
	}


	Action getPrepearedMenu(RegistrateAction action) 
	{
		Items item = (Items) action.getTarget();
		if(item instanceof Weapon)
		{
			return ChangeAction.create(action, "Return to bag?" , new String[][] {{"RETURN", "ATTACK"}});
		}
		return ChangeAction.create(action, "Return to bag?" , new String[][] {{"RETURN"}});
	}

	Action changePrepeared(ChangeAction action) 
	{
		String answer = action.getAnswer();
		Items item = (Items)action.getTarget();
		if(answer.contains("RETURN"))
		{
			prepearedWeapons.remove(item);
			myBags.get(0).add(item);
			return action.returnTo("STUFF", "PREPEARED");
		}
		else if(answer.contains("ATTACK"))
		{
			return RegistrateAction.create("ATTACK", (Weapon)item).key(ATTACK_MACHINE);
		}
		return null;
	}

	Action getItemMenu(RegistrateAction action) 
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
		else
		{
			return itemMenu(action);
		}
	}

	Action changeWallet(ChangeAction action)
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
					return action.returnTo("STUFF", "WALLET");
				}
				else if(target.getPoolAnswer().get(1).contains("-"))
				{
					if(wallet.lostCoin(target.getPoolAnswer().get(0), value))
					{
						return action.returnTo("STUFF", "WALLET");
					}
					else
					{
						return WrappAction.create("NoMoney", NO_ANSWER, "You don`t have enough coins for that ;(", null).returnTo("BodyArray");
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

	Action change(ChangeAction action)
	{
		ObjectDnd target = action.getTarget();
		String answer = action.getAnswer();
		Log.add("Change " + answer);
		if(answer.contains("THROW OUT"))
		{
			myBags.get(0).getInsideBag().remove((Items)target);
			return action.returnTo("STUFF", "BAG");
		}
		else if(answer.equals("WEAR"))
		{
			wear((Armor)target);
			prepearedWeapons.add((Armor) target);
			myBags.get(0).getInsideBag().remove((Items)target);
			return action.returnTo("STUFF", "BAG");
		}
		else if(answer.equals("PREPEAR"))
		{
			prepearedWeapons.add((Weapon) target);
			myBags.get(0).getInsideBag().remove(target);
			return action.returnTo("STUFF", "BAG");
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
				return action.returnTo("STUFF", "BAG");
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
	
	private Action itemMenu(RegistrateAction action)
	{
		Items item = (Items) action.getTarget();
		return ChangeAction.create(action, item.toString() , new String[][] {{"THROW OUT"}});
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

	public String info() {
		// TODO Auto-generated method stub
		return getAC() + "\n";
	}

}
