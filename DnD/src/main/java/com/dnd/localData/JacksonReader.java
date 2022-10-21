package com.dnd.localData;

import java.io.File;
import java.io.IOException;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonReader {

	private ObjectMapper mapper = new ObjectMapper();

	public static CharacterDnd getCharacter(String name)
	{
		CharacterDnd character = (CharacterDnd) mapper.readValue(new File("conector.json"), CharacterDnd.class);
			}
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        
        try {

            // JSON file to Java object
            Staff staff = mapper.readValue(new File("file.json"), Staff.class);

            // JSON string to Java object
         

            // compact print
           
            System.out.println(staff);
            // pretty print
            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);
            
            System.out.println(prettyStaff1);
           

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}



