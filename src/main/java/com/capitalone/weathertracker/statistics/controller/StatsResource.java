package com.capitalone.weathertracker.statistics.controller;

import java.time.ZonedDateTime;
import java.util.List;

import com.capitalone.weathertracker.measurements.model.Measurement;
import com.capitalone.weathertracker.measurements.service.MeasurementQueryService;

import com.capitalone.weathertracker.statistics.model.AggregateResult;
import com.capitalone.weathertracker.statistics.service.MeasurementAggregator;
import com.capitalone.weathertracker.statistics.model.Statistic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsResource {
    private final MeasurementQueryService queryService;
    private final MeasurementAggregator aggregator;

    public StatsResource(MeasurementQueryService queryService, MeasurementAggregator aggregator) {
        this.queryService = queryService;
        this.aggregator = aggregator;
    }

    @GetMapping
    public List<AggregateResult> getStats(
            @RequestParam("metric") List<String> metrics,
            @RequestParam("stat") List<Statistic> stats,
            @RequestParam("fromDateTime") ZonedDateTime from,
            @RequestParam("toDateTime") ZonedDateTime to
    ) {
        List<Measurement> measurements = queryService.queryDateRange(from, to);
       return aggregator.analyze(measurements, metrics, stats);
    }
}
