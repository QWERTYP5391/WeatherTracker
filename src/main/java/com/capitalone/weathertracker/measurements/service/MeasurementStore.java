package com.capitalone.weathertracker.measurements.service;

import com.capitalone.weathertracker.measurements.model.Measurement;

import java.time.ZonedDateTime;

public interface MeasurementStore {
  void add(Measurement measurement);

  Measurement fetch(ZonedDateTime timestamp);
}
