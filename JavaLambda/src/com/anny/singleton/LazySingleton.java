package com.anny.singleton;

public class LazySingleton {
	
	private LazySingleton()
	{
		System.out.println("LazySIngleton Instanciation");
	}
	
	private static LazySingleton instance;
	
	public static LazySingleton getInstance() {
		if(instance==null) {
			return instance=new LazySingleton();
		}else {
			return instance;
		}
	}
}
