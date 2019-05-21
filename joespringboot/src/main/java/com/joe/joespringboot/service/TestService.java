package com.joe.joespringboot.service;

import java.util.Map;

public interface TestService {
    Map<String,Object> test(String test);
    String testThread(String test);
}
