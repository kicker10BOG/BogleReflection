package bogle.projectReflection.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;



import bogle.projectReflection.ClassReflection;

public class ClassReflectionTests {
	public static void main(String[] args) {
		ClassReflection cr = new ClassReflection();
		Class<?> theClass = Airplane.class;
		List<Method> methods = null;
		List<Field> fields = null;
		Field field = null;
		Pattern pattern = Pattern.compile("(?i)price");
		
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("Start Class Display Test");
		System.out.println("~~~~~~~~~~~~~~~");
		
		System.out.println("  display(theClass)");
		cr.display(theClass);
		
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("End Class Display Test");
		System.out.println("~~~~~~~~~~~~~~~");
		
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("Start Class Method Tests");
		System.out.println("~~~~~~~~~~~~~~~");
		
		System.out.println("Test getMethodsByString");
		methods = cr.getMethodsByString("getPrice", theClass);
		System.out.println("  getMethodsByString(\"getPrice\", theClass)");
		for (Method m : methods) {
			System.out.println("  ->"+m.toGenericString());
		}
		
		System.out.println("\nTest getMethodsByPattern");
		methods = cr.getMethodsByPattern(pattern, theClass);
		System.out.println("  getMethodsByPattern(pattern, theClass)");
		for (Method m : methods) {
			System.out.println("  ->"+m.toGenericString());
		}
		
		System.out.println("\nTest filterMethodsByParams");
		String[] params = {"String"};
		methods = cr.getMethodsByPattern(Pattern.compile(".*"), theClass);
		methods = cr.filterMethodsByParams(methods, params);
		System.out.println("  filterMethodsByParams(methods, params)");
		for (Method m : methods) {
			System.out.println("  ->"+m.toGenericString());
		}
		
		System.out.println("\nTest filterMethodsByReturnType");
		methods = cr.getMethodsByPattern(Pattern.compile(".*"), theClass);
		methods = cr.filterMethodsByReturnType(methods, "String");
		System.out.println("  filterMethodsByReturnType(methods, \"String\")");
		for (Method m : methods) {
			System.out.println("  ->"+m.toGenericString());
		}
		
		System.out.println("\n~~~~~~~~~~~~~~~");
		System.out.println("End Class Method Tests");
		System.out.println("~~~~~~~~~~~~~~~");

		System.out.println("\n~~~~~~~~~~~~~~~");
		System.out.println("Start Class Field Tests");
		System.out.println("~~~~~~~~~~~~~~~");
		
		System.out.println("Test getFieldByString");
		field = cr.getFieldByString("airliner", theClass);
		System.out.println("  getFieldByString(\"airliner\", theClass)");
		if (field != null) {
			System.out.println("  ->"+field.toGenericString());
		}
		else {
			System.out.println("Test Failed");
		}
		
		System.out.println("\nTest getFieldsByPattern");
		fields = cr.getFieldsByPattern(pattern, theClass);
		System.out.println("  getFieldsByPattern(pattern, theClass)");
		for (Field f : fields) {
			System.out.println("  ->"+f.toGenericString());
		}
		
		System.out.println("\nTest FilterFieldsByType");
		fields = cr.getFieldsByPattern(Pattern.compile(".*"), theClass);
		fields = cr.filterFieldsByType(fields, "double");
		System.out.println("  FilterFieldsByType(fields, \"double\")");
		for (Field f : fields) {
			System.out.println("  ->"+f.toGenericString());
		}
		
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("End Class Field Tests");
		System.out.println("~~~~~~~~~~~~~~~");


	}
}
