package com.example.sbb.question;

import com.example.sbb.answer.AnswerForm;
import com.example.sbb.user.SiteUser;
import com.example.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequestMapping("/question") // 메서드 단위에서는 /question 를 생략한 그 뒷 부분만을 적으면 됨 & URL 매핑은 항상 /question 으로 시작해야 하는 규칙이 생긴 것
@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 생성하는 역할 -> 스프링 의존성 주입 규칙에 의해 questionRepository 객체가 자동으로 주입
@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    // 원래 ResponseBody 가 있었는데 question_list.html 파일이 템플릿 파일이여서 @ResponseBody 애너테이션은 필요없으므로 삭제
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) { // Model 객체는 자바 클래스와 템플릿 간의 연결고리 역할 (MVC pattern) & View와 Controller 사이에서 데이터를 주고받는 데 사용되는 객체로, Controller에서 생성한 데이터를 View에 전달하는 역할
        log.info("page:{}, kw:{}", page, kw);
        Page<Question> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw); // 화면에서 입력한 검색어를 화면에 유지하기 위해 kw 로 값지정
        return "question_list"; // list 메서드에서 question_list.html 템플릿 파일의 이름인 "question_list"를 return
    }
    @GetMapping(value = "/detail/{id}") // {}를 통해 id 값은 변할 수 있는 값임을 명시
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) { // 변하는 id 값을 얻을 때에는 @PathVariable 사용
        Question question = this.questionService.getQuestion(id); // QuestionController에서 QuestionService의 getQuestion 메서드를 호출하여 Question 객체를 템플릿에 전달할 수 있도록
        model.addAttribute("question", question);
        return "question_detail";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) { //  questionCreate 메서드는 question_form 템플릿을 렌더링하여 출력
        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create") // "질문 등록하기" 버튼을 통한 /question/create 요청
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) { // subject, content 항목을 지닌 폼이 전송되면 QuestionForm의 subject, content 속성이 자동으로 바인딩
        if (bindingResult.hasErrors()) { // 오류가 있는 경우에는 다시 폼을 작성하는 화면을 렌더링하게
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser); // QuestionService로 질문 데이터를 저장하는 코드
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
