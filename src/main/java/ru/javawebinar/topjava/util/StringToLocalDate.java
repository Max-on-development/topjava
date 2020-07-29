package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public final class StringToLocalDate implements Converter<String, LocalDate> {

    @Nullable
    public LocalDate convert(String source) {
        return source.length() > 0 ? LocalDate.parse(source) : null;
    }
}
