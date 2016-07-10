/**
 * 
 */
package com.lw.blog.dao.mongo.common.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LWang
 * @date   2016-1-18 
 */
@Component
public class ObjectParams {
	private  String javaType = "java";
	public  Map<String, String> createParams(Object object) throws Exception {
        Map<String, String> params = new HashMap<String, String>();

        setIntoParams(params,object, null);
        return params;
    }

    private  void setIntoParams(Map<String, String> params,Object object, String fatherName) throws IllegalAccessException,
            Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field file : fields) {
            boolean accessFlag = file.isAccessible();
            file.setAccessible(true);
            String name = file.getName();
            Object value = file.get(object);

            if(file.getType().getName().equals("java.lang.Class")){
                break;
            }else if(file.getType().getName().contains(javaType)){
                if(fatherName != null && !fatherName.equals(" ")){
                    name = fatherName+"."+name;
                }

                if(value != null){
                    params.put(name, value+"");
                }


            }else{
                if(value != null){
                    setIntoParams(params,file.get(object), name);
                }

            }
            file.setAccessible(accessFlag);
        }
    }
}
