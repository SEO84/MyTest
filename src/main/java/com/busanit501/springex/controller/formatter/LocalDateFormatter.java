package com.busanit501.springex.controller.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// LocalDate <-> String, 형변환 작업 기능.
// 기능으로 사용, 유틸 역할, 시스템에 빈으로 등록함.
public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        LocalDate localDate = LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return localDate;
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        String formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
        return formatter;
    }
}
