package com.weather.report.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.weather.report.api.service.WeatherService;


@RestController
@RequestMapping("/api")
@Validated
public class WeatherReportController {
	
	private static final Logger Log = LoggerFactory.getLogger(WeatherReportController.class);
	
	@Autowired
	WeatherService weatherService;

	@GetMapping("/weather/{cityName}/{countryName}")
	public ResponseEntity<?> getWeatherDetailsByCityName(
			@PathVariable(name="cityName", required=true) String cityName, @PathVariable(name="countryName", required=true) String countryName,
			@RequestHeader (name = "api-key", required = true) String apiKey) throws JsonMappingException, JsonProcessingException {
		Log.info("Fetching weather details with cityName: {}", cityName, apiKey);
		
		JSONObject jsonObj = weatherService.getWeatherReport(cityName, countryName, apiKey);
		if (jsonObj.isEmpty()) {
			ResponseEntity<?> responseEntity = new ResponseEntity<Object>("No Response from API", HttpStatus.NOT_FOUND);
			return responseEntity;
		}
		return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
	}
	
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    public Map<String, String> handleValidationExceptions(HttpClientErrorException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        return errors;
    }
	
}
