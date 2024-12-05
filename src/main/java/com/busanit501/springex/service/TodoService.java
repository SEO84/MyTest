package com.busanit501.springex.service;

import com.busanit501.springex.dto.PageRequestDTO;
import com.busanit501.springex.dto.PageResponseDTO;
import com.busanit501.springex.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    void register(TodoDTO todoDTO);

    List<TodoDTO> getAll();

    //페이징 처리된 목록
    // 준비물을 준비 해둠,
    // 웹 -> 서버 , 박스
    // 웹 <- 서버, 박스
    PageResponseDTO<TodoDTO> getListWithPage(PageRequestDTO pageRequestDTO);

    TodoDTO getOne(Long tno);

    void delete(Long tno);

    void update(TodoDTO todoDTO);
}
