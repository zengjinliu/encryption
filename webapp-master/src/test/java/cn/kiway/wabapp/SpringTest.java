package cn.kiway.wabapp;


import cn.kiway.webapp.model.PersonInfoModel;
import cn.kiway.webapp.model.TestBatchModel;
import cn.kiway.webapp.service.PersonInfoService;
import cn.kiway.webapp.service.TestBatchService;
import cn.kiway.webapp.util.SnowflakeIdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import web.config.SpringRootConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minte
 * @date 2019/9/4 08:33
 */

@RunWith(value= SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringRootConfig.class})
public class SpringTest {
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private TestBatchService service;
    @Test
    public void testPersonMapper(){
        PersonInfoModel person = personInfoService.selectBySeqId(1L);

        System.out.println(person);

        PersonInfoModel person1 = personInfoService.selectBySeqId(1L);
        System.out.println(person1);


        List<PersonInfoModel> list =personInfoService.selectAll();
        list.forEach(e-> System.out.println(e));
    }

    @Test
    public void add(){

        List<TestBatchModel> list = new ArrayList<>(100000);
        for(int i = 0 ;i<100;i++){
            TestBatchModel testBatchModel = new TestBatchModel();
            testBatchModel.setAge(22);
            testBatchModel.setCreateDate(LocalDateTime.now());
            testBatchModel.setName("minte-"+i);
            testBatchModel.setSeqId(snowflakeIdWorker.nextId());
            list.add(testBatchModel);
        }
        try {
            service.addBatch(list);

            Thread.sleep(600);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).error(e.getMessage(),e);
        }




    }
}
