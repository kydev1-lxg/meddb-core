// ======================================
// Project Name:core
// Package Name:cn.meddb.core.jdbc
// File Name:ComplexSQLCreator.java
// Create Date:2018年12月21日  15:04
// ======================================
package cn.meddb.core.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 复合sql构造器
 *
 * @author 李旭光
 * @version 2018年12月21日  15:04
 */
public class ComplexSQLCreator implements ISpecialSQLCreator {

    private List<ISpecialSQLCreator> list = new ArrayList<>();

    public static ComplexSQLCreator getInstance() {
        return new ComplexSQLCreator();
    }

    public static ComplexSQLCreator getInstance(ISpecialSQLCreator... creators) {
        return new ComplexSQLCreator(creators);
    }

    private ComplexSQLCreator() {
    }

    private ComplexSQLCreator(ISpecialSQLCreator... creators) {
        this.list = Arrays.asList(creators);
    }

    public ComplexSQLCreator add(ISpecialSQLCreator creator) {
        if (creator instanceof ComplexSQLCreator) {
            throw new RuntimeException("循环引用");
        }
        this.list.add(creator);
        return this;
    }

    @Override
    public StringBuffer createSearchQuery(String key, Map<String, Object> param, StringBuffer sb) {
        for (ISpecialSQLCreator creator : list) {
            if (creator.isSpecialKey(param, key)) {
                return creator.createSearchQuery(key, param, sb);
            }
        }
        return new StringBuffer();
    }

    @Override
    public boolean isSpecialKey(Map<String, Object> param, String key) {
        for (ISpecialSQLCreator creator : list) {
            if (creator.isSpecialKey(param, key)) {
                return true;
            }
        }
        return false;
    }

}