package com.capitalone.weathertracker.statistics.service;

import com.capitalone.weathertracker.measurements.model.Measurement;
import com.capitalone.weathertracker.statistics.model.AggregateResult;
import com.capitalone.weathertracker.statistics.model.Metric;
import com.capitalone.weathertracker.statistics.model.Statistic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementAggregatorImpl implements MeasurementAggregator {

    private boolean checkTemperature;
    private boolean checkDewPoint;
    private boolean checkPrecipitation;

    private double temperatureTotal;
    private double dewPointTotal;
    private double precipitationTotal;

    private double temperatureMax = Double.MIN_VALUE;
    private double temperatureLow = Double.MAX_VALUE;
    private double temperatureCount = 0;

    private double dewPointLow = Double.MAX_VALUE;
    private double dewPointMax = Double.MIN_VALUE;
    private double dewPointCount = 0;

    private double precipitationMax = Double.MIN_VALUE;
    private double precipitationLow = Double.MAX_VALUE;
    private double precipitationCount = 0;


    public List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistic> stats) {
        setWhichMetricsToCheck(metrics);
        List<AggregateResult> aggregateResults = new ArrayList<>();

        for (Measurement measurement : measurements) {

            Double currentTemperature = measurement.getMetric(Metric.TEMPERATURE.getMetric());
            Double currentDewPoint = measurement.getMetric(Metric.DEW_POINT.getMetric());
            Double currentPrecipitation = measurement.getMetric(Metric.PRECIPITATION.getMetric());


            if (currentTemperature != null) {
                temperatureTotal += currentTemperature;
                temperatureCount++;
                temperatureLow = Math.min(currentTemperature, temperatureLow);
                temperatureMax = Math.max(currentTemperature, temperatureMax);
            }

            if (currentDewPoint != null) {
                dewPointTotal += currentDewPoint;
                dewPointCount++;
                dewPointLow = Math.min(currentDewPoint, dewPointLow);
                dewPointMax = Math.max(currentDewPoint, dewPointMax);
            }

            if (currentPrecipitation != null) {
                precipitationTotal += currentPrecipitation;
                precipitationCount++;
                precipitationLow = Math.min(currentPrecipitation, precipitationLow);
                precipitationMax = Math.max(currentPrecipitation, precipitationMax);
            }
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



    private void loadAggregateResults(List<Statistic> stats, List<AggregateResult> aggregateResults) {
        if (checkTemperature && temperatureCount > 0) {
            loadAggregateResultsByMetric(stats, temperatureLow, temperatureMax, temperatureTotal / temperatureCount, Metric.TEMPERATURE.getMetric(), aggregateResults);
        }
        if (checkDewPoint && dewPointCount > 0) {
            loadAggregateResultsByMetric(stats, dewPointLow, dewPointMax, dewPointTotal / dewPointCount, Metric.DEW_POINT.getMetric(), aggregateResults);
        }
        if (checkPrecipitation && precipitationCount > 0) {
            loadAggregateResultsByMetric(stats, precipitationLow, precipitationMax, precipitationTotal / precipitationCount, Metric.PRECIPITATION.getMetric(), aggregateResults);
        }
    }

    private void loadAggregateResultsByMetric(List<Statistic> stats, double low, double high, double count, String metric, List<AggregateResult> aggregateResults) {
        for (Statistic stat : stats) {
            if (stat.equals(Statistic.MIN)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.MIN, low));
            } else if (stat.equals(Statistic.MAX)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.MAX, high));
            } else if (stat.equals(Statistic.AVERAGE)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.AVERAGE, count));
            }
        }
    }
}
