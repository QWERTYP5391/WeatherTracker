package com.capitalone.weathertracker.statistics.model;

public enum Metric {

    TEMPERATURE("temperature"),
    DEW_POINT("dewPoint"),
    PRECIPITATION ("precipitation");

    private String metric;

    public String getMetric(){
        return this.metric;
    }

     Metric(String metric){
        this.metric = metric;
    }
}
