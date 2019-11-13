package cn.kiway.wabapp;

import cn.kiway.webapp.util.SnowflakeIdWorker;
import org.junit.Test;
import org.springframework.scripting.ScriptEvaluator;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.groovy.GroovyScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author minte
 * @date 2019/9/5 14:57
 */
public class SnowflakeIdWorkerTest {

    @Test
    public void test() {

        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 1);

        System.out.println(snowflakeIdWorker.nextId());
    }


    @Test
    public void exp() {

        ScriptEvaluator scriptEvaluator = new GroovyScriptEvaluator();

        //ResourceScriptSource 外部的  
        ScriptSource source = new StaticScriptSource("i+j");
        Map<String, Object> env = new HashMap<>();
        env.put("i", 2);
        env.put("j", 6);
        Object o = scriptEvaluator.evaluate(source, env);
        System.out.println(o);


    }
}
