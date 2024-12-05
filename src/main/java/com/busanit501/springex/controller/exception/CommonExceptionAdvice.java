package com.busanit501.springex.controller.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@Log4j2
@ControllerAdvice
public class CommonExceptionAdvice {

    // 우리가 봐야 할 에러 메세지.
    // @Controller : 1)화면 제공 2)데이터 제공
    // @RestController (@ResponseBody 내포가 되어 있음)
    // :  2)데이터 제공만
    @ResponseBody //서버에서 데이터만 전달하는 애너테이션.
//    @ExceptionHandler(NumberFormatException.class)
//    public String exceptNumber(NumberFormatException numberFormatException) {
//        return "numberFormatException:" + numberFormatException.getMessage();
    @ExceptionHandler(Exception.class)
    public String exceptCommon(Exception exception) {
        log.error("예외가 발생할 경우 동작하는 클래스 ");
        log.error(exception.getMessage());
        // 서버 -> 웹브라우저, 데이터만 전달 할 예정.
        // 리턴 타입 : String , 문자열로 데이터만 전달함.
        StringBuffer buffer = new StringBuffer("<ul>");
        buffer.append("<li>" + exception.getMessage() + "</li>");

        // 예외가 발생하는 과정을 추적하는 코드도 같이 추가.
        // Stream 으로 , 여러 단계를 표현하기.
        // exception.getStackTrace() : 목록.
        //
        Arrays.stream(exception.getStackTrace())
                .forEach(t -> buffer.append("<li>" + t + "</li>"));

        buffer.append("</ul>");
        return buffer.toString();


    } //

    //404 페이지 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "custom404page";
    }


}
