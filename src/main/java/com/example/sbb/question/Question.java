package com.example.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.example.sbb.answer.Answer;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key & auto_increment가 자동으로 됨, strategy는 고유번호를 생성하는 옵션
    private Integer id;

    @Column(length = 200) // 컬럼 길이 제한 200
    private String subject;

    @Column(columnDefinition = "TEXT") // TEXT 는 글자 수 제한할 수 없는 경우에 사용
    private String content;

    private LocalDateTime createDate; // 정의 한 local 타임

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // Question 엔티티와 Answer 엔티티 간의 일대다 관계를 설정 & 질문을 삭제하면 그에 달린 답변들도 모두 함께 삭제하기 위해 cascade 사용
    private List<Answer> answerList; // question 하나에 answer 은 여러개이므로 question 엔티티에 추가할 답변의 속성은 list 형태로 구성
}