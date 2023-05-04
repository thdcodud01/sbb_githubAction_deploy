package com.example.sbb.question;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.sbb.DataNotFoundException;
@RequiredArgsConstructor // questionRepository 가 생성될 때 생성자 함수가 초기화 되게 하는 annotation
@Service
public class QuestionService { // 데이터 처리를 위해 작성하는 클래스
    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) { // 낱개로 객체 하나만 return / Integer 은 null 값도 허용
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");        }
    }
}
