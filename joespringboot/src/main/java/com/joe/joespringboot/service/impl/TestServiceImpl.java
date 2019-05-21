package com.joe.joespringboot.service.impl;

import com.joe.joespringboot.dao.TestMapper;
import com.joe.joespringboot.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper testMapper;

    @Override
    public Map<String,Object> test(String test) {

        return testMapper.test(Integer.parseInt(test));
    }
}
