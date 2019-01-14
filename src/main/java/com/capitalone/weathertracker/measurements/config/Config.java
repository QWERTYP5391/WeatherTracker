package com.capitalone.weathertracker.measurements.config;

import com.capitalone.weathertracker.measurements.model.Measurement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.HashMap;


@Configuration
public class Config {


    @Bean
    public HashMap<ZonedDateTime, Measurement> getMeasurementMap(){
        return new HashMap<>();
    }
}
