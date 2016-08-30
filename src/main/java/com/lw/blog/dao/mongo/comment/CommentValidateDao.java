package com.lw.blog.dao.mongo.comment;


import org.springframework.transaction.annotation.Transactional;


/**
 * Created by LWang on 2016/7/17.
 */

@Transactional
public interface CommentValidateDao {

	boolean isHavaIllegalInfo(String blog_comment);
}
