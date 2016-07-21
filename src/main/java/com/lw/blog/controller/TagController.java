package com.lw.blog.controller;

import com.lw.blog.model.Tag;
import com.lw.blog.service.tag.TagService;
import com.lw.blog.service.tag.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by LWang on 2016/7/20.
 */
@Controller
@RequestMapping("/tags")
public class TagController {
	@Autowired
	private TagServiceImpl tagService;

	@RequestMapping("/all")
	@ResponseBody
	public List<Tag> getAllTags(){
		List<Tag> tagList=tagService.getAllTags();
		return tagList;
	}
}
