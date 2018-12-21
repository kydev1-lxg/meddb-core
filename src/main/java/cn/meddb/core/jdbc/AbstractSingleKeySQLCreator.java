// ======================================
// Project Name:core
// Package Name:cn.meddb.core.jdbc
// File Name:AbstractSingleKeySQLCreator.java
// Create Date:2018年12月21日  14:55
// ======================================
package cn.meddb.core.jdbc;

import java.util.Map;

/**
 * 只有一个需要特殊处理的key时
 *
 * @author 李旭光
 * @version 2018年12月21日  14:55
 */
public abstract class AbstractSingleKeySQLCreator implements ISpecialSQLCreator {

    private String key;

    public AbstractSingleKeySQLCreator(String key) {
        this.key = key;
    }

    @Override
    public boolean isSpecialKey(Map<String, Object> param, String key) {
        return this.key.equals(key);
    }

}