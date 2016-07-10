/**
 * 
 */
package com.lw.blog.dao.mongo.common;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author LWang
 * @date   2016-1-18 
 */
public interface IBaseDao<T> {
	/**
	 * 获取查询数据的数量
	 * @author LWang
	 * @date   2016-1-18 下午2:33:06
	 * @param query
	 * @return
	 */
	public Long findCount(Query query);
	/**
	 * 查询数据
	 * @author LWang
	 * @date   2016-1-18 下午2:34:40
	 * @param query
	 * @return
	 */
	public List<T> find(Query query);
	/**
	 * 分页查询数据
	 * @author LWang
	 * @date   2016-1-18 下午2:36:48
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<T> findList(int start, int limit);
	/**
	 * 根据ＩＤ获取单个数据
	 * @author LWang
	 * @date   2016-1-18 下午2:37:49
	 * @param id
	 * @return
	 */
	public T findOne(String id);
	/**
	 * 插入一条数据
	 * @author LWang
	 * @date   2016-1-18 下午2:38:56
	 * @param entity
	 */
	public void insert(T entity);
	/**
	 * 更新数据
	 * @author LWang
	 * @date   2016-1-18 下午2:39:51
	 * @param entity
	 */
	public void update(T entity) throws Exception;
	/**
	 * 更新数据
	 * @author LWang
	 * @date   2016-1-18 下午2:39:51
	 * @param entity
	 */
	public void update(Query query, Update update);
	/**
	 * 删除数据
	 * @author LWang
	 * @date   2016-1-18 下午2:41:35
	 * @param entity
	 */
	public void remove(T entity);
 }
