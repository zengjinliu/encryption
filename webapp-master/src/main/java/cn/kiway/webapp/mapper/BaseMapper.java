package cn.kiway.webapp.mapper;

import cn.kiway.webapp.bean.PersonInfo;

/**
 * 持久层超类
 * @param <PK> 主键类型
 * @param <T>  实体
 * @author 刘玉祥
 */
public interface BaseMapper<PK, T> {
    /**
     * 根据主键删除
     *
     * @param seqId 主键
     * @return 更改条数
     */
    int deleteByPrimaryKey(PK seqId);

    /**
     * 插入一条数据，所有字段插入
     *
     * @param record 实体
     * @return 返回更新条数
     */
    int insert(T record);

    /**
     * 插入一条数据，有值的字段插入
     *
     * @param record 实体
     * @return 返回更新条数
     */
    int insertSelective(T record);

    /**
     * 根据主键查询一条数据
     *
     * @param seqId 主键
     * @return
     */
    T selectByPrimaryKey(PK seqId);

    /**
     * 根据字段跟新一条数据，主键必传
     *
     * @param record 待更新数据的实体
     * @return 返回更新条数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新全部字段
     *
     * @param record 待更新的实体数据
     * @return 返回更新的条数
     */
    int updateByPrimaryKey(T record);
}
