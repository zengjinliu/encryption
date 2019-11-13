package cn.kiway.webapp.service.impl;

import cn.kiway.webapp.bean.PersonInfo;
import cn.kiway.webapp.converter.PersonInfoConverter;
import cn.kiway.webapp.mapper.PersonInfoMapper;
import cn.kiway.webapp.model.PersonInfoModel;
import cn.kiway.webapp.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author minte
 * @date 2019/9/4 14:48
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private PersonInfoMapper personInfoMapper;
    @Autowired
    private PersonInfoConverter personInfoConverter;

    @Override
    @Transactional(readOnly = true, value = "transactionManager")
    public PersonInfoModel selectBySeqId(Long seqId) {
        return personInfoConverter.toVo(selectByPrimaryKey(seqId));
    }


    @Override
    @Transactional(readOnly = true, value = "transactionManager")
    public List<PersonInfoModel> selectAll() {
        return this.personInfoConverter.toVo(this.select());
    }

    private List<PersonInfo> select() {
        return this.personInfoMapper.selectAll();
    }


    private PersonInfo selectByPrimaryKey(Long seqId) {
        return personInfoMapper.selectByPrimaryKey(seqId);
    }
}
