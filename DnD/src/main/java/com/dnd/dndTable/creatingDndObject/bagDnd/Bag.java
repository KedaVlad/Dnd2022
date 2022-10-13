package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bag implements Serializable
{

	private static final long serialVersionUID = -3894341880184285889L;

	private int id;
	private String bagName;
	private List<Items> insideBag;


	public Bag(String bagName) {
		this.bagName = bagName;
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
		return id == characterDnd.id && (bagName == characterDnd.bagName ||(bagName != null && bagName.equals(characterDnd.bagName)));
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bagName == null) ? 0 : bagName.hashCode());             
		result = prime * result + id; 
		return result;
	}


	public List<Items> getInsideBag() {
	return insideBag;
	}
}
