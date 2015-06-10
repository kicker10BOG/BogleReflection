package bogle.projectReflection.test;


import java.util.List;

import bogle.projectReflection.ProjectReflection;

/**
 * This file provides a simple example of how to map out the classes
 * of a project. 
 * 
 * @author Jason L. Bogle
 */
public class ProjectMappingTest {
	public static void main(String[] args) {

		
		ProjectReflection pr = new ProjectReflection();
		
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("Start Project Mapping Test");
		System.out.println("~~~~~~~~~~~~~~~");
		

		System.out.println("Test getAllLoadedClasses");
		System.out.println("  getAllLoadedClasses()");
		List<Class<?>> allLoadedClasses = pr.getAllLoadedClasses();
		pr.displayAll(allLoadedClasses);
		for (Class<?> c : allLoadedClasses) {
			System.out.println(c.toGenericString());
		}

		System.out.println("\nTest getClass - getting a potentially unloaded class");
		System.out.println("  getClass(\"bogle.projectReflection.test.RentCar\")");
		Class<?> klass = pr.getClass("bogle.projectReflection.test.RentCar");
		if (klass != null) {
			pr.CR.display(klass);
		}
		else {
			System.out.println("failed");
		}
		System.out.println("\n  getClass(\"java.util.Timer\")");
		klass = pr.getClass("java.util.Timer");
		if (klass != null) {
			pr.CR.display(klass);
		}
		else {
			System.out.println("failed");
		}
		
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("End Project Mapping Test");
		System.out.println("~~~~~~~~~~~~~~~");
	}
}