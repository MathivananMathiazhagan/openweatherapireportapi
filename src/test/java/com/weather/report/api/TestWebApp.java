
package com.weather.report.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherReportAPIApplication.class)
public class TestWebApp {

	@Test
	public void testWeatherAPI() {
	    UriComponents uriComponents = UriComponentsBuilder.newInstance()
	      .scheme("http").host("api.openweathermap.org/data/2.5/weather")
	      .path("").query("q={keyword}&appid={appid}").buildAndExpand("London,uk","1c9770dfaf3b327dd03510a4c07b7f2d");
	 
	     assertEquals("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=1c9770dfaf3b327dd03510a4c07b7f2d", uriComponents.toUriString());

	}
}
	