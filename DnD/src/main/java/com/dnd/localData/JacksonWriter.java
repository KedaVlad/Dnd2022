package com.dnd.localData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.util.Mapper;

import com.dnd.botTable.CharacterDndBot;
import com.dnd.botTable.GameTable;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonWriter {

	static ObjectMapper mapper = new ObjectMapper();
	
	static Conector conector = new Conector();
	 
	public static void saveCharacter(CharacterDnd character)  
	{
		File file = new File("conector.json");
		try {
			
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(character);
				
				
				objectOutputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
    public static void main(String[] args) {

       

        
       
        conector.getSession().add(new GameTable(231134314));
        
       
        conector.getSession().get(0).setActualGameCharacter(new CharacterDnd("Durf"));
        
        try {
       	
        	
            // Java objects to JSON file
            mapper.writeValue(new File("conector.json"), conector);
            save(new CharacterDnd("Deboris"));

            // Java objects to JSON string - compact-print
           String jsonString = mapper.writeValueAsString(conector);

            System.out.println(jsonString);
            // Java objects to JSON string - pretty-print
           

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}