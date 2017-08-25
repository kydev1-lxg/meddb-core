// ======================================
// Project Name:core
// Package Name:cn.meddb.core.model
// File Name:ExtPageInfo.java
// Create Date:2017年08月23日  15:57
// ======================================
package cn.meddb.core.model;

import java.util.List;

/**
 * EXTJS专用的分页
 *
 * @author 李旭光
 * @version 2017年08月23日  15:57
 */
public class ExtPageInfo<T> implements IPageInfo<T> {

    private static final long serialVersionUID = 6665767063832506912L;

    private int count;

    private int start;

    private int limit;

    private List<T> items;

    public ExtPageInfo() {
    }

    public ExtPageInfo(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public int getLimit() {
        return this.limit;
    }

    @Override
    public int getStart() {
        return this.start;
    }

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

    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}