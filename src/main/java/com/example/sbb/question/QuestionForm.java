package com.example.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수항목입니다.") // 해당 값이 null 또는 빈 문자열 허용 x & message 는 검증 실패시 화면에 표시할 오류 메시지
    @Size(max=200) // 최대길이 200 byte
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;
}

