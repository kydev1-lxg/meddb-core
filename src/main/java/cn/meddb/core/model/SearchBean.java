// ======================================
// Project Name:core
// Package Name:cn.meddb.core.model
// File Name:SearchBean.java
// Create Date:2017年08月24日  11:34
// ======================================
package cn.meddb.core.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 检索条件bean
 *
 * @author 李旭光
 * @version 2017年08月24日  11:34
 */
public class SearchBean implements ISearchBean {

    /**
     * 检索条件
     */
    private Map<String, Object> param = new HashMap<>();

    /**
     * 排序规则
     */
    private Map<String, Sort> sort = new LinkedHashMap<>();

    /**
     * 添加查询条件
     *
     * @param field String
     * @param value Object
     * @return SearchBean
     */
    public SearchBean addParamField(String field, Object value) {
        this.param.put(field, value);
        return this;
    }

    /**
     * 添加排序条件
     *
     * @param field String
     * @param sort  Sort
     * @return SearchBean
     */
    public SearchBean addSortField(String field, Sort sort) {
        this.sort.put(field, sort);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SearchBean)) {
            return false;
        }
        SearchBean another = (SearchBean) o;
        return sort.equals(another.sort) && param.equals(another.param);
    }

    @Override
    public int hashCode() {
        return param.hashCode() + sort.hashCode();
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public void setSort(Map<String, Sort> sort) {
        this.sort = sort;
    }

    @Override
    public Map<String, Object> getParam() {
        return this.param;
    }

    @Override
    public Map<String, Sort> getSort() {
        return this.sort;
    }

}