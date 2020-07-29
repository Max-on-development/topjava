package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalTime;

final class StringToLocalTime implements Converter<String, LocalTime> {

    @Nullable
    public LocalTime convert(String source) {
        return source.length()>0 ? LocalTime.parse(source) : null;
    }
}
