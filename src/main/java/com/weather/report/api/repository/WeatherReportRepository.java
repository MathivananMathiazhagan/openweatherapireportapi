package com.weather.report.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.report.api.model.Weather;

public interface WeatherReportRepository extends JpaRepository<Weather, Long> {

}