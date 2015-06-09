package com.projectReflection.test;


import java.util.List;

import com.projectReflection.ProjectReflection;

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
		List<Class<?>> allLoadedClases = pr.getAllLoadedClasses();
		pr.displayAll(allLoadedClases);
		
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("End Project Mapping Test");
		System.out.println("~~~~~~~~~~~~~~~");
	}
}