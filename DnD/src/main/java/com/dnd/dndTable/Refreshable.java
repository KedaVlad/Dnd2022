package com.dnd.dndTable;

public interface Refreshable {

	enum Time implements ObjectDnd
	{
		LONG, SHORT;

		@Override
		public long key() 
		{
			return rest;
		}
	}
	
	abstract void refresh(Time time);
}
