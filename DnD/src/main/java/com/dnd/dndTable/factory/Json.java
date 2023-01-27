package com.dnd.dndTable.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.dnd.Source;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {

	private static final ObjectMapper mapper = getObjectMapper();
	
	public static JsonNode parse(String src) throws IOException
	{
		return mapper.readTree(src);
	}

	public static <T> T fromJson(JsonNode node, Class<T> clazz) throws JsonProcessingException, IllegalArgumentException
	{
		return mapper.treeToValue(node, clazz);
	}

	public static <T> T fromFileJson(String file, Class<T> clazz) throws IOException
	{
		Path filePath = Path.of(file);
		String json = Files.readString(filePath) ;
		return fromJson(parse(json), clazz);
	}

	public static <T>T convertor(Object object, Class<T> subject)
	{
		JsonNode prepear = Json.toJson(object);
		try {
			return Json.fromJson(prepear, subject);
		} catch (JsonProcessingException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonNode toJson(Object object)
	{
		return mapper.valueToTree(object);
	}

	public static String stingify(JsonNode node) throws IOException
	{
		ObjectWriter objectWriter = mapper.writer();

		return objectWriter.writeValueAsString(node);
	}

	private static ObjectMapper getObjectMapper()
	{
		ObjectMapper defaultObjectMapper = new ObjectMapper();
		defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		defaultObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return defaultObjectMapper;
	}

	
	public static Source fromJson(JsonNode node, Source source) throws IllegalArgumentException, IOException
	{
		String file = source.source();
		Path filePath = Path.of(file);
		String json = Files.readString(filePath) ;
		return fromJson(parse(json), Source.class);
	}

}
