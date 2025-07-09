package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// @RequestMapping을 이용해서 프리픽스(prefix 접두사) 지정해서 사용
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	// Controller 에 있는 repository 기능을 모두 service 에게 전달
	private final QuestionService questionService;
	// DI(객체 주입) --> UserService 추가
	private final UserService userService;

	// 질문 목록
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging); // db --> model 저장
		return "question_list"; // question_list.html에서 model 접근해서 사용

	}

	// 상세조회
	// @PathVariable("id") Integer id
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";

	}

	// @PreAuthorize("isAuthenticated()") --> 로그인이 된 경우만 실행 --> 로그인 x , 로그인 페이지로 강제
	// 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}

	// 스프링 시큐리티 Principal 객체를 매개변수로 선언
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		// principal 객체를 통해서 사용자명을 얻고, siteUser 객체를 얻음
		SiteUser siteUser = this.userService.getUser(principal.getName());
		// 질문이 등록 될 때 siteUser(글쓴이id) 저장
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}

}