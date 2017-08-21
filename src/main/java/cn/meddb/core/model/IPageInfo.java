// ======================================
// Project Name:core
// Package Name:cn.meddb.core.model
// File Name:IPageInfo.java
// Create Date:2017年03月29日  14:57
// ======================================
package cn.meddb.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李旭光
 * @version 2017年03月29日  14:57
 */
public interface IPageInfo<E> extends Serializable {

    /**
     * 取得总记录数
     *
     * @return int
     */
    int getCount();

    /**
     * 设置总记录数
     *
     * @param count int
     */
    void setCount(int count);

    /**
     * 开始的记录
     *
     * @return int
     */
    int getStart();

    /**
     * 每次取得的记录数
     *
     * @return int
     */
    int getLimit();

    /**
     * 记录数据
     *
     * @param items List<E>
     */
    void setItems(List<E> items);

    /**
     * 记录数据
     *
     * @return List<E>
     */
    List<E> getItems();

}