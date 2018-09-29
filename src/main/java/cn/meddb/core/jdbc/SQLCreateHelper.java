// ======================================
// Project Name:core
// Package Name:cn.meddb.core.jdbc
// File Name:SQLCreateHelper.java
// Create Date:2018年09月29日  15:00
// ======================================
package cn.meddb.core.jdbc;

import cn.meddb.core.model.IPageInfo;
import cn.meddb.core.model.ISearchBean;

import java.util.Map;
import java.util.Set;

/**
 * 创建SQL语句的工具类<br>
 * <b>Mysql</b>
 *
 * @author 李旭光
 * @version 2018年09月29日  15:00
 */
public class SQLCreateHelper {

    /**
     * 排序信息
     *
     * @param sortMap 来自 {@link ISearchBean#getSort()}
     * @return 排序语句，order by XXX desc,YYY asc ...
     */
    public static String createSortQuery(Map<String, ISearchBean.Sort> sortMap) {
        if (sortMap == null || sortMap.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder(" order by ");
        for (String key : sortMap.keySet()) {
            sb.append(" ");
            sb.append(key);
            sb.append(" ");
            sb.append(sortMap.get(key));
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1) + " ";
    }

    /**
     * MySQL分页信息<br>
     * limit小于或等于0时代表不分页，直接返回空字符串
     *
     * @param page 分页参数
     * @return 分页语句, limit n,m
     */
    public static String createMySQLPageString(IPageInfo<?> page) {
        if (page.getLimit() <= 0) {
            return "";
        }
        return " limit " +
                page.getStart() +
                "," +
                page.getLimit();
    }

    /**
     * 构造查询条件,使用and连接的等于查询，支持传入特殊参数处理器
     *
     * @param param 所有参数
     * @return where开头的查询语句
     */
    public static String createSearchQuery(Map<String, Object> param,
                                           ISpecialSQLCreator creator) {
        if (param == null || param.isEmpty())
            return "";
        StringBuffer sb = new StringBuffer();
        boolean init = true;
        for (String key : param.keySet()) {
            if (init) {
                sb.append(" where ");
                init = false;
            } else {
                sb.append(" and ");
            }
            if (creator != null && creator.isSpecialKey(param, key)) {
                sb.append(creator.createSearchQuery(key, param, sb));
            } else {
                sb.append(key);
                sb.append("=:");
                sb.append(key);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 构造赋值语句
     *
     * @param keys 需要赋值的字段
     * @return set开头的赋值语句
     */
    public static String createSetQuery(Set<String> keys) {
        StringBuilder sb = new StringBuilder(" set ");
        for (String key : keys) {
            sb.append(key);
            sb.append("=:");
            sb.append(key);
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1) + " ";
    }

    /**
     * 构造赋值语句，数据库字段与map的key不同的时候使用
     *
     * @param keys 需要赋值的字段及对应的参数名
     * @return set开头的赋值语句
     */
    public static String createSetQuery(Map<String, String> keys) {
        StringBuilder sb = new StringBuilder(" set ");
        for (Map.Entry<String, String> key : keys.entrySet()) {
            sb.append(key.getKey());
            sb.append("=:");
            sb.append(key.getValue());
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1) + " ";
    }


}