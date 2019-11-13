package cn.kiway.webapp.service.impl;

import cn.kiway.webapp.converter.TestBatchConverter;
import cn.kiway.webapp.model.TestBatchModel;
import cn.kiway.webapp.service.TestBatchService;
import cn.kiway.webapp.util.WebAppCollectionUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author minte
 * @date 2019/9/5 15:15
 */
@Service
public class TestBatchServiceImpl implements TestBatchService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private TestBatchConverter testBatchConverter;

    @Autowired
    private AsyncBatchAdd asyncBatchAdd;


    @Override
    public boolean add(List<TestBatchModel> list) throws Exception {
        //每批次插入的数据量
        int dataSum = 10;
        int updateSum = 0;
        List<List<TestBatchModel>> listSplit = WebAppCollectionUtils.split(list,dataSum);

        for (List<TestBatchModel> e : listSplit) {
            Future<Integer> future = asyncBatchAdd.add(sqlSessionTemplate, testBatchConverter.toPo(e));
            updateSum += future.get(20, TimeUnit.MINUTES);
        }
        return list.size() == updateSum;
    }


    @Override
    public boolean addBatch(List<TestBatchModel> list) throws Exception {

        this.asyncBatchAdd.addBatch(list);
        return true;

    }
}
