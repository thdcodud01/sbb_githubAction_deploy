package com.example.sbb;

import com.example.sbb.answer.Answer;
import com.example.sbb.answer.AnswerRepository;
import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import com.example.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional; // null 값 허용


@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerRepository answerRepository; // 답변 데이터 처리를 위해서는 답변 리포지터리가 필요하므로 AnswerRepository 객체를 @Autowired로 주입

	//@Transactional // 끝날 때 까지 db session 이 종료되지 않게 해주는 annotation (메서드가 종료될 때까지 DB 세션이 유지)
	@Test
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}
}
