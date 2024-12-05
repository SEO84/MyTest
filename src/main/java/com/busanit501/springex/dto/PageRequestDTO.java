package com.busanit501.springex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    // 웹 -> 서버
    //예시: http://localhost:8080/todo/list?page=1&size=10
    // 화면에서 전달할 페이지 번호, 크기를 받을 상자필요.
    // 현재 페이지 번호
    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1;

    // 페이징당 출력할 데이터 갯수
    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;

    // 데이터를 얼마나 스킵 할지 정할 기능.
    public int getSkip() {
        return (page-1)*size;
    }

}




