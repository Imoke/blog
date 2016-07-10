/**
 * 
 */
package com.lw.blog.dao.mongo.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**Mongo辅助类
 * @author LWang
 * @date   2016-1-18 
 */
@Component
public class MongoFactory {
	  @Autowired
	 private ObjectParams objectParams;
	 public Map<String, Object> converObjectToParams(Object obj) throws Exception{
	        Map<String, Object> map = new HashMap<String, Object>();
	        Update update = new Update();
	        Map<String, String> params = objectParams.createParams(obj);
	        String id = params.get("id");
	        Set<Map.Entry<String, String>> set = params.entrySet();
	        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
	            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
	            if(!entry.getKey().equals("id")){
	                update.set(entry.getKey(), entry.getValue());
	            }
	        }

	        map.put("id", id);
	        map.put("update", update);

	        return map;
	    }


}
