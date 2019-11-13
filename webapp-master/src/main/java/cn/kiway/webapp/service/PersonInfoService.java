package cn.kiway.webapp.service;

import cn.kiway.webapp.model.PersonInfoModel;

import java.util.List;

/**
 * @author minte
 * @date 2019/9/4 14:31
 */
public interface PersonInfoService {

    /**
     * 根据主键查询用户信息数据
     * @param seqId 主键
     * @return
     */
    PersonInfoModel selectBySeqId(Long seqId);

    /**
     * 查询所有数据
     * @return 返回所有用户信息数据
     */
    List<PersonInfoModel> selectAll();
}
