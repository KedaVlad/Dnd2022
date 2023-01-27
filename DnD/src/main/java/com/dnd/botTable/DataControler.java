package com.dnd.botTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.stream.events.Characters;


abstract class DataControler
{
	private static String ADRESS = "C:\\Users\\ALTRON\\git\\Dnd2022\\DnD\\LocalData\\reserve";
	static void save() throws IOException
	{
		File file = new File(ADRESS);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(Users.getInstance());
		objectOutputStream.close();
	}

	static Users download() throws IOException, ClassNotFoundException 
	{
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream = null;
		fileInputStream = new FileInputStream(ADRESS);
		objectInputStream = new ObjectInputStream(fileInputStream);
		Users users = (Users) objectInputStream.readObject();
		objectInputStream.close();
		return users;
	}
	
}
