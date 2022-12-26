package com.dnd.dndTable.creatingDndObject.modification.pool;

import java.util.ArrayList;
import java.util.List;

public class SecondTypePool<T> extends SimplePool<T> {

	private static final long serialVersionUID = 1L;
	private List<T> weating = new ArrayList<>();
	
	public void pull(int target)
	{
		if(this.getActive().size() >= getActiveMaxSize())
		{
			return;
		}
		getActive().add(getWeating().get(target));
		getWeating().remove(target);
	}
	
	public void giveBack(int target)
	{
		getWeating().add(getActive().get(target));
		getActive().remove(target);
	}


	public List<T> getWeating() {
		return weating;
	}

	public void setWeating(List<T> weating) {
		this.weating = weating;
	}
	
	public void add(T object)
	{
		weating.add(object);
	}
}
