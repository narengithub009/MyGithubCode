package com.java.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CourseApiApp {
	public static void main(String[] args) {
		SpringApplication.run(CourseApiApp.class, args);
		
		System.out.println("Created Spring boot application");
	}
	
	
	@RequestMapping("/welcome")
	String message(String s) {
		return "Hello Welcome World";
	}
}
