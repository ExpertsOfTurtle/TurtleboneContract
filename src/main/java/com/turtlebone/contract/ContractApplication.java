package com.turtlebone.contract;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(scanBasePackages = "com.turtlebone.contract")
@MapperScan("com.turtlebone.contract.repository")
// @EnableScheduling
@EnableAutoConfiguration
public class ContractApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContractApplication.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "DELETE", "GET", "POST")
						.allowedHeaders("*")
						.exposedHeaders("access-control-allow-headers", "access-control-allow-methods",
								"access-control-allow-origin", "access-control-max-age", "X-Frame-Options")
						.allowCredentials(false).maxAge(3600);
			}
		};

	}
}
