package com.capitalone.weathertracker;

import java.time.ZonedDateTime;
import java.util.List;

import com.capitalone.weathertracker.measurements.model.Measurement;
import com.capitalone.weathertracker.measurements.service.MeasurementQueryService;
import com.capitalone.weathertracker.statistics.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
class NotImplementedService implements MeasurementQueryService, MeasurementAggregator {


  @Override
  public List<Measurement> queryDateRange(ZonedDateTime from, ZonedDateTime to) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistic> stats) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
