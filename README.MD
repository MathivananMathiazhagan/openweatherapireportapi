This project is developed to pull weather report from openweatherapi.org and save into database.

Steps to run this project:

1. download and import as maven project into your ide
2. perform maven update if errors on the project
3. run WeatherReportAPIApplication application as spring boot app or java application
4. connect H2 console using below url: http://localhost:8080/h2-console/
5. insert below DDL script in h2 database

Create table script:

CREATE TABLE T_WEATHER_DATA(ID BIGINT PRIMARY KEY auto_increment, CITY_ID INT, CITY_NAME
	  VARCHAR(255), WEATHER VARCHAR(255), TEMP DOUBLE, CREATED_DATE DATE);
    
 6. open  SOAPUI/Postman to hit api requests. below are the valid api keys to get wheather report and each api key request is limited to 5 in 1 hr. 
 
 54dd6746e3630a5fb08d812a1165a32d
 89416db7e9215d3d5743b7e0db1d41f1
 5a6fba4d0cf0fb9061db206ed9f79a03
 ee24389aa6eea77b69c5ca6bacf5172c
 3f152912dd9247691ad2317668025a54

curl --location --request GET 'http://localhost:8080/api/weather/Londons/uk' \
--header 'Content-Type: application/json' \
--header 'api-key: 5a6fba4d0cf0fb9061db206ed9f79a03'

