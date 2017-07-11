package com.lunamaan.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunamaan.account.entity.Member;
import com.lunamaan.account.repository.MemberRepository;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberRepository memberRepository;
	
	@RequestMapping("/info")
	public String memberInfo(){
		return "member/info";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public List<Member> getMemberList(){
		return memberRepository.findAll(); 		
	}
}
