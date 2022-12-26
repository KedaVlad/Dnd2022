package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.ObjectDnd;

public class Bag implements Serializable, ObjectDnd
{

	private static final long serialVersionUID = -3894341880184285889L;

	private int id;
	private String name;
	private List<Items> insideBag;


	public Bag(String bagName) {
		this.name = bagName;
		insideBag = new ArrayList<Items>();
	}




	public void whatInTheBag() {
		if(getInsideBag().size() == 0) {
			System.out.println("Your bag is empty!");
		} else {
			for(int i = 0; i < getInsideBag().size(); i++) {
				System.out.println(getInsideBag().get(i));
			}
		}
	}

	//equals + hashCode()
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		Bag characterDnd = (Bag) obj;
		return id == characterDnd.id && (getName() == characterDnd.getName() ||(getName() != null && getName().equals(characterDnd.getName())));
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());             
		result = prime * result + id; 
		return result;
	}


	public List<Items> getInsideBag() {
	return insideBag;
	}




	public String getName() {
		return name;
	}




	@Override
	public long key() {
		// TODO Auto-generated method stub
		return BAG;
	}
}
