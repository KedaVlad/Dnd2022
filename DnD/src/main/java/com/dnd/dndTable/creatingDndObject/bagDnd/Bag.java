package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.ObjectDnd;

public class Bag implements Serializable, ObjectDnd
{

	private static final long serialVersionUID = -3894341880184285889L;

	private String name;
	private Wallet wallet;
	private List<Items> insideBag;

	public void add(Items item)
	{
		insideBag.add(item);
	}
	
	public Bag(String bagName) 
	{
		this.name = bagName;
		this.wallet = new Wallet();
		insideBag = new ArrayList<Items>();
	}

	public List<Items> getInsideBag() 
	{
		return insideBag;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return BAG;
	}

	public Wallet getWallet() {
		return wallet;
	}
}
