package com.anny.mapvsflatmap;

import java.util.Arrays;
import java.util.List;

public class DataClass {
	
	public static List<Employee> getEmployees() {
		
		List<Employee> employees=Arrays.asList(
				new Employee(11, "Naren", "Naren@gmail.com", Arrays.asList("23232323","32323232")),
				new Employee(11, "Funny", "Funny@gmail.com", Arrays.asList("23232323","32323232")),
				new Employee(11, "Mittu", "Mittu@gmail.com", Arrays.asList("23232323","32323232")),
				new Employee(11, "Anny", "Anny@gmail.com", Arrays.asList("23232323","32323232")));
		
		return employees;
		
	}
}
