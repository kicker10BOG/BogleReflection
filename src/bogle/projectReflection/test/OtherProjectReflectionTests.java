package bogle.projectReflection.test;

import java.util.List;

import bogle.projectReflection.ProjectReflection;

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
		exists = pr.classExists("RentCar", "bogle.projectReflection");
		System.out.println("  classExists(\"RentCar\", \"bogle.projectReflection\")");
		System.out.println("  ->"+exists);
		exists = pr.classExists("airplane", "bogle.projectReflection");
		System.out.println("  classExists(\"airplane\", \"bogle.projectReflection\")");
		System.out.println("  ->"+exists);

		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("End Class Exists Tests");
		System.out.println("~~~~~~~~~~~~~~~");

		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("Start Get Class Tests");
		System.out.println("~~~~~~~~~~~~~~~");
		
		System.out.println("\nTest getLoadedClassesFromPackage");
		System.out.println("  getLoadedClassesFromPackage(\"bogle.projectReflection\")");
		List<Class<?>> packClasses = pr.getLoadedClassesFromPackage("bogle.projectReflection");
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
		theClass = pr.getClass("RentCar", "bogle.projectReflection");
		System.out.println("  getClass(\"RentCar\", \"bogle.projectReflection\")");
		if (theClass != null) {
			System.out.println("  ->"+theClass.toGenericString());
		} 
		else {
			System.out.println("  ->RentCar not found");
		}
		theClass = pr.getClass("airplane", "bogle.projectReflection");
		System.out.println("  getClass(\"airplane\", \"bogle.projectReflection\")");
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
