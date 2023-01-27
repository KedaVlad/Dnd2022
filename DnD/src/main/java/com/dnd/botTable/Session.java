package com.dnd.botTable;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.ButtonName;
import com.dnd.KeyWallet;

class Session
{
	static Session SESSION;
	Users USERS;
	Moderator MODER;

	private Session()
	{
		USERS = Users.getInstance();
		MODER = Moderator.getInstance();
	}

	public static Session getInstance()
	{
		if(SESSION == null)
		{
			SESSION = new Session();
		}
		return SESSION;
	}

	protected User execute(Update update)
	{
		long id; 
		if(update.hasCallbackQuery())
		{
			id = update.getCallbackQuery().getMessage().getChatId();
		}
		else
		{
			id = update.getMessage().getChatId();
		}
		return 	MODER.useTable(USERS.initialize(id), update);
	
	}
}

class Users implements Serializable
{
	private static final long serialVersionUID = 1L;
	static Users USERS;
	Map<Long, User> users;

	private Users() 
	{
		users = new HashMap<>();
	}
	
	void addUser(User user)
	{
		user.tableKey = Moderator.MODER.reserv;
		users.put(user.ID, user);
	}

	User initialize(long key)
	{
		if(users.containsKey(key))
		{
			return users.get(key);
		}
		else
		{
			addUser(new User(key));
			return users.get(key);
		}
	}

	public static Users getInstance()
	{
		if(USERS == null)
		{
			try {
				USERS = DataControler.download();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return USERS;
	}
}

class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	final long ID;
	long tableKey;
	final CharactersPool CHARACTERS;
	final Script SCRIPT;

	protected User(long ID) 
	{
		this.ID = ID;
		SCRIPT = new Script(ID);
		CHARACTERS = new CharactersPool(SCRIPT.trash);
	}
}

class Script implements Serializable
{
	private static final long serialVersionUID = 1L;
	final List<Act> mainTree;
	final List<Integer> trash;
	Act target;

	Script(long id) 
	{
		mainTree = new ArrayList<>();
		trash = new ArrayList<>();
		mainTree.add(Act.builder().name(id+"").build());
	}
	
	List<Integer> trashThrowOut()
	{
		List<Integer> throwed = new ArrayList<>();
		throwed.addAll(trash);
		trash.clear();
		return throwed;
	}
}


class Moderator
{
	static Moderator MODER;

	long reserv;
	private Map<Long, Table> gameTables;

	private Moderator()
	{
		gameTables = new HashMap<>();
	}

	User useTable(User user, Update update)
	{
		for(Long key: gameTables.keySet())
		{
			if(key == user.tableKey)
			{
				if(gameTables.get(key).execute(user, update))
				{
					return user;
				}
				else
				{
					gameTables.get(reserv).execute(user, update);
					return user;
				}
			}
		}
		return null;
	}

	void addTable(Table table)
	{
		MODER.gameTables.put(table.tableKey, table);
		reserv = table.tableKey;
	}

	public static Moderator getInstance()
	{
		if(MODER == null)
		{
			MODER = new Moderator();
		}
		return MODER;
	}

}

abstract class Table implements KeyWallet, ButtonName
{
	final long tableKey;

	Table(long tableKey)
	{
		this.tableKey = tableKey;
	}

	protected abstract boolean execute(User user, Update update);
}




