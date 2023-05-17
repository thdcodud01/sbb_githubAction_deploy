package com.example.sbb.answer;

import com.example.sbb.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}

// QuestionRepository 와 같은 방식
// 이제 repository 들을 통하여 테이블에 데이터를 저장하거나 조회 가능