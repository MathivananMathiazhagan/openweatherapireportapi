package com.weather.report.api.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.weather.report.api.config.RateLimitInterceptor;
import com.weather.report.api.model.Weather;
import com.weather.report.api.model.WeatherDetails;
import com.weather.report.api.model.WeatherUrl;
import com.weather.report.api.repository.WeatherReportRepository;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	RestTemplate restTemp;

	@Autowired
	private WeatherUrl weatherData;

	@Autowired
	private WeatherReportRepository weatherReportRepository;
	
	private static final Logger Log = LoggerFactory.getLogger(RateLimitInterceptor.class);


	@Override
	public JSONObject getWeatherReport(String cityName, String countryName, String apiKey)
			throws JsonMappingException, JsonProcessingException {
		Log.info("getWeatherReport");
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(weatherData.getUrl())
				.path("").query("q={keyword}&appid={appid}")
				.buildAndExpand(cityName + "," + countryName, apiKey);
		String uri = uriComponents.toUriString();

		ResponseEntity<String> resp = restTemp.exchange(uri, HttpMethod.GET, null, String.class);

		WeatherDetails weatherDetails = new WeatherDetails(new JSONObject(resp.getBody()));

		return saveAndRetrieveResponse(weatherDetails);
	}

	private JSONObject saveAndRetrieveResponse(WeatherDetails weatherDetails) {

		Weather weatherObj = new Weather();

		weatherObj.setCityId(weatherDetails.getCityId());
		weatherObj.setCityName(weatherDetails.getCityName());
		weatherObj.setWeather(formatWeatherListToString(weatherDetails));
		weatherObj.setTemp(weatherDetails.getMainInstance().getTemp());

		weatherReportRepository.save(weatherObj);

		return formatJSONResponse(weatherObj);
	}

}
