package com.dnd.dndTable.factory.inerComands;

import java.io.IOException;

import com.dnd.dndTable.factory.Json;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import test.A;

public class InerComadDeserializer extends JsonDeserializer<InerComand>{

	@Override
	public InerComand deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		JsonNode node = p.getCodec().readTree(p);
		if(node.has("some"))
		{
			InerComand a = Json.fromJson(node, InerComand.class);
			
		}
		return null;
	}
	
}
