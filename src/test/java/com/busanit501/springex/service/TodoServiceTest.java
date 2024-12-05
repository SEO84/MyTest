package com.busanit501.springex.service;

import com.busanit501.springex.dto.PageRequestDTO;
import com.busanit501.springex.dto.PageResponseDTO;
import com.busanit501.springex.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 테스트 설정.
//JUnit4 테스트 설정. @Runwith
// 설정 파일의 경로를 지정.
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
//방법2
//@RequiredArgsConstructor
public class TodoServiceTest {

    // 방법1
    @Autowired
    private TodoService todoService;

    //방법2
//    private final TodoService todoService;

    @Test
    public void testRegister() {
        TodoDTO todoDTO = TodoDTO.builder()
                .title("샘플데이터 서비스에서 입력")
                .dueDate(LocalDate.now())
                .writer("이상용")
                .build();
        todoService.register(todoDTO);
    } //

    @Test
    public void testGetAll() {
        List<TodoDTO> list = todoService.getAll();
        for (TodoDTO todoDTO : list) {
            log.info("todoDTO : " + todoDTO);
        }
    } //

    @Test
    public void testGetOne() {
        TodoDTO todoDTO = todoService.getOne(9L);
        log.info("todoDTO : " + todoDTO);

    } //

    @Test
    public void testDelete() {
        todoService.delete(8L);
    } //

    @Test
    public void testUpdate() {
        // 업데이트 할 더미 데이터 필요, TodoVO
        TodoDTO todoDTO = TodoDTO.builder()
                .tno(5L)
                .title("수정 제목")
                .dueDate(LocalDate.now())
                .finished(true)
                .build();

        todoService.update(todoDTO);
    }

    @Test
    public void testPageList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(180)
                .size(10)
                .build();
        // PageResponseDTO, 안에는 , page, size, skip, start,end,
        // prev, next,  페이징된 목록 요소들
        PageResponseDTO<TodoDTO> list = todoService.getListWithPage(pageRequestDTO);
        list.getDtoList().stream().forEach(dto -> log.info("dto : " + dto));
        log.info("list total : " + list.getTotal());
        log.info("list prev : " + list.isPrev());
        log.info("list next : " + list.isNext());
        log.info("list start : " + list.getStart());
        log.info("list end : " + list.getEnd());

    }

}//
