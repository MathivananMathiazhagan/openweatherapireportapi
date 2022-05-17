package com.weather.report.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.weather.report.api.config.RateLimitInterceptor;

@SpringBootApplication(exclude =SecurityAutoConfiguration.class)
@EnableCaching
public class WeatherReportAPIApplication implements WebMvcConfigurer {

	@Autowired
    @Lazy
    private RateLimitInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
            .addPathPatterns("/api/weather/**");
    }
    
	public static void main(String[] args) {
		SpringApplication.run(WeatherReportAPIApplication.class, args);
	}
	
	/*
	 * CREATE TABLE T_WEATHER_DATA(ID BIGINT PRIMARY KEY auto_increment, CITY_ID INT, CITY_NAME
	  VARCHAR(255), WEATHER VARCHAR(255), TEMP DOUBLE, CREATED_DATE DATE);
	  
	  t_weather_data (id, city_id, city_name, created_date, temp, weather)
	 * 
	 */
}
