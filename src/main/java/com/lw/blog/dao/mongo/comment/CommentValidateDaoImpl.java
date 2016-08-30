package com.lw.blog.dao.mongo.comment;

import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.model.Comment;
import com.lw.blog.model.ValidateComment;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;


/**
 * Created by LWang on 2016/7/17.
 */

@Repository
public class CommentValidateDaoImpl extends BaseDao<ValidateComment> implements CommentValidateDao {

	private SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean isHavaIllegalInfo(String blog_comment) {
		return false;
	}
}
