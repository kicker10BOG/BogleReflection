package bogle.projectReflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class allows for mapping of a Class.
 * 
 * @author Jason L. Bogle
 *
 */
public class ClassReflection {
	
	/**
	 * Gets a list of methods that match the regular expression and exist in the
	 * given class. It loops through the methods of the class and adds each method
	 * that matches the regular expression to a list. That list is returned. If no
	 * matches are found, the list is empty. 
	 * 
	 * @param methodName	The regular expression used for matching the method names
	 * @param theClass		The class to search in for the method name
	 * @return	a list of methods that match the regular expression
	 * 			<br>the list is empty if no matches are found
	 */
	public List<Method> getMethodsByPattern(Pattern methodName, Class<?> theClass) {
		ArrayList<Method> matches = new ArrayList<Method>();
		if (theClass != null) {
			Method[] methods = theClass.getMethods();
			for (Method m : methods) {
				Matcher matcher = methodName.matcher(m.getName()); 
				if (matcher.find()) {
					matches.add(m);
				}
			}
		}
		return matches;
	}

	/**
	 * Gets a list of methods that match the specified method name in a class. 
	 * It generates a Pattern to exactly match the string calls
	 * {@link #getMethodsByPattern(Pattern, Class)}
	 * 
	 * @param methodName	The method name to search for
	 * @param theClass		The class to search in for the method name
	 * @return	a list of methods that match the exact method name
	 * 			<br>the list is empty if no matches are found
	 */
	public List<Method> getMethodsByString(String methodName, Class<?> theClass) {
		Pattern pattern = Pattern.compile(String.format("\\b%s\\b", methodName));
		return getMethodsByPattern(pattern, theClass);
	}
	
	/**
	 * Filters a list of methods by parameter type. 
	 * 
	 * @param methods	the list of methods to filter
	 * @param params	the array of strings that contain the names of the classes
	 * 					that the parameters should match - names can be "simple names"
	 * 					(for example: "Bar") or fully qualified names (for example: 
	 * 					"com.foo.bar")
	 * @return	list of filtered methods
	 * 			<br>empty list of no matches are found
	 */
	public List<Method> filterMethodsByParams(List<Method> methods, String[] params) {
		List<Method> filtered = new ArrayList<Method>();
		for (Method m : methods) {
			Class<?>[] classes = m.getParameterTypes();
			boolean matches = true;
			if (classes.length == params.length) {
				for (int i = 0; i < params.length && matches; i++) {
					if (!(classes[i].getSimpleName().equals(params[i]) 
							|| classes[i].getName().equals(params[i]))) {
						matches = false;
					}
				}
				if (matches) {
					filtered.add(m);
				}
			}
		}
		return filtered;
	}
	
	/**
	 * Filters a list of methods by Return type. 
	 * 
	 * @param methods	the list of methods to filter
	 * @param type		a string containing the name of the type that the return type
	 * 					should match - names can be "simple names" (for example: "Bar") 
	 * 					or fully qualified names (for example: "com.foo.bar")
	 * @return	list of filtered methods
	 * 			<br>empty list of no matches are found
	 */
	public List<Method> filterMethodsByReturnType(List<Method> methods, String type) {
		List<Method> filtered = new ArrayList<Method>();
		for (Method m : methods) {
			if (m.getReturnType().getName().equals(type) 
					|| m.getReturnType().getSimpleName().equals(type)) {
				filtered.add(m);
			}
		}
		return filtered;
	}
	
	/**
	 * Gets a list of fields that match the given regular expression and exist
	 * in the given class. It loops through the fields of the class and checks 
	 * for matches. When a match is found, it is added to a list of fields. 
	 * That list is returned. If no matches are found, the list is empty. 
	 * 
	 * @param fieldName		The name of the field to search for
	 * @param theClass		The class to search in for the field
	 * @return	list of fields that match the regular expression
	 * 			<br>list is empty if no matches were found
	 */
	public List<Field> getFieldsByPattern(Pattern fieldName, Class<?> theClass) {
		ArrayList<Field> matches = new ArrayList<Field>();
		if (theClass != null) {
			Field[] fields = theClass.getDeclaredFields();
			for (Field f : fields) {
				Matcher matcher = fieldName.matcher(f.getName()); 
				if (matcher.find()) {
					matches.add(f);
				}
			}
		}
		return matches;
	}
	
	/**
	 * Gets the field that matches the given field name exactly and exists in the
	 * given class. 
	 * 
	 * @param fieldName		The name of the field to search for
	 * @param theClass		The class to search in for the field
	 * @return	the field if a match is found
	 * 			<br>otherwise, null
	 */
	public Field getFieldByString(String fieldName, Class<?> theClass) {
		Field field = null;
		try {
			field = theClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			//e.printStackTrace();
		} catch (SecurityException e) {
			//e.printStackTrace();
		} 
		return field;
	}
	
	/**
	 * Filters a list of fields by type. 
	 * 
	 * @param fields	the list of fields to be filtered
	 * @param type		a string containing the name of the type that the type
	 * 					should match - names can be "simple names" (for example: "Bar") 
	 * 					or fully qualified names (for example: "com.foo.bar")
	 * @return	list of fields that are of the specified type
	 * 			<br>empty list if none are found
	 */
	public List<Field> filterFieldsByType(List<Field> fields, String type) {
		List<Field> filtered = new ArrayList<Field>();
		for (Field f : fields) {
			if (f.getType().getName().equals(type) 
					|| f.getType().getSimpleName().equals(type)) {
				filtered.add(f);
			}
		}
		return filtered;
	}
	
	/**
	 * Displays a structural mapping of the class
	 * 
	 * @param theClass		the class being displayed
	 */
	public void display(Class<?> theClass) {
		// Print class name
		System.out.println(theClass.toGenericString());
		// Get child classes
		try {
			Class<?>[] childClasses = theClass.getDeclaredClasses();
			if (childClasses.length > 0) {
				System.out.println("  Child Classes:");
				for (Class<?> c : childClasses) {
					System.out.println("  -->"+c.toGenericString());
				}
			}
		} catch (SecurityException e) {
			//e.printStackTrace();
		}
		// Get fields
		try {
			Field[] fields = theClass.getDeclaredFields();
			if (fields.length > 0) {
				System.out.println("  Fields:");
				for (Field f : fields) {
					// Print the field name with the type before then name
					System.out.println("  ->"+f.toString());
				}
			}
		} catch (SecurityException e) {
			//e.printStackTrace();
		}
		// Get methods
		try {
			Method[] methods = theClass.getDeclaredMethods();
			if (methods.length > 0) {
				System.out.println("  Methods:");
				for (Method m : methods) {
					// Print method prototype
					System.out.println("  ->"+m.toString());
				}
			}
		} catch (SecurityException e) {
			//e.printStackTrace();
		}
	}
}
