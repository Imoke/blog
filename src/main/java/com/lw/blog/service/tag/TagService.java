package com.lw.blog.service.tag;

import com.lw.blog.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LWang on 2016/7/18.
 */

@Service
public interface TagService {
	
	Tag getTagByClassName(String name);

	List<Tag> getAllTags();
}
