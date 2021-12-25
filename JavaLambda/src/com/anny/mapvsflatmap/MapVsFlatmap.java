package com.anny.mapvsflatmap;

import java.util.List;
import java.util.stream.Collectors;

public class MapVsFlatmap {
	public static void main(String[] args) {
		
		List<Employee> employees=DataClass.getEmployees();
		
		List<String> email= employees.stream().map(employee -> employee.getEmail()).sorted().collect(Collectors.toList());
		System.out.println(email);
		
		List<List<String>> empPhone = employees.stream().map(employee->employee.getPhoneNumber()).collect(Collectors.toList());
		System.out.println(empPhone);
		
		List<String> phones = employees.stream().flatMap(employee->employee.getPhoneNumber().stream().sorted()).collect(Collectors.toList());
		
		System.out.println(phones);
	}
}	
