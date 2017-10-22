package com.turtlebone.contract;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.turtlebone.contract")
@MapperScan("com.turtlebone.contract.repository")
// @EnableScheduling
@EnableAutoConfiguration
public class ContractApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContractApplication.class);
	}
}
