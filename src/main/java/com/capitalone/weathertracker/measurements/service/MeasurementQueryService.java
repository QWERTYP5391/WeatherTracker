package com.capitalone.weathertracker.measurements.service;

import com.capitalone.weathertracker.measurements.model.Measurement;

import java.util.List;
import java.time.ZonedDateTime;

public interface MeasurementQueryService {
  List<Measurement> queryDateRange(ZonedDateTime from, ZonedDateTime to);
}
