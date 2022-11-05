package com.dnd.dndTable.factory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.dnd.botTable.GameTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {

	private static final ObjectMapper mapper = getObjectMapper();
	
	public static JsonNode parse(String src) throws JsonMappingException, JsonProcessingException
	{
		return mapper.readTree(src);
	}
	
	public static <T> T fromJson(JsonNode node, Class<T> clazz) throws JsonProcessingException, IllegalArgumentException
	{
		return mapper.treeToValue(node, clazz);
	}
	
	public static JsonNode toJson(Object object)
	{
		return mapper.valueToTree(object);
	}
	
	public static String stingify(JsonNode node) throws JsonProcessingException
	{
		ObjectWriter objectWriter = mapper.writer();
		
		return objectWriter.writeValueAsString(node);
	}
	
	public static <T> T fromFileJson(String file, Class<T> clazz) throws IOException
	{
	
		Path filePath = Path.of(file);
		String json = Files.readString(filePath) ;
		return fromJson(parse(json), clazz);
		
	}
	
	public static <T> void backUp(String file, Map<Long, GameTable> map) throws StreamWriteException, DatabindException, IOException
	{
		mapper.writeValue(new File(file), map);
	}
		
	private static ObjectMapper getObjectMapper()
	{
		ObjectMapper defaultObjectMapper = new ObjectMapper();
		defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		defaultObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return defaultObjectMapper;
	}
	
	
}
