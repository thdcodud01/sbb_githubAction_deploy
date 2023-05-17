package com.example.sbb.answer;
import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    @PostMapping("/create/{id}") // @GetMapping과 동일하게 매핑을 담당하는 역할을 하지만 POST요청만 받아들일 경우에 사용 & /answer/create/{id}와 같은 URL 요청시 createAnswer 메서드가 호출되도록 @PostMapping으로 매핑
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult) { // before : 템플릿에서 답변으로 입력한 내용(content)을 얻기 위해 @RequestParam String content 추가 -> after : @Valid와 BindingResult를 사용하여 검증을 진행한다. 검증에 실패할 경우에는 다시 답변을 등록할 수 있는 question_detail 템플릿을 렌더링하게
        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent()); // AnswerService의 create 메서드를 호출하여 답변을 저장할수 있게
        return String.format("redirect:/question/detail/%s", id);
    }
}
