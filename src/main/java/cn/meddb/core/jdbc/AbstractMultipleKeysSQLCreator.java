// ======================================
// Project Name:core
// Package Name:cn.meddb.core.jdbc
// File Name:AbstractMultipleKeysSQLCreator.java
// Create Date:2018年12月21日  14:58
// ======================================
package cn.meddb.core.jdbc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 有多个需要特殊处理的key
 *
 * @author 李旭光
 * @version 2018年12月21日  14:58
 */
public abstract class AbstractMultipleKeysSQLCreator implements ISpecialSQLCreator {

    private List<String> keys;

    public AbstractMultipleKeysSQLCreator(String... keys) {
        this.keys = Arrays.asList(keys);
    }

    public AbstractMultipleKeysSQLCreator(List<String> keys) {
        this.keys = keys;
    }

    @Override
    public boolean isSpecialKey(Map<String, Object> param, String key) {
        return keys != null && keys.contains(key);
    }
}