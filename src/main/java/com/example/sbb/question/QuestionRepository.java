package com.example.sbb.question;
import java.util.List;

import com.example.sbb.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository extends JpaRepository<Question, Integer> { // Question 엔티티의 pk 속성인 id 타입은 Integer
    Question findBySubject(String subject); // findBySubject 메서드를 사용하려면 QuestionRepository 인터페이스를 변경해야 함
    Question findBySubjectAndContent(String subject, String content); // 두 개의 속성을 And 조건으로 조회할때는 리포지터리에 이러한 메서드를 추가해야 함
    List<Question> findBySubjectLike(String subject); // 제목에 특정 문자열이 포함되어 있는 데이터를 조회
    Page<Question> findAll(Pageable pageable); // Pageable 객체를 입력으로 받아 Page<Question> 타입 객체를 리턴하는 findAll 메서드를 생성
}

// 리포지터리는 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는 메서드들(예: findAll, save 등)을 사용하기 위한 인터페이스이다.
// 데이터 처리를 위해서는 테이블에 어떤 값을 넣거나 값을 조회하는 등의 CRUD(Create, Read, Update, Delete)가 필요하다.
// 이 때 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 바로 리포지터리이다.