package com.capitalone.weathertracker.statistics.service;

import com.capitalone.weathertracker.measurements.model.Measurement;
import com.capitalone.weathertracker.statistics.model.Metric;
import com.capitalone.weathertracker.statistics.model.AggregateResult;
import com.capitalone.weathertracker.statistics.model.Statistic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
public class MeasurementAggregatorImpl implements MeasurementAggregator {

    private static boolean checkTemperature = false;
    private static boolean checkDewPoint = false;
    private static boolean checkPrecipitation = false;
    private TreeSet<Double> temperatures = new TreeSet<>();
    private TreeSet<Double> dewPoints = new TreeSet<>();
    private TreeSet<Double> precipitations = new TreeSet<>();
    private double temperatureTotal;
    private double dewPointTotal;
    private double precipitationTotal;

    public List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistic> stats) {

        setMetricsToCheck(metrics);

        List<AggregateResult> aggregateResults = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatureTotal = getTemperatureTotal(temperatures, temperatureTotal, measurement, Metric.TEMPERATURE);
            dewPointTotal = getTemperatureTotal(dewPoints, dewPointTotal, measurement, Metric.DEW_POINT);
            precipitationTotal = getTemperatureTotal(precipitations, precipitationTotal, measurement, Metric.PRECIPITATION);
        }

        loadAggregateResults(stats, aggregateResults, measurements.size());
        setMetricsBack();
        return aggregateResults;
    }

    private void setMetricsBack() {
        checkTemperature = false;
        checkDewPoint = false;
        checkPrecipitation = false;
    }

    private double getTemperatureTotal(TreeSet<Double> set, double total, Measurement measurement, Metric metric) {
        Double value = measurement.getMetric(metric.getMetric());
        if (value != null) {
            set.add(value);
            total += value;
        }
        return total;
    }

    private void setMetricsToCheck(List<String> metrics) {
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

    private void loadAggregateResults(List<Statistic> stats, List<AggregateResult> aggregateResults, int size) {
        if (checkTemperature) {
            loadAggregateResultsByMetric(stats, temperatures, temperatureTotal, Metric.TEMPERATURE.getMetric(), aggregateResults, size);
        }
        if (checkDewPoint) {
            loadAggregateResultsByMetric(stats, dewPoints, dewPointTotal, Metric.DEW_POINT.getMetric(), aggregateResults, size);
        }
        if (checkPrecipitation) {
            loadAggregateResultsByMetric(stats, precipitations, precipitationTotal, Metric.PRECIPITATION.getMetric(), aggregateResults, size);
        }
    }

    private void loadAggregateResultsByMetric(List<Statistic> stats, TreeSet<Double> set, double total, String metric, List<AggregateResult> aggregateResults, int size) {
        if (set.size() > 0) {
            if (stats.contains(Statistic.MIN)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.MIN, set.first()));
            }
            if (stats.contains(Statistic.MAX)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.MAX, set.last()));
            }
            if (stats.contains(Statistic.AVERAGE)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.AVERAGE, total / set.size()));
            }
        }
    }
}
