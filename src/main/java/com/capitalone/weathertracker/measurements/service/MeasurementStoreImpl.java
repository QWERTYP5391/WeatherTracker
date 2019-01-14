package com.capitalone.weathertracker.measurements.service;

import com.capitalone.weathertracker.measurements.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;

@Service
public class MeasurementStoreImpl implements MeasurementStore {

    private HashMap<ZonedDateTime, Measurement> measurementHashMap;

    @Autowired
    public MeasurementStoreImpl(HashMap<ZonedDateTime, Measurement> measurementHashMap) {
        this.measurementHashMap = measurementHashMap;
    }

    public void add(Measurement measurement) {
        measurementHashMap.put(measurement.getTimestamp(), measurement);
    }

    public Measurement fetch(ZonedDateTime timestamp) {
        return measurementHashMap.get(timestamp);
    }
}
