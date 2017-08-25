// ======================================
// Project Name:core
// Package Name:cn.meddb.core.model
// File Name:POJOPageInfo.java
// Create Date:2017年08月23日  13:07
// ======================================
package cn.meddb.core.model;

import java.util.List;

/**
 * 普通分页
 *
 * @author 李旭光
 * @version 2017年08月23日  13:07
 */
public class POJOPageInfo<T> implements IPageInfo<T> {

    /**
     */
    private int count;

    /**
     */
    private int rowsPerPage;

    /**
     */
    private int nowPage;

    /**
     */
    private List<T> items;

    public POJOPageInfo(int rowsPerPage, int nowPage) {
        this.rowsPerPage = rowsPerPage;
        this.nowPage = nowPage;
    }

    public POJOPageInfo(int rowsPerPage, int nowPage, int count) {
        this.rowsPerPage = rowsPerPage;
        this.nowPage = nowPage;
        this.setCount(count);
    }

    /**
     * @return int
     */
    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public int getLimit() {
        return this.rowsPerPage;
    }

    @Override
    public int getStart() {
        return (this.nowPage - 1) * this.rowsPerPage;
    }

    /**
     * @param count int
     */
    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public List<T> getItems() {
        return items;
    }

    /**
     * @return the rowsPerPage
     */
    public int getRowsPerPage() {
        return rowsPerPage;
    }

    /**
     * @return the nowPage
     */
    public int getNowPage() {
        return nowPage;
    }

}