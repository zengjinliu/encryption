package cn.kiway.webapp.service;

import cn.kiway.webapp.bean.TestBatch;
import cn.kiway.webapp.model.TestBatchModel;

import java.util.List;

/**
 * @author minte
 * @date 2019/9/5 15:11
 */
public interface TestBatchService {

    /**
     * 批量新增数据
     *
     * @param list
     * @return 是否新增成功
     * @throws Exception
     */
    boolean add(List<TestBatchModel> list) throws Exception;

    boolean addBatch(List<TestBatchModel> list) throws Exception;
}
