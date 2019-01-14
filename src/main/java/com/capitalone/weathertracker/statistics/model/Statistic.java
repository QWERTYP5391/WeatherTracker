package com.capitalone.weathertracker.statistics.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Statistic {
  @JsonProperty("min") MIN,
  @JsonProperty("max") MAX,
  @JsonProperty("average") AVERAGE,
}
