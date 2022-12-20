package test;

import java.io.IOException;

import com.dnd.dndTable.factory.Json;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class TestDeserializer extends JsonDeserializer<TestSome> {

	@Override
	public TestSome deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		JsonNode node = p.getCodec().readTree(p);
		if(node.has("some"))
		{
			return Json.fromJson(node, A.class);
			
		}
		return Json.fromJson(node, TestSome.class);
	}

}
