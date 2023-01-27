package com.dnd.botTable.actions;

public class PoolActionBuilder extends BaseActionBuilder<PoolActionBuilder> 
{

	private BaseAction[][] pool;
	
	PoolActionBuilder(){}
	
	public PoolActionBuilder actionsPool(BaseAction[][] pool)
	{
		this.pool = pool;
		return this;
	}
	
	public PoolActions build()
	{
		PoolActions action = new PoolActions();
		action.replyButtons = this.replyButtons;
		action.mediator = this.mediator;
		action.name = this.name;
		action.key = this.key;
		action.location = this.location;
		if(this.cloud)
		{
			action.cloud = this.cloud;
			BaseAction[][] newPool = new BaseAction[this.pool.length + 1][];
			for(int i = 0; i < this.pool.length; i++)
			{
				newPool[i] = this.pool[i];
			}
			newPool[this.pool.length] = new BaseAction[] {Action.builder().name("ELIMINATION").build()};
			this.pool = newPool;
		}
		action.setPool(this.pool);
		return action;
	}
	
	@Override
	protected PoolActionBuilder self() 
	{
		return this;
	}

}
