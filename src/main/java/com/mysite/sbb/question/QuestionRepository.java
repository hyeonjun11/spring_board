package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	Question findBySubject(String subject);

	Question findBySubjectAndContent(String subject, String content);

	List<Question> findBySubjectLike(String subject);

	// 질문 전체 목록을 지정된 페이지 단위로 나누어 가져온다
//	Page<Question> findAll(Pageable pageable);

	// Db에서 Question Entity를 조회한 결과를 페이징하여 반환함(중요)
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}