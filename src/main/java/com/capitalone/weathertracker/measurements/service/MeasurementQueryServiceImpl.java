package com.capitalone.weathertracker.measurements.service;

import com.capitalone.weathertracker.measurements.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class MeasurementQueryServiceImpl implements MeasurementQueryService {
    private HashMap<ZonedDateTime, Measurement> measurementHashMap;

    @Autowired
    public MeasurementQueryServiceImpl(HashMap<ZonedDateTime, Measurement> measurementHashMap) {
        this.measurementHashMap = measurementHashMap;
    }

    public List<Measurement> queryDateRange(ZonedDateTime from, ZonedDateTime to) {
        List<Measurement> measurements = new ArrayList<>();

        Set<ZonedDateTime> zonedDateTimes = measurementHashMap.keySet();
        for (ZonedDateTime key : zonedDateTimes) {
            if (key.equals(from) || (key.isAfter(from) && key.isBefore(to))) {
                Measurement measurement = measurementHashMap.get(key);
                measurements.add(measurement);
            }
        }

        return measurements;
    }
}
