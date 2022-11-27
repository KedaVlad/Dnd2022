package com.dnd.dndTable.creatingDndObject.workmanship.magicEffects;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.factory.InerComand;

public class Buff extends Effect {

	private InerComand cast = new InerComand();
	
	private static final long serialVersionUID = 1L;



	public InerComand getCast() {
		return cast;
	}

	public void setCast(InerComand cast) {
		this.cast = cast;
	}

}
