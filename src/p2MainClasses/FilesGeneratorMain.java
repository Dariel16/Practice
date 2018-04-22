package p2MainClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import dataGenerator2.DataGenerator;

public class FilesGeneratorMain {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length <= 1) {
			int n = 15;  //el valor de File_i.txt  (n==i y n empieza en 1)
			//int m = 50; 
			//int size = 50000; 
			if (args.length >= 1) 
				n = Integer.parseInt(args[0]); 
//			if (args.length >= 2) 
//				m = Integer.parseInt(args[1]); 
//			if (args.length == 3) 
//				size = Integer.parseInt(args[2]); 
			generateFiles(n); 
		} 
		else 
			System.out.println("Invalid number of parameters. Must be < 1.");

 
	}

	private static void generateFiles(int n) throws FileNotFoundException {
		String parentDirectory = "inputFiles";   // must exist in current directory
		DataGenerator dg = new DataGenerator(n);
		Object[] setsLists = dg.generateData();  

		PrintWriter paramsFile = new PrintWriter(new File(parentDirectory, "dataFiles.txt")); 
		paramsFile.println(n);   // save parameter n
		//paramsFile.println(m);   // save parameter m
		paramsFile.close();
		
		// create all the files for testing and grading with random integer values as
		// content. Each such file represents a set, since there is no repetition of
		// values. Some might end being empty...
		for (int i=1; i<=n; i++)
			 {
				String fileName = "F_" + i +".txt"; //genera los File_i.txt
				PrintWriter out = new PrintWriter(new File(parentDirectory, fileName)); 
//				for (int k=0; k<setsLists[i].length; k++) // no se que hace la k
//					out.println(setsLists[i][k]);
				out.close();
			}


	}
}
