package com.capitalone.weathertracker;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Configuration
class DateSerializationConfiguration {
  private final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendInstant(3)
    .toFormatter();

  private static final String ZONE_ID = "UTC";

  @Bean
  public DateTimeFormatter dateTimeFormatter() {
    return dateTimeFormatter;
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
      .registerModule(javaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  private Module javaTimeModule() {
    JavaTimeModule module = new JavaTimeModule();

    return module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(dateTimeFormatter));
  }

  @Component
  static class ZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(String source) {
      return ZonedDateTime.parse(source).withZoneSameInstant(ZoneId.of(ZONE_ID));
    }
  }
}
