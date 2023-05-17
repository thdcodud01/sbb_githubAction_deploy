package com.example.sbb.user;

import lombok.Getter;

@Getter // 상수형 자료형이므로 Getter만 사용
public enum UserRole { // 열거 자료형 enum : 고정된 상수들의 집합을 정의하는데 사용되는 데이터 형식
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }
    private String value;
}
