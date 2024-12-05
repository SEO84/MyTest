package com.busanit501.springex.controller.formatter;

import lombok.extern.log4j.Log4j2;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

// 웹 브라우저에서, 체크박스 체크된 값이 문자열 on으로 전달 받으면,
// 시스템에서 자동으로 , true, false 형태로 자동 변환해주는 기능을 추가함.
@Log4j2
public class CheckboxFormatter implements Formatter<Boolean> {

    @Override
    public Boolean parse(String text, Locale locale) throws ParseException {
        if(text == null){
            log.info("CheckboxFormatter : 체크가 안된 상태로 넘어올 경우");
            return false;
        }
        log.info("CheckboxFormatter : 체크가 된 상태로 넘어올 경우 text: " + text);
        boolean checkFinished = text.equals("on");
        return checkFinished;
    }

    @Override
    public String print(Boolean object, Locale locale) {
        return object.toString();
    }
}
