package cn.kiway.webapp.converter;

import cn.kiway.webapp.bean.TestBatch;
import cn.kiway.webapp.model.TestBatchModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-13T13:54:25+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class TestBatchConverterImpl implements TestBatchConverter {

    @Override
    public TestBatchModel toVo(TestBatch pojo) {
        if ( pojo == null ) {
            return null;
        }

        TestBatchModel testBatchModel = new TestBatchModel();

        testBatchModel.setSeqId( pojo.getSeqId() );
        testBatchModel.setName( pojo.getName() );
        testBatchModel.setAge( pojo.getAge() );
        testBatchModel.setCreateDate( pojo.getCreateDate() );

        return testBatchModel;
    }

    @Override
    public List<TestBatchModel> toVo(List<TestBatch> pojo) {
        if ( pojo == null ) {
            return null;
        }

        List<TestBatchModel> list = new ArrayList<TestBatchModel>( pojo.size() );
        for ( TestBatch testBatch : pojo ) {
            list.add( toVo( testBatch ) );
        }

        return list;
    }

    @Override
    public TestBatch toPo(TestBatchModel vo) {
        if ( vo == null ) {
            return null;
        }

        TestBatch testBatch = new TestBatch();

        testBatch.setSeqId( vo.getSeqId() );
        testBatch.setName( vo.getName() );
        testBatch.setAge( vo.getAge() );
        testBatch.setCreateDate( vo.getCreateDate() );

        return testBatch;
    }

    @Override
    public List<TestBatch> toPo(List<TestBatchModel> vo) {
        if ( vo == null ) {
            return null;
        }

        List<TestBatch> list = new ArrayList<TestBatch>( vo.size() );
        for ( TestBatchModel testBatchModel : vo ) {
            list.add( toPo( testBatchModel ) );
        }

        return list;
    }
}
