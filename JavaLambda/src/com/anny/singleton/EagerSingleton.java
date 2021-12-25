package com.anny.singleton;

public class EagerSingleton {
	
	private static final EagerSingleton instance = new EagerSingleton();
	
	private EagerSingleton() {

		System.out.println("EagerSingletone instanciation");
	}
	
	public static EagerSingleton getInstance() {
		return instance;
		
	}
}
