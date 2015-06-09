package com.projectReflection.test;

import java.util.List;

import com.projectReflection.ProjectReflection;

public class OtherProjectReflectionTests {
	public static void main(String[] args) {
		ProjectReflection pr = new ProjectReflection();
		boolean exists = true;
		try {
			ClassLoader.getSystemClassLoader().loadClass(RentCar.class.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Class<?> theClass = null;
		List<Class<?>> classes = pr.getAllLoadedClasses();

		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("Start Class Exists Tests");
		System.out.println("~~~~~~~~~~~~~~~");

		System.out.println("Test classExists");
		exists = pr.classExists("RentCar", classes);
		System.out.println("  classExists(\"RentCar\", classes)");
		System.out.println("  ->"+exists);
		exists = pr.classExists("airplane", classes);
		System.out.println("  classExists(\"airplane\", classes)");
		System.out.println("  ->"+exists);
		exists = pr.classExists("RentCar", "com.projectReflection");
		System.out.println("  classExists(\"RentCar\", \"com.projectReflection\")");
		System.out.println("  ->"+exists);
		exists = pr.classExists("airplane", "com.projectReflection");
		System.out.println("  classExists(\"airplane\", \"com.projectReflection\")");
		System.out.println("  ->"+exists);

		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("End Class Exists Tests");
		System.out.println("~~~~~~~~~~~~~~~");

		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("Start Get Class Tests");
		System.out.println("~~~~~~~~~~~~~~~");
		
		System.out.println("\nTest getLoadedClassesFromPackage");
		System.out.println("  getLoadedClassesFromPackage(\"com.projectReflection\")");
		List<Class<?>> packClasses = pr.getLoadedClassesFromPackage("com.projectReflection");
		for (Class<?> c : packClasses) {
			System.out.println("  ->"+c.toGenericString());
		}

		System.out.println("\nTest getClass");
		theClass = pr.getClass("RentCar", classes);
		System.out.println("  getClass(\"RentCar\", classes)");
		if (theClass != null) {
			System.out.println("  ->"+theClass.toGenericString());
		} 
		else {
			System.out.println("  ->RentCar not found");
		}
		theClass = pr.getClass("airplane", classes);
		System.out.println("  getClass(\"airplane\", classes)");
		if (theClass != null) {
			System.out.println("  ->"+theClass.toGenericString());
		} 
		else {
			System.out.println("  ->airplane not found");
		}
		theClass = pr.getClass("RentCar", "com.projectReflection");
		System.out.println("  getClass(\"RentCar\", \"com.projectReflection\")");
		if (theClass != null) {
			System.out.println("  ->"+theClass.toGenericString());
		} 
		else {
			System.out.println("  ->RentCar not found");
		}
		theClass = pr.getClass("airplane", "com.projectReflection");
		System.out.println("  getClass(\"airplane\", \"com.projectReflection\")");
		if (theClass != null) {
			System.out.println("  ->"+theClass.toGenericString());
		} 
		else {
			System.out.println("  ->airplane not found");
		}

		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("End Get Class Tests");
		System.out.println("~~~~~~~~~~~~~~~");
	}
}
