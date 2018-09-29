// ======================================
// Project Name:core
// Package Name:cn.meddb.core.jdbc
// File Name:JdbcBaseDAO.java
// Create Date:2018年09月29日  15:18
// ======================================
package cn.meddb.core.jdbc;

import cn.meddb.core.model.IPageInfo;
import cn.meddb.core.model.ISearchBean;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * 基于spring JDBC的数据库访问<br/>
 * 以spring jdbcTemplate为基础<br/>
 * 包含通用的增删改查
 *
 * @author 李旭光
 * @version 2018年09月29日  15:18
 * @see org.springframework.jdbc.core.JdbcTemplate
 * @see org.springframework.jdbc.core.simple.SimpleJdbcInsert
 */
public class JdbcBaseDAO<T> extends NamedParameterJdbcDaoSupport {

    /**
     * 表名
     */
    protected String tableName;

    /**
     * 主键
     */
    protected String keyName;

    /**
     */
    protected boolean useInsert = true;

    /**
     * 插入数据时使用此对象可以返回主键值<br/>
     * 使用 insertActor 必须设置 tableName
     */
    protected SimpleJdbcInsert insertActor;

    /**
     * 向数据库中插入一条数据并返回主键值<br/>
     * 使用 insertActor
     *
     * @param <N>   主键类型
     * @param table 插入的属性
     * @return 生成的主键
     * @see SimpleJdbcInsert#executeAndReturnKey(Map)
     */
    @SuppressWarnings("unchecked")
    public <N extends Number> N insertAndReturnKey(Map<String, Object> table) {
        if (this.insertActor == null) {
            throw new RuntimeException("tableName is required");
        }
        if (this.insertActor.getGeneratedKeyNames() == null
                || this.insertActor.getGeneratedKeyNames().length == 0) {
            throw new RuntimeException("key is required");
        }
        return (N) this.insertActor.executeAndReturnKey(table);
    }

    /**
     * 向数据库中插入一条数据并返回主键值<br/>
     * 使用 insertActor
     *
     * @param <N> 主键类型
     * @see SimpleJdbcInsert#executeAndReturnKey(SqlParameterSource)
     */
    @SuppressWarnings("unchecked")
    public <N extends Number> N insertAndReturnKey(T bean) {
        if (this.insertActor == null) {
            throw new RuntimeException("tableName is required");
        }
        if (this.insertActor.getGeneratedKeyNames() == null
                || this.insertActor.getGeneratedKeyNames().length == 0) {
            throw new RuntimeException("key is required");
        }
        return (N) this.insertActor
                .executeAndReturnKey(new BeanPropertySqlParameterSource(bean));
    }

    /**
     * 批量插入数据<br/>
     * 使用 insertActor
     *
     * @return 影响的行数数组
     * @throws Exception 没有设置 tableName
     * @see SimpleJdbcInsert
     */
    public int[] insertBatch(Map<String, Object>[] batch) throws Exception {
        if (this.insertActor == null) {
            throw new Exception("tableName is required");
        }
        return this.insertActor.executeBatch(batch);
    }

