package com.lw.blog.controller;

import com.lw.blog.model.Tag;
import com.lw.blog.service.tag.TagService;
import com.lw.blog.service.tag.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	/**
	 * Get all tags
	 * @return tagList
	 */
	@RequestMapping("/all")
	@ResponseBody
	public List<Tag> getAllTags(){
		List<Tag> tagList=tagService.getAllTags();
		return tagList;
	}

	/**
	 * is a tag's name exist.
	 * @param tagName
	 * @return boolean
	 */
	@RequestMapping("/manage/checkTagName")
	@ResponseBody
	public Boolean isExistTagName(@RequestParam String tagName){
		boolean exist = false;
		exist = tagService.isExistTagName(tagName);
		if(exist){
			return false;
		}else {
			return true;
		}
	}

	/**
	 * is a tag's english name exist.
	 * @param tagEngName
	 * @return boolean
	 */
	@RequestMapping("/manage/checkTagEngName")
	@ResponseBody
	public Boolean isExistTagEngName(@RequestParam String tagEngName){
		boolean exist = false;
		exist = tagService.isExistTagEngName(tagEngName);
		if(exist){
			return false;
		}else {
			return true;
		}
	}

	/**
	 * add tag info to database
	 * @param tagName
	 * @param tagEngName
	 * @return boolean
	 */
	@RequestMapping("/manage/addTagInfo")
	@ResponseBody
	public Boolean isInsertTagSuccess(@RequestParam String tagName,@RequestParam String tagEngName) {
		if (tagName != null && !tagName.equals("") && tagEngName != null && !tagEngName.equals("")) {
			boolean isInsert = false;
			isInsert = tagService.isInsertTagInfo(tagName, tagEngName);
			if (isInsert) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}

	/**
	 * Edit the tag.
	 * @param tagId
	 * @param tagName
	 * @param tagEngName
	 * @return
	 */
	@RequestMapping("/manage/editTagInfo")
	@ResponseBody
	public Boolean isEditTagSuccess(@RequestParam String tagId,@RequestParam String tagName,@RequestParam String tagEngName) {
		if (tagId != null && !tagId.equals("")&&tagName != null && !tagName.equals("") && tagEngName != null && !tagEngName.equals("")) {
			boolean isEdit = false;
			isEdit = tagService.isEditTagInfo(tagId, tagName, tagEngName);
			if (isEdit) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}

	@RequestMapping("/manage/delTagInfo")
	@ResponseBody
	public Boolean isDelTagSuccess(@RequestParam String tagId) {
		if (tagId != null && !tagId.equals("")) {
			boolean isDel = false;
			isDel = tagService.isDelTagInfo(tagId);
			if (isDel) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}



}
