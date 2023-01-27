package com.dnd.botTable.actions;


public class PoolActions extends BaseAction
{

	private static final long serialVersionUID = 1L;
	private BaseAction[][] pool;
	
	PoolActions(){}
	
	public static PoolActionBuilder builder()
	{
		return new PoolActionBuilder();
	}

	@Override
	public String[][] buildButtons() 
	{
		String[][] answer = new String[pool.length][];
		
		for(int i = 0; i < pool.length; i++)
		{
			answer[i] = new String[pool[i].length];
			for(int j = 0; j < pool[i].length; j++)
			{
				answer[i][j] = pool[i][j].name;
			}
		}
		
		return answer;
	}

	@Override
	public BaseAction continueAction(String answer)
	{
		for(BaseAction[] line: pool)
		{
			for(BaseAction target: line)
			{
				if(target.name.equals(answer))
				{
					return target;
				}
			}
		}
		return null;
	}

	@Override
	public boolean hasButtons() 
	{
		return pool!= null && pool.length > 0;
	}

	@Override
	public boolean replyContain(String string) 
	{
		for(BaseAction[] line: pool)
		{
			for(BaseAction target: line)
			{
				if(target.name.equals(string))
				{
					return true;
				}
			}
		}
		return false;
	}

	public BaseAction[][] getPool() {
		return pool;
	}

	public void setPool(BaseAction[][] pool) {
		this.pool = pool;
	}

}
