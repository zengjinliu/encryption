package cn.kiway.webapp.service.impl;


import cn.kiway.webapp.bean.TestBatch;
import cn.kiway.webapp.mapper.TestBatchMapper;
import cn.kiway.webapp.model.TestBatchModel;
import cn.kiway.webapp.processor.TestBatchProcessor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author minte
 * @date 2019/9/5 15:18
 */
@Component
public class AsyncBatchAdd {

    private Logger logger = LoggerFactory.getLogger(AsyncBatchAdd.class);


    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    private SimpleJobLauncher launcher;

    @Autowired
    private TestBatchProcessor testBatchProcessor;

    @Autowired
    @Qualifier("testBatchAddItemWriter")
    private MyBatisBatchItemWriter testBatchAddItemWriter;


    @Bean("testBatchAddItemWriter")
    @Autowired
    protected MyBatisBatchItemWriter testBatchAddItemWriter(SqlSessionTemplate sqlSessionTemplate, SqlSessionFactory sqlSessionFactory) {
        MyBatisBatchItemWriter itemWriter = new MyBatisBatchItemWriter();
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        itemWriter.setSqlSessionTemplate(sqlSessionTemplate);
        itemWriter.setStatementId("cn.kiway.webapp.mapper.TestBatchMapper.insertSelective");
        return itemWriter;
    }


    private Job createTestBatchAddJob(Step step) {
        Job job = jobBuilderFactory.get("testBatchAddJob").start(step).build();
        return job;
    }


    /**
     * 构建step
     *
     * @param itemReader
     * @param addProcessor
     * @param writer
     * @return
     */
    private Step createTestBatchAddStep(ItemReader<TestBatchModel> itemReader, ItemProcessor<TestBatchModel, TestBatch> addProcessor, ItemWriter<TestBatch> writer) {
        return stepBuilderFactory.get("testBatchAddStep").<TestBatchModel, TestBatch>chunk(20).
                reader(itemReader).processor(addProcessor).writer(writer).build();
    }


    @Async("taskExecutor")
    public Future<Integer> add(SqlSessionTemplate sqlSessionTemplate, List<TestBatch> list) {
        TestBatchMapper mapper = sqlSessionTemplate.getMapper(TestBatchMapper.class);
        list.forEach(mapper::insertSelective);
        sqlSessionTemplate.flushStatements();
        logger.debug(" current Thread name ：" + Thread.currentThread().getName() + "  插入" + list.size() + "条数据");
        return AsyncResult.forValue(list.size());

    }

    public void addBatch(List<TestBatchModel> list) throws Exception {
        ListItemReader<TestBatchModel> listItemReader = new ListItemReader<>(list);
        Step step = this.createTestBatchAddStep(listItemReader, testBatchProcessor, testBatchAddItemWriter);
        Job job = this.createTestBatchAddJob(step);
        JobParametersBuilder jobParameters = new JobParametersBuilder();
        jobParameters.addLong("date", LocalDateTime.now().getLong(ChronoField.MICRO_OF_DAY));
        this.launcher.run(job, jobParameters.toJobParameters());
    }


}
