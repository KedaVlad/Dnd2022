package com.dnd.localData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
		
	private static ObjectMapper getObjectMapper()
	{
		ObjectMapper defaultObjectMapper = new ObjectMapper();
		defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return defaultObjectMapper;
	}
	
	
}
