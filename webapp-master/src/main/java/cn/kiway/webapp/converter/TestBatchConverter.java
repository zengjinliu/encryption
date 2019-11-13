package cn.kiway.webapp.converter;

import cn.kiway.webapp.bean.TestBatch;
import cn.kiway.webapp.model.TestBatchModel;
import org.mapstruct.Mapper;

/**
 * @author minte
 * @date 2019/9/5 15:12
 */
@Mapper(componentModel = "spring")
public interface TestBatchConverter extends BaseBeanConverter<TestBatch, TestBatchModel> {
}
