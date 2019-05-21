package com.joe.joespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@ServletComponentScan
@EnableTransactionManagement // 支持事务
@EnableAutoConfiguration
public class JoespringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoespringbootApplication.class, args);
	}

}
