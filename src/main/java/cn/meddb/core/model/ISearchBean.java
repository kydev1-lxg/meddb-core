// ======================================
// Project Name:core
// Package Name:cn.meddb.core.model
// File Name:ISearchBean.java
// Create Date:2017年03月29日  14:57
// ======================================
package cn.meddb.core.model;

import java.util.Map;

/**
 * @author 李旭光
 * @version 2017年03月29日  14:57
 */
public interface ISearchBean {

    /**
     * 排序类型
     *
     * @author 李旭光
     * @version 2017年3月29日 14:59
     */
    enum Sort {
        /**
         */
        desc, /**
         */
        asc
    }

    /**
     * 取得排序信息
     *
     * @return Map
     */
    Map<String, Sort> getSort();

    /**
     * 取得查询条件信息
     *
     * @return Map
     */
    Map<String, Object> getParps();

}