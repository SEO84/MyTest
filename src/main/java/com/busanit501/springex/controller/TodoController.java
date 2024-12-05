
package com.busanit501.springex.controller;

import com.busanit501.springex.dto.PageRequestDTO;
import com.busanit501.springex.dto.PageResponseDTO;
import com.busanit501.springex.dto.TodoDTO;
import com.busanit501.springex.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller// 1)화면 2)데이터 제공.
@RequestMapping("/todo")
// 웹브라우저에서 아래의 경로로 오는 url 전부 여기 컨트롤러가 받아서 작업함.
// localhost:8080/todo/
@Log4j2
@RequiredArgsConstructor
public class TodoController {

//    @Autowired
//    private TodoService todoService;

    private final TodoService todoService;



    // localhost:8080/todo/list
    @RequestMapping("/list")
    // 기존 전체 페이지 출력 -> 페이징 처리된 목록 요소만 출력.
//    public void list(Model model) {
    public void list(@Valid PageRequestDTO pageRequestDTO ,
                     BindingResult bindingResult,
                     Model model) {
        log.info("TodoController list : 화면제공은 해당 메서드 명으로 제공함.");
        if(bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        PageResponseDTO<TodoDTO> pageResponseDTO = todoService.getListWithPage(pageRequestDTO);
        log.info("TodoController list 데이터 유무 확인 :" + pageResponseDTO);
        //데이터 탑재. 서버 -> 웹
        model.addAttribute("pageResponseDTO", pageResponseDTO);

    }

    // localhost:8080/todo/register
    // 1) 글작성 폼, 화면 -> get
    // 2) 글작성 로직 처리 -> post
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register() {
        log.info("TodoController register : 화면제공은 해당 메서드 명으로 제공함.");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    //@Valid TodoDTO todoDTO : 검사 대상 클래스,
    // BindingResult bindingResult : 검사 결과의 오류를 모아두는 임시 공간
    // RedirectAttributes redirectAttributes : 서버 -> 웹 , 데이터 전달하는 도구
    public String registerPost(@Valid TodoDTO todoDTO, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        log.info("TodoController register post 로직처리: ");
        log.info("TodoController register post  todoDTO : " + todoDTO);

        // 유효성 체크 -> 유효성 검증시, 통과 안된 원인이 있다면,
        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            // 1회용으로, 웹 브라우저에서, errors , 키로 조회 가능함. -> 뷰 ${errors}
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }
        //검사가 통과가 되고, 정상 입력
        todoService.register(todoDTO);

        return "redirect:/todo/list";
    }

    // 상세조회, 컨트롤러, 서비스 연결 부분,
    // http://localhost:8080/todo/read?tno=9
    // 파라미터 자동 수집 부분 많이 활용.
    // tno, 서버에서 바로 이용가능.
    // 파리미터로 (TodoDTO todoDTO), 웹에서 넘어온 정보는
    //model.addAttribute("todoDTO", todoDTO) 없이도,
    // 뷰에서 -> EL 표기법으로 바로 사용가능 ${todoDTO}
    @RequestMapping("/read")
    public void read(Long tno, Model model) {
        log.info("TodoController read :");
        TodoDTO todoDTO = todoService.getOne(tno);
        log.info("TodoController read 데이터 유무 확인 :" + todoDTO);
        //데이터 탑재. 서버 -> 웹
        model.addAttribute("todoDTO", todoDTO);

    }


    // 수정 1) 폼 2) 로직 처리
    @RequestMapping("/update")
    public void update(Long tno, Model model) {
        log.info("TodoController update :");
        TodoDTO todoDTO = todoService.getOne(tno);
        log.info("TodoController update 데이터 유무 확인 :" + todoDTO);
        //데이터 탑재. 서버 -> 웹
        model.addAttribute("todoDTO", todoDTO);

    }

    //수정 로직 처리
    @PostMapping("/update")
    // 수정할 항목을 모두 받아서, TodoDTO 담습니다. 여기에 tno 도 포함 시키기
    public String updateLogic(@Valid TodoDTO todoDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        // 유효성 체크 -> 유효성 검증시, 통과 안된 원인이 있다면,
        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            // 1회용으로, 웹 브라우저에서, errors , 키로 조회 가능함. -> 뷰 ${errors}
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/todo/update";
        }

        // 수정하는 로직 필요함.
        // 주의사항, 체크박스의 값의 문자열 on 전달 받습니다.
        log.info("todoDTO확인 finished의 변환 여부 확인. : " + todoDTO);

        todoService.update(todoDTO);
        return "redirect:/todo/list";
    }



    // 삭제
    @PostMapping("/delete")
    public String delete(Long tno) {
        todoService.delete(tno);
        return "redirect:/todo/list";
    }

    // 페이징,

    // 검색,

    // 필터

}
