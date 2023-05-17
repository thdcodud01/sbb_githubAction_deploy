package com.example.sbb.question;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.sbb.DataNotFoundException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor // questionRepository 가 생성될 때 생성자 함수가 초기화 되게 하는 annotation
@Service
public class QuestionService { // 데이터 처리를 위해 작성하는 클래스
    private final QuestionRepository questionRepository;

    public List<Question> getList() { // 질문 목록을 조회하여 리턴하는 getList 메서드를 추가
        return this.questionRepository.findAll();
    }
    // 앞으로 작성할 컨트롤러들도 리포지터리를 직접 사용하지 않고 Controller -> Service -> Repository 구조로 데이터를 처리할 것
    public Question getQuestion(Integer id) { // 낱개로 객체 하나만 return & Integer 은 null 값도 허용 & id 값으로 Question 데이터를 조회하는 getQuestion 메서드를 추가
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) { // Question 객체는 Optional 객체이기 때문에 isPresent 메서드로 해당 데이터가 존재하는지 검사하는 로직 필요
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");        }
    }
    public void create(String subject, String content) { // 제목과 내용을 입력으로 하여 질문 데이터를 저장하는 create 메서드 생성
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 작성일시 역순 조회
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));// page는 조회할 페이지의 번호이고 10은 한 페이지에 보여줄 게시물의 갯수를 의미 & 게시물을 역순으로 조회하기 위해서는 위와 같이 PageRequest.of 메서드의 세번째 파라미터로 Sort 객체를 전달해야 함
        return this.questionRepository.findAll(pageable);
    }
}
