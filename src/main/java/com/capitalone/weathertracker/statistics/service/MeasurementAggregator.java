package com.capitalone.weathertracker.statistics.service;

import java.util.List;

import com.capitalone.weathertracker.measurements.model.Measurement;
import com.capitalone.weathertracker.statistics.model.AggregateResult;
import com.capitalone.weathertracker.statistics.model.Statistic;

public interface MeasurementAggregator {
  List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistic> stats);
}
