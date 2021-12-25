package com.anny;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.anny.model.Employee;

public class LambdaEx {
	public static void main(String[] args) {
		
		List<Employee> employees=Arrays.asList(
				new Employee("Anny", 10),
				new Employee("Narender", 34),
				new Employee("Funny", 17),
				new Employee("Mittu", 15));
		
		//Collections.sort(employees,new nameComparator());
		Comparator<Employee> comparator=(e1,e2)->e1.getName().compareTo(e2.getName());
		Collections.sort(employees,comparator);
		for(Employee employee: employees) {
			System.out.println(employee.getName()+" "+employee.getAge());
		}
		
		
	}
}
class nameComparator implements Comparator<Employee>{

	@Override
	public int compare(Employee o1, Employee o2) {


		return o1.getName().compareTo(o2.getName());
	}
	
}