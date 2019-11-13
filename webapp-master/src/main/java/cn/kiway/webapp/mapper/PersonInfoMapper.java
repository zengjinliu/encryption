package cn.kiway.webapp.mapper;

import cn.kiway.webapp.bean.PersonInfo;


import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 账号信息持久化层
 * @author 刘玉祥
 */
public interface PersonInfoMapper  extends BaseMapper<Long,PersonInfo>  {
    /**
     * 查询所有数据
     * @return
     */
    @Select(" SELECT  * FROM  PERSON_INFO")
    @ResultMap("cn.kiway.webapp.mapper.PersonInfoMapper.BaseResultMap")
    List<PersonInfo> selectAll();

}