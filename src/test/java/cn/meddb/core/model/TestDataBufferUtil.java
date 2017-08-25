// ======================================
// Project Name:core
// Package Name:cn.meddb.core.model
// File Name:TestDataBufferUtil.java
// Create Date:2017年08月25日  10:04
// ======================================
package cn.meddb.core.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存类测试
 *
 * @author 李旭光
 * @version 2017年08月25日  10:04
 */
public class TestDataBufferUtil {

    @Test
    public void testNext() {
        DataBufferUtil<Integer> dataBufferUtil = new DataBufferUtil<>((searchBean, pageInfo) -> {
            System.out.println("load from database");
            int start = pageInfo.getStart();
            int limit = pageInfo.getLimit();
            pageInfo.setCount(325);
            List<Integer> items = new ArrayList<>();
            for (int i = start; i < start + limit; i++) {
                if (i >= pageInfo.getCount()) {
                    break;
                }
                items.add(i);
            }
            pageInfo.setItems(items);
            return pageInfo;
        }, new SearchBean(), 100);

        while (dataBufferUtil.hasNext()) {
            System.out.println(dataBufferUtil.next());
        }

    }

}