package com.lw.blog.dao.mongo.common;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 时间转换器
 * @author LWang
 * @date   2016-1-18
 */

public class TimestampConverter implements Converter<Date, Timestamp> {

	
	public Timestamp convert(Date date) {
		if(date != null){
            return new Timestamp(date.getTime());
        }
		return null;
	}

	}

