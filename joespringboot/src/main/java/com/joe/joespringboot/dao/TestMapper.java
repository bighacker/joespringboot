package com.joe.joespringboot.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.Map;

@Mapper
public interface TestMapper {
    Map<String,Object> test(Integer sysno);
}
