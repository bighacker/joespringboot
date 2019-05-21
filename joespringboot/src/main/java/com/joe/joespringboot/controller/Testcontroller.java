package com.joe.joespringboot.controller;

import com.joe.joespringboot.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("user")
public class Testcontroller {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public Map<String, Object> test(String test){
        return testService.test(test);
    }

}
