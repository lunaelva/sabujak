package com.lunamaan.post.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunamaan.post.entity.Post;
import com.lunamaan.post.repository.PostRepository;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	PostRepository postRepository;
	
	@RequestMapping("/write")
	public String writeView(){
		return "post/write";
	}
	
	@ResponseBody
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public void writeProc(@RequestParam Map<String, String> param, Model model){
		Post post= new Post();
		post.setCategoryId(0);
		post.setContent(param.get("content"));
		post.setTitle(param.get("title"));
		post.setOccTime(LocalDateTime.now());
		post.setUpdTime(LocalDateTime.now());
		post.setWriter("나");
		post.setUpdater("나");
		postRepository.save(post);
	}
	
	@RequestMapping("/list") 
	public String list(Model model) {		
		PageRequest pageRequest = new PageRequest(0, 20, new Sort(Direction.DESC, "pId")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Post> list = postRepository.findAll(pageRequest);
		
		model.addAttribute("list", list);
		return "post/list";
	}
	
	@RequestMapping("/view/{pid}") 
	public String view(Model model, @PathVariable("pid") long pId) {
		Post post = postRepository.findOne(pId);
		model.addAttribute("post", post);
		return "post/view";
	}
}
