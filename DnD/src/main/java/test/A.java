package test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("some")
public class A extends TestSome
{
	
	//@JsonProperty("dura")
	String name = "Dora";
}