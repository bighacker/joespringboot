package com.joe.joespringboot.controller;

import com.joe.joespringboot.service.TestService;
import com.joe.joespringboot.utils.AsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class Testcontroller {
    private static Logger logger = LoggerFactory.getLogger(Testcontroller.class);

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public Map<String, Object> test(String test){
        return testService.test(test);
    }

    @RequestMapping("/testThreadpool")
    public String testThread(String param){
        String result = "";
        try {
            Callable callable = ()-> {//lambda
                String res = testService.testThread(param);
                TimeUnit.MILLISECONDS.sleep(5000);
                return res + new Date();
            };

            FutureTask<String> futureTask = new FutureTask<String>(callable);
            AsyncTask.addThread(futureTask);

            result = futureTask.get();

        }catch (Exception e){
            logger.info("test exception");
        }

        return result;
    }

}
