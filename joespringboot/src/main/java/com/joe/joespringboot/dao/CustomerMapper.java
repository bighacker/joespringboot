package com.joe.joespringboot.dao;

import com.joe.joespringboot.model.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    int insert(Customer record);

    int insertSelective(Customer record);
}