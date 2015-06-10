package bogle.projectReflection;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class allows for the mapping of a project. 
 * 
 * @author Jason L. Bogle
 */
public class ProjectReflection {

	private static final char DOT = '.';

	private static final char SLASH = '/';
	
	/**
	 * For easy access to the ClassReflection class
	 */
	public final ClassReflection CR = new ClassReflection();
	
	/**
	 * Gets all classes that are loaded. 
	 * 
	 * @return	A list of all loaded classes
	 */
	public List<Class<?>> getAllLoadedClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		Properties props = System.getProperties();
		for (String p1 : props.toString().split(", ")) {
			String[] s = p1.split("=");
			if (s[0].equals("java.class.path")) {
				String[] s2 = s[1].split(";");
				for (String s3 : s2) {
					classes.addAll(getLoadedClassesFromFile(s3));
				}
			} 
			else if (s[0].equals("sun.boot.class.path")) {
				String[] s2 = s[1].split(";");
				for (String s3 : s2) {
					classes.addAll(getLoadedClassesFromFile(s3));
				}
			}
		}
		// Be sure there are no duplicates
		Set<Class<?>> theClasses = new LinkedHashSet<>(classes);
		classes.clear();
		classes.addAll(theClasses);
		return classes;
	}
	
	/**
	 * Gets all loaded classes from a file (or directory). Calls
	 * {@link #getLoadedClassesFromFile(File)}  
	 * 
	 * @param fileName	The name of the file (or directory) to search in
	 * @return	list of loaded classes found
	 * 			<br>empty list if none are found
	 */
	public List<Class<?>> getLoadedClassesFromFile(String fileName) {
		
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		File file = new File(fileName); 
		if (file.exists()) {
			classes.addAll(getLoadedClassesFromFile(file));
		}
		return classes;
	}
	
	/**
	 * Gets all loaded classes in a file. 
	 * 
	 * @param file		The file to search for loaded classes in 
	 * @return	list of loaded classes from the jar file
	 * 			<br>empty list if no loaded classes are found
	 */
	public List<Class<?>> getLoadedClassesFromFile(File file) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		if (file.exists()) {
			if (file.isFile()) {
				if (file.getName().endsWith(".jar")) {
					try {
						classes.addAll(getLoadedClassesFromJarFile(new JarFile(file)));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if (file.getName().endsWith(".class")) {
					Class<?> c = getLoadedClassFromDotClassFile(file);
					if (c != null) {
						classes.add(c);
					}
				}
			}
			else if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					classes.addAll(getLoadedClassesFromFile(f));
				}
			}
		}
		return classes;
	}
	
	/**
	 * Gets all loaded classes in a jar file. 
	 * 
	 * @param jarFile		The jar file to search for loaded classes in 
	 * @return	list of loaded classes in the jar file
	 * 			<br>empty list if none are found
	 */
	public List<Class<?>> getLoadedClassesFromJarFile(JarFile jarFile) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		ClassLoader scl = ClassLoader.getSystemClassLoader();
		try {
			java.lang.reflect.Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
	        m.setAccessible(true);
	        
	        Enumeration<JarEntry> allEntries = jarFile.entries();
            while (allEntries.hasMoreElements()) {
                JarEntry entry = (JarEntry) allEntries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".class")) {
                	name = name.replace(SLASH, DOT).substring(0, name.length() - ".class".length());
                	Class<?> c = (Class<?>) m.invoke(scl, name);
                	if (c != null) {
                		classes.add(c);
                	}		                	
                }
            }
            jarFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	/**
	 * Gets the loaded classes in a .class file. 
	 * 
	 * @param file		The file to search for loaded a class in 
	 * @return	the class in the .class file if it is loaded
	 * 			<br>null if the class is not loaded
	 */
	public Class<?> getLoadedClassFromDotClassFile(File file) {
		ClassLoader scl = ClassLoader.getSystemClassLoader();
		try {
			java.lang.reflect.Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
	        m.setAccessible(true);
	        
	        Package[] packs = Package.getPackages();
	        for (Package p : packs) {
	        	String className = p.getName()+DOT+file.getName().replace(SLASH, DOT)
	        			.substring(0, file.getName().length() - ".class".length());
	        	Class<?> c = (Class<?>) m.invoke(scl, className);
            	if (c != null) {
            		return c;
            	}		        
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generates a List of classes that exist within a specified package. 
	 * 
	 * @param scannedPackage	Specifies the package to scan for classes
	 * @return A list of classes found
	 */
	public List<Class<?>> getLoadedClassesFromPackage(String scannedPackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.addAll(getAllLoadedClasses());
		List<Class<?>> packageClasses = new ArrayList<Class<?>>();
		for (Class<?> c : classes) {
			if (c.getPackage().getName().startsWith(scannedPackage)) {
				packageClasses.add(c);
			}
		}
		return packageClasses;
	}

	/**
	 * Checks to see if a specific class exists in a list of classes. 
	 * 
	 * @param className		The specified name of the class being checked 
	 * 						for existence - this can be the <em>simple name</em> 
	 * 						(for example: "Bar") or it can be the <em>fully
	 * 						qualified name</em> (for example: "com.foo.Bar")
	 * @param classes		The list of classes being searched
	 * @return true if the class is found
	 * 		   <br>false if the class is not found
	 */
	public boolean classExists(String className, List<Class<?>> classes) {
		if (classes.isEmpty()){
			return false;
		}
		for (Class<?> c : classes) {
			if (c.getSimpleName().equals(className) || c.getName().equals(className)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks to see if a specific class exists in a package. This method calls 
	 * {@link #classExists(String, List)}
	 * after generating a list of classes found in the package specified by "packageName". 
	 * 
	 * @param className		The specified name of the class being checked 
	 * 						for existence - this can be the <em>simple name</em> 
	 * 						(for example: "Bar") or it can be the <em>fully
	 * 						qualified name</em> (for example: "com.foo.Bar")
	 * @param packageName	Specifies the package name to use when generating a list of classes
	 * @return true if the class is found
	 * 		   <br>false if the class is not found
	 */
	public boolean classExists(String className, String packageName) {
		List<Class<?>> classes = getLoadedClassesFromPackage(packageName);
		return classExists(className, classes);
	}
	
	
	
	/**
	 * Gets a specified class from a list of classes. It compares the
	 * class name to the classes and returns the class if it is found. 
	 * Otherwise, it returns null.
	 * 
	 * @param className		The specified name of the class being checked 
	 * 						for existence - this can be the <em>simple name</em> 
	 * 						(for example: "Bar") or it can be the <em>fully
	 * 						qualified name</em> (for example: "com.foo.Bar")
	 * @param classes		The list of classes being searched
	 * @return 	the class if exists
	 * 			<br>null if the class does not exist
	 */
	public Class<?> getClass(String className, List<Class<?>> classes) {
		for (Class<?> c : classes) {
			if (c.getSimpleName().equals(className) || c.getName().equals(className)){
				return c;
			}
		}
		return null;
	}

	/**
	 * Gets a specified class from a package. It creates a list of classes
	 * and calls
	 * {@link #getClass(String, List)}
	 * which returns the class if it is found. Otherwise, it returns null. 
	 * 
	 * @param className		The specified name of the class being checked 
	 * 						for existence - this can be the <em>simple name</em> 
	 * 						(for example: "Bar") or it can be the <em>fully
	 * 						qualified name</em> (for example: "com.foo.Bar")
	 * @param packageName	The specified package to be used when creating 
	 * 						the list of classes
	 * @return 	the class if exists
	 * 			<br>null if the class does not exist
	 */
	public Class<?> getClass(String className, String packageName) {
		List<Class<?>> classes = getLoadedClassesFromPackage(packageName);
		return getClass(className, classes);
	}	
	
	/**
	 * Gets any class, loaded or unloaded, that exists in the build path. 
	 * 
	 * @param className		string containing the name of the class to get - 
	 * 						the name must be the fully qualified name (for 
	 * 						example: "com.foo.Bar")
	 * @return	the class searched for if found
	 * 			<br>null if not found
	 */
	public Class<?> getClass(String className) {
		List<Class<?>> loadedClasses = getAllLoadedClasses();
		Class<?> theClass = getClass(className, loadedClasses);
		if (theClass == null) {
			try {
				ClassLoader scl = ClassLoader.getSystemClassLoader();
				theClass = scl.loadClass(className);
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
			}
		}
		return theClass;
	}
	
	/**
	 * Displays a structural mapping of each class in a list. 
	 * 
	 * @param classes	list of classes that are to be displayed
	 */
	public void displayAll(List<Class<?>> classes) {
		// Print each class
		for (Class<?> c : classes) {
			CR.display(c);
		}
	}	
}