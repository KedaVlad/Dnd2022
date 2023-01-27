package com.dnd;

public interface Names 
{

	
	public enum SpellClass
	{
		WIZARD("Wizard"), SORCER("Sorcer"), ARTIFACER("Artifacer");
		
		String name;
		SpellClass(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return name;
		}
	}
	
	
	
	
	
	
	public enum TypeDamage
	{
		CHOPPING, CRUSHING, STICKING
	}
}
