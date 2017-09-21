// ======================================
// Project Name:core
// Package Name:cn.meddb.core.util
// File Name:DataBufferUtil.java
// Create Date:2017年08月24日  11:37
// ======================================
package cn.meddb.core.util;

import cn.meddb.core.model.IPageInfo;
import cn.meddb.core.model.ISearchBean;
import cn.meddb.core.model.POJOPageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据缓冲工具类
 *
 * @author 李旭光
 * @version 2017年08月24日  11:37
 */
public class DataBufferUtil<T> {

    /**
     * 缓冲数据
     */
    private List<T> bufferList;

    private IDataBufferHandler<T> handler;

    private ISearchBean searchBean;

    private IPageInfo<T> pageInfo;

    private int page = 1;

    public DataBufferUtil(IDataBufferHandler<T> handler,
                          ISearchBean searchBean, int buffer) {
        this.handler = handler;
        this.pageInfo = new POJOPageInfo<>(buffer, page);
        this.searchBean = searchBean;
    }

    /**
     * 从缓冲数据中取得一条数据（缓冲数据中删除此数据）
     *
     * @return T
     */
    public T next() {
        T obj = null;
        if (needData())
            setBufferData();
        if (bufferList.size() > 0) {
            obj = bufferList.remove(0);
        }
        return obj;
    }

    // 从数据库中取出一定数量的数据放入缓冲list
    private void setBufferData() {
        if (handler == null) {
            return;
        }
        if (page == 1 || pageInfo.getCount() > pageInfo.getStart()) {
            pageInfo = handler.getNextList(searchBean, new POJOPageInfo<>(
                    pageInfo.getLimit(), page++));
            bufferList = pageInfo.getItems();
        }
    }

    /**
     * 判断是否还有数据
     *
     * @return boolean
     */
    public boolean hasNext() {
        if (needData()) {
            setBufferData();
        }
        return !needData();
    }

    // 判断是否需要从数据库中取得数据
    private boolean needData() {
        return getBufferCurrentNum() == 0;
    }

    // 取得当前缓冲list中的数据数量
    private int getBufferCurrentNum() {
        if (bufferList == null) {
            bufferList = new ArrayList<>();
        }
        return bufferList.size();
    }
}