    /**
     * 根据主键删除一条记录
     *
     * @param id 主键
     * @return 影响的行数
     */
    public <N extends Number> int deleteById(N id) {
        if (id == null)
            return 0;
        MapSqlParameterSource namedParameters = new MapSqlParameterSource(
                "id", id);
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).update("DELETE from " + tableName
                + " where " + keyName + " =:id", namedParameters);
    }

    /**
     * 更新数据库记录
     *
     * @param values 要更新的字段，map中所有字段都会更新，包括值为null的字段,map中不需要包括主键
     * @param key    主键
     * @return 影响的行数
     */
    public <N extends Number> int updateById(Map<String, Object> values, N key) {
        String sql = "UPDATE " + tableName
                + SQLCreateHelper.createSetQuery(values.keySet()) + " where " + keyName
                + "=:" + keyName;
        values.put(keyName, key);
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).update(sql, values);
    }

    /**
     * 更新,更新所有属性，包括值为null的属性
     *
     * @param bean 要更新的数据
     */
    public void updateById(T bean) {
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
        Set<String> keys = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                keys.add(pd.getName());
            }
        }
        keys.remove(keyName);
        String sql = "UPDATE " + tableName
                + SQLCreateHelper.createSetQuery(keys) + " where " + keyName
                + "=:" + keyName;
        Objects.requireNonNull(getNamedParameterJdbcTemplate()).update(sql, new BeanPropertySqlParameterSource(bean));
    }

    /**
     * 更新，更新所有属性，包括值为null的属性
     *
     * @param bean    要更新的数据
     * @param ignores 不需要更新的字段
     */
    public void updateById(T bean, List<String> ignores) {

        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
        Set<String> keys = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null && (ignores == null || !ignores.contains(pd.getName()))) {
                keys.add(pd.getName());
            }
        }
        keys.remove(keyName);
        String sql = "UPDATE " + tableName
                + SQLCreateHelper.createSetQuery(keys) + " where " + keyName
                + "=:" + keyName;
        Objects.requireNonNull(getNamedParameterJdbcTemplate()).update(sql, new BeanPropertySqlParameterSource(bean));
    }

    /**
     * 更新，更新所有属性，包括值为null的属性
     *
     * @param bean    要更新的数据
     * @param ignores 不需要更新的字段
     */
    public void updateById(T bean, String... ignores) {
        updateById(bean, Arrays.asList(ignores));
    }

    /**
     * 根据id取得数据库记录
     *
     * @param id 主键
     * @see NamedParameterJdbcTemplate#queryForObject(String, SqlParameterSource, RowMapper)
     */
    public <N extends Number> T load(N id, RowMapper<T> mapper) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).queryForObject("SELECT * from "
                        + tableName + " where " + keyName + "=:id", namedParameters,
                mapper);
    }

    /**
     * 根据id取得数据库记录
     *
     * @param id     主键
     * @param eClass 对应的db对象
     * @see NamedParameterJdbcTemplate#queryForObject(String, SqlParameterSource, RowMapper)
     */
    public <N extends Number> T load(N id, Class<T> eClass) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).queryForObject("SELECT * from "
                        + tableName + " where " + keyName + "=:id", namedParameters,
                BeanPropertyRowMapper.newInstance(eClass));
    }

    /**
     * 根据id取得数据库记录
     *
     * @param id 主键
     * @see NamedParameterJdbcTemplate#queryForMap(String, SqlParameterSource)
     */
    public <N extends Number> Map<String, Object> load(N id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id",
                id);
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).queryForMap("SELECT * from "
                + tableName + " where " + keyName + "=:id", namedParameters);
    }

    /**
     * 检索
     *
     * @param pageInfo   分页信息
     * @param searchBean 查询条件
     */
    public IPageInfo<Map<String, Object>> list(
            IPageInfo<Map<String, Object>> pageInfo, ISearchBean searchBean) {
        Map<String, Object> params = searchBean.getParam();
        // 构造查询条件
        String searchQuery = SQLCreateHelper.createSearchQuery(params, createSpecialSQLCreator(searchBean));
        // 查询记录数量
        int count = queryCount(searchQuery, params);
        pageInfo.setCount(count);
        if (count == 0) {
            return pageInfo;
        }
        // 构造排序条件
        String sortQuery = SQLCreateHelper.createSortQuery(searchBean.getSort());
        // 查询语句
        String selectQuery = "select *";
        // 自定义查询语句
        String customSelectSql = customSelectSql();
        if (null != customSelectSql) {
            selectQuery = customSelectSql;
        }
        selectQuery += " from " + tableName;

        searchQuery = selectQuery + createAppendSql() + searchQuery + createGroupBySql()
                + sortQuery + SQLCreateHelper.createMySQLPageString(pageInfo);
        List<Map<String, Object>> items = Objects.requireNonNull(getNamedParameterJdbcTemplate())
                .queryForList(searchQuery, params);
        pageInfo.setItems(items);
        return pageInfo;
    }

    /**
     * 检索
     *
     * @param pageInfo   分页信息
     * @param searchBean 查询条件
     */
    public IPageInfo<T> list(IPageInfo<T> pageInfo, ISearchBean searchBean, Class<T> cls) {
        Map<String, Object> params = searchBean.getParam();
        // 构造查询条件
        String searchQuery = SQLCreateHelper.createSearchQuery(params,
                createSpecialSQLCreator(searchBean));
        // 查询记录数量
        int count = queryCount(searchQuery, params);
        pageInfo.setCount(count);
        if (count == 0) {
            return pageInfo;
        }
        // 构造排序条件
        String sortQuery = SQLCreateHelper.createSortQuery(searchBean.getSort());
        // 查询语句
        String selectQuery = "select *";
        // 自定义查询语句
        String customSelectSql = customSelectSql();
        if (null != customSelectSql) {
            selectQuery = customSelectSql;
        }
        selectQuery += " from " + tableName;

        searchQuery = selectQuery + createAppendSql() + searchQuery + createGroupBySql()
                + sortQuery + SQLCreateHelper.createMySQLPageString(pageInfo);
        List<T> items = Objects.requireNonNull(getNamedParameterJdbcTemplate())
                .query(searchQuery, params, BeanPropertyRowMapper.newInstance(cls));
        pageInfo.setItems(items);
        return pageInfo;
    }

    private int queryCount(String searchQuery, Map<String, Object> params) {
        // 查询记录数量
        int count = 0;
        List<Integer> countByGroup = Objects.requireNonNull(getNamedParameterJdbcTemplate()).queryForList(
                "select count(*) from " + tableName + createAppendSql() + searchQuery + createGroupBySql(),
                params, Integer.class);
        if (null != countByGroup && !countByGroup.isEmpty()) {
            if (countByGroup.size() > 1) {
                // 针对 table1 left join table2 group by table1.pk，table1 和 table2 是一对多的关系，
                // count 结果只关心 table1 的 distinct 条数
                count = countByGroup.size();
            } else {
                // 未分组
                if ("".equals(createGroupBySql())) {
                    count = countByGroup.get(0);
                } else {
                    // 分组但是table1只有一条记录
                    count = 1;
                }
            }
        }
        return count;
    }


    /**
     * 处理特殊查询条件
     */
    protected ISpecialSQLCreator createSpecialSQLCreator(ISearchBean searchBean) {
        return null;
    }

    /**
     * 可以在查询时添加join之类的语句
     */
    protected String createAppendSql() {
        return "";
    }

    /**
     * 指定要获取的字段
     */
    protected String customSelectSql() {
        return null;
    }

    /**
     * 指定查询时的分组
     */
    protected String createGroupBySql() {
        return "";
    }

    @Override
    protected void initDao()  {
        if (StringUtils.hasText(tableName) && useInsert) {
            this.insertActor = new SimpleJdbcInsert(getJdbcTemplate())
                    .withTableName(tableName);
            if (StringUtils.hasText(keyName)) {
                this.insertActor.usingGeneratedKeyColumns(keyName);
            }
        }
        try {
            super.initDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param keyName the keyName to set
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

}