package com.anny.singleton;

public class App {
	public static void main(String[] args) {
		
		/*EagerSingleton eagerSingleton=EagerSingleton.getInstance();
		System.out.println(eagerSingleton.hashCode());
		
		EagerSingleton eagerSingleton2=EagerSingleton.getInstance();
		System.out.println(eagerSingleton2.hashCode());*/
		
		/*LazySingleton lazySingleton=LazySingleton.getInstance();
		System.out.println(lazySingleton.hashCode());
		
		LazySingleton lazySingleton1=LazySingleton.getInstance();
		System.out.println(lazySingleton1.hashCode());*/
		
		DoubleCheckingLazySingleton lazySingleton=DoubleCheckingLazySingleton.getInstance();
		System.out.println(lazySingleton.hashCode());
		
		DoubleCheckingLazySingleton lazySingleton1=DoubleCheckingLazySingleton.getInstance();
		System.out.println(lazySingleton1.hashCode());
	}
}
