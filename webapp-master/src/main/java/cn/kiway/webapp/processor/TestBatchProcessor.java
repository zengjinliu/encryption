package cn.kiway.webapp.processor;

import cn.kiway.webapp.bean.TestBatch;
import cn.kiway.webapp.converter.TestBatchConverter;
import cn.kiway.webapp.model.TestBatchModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author 刘玉祥
 * @date 2019/9/11 17:30
 */
@Component
public class TestBatchProcessor implements ItemProcessor<TestBatchModel, TestBatch> {
    @Autowired
    private TestBatchConverter converter;

    @Override
    public TestBatch process(TestBatchModel item) throws Exception {
        return converter.toPo(item);
    }
}
