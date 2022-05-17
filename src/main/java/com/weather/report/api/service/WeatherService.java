package com.weather.report.api.service;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.weather.report.api.model.Weather;
import com.weather.report.api.model.WeatherDetails;
import com.weather.report.api.util.Constants;

@FunctionalInterface
public interface WeatherService {
	public JSONObject getWeatherReport(String cityName, String countryName, String apiKey) throws JsonMappingException, JsonProcessingException;
	
	default public JSONObject formatJSONResponse(Weather weather) {
		
		JSONObject jsonResp = new JSONObject();
			
		//jsonResp.put(Constants.JSON_ID, weather.getCityId());
		//jsonResp.put(Constants.JSON_NAME, weather.getCityName());
		jsonResp.put(Constants.JSON_WEATHER, weather.getWeather());
		//jsonResp.put(Constants.JSON_MAIN_TEMP, weather.getTemp());
		
		return jsonResp;
	}
	
	default public String formatWeatherListToString(WeatherDetails weatherDetails) {
		
		String formattedString = "";
		
		formattedString += weatherDetails.getWeatherInstance(0).getWeatherDescription();
		for (int i = 1; i < weatherDetails.getWeatherCount(); i++) {
			formattedString += ", " + weatherDetails.getWeatherInstance(i).getWeatherDescription();
		}
		
		return formattedString;
	}
}
