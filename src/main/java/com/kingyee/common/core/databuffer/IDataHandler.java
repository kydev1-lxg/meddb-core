// ======================================
// Project Name:core
// Package Name:com.kingyee.common.core.databuffer
// File Name:IDataHandler.java
// Create Date:2017年03月29日  14:56
// ======================================
package com.kingyee.common.core.databuffer;

import com.kingyee.common.core.model.IPageInfo;
import com.kingyee.common.core.model.ISearchBean;

/**
 * @author 李旭光
 * @version 2017年03月29日  14:56
 */

public interface IDataHandler<T> {
    /**
     * 取得下一批数据
     *
     * @param searchBean ISearchBean
     * @param pageInfo   IPageInfo<T>
     * @return IPageInfo<T>
     */
    IPageInfo<T> getNextList(ISearchBean searchBean,
                             IPageInfo<T> pageInfo);
}