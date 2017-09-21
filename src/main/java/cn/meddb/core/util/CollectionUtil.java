// ======================================
// Project Name:core
// Package Name:cn.meddb.core.util
// File Name:CollectionUtil.java
// Create Date:2017年09月21日  14:37
// ======================================
package cn.meddb.core.util;

import java.util.List;

/**
 * 集合工具类
 *
 * @author 李旭光
 * @version 2017年09月21日  14:37
 */
public class CollectionUtil {

    /**
     * 把一个List 拼接为字符串
     *
     * @param arr       待拼接的list
     * @param connector 两个项目之间的连接符
     * @return String
     */
    public static <T> String join(List<T> arr, String connector) {
        if (arr == null || arr.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (T one : arr) {
            sb.append(connector);
            sb.append(one);
        }
        return sb.substring(connector.length());
    }

}