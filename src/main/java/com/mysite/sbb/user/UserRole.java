package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {

	// ADMIN, USER 상수 각각 'ROLE_ADMIN', 'ROLE_USER'라는 값을 저장
	// 상수 = @Setter 필요 X
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

	UserRole(String value) {
		this.value = value;
	}

	private String value;
}
