package com.dnd.dndTable;

public interface Refreshable {

	public enum Time implements ObjectDnd
	{
		LONG("LONG"), SHORT("SHORT");

		String name;
		
		Time(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return name;
		}
		@Override
		public long key() 
		{
			return REST;
		}
	}
	
	abstract void refresh(Time time);
}
