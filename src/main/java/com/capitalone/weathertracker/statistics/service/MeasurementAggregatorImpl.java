package com.capitalone.weathertracker.statistics.service;

import com.capitalone.weathertracker.measurements.model.Measurement;
import com.capitalone.weathertracker.statistics.model.AggregateResult;
import com.capitalone.weathertracker.statistics.model.Metric;
import com.capitalone.weathertracker.statistics.model.Statistic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
public class MeasurementAggregatorImpl implements MeasurementAggregator {

    private boolean checkTemperature;
    private boolean checkDewPoint;
    private boolean checkPrecipitation;
    private double temperatureTotal;
    private double dewPointTotal;
    private double precipitationTotal;
    private TreeSet<Double> temperatures;
    private TreeSet<Double> dewPoints;
    private TreeSet<Double> precipitations;


    public List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistic> stats) {

        setWhichMetricsToCheck(metrics);

        List<AggregateResult> aggregateResults = new ArrayList<>();
        temperatures = new TreeSet<>();
        dewPoints = new TreeSet<>();
        precipitations = new TreeSet<>();

        for (Measurement measurement : measurements) {
            temperatureTotal = getTemperatureTotal(temperatures, temperatureTotal, measurement, Metric.TEMPERATURE);
            dewPointTotal = getTemperatureTotal(dewPoints, dewPointTotal, measurement, Metric.DEW_POINT);
            precipitationTotal = getTemperatureTotal(precipitations, precipitationTotal, measurement, Metric.PRECIPITATION);
        }

        loadAggregateResults(stats, aggregateResults);
        return aggregateResults;
    }

    private void setWhichMetricsToCheck(List<String> metrics) {
        for (String metric : metrics) {
            if (metric.equals(Metric.TEMPERATURE.getMetric())) {
                checkTemperature = true;
            } else if (metric.equals(Metric.DEW_POINT.getMetric())) {
                checkDewPoint = true;
            } else if (metric.equals(Metric.PRECIPITATION.getMetric())) {
                checkPrecipitation = true;
            }
        }
    }

    private double getTemperatureTotal(TreeSet<Double> set, double total, Measurement measurement, Metric metric) {
        Double value = measurement.getMetric(metric.getMetric());
        if (value != null) {
            set.add(value);
            total += value;
        }
        return total;
    }


    private void loadAggregateResults(List<Statistic> stats, List<AggregateResult> aggregateResults) {
        if (checkTemperature) {
            loadAggregateResultsByMetric(stats, temperatures, temperatureTotal, Metric.TEMPERATURE.getMetric(), aggregateResults);
        }
        if (checkDewPoint) {
            loadAggregateResultsByMetric(stats, dewPoints, dewPointTotal, Metric.DEW_POINT.getMetric(), aggregateResults);
        }
        if (checkPrecipitation) {
            loadAggregateResultsByMetric(stats, precipitations, precipitationTotal, Metric.PRECIPITATION.getMetric(), aggregateResults);
        }
    }

    private void loadAggregateResultsByMetric(List<Statistic> stats, TreeSet<Double> set, double total, String metric, List<AggregateResult> aggregateResults) {
        if (set.size() > 0) {
            for (Statistic stat : stats) {
                if (stat.equals(Statistic.MIN)) {
                    aggregateResults.add(new AggregateResult(metric, Statistic.MIN, set.first()));
                } else if (stat.equals(Statistic.MAX)) {
                    aggregateResults.add(new AggregateResult(metric, Statistic.MAX, set.last()));
                } else if (stat.equals(Statistic.AVERAGE)) {
                    aggregateResults.add(new AggregateResult(metric, Statistic.AVERAGE, total / set.size()));
                }
            }
        }
    }
}
