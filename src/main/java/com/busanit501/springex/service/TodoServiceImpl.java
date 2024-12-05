package com.busanit501.springex.service;

import com.busanit501.springex.domain.TodoVO;
import com.busanit501.springex.dto.PageRequestDTO;
import com.busanit501.springex.dto.PageResponseDTO;
import com.busanit501.springex.dto.TodoDTO;
import com.busanit501.springex.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoMapper todoMapper;
    private final ModelMapper modelMapper;

    @Override
    public void register(TodoDTO todoDTO) {
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        todoMapper.insert(todoVO);

    }

    // mapper , TodoVO 타입 요소로 목록을 받아옴.
    // 화면에 전달하는 타입, TodoDTO 타입으로 변환
    @Override
    public List<TodoDTO> getAll() {
        List<TodoDTO> list = todoMapper.selectAll().stream()
                .map(vo -> modelMapper.map(vo,TodoDTO.class))
                .collect(Collectors.toList());
        return list;
    }



    @Override
    public TodoDTO getOne(Long tno) {
        TodoVO todoVO= todoMapper.selectOne(tno);
        TodoDTO todoDTO = modelMapper.map(todoVO,TodoDTO.class);
        return todoDTO;
    }

    @Override
    public void delete(Long tno) {
        todoMapper.delete(tno);
    }

    @Override
    public void update(TodoDTO todoDTO) {
        TodoVO todoVO = modelMapper.map(todoDTO,TodoVO.class);
        todoMapper.update(todoVO);

    }

    @Override
    public PageResponseDTO<TodoDTO> getListWithPage(PageRequestDTO pageRequestDTO) {
        // 준비물,
        // 서버 -> 웹 , 전달한 준비물 1) 전체갯수 2) 페이징 처리 todo 목록, 3) pageRequestDTO
//        1) 전체갯수
        int total = todoMapper.getCount(pageRequestDTO);
//        2) 페이징 처리 todo 목록 , TodoVO -> TodoDTO 변환해서 전달.
        List<TodoDTO> dtoList = todoMapper.selectList(pageRequestDTO).stream()
                .map(vo -> modelMapper.map(vo,TodoDTO.class))
                .collect(Collectors.toList());
        // 준비물을 가지고 , 응답할 PageResponseDTO, 생성자 통해서 초기화 작업.
        PageResponseDTO<TodoDTO> pageResponseDTO = PageResponseDTO.<TodoDTO>withAll()
                .total(total)
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .build();
        // 기존 방법1, 빌더 패턴
//        PageResponseDTO<TodoDTO> pageResponseDTO = PageResponseDTO.builder()
//                .total(total)
//                .dtoList(dtoList)
//                .pageRequestDTO(pageRequestDTO)
//                .build();

        return pageResponseDTO;
    }


}
