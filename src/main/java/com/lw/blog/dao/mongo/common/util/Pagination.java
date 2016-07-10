/**
 * 
 */
package com.lw.blog.dao.mongo.common.util;


import com.lw.blog.dao.mongo.common.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;


/**
 * @author LWang
 * @date   2016-1-18 
 */
public class Pagination {
	/** 每页数量 */
    private Integer pageSize;
    /** 开始位置 */
    private Integer skip;
    /** 当前页 */
    private Integer currentPage;
    /** 总页数 */
    private Long totalPage;
    /** 查询到的总数据量 */
    private Long totalNumber;
    /** 数据集 */
    private List items;
    /** 排序 */
    private Sort sort;

    public Pagination(int currentPage, Long totalNumber) {
        this.pageSize = Constant.PAGE_SIZE;
        this.currentPage = currentPage;
        this.totalNumber = totalNumber;
        this.totalPage = totalNumber / pageSize + 1;
        this.skip = (currentPage - 1) * pageSize;
        this.sort = new Sort(new Sort.Order(Direction.ASC, "_id"));
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(String dir, boolean isAsc) {
        if (isAsc) {
            this.sort = new Sort(new Sort.Order(Direction.ASC, dir));
        } else {
            this.sort = new Sort(new Sort.Order(Direction.DESC, dir));
        }

    }

}
