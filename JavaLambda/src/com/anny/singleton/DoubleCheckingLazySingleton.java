package com.anny.singleton;

public class DoubleCheckingLazySingleton {
	
	private static DoubleCheckingLazySingleton instance;
	
	private DoubleCheckingLazySingleton() {
		
	}
	
	public static DoubleCheckingLazySingleton getInstance() {
		
		if(instance==null) {
			synchronized (DoubleCheckingLazySingleton.class) {
				if(instance==null) {
					instance=new DoubleCheckingLazySingleton();
				}
			}
		}
		return instance;
	}
}
