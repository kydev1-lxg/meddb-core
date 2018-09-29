// ======================================
// Project Name:core
// Package Name:cn.meddb.core.jdbc
// File Name:ISpecialSQLCreator.java
// Create Date:2018年09月29日  14:52
// ======================================
package cn.meddb.core.jdbc;

import java.util.Map;

/**
 * 处理特殊的查询条件<br>
 * 构造查询条件时对map中的每个键调用此类的方法
 *
 * @author 李旭光
 * @version 2018年09月29日  14:52
 */
public interface ISpecialSQLCreator {

    /**
     * 如果 {@link #isSpecialKey(Map, String)}返回的是true，则通过此方法处理参数
     *
     * @param key   参数名
     * @param param 所有参数
     * @param sb    已经构造好的查询条件
     * @return 针对该key的查询条件，不包含连接符<br>
     * 例如：new StringBuffer("(user.hospital like:keyword or user.name like:keyword)");
     */
    StringBuffer createSearchQuery(String key, Map<String, Object> param,
                                   StringBuffer sb);

    /**
     * 检查指定的参数是否需要特殊处理<br>
     *
     * @param param 所有参数对象
     * @param key   要检查的参数名称
     * @return 是否需要特殊处理，例如：<pre>return "keyword".equals(key);</pre>
     */
    boolean isSpecialKey(Map<String, Object> param, String key);

}