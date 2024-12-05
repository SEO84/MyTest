package com.busanit501.springex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

// <E> 제너릭으로, Element, 해당 타입을 유연하게 설정.
// PageResponseDTO 응답을 하는 건, 페이징 처리
// 페이징 처리 도메인(예시, Todo, Member, Reply, Product 등.)
@Getter
@ToString
public class PageResponseDTO<E> {
    // 서버 -> 웹 , 페이징 준비물 전달.
    // 준비물
    // 1) 보여줄 todo 게시물 10개
    // 2) 전체 갯수 ,
    // 3) 현재 페이지 번호 page, PageRequestDTO
    // 4) 시작 페이지 번호(start) , 끝나는 페이지 번호(end)
    // 스프링 부트 -> Pagination 등을 이용해서 조금더 편하게 구현할 예정.
    private int page;
    private int size; // 페이징당 보여줄 갯수
    private int total;

    private int start;
    private int end;

    // 이전페이지 존재여부
    private boolean prev;
    // 다음페이지 존재여부
    private boolean next;

    // 웹에 전달할 페이징 처리된 게시글 10개, 임의로
    private List<E> dtoList;

    //방법2,
    // 생성자를 특정이름으로 직접 정의해서, 호출해서 사용함.
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, int total,
                           PageRequestDTO pageRequestDTO) {
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        // start, end, prev,next 초기화가 필요함.
        this.end = ((int) Math.ceil(page / 10.0)) * 10;
        this.start = this.end - 9;
        // last
        // total : 75개, 화면에 페이지 번호를 10개씩 출력,
        // last : 8
        // end : 10
        // 10 > 8 ? last(8) : end(10),
        // 결론, end = 8

        //예시2)
        // total : 123개, 화면에 페이지 번호를 10개씩 출력,
        // last : 13
        // end :  현재  page 1, -> end = 10
        // 10 > 13 ? last(13) : end(10) ,
        // 결론, end = 10
        int last = (int)(Math.ceil(total/10.0));
        this.end = end > last ? last :end;

        this.prev = this.start > 1;
        //예시, total = 123, end 10, size 10
        //예시2, total = 85, end 10, size 10
        this.next = total > this.end * this.size;

    }


}
