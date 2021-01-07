package org.trg;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONValue;

public class Representation {

	@Override
	public String toString() {
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try 
		{
			return objectMapper.writeValueAsString(this);
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public JSONObject toJSONObject()
	{
		JSONObject json=new JSONObject();

		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try 
		{
			String jsonString= objectMapper.writeValueAsString(this);
			json = (JSONObject) JSONValue.parse(jsonString);
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
