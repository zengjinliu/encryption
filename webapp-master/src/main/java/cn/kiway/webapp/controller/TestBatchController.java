package cn.kiway.webapp.controller;

import cn.kiway.webapp.model.PersonInfoModel;
import cn.kiway.webapp.model.ResultJson;
import cn.kiway.webapp.model.TestBatchModel;
import cn.kiway.webapp.service.TestBatchService;
import cn.kiway.webapp.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minte
 * @date 2019/9/12 12:17
 */

@Controller
@RequestMapping("testBatch")
public class TestBatchController {

    @Autowired
    private TestBatchService testBatchService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @PostMapping("add")
    @ResponseBody
    public Map<String,String> addTestBatch()throws Exception{
        List<TestBatchModel> list = new ArrayList<>(100000);
        for(int i = 0 ;i<100000;i++){
            TestBatchModel testBatchModel = new TestBatchModel();
            testBatchModel.setAge(i);
            testBatchModel.setCreateDate(LocalDateTime.now());
            testBatchModel.setName("minte-"+i);
            testBatchModel.setSeqId(snowflakeIdWorker.nextId());
            list.add(testBatchModel);
        }
        testBatchService.addBatch(list);

        Map<String,String> rs = new HashMap<>(6);
        rs.put("a","b");
        return  rs;
    }

    @PostMapping("getPersonInfo")
    @ResponseBody
    public ResultJson<PersonInfoModel> getPersonInfo(String aesKey){




        PersonInfoModel personInfoModel = new PersonInfoModel();
        personInfoModel.setAvatarUrl("bbb");
        personInfoModel.setCreateDate(LocalDateTime.now());
        personInfoModel.setPersonId(1231231321231L);
        personInfoModel.setPhone("13660110469");
        personInfoModel.setPosition("minte");
        personInfoModel.setWeixinId("轻歌抚雨");

        ResultJson<PersonInfoModel> resultJson = new ResultJson<>();
        resultJson.setStatusCode(ResultJson.STATUS_TYPE_SUCCEED);
        resultJson.setData(personInfoModel);
        return  resultJson;
    }

}
