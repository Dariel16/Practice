package p2MainClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dataGenerator2.DataGenerator;
import dataGenerator2.DataReader;



public class Part1Main {

/*
 * */
	public static void content(String archivo) throws FileNotFoundException, IOException {
		String s;
		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);

		while((s = b.readLine())!=null) {
			System.out.println(s);
		}
		b.close();
	}

	public static void main(String[] args) throws IOException {
		


		
		int n=15 ; //int  m = 50; 

		//1. read and print the sets
		DataReader reader = new DataReader(); 
		reader.readDataFiles();
		reader.printSets();
		System.out.println();

		//(n,m, totalSize)
//		Object [][][] dataSet = reader.readDataFiles();  // objects corresponding to sets To, T1,...Tm-1
//
//		
//
//
//
//		MySet<Integer>[] filesParaSet1 = new MySet[m*n];
//		MySet<Integer>[] filesParaSet2 = new MySet[m*n];

		//sin depender de la 
//		int a=0, b=0;
//		for (int j=0; j<m; j++) {  // guarda los Objects en Sets
//			if(b==0){
//				filesParaSet1[j] = new Set1<>();
//				b++;
//			}
//
//			for (int i=0; i<n; i++) {
//
//				for (int k = 0; k < dataSet[i][j].length; k++)
//					filesParaSet1[a].add((Integer)dataSet[i][j][k]);
//				a++;
//				
//				if(a != n*m)
//					filesParaSet1[a] = new Set1<>();
//			}
//		}
//
//		//sin depender de la 
//		int c=0, d=0;
//		for (int j=0; j<m; j++) {  // guarda los Objects en Sets
//			if(d==0){
//				filesParaSet2[j] = new Set2<>();
//				d++;
//			}
//
//			for (int i=0; i<n; i++) {
//
//				for (int k = 0; k < dataSet[i][j].length; k++)
//					filesParaSet2[c].add((Integer)dataSet[i][j][k]);
//				c++;
//				
//				if(c != n*m)
//					filesParaSet2[c] = new Set2<>();
//			}
//		}
//		ArrayList<IntersectionFinder<Integer>> strategies = new ArrayList<IntersectionFinder<Integer>>();
//		strategies.add(new P1yP2Finder<Integer>("P1"));
//		strategies.add(new P1yP2Finder<Integer>("P2"));
//		strategies.add(new P3Finder<Integer>("P3"));
//		strategies.add(new P4Finder<Integer>("P4"));
//		
		
		//aplicar estrategias de intersectos
//		if(args.length== 0){
//			P1yP2Finder	p1 =  new P1yP2Finder("1");
//			
//			System.out.println("Final Set by "+ strategies.get(0).getName() + ": "
//					+ p1.intersectSets(filesParaSet1));
//
//			P1yP2Finder	p2  = new P1yP2Finder("2");
//			
//			System.out.println("Final Set by "+ strategies.get(1).getName() + ": "
//					+ p2.intersectSets(filesParaSet2));
//
//			P3Finder p3 = new P3Finder("3");
//			
//			System.out.println("Final Set by "+ strategies.get(2).getName() + ": "
//					+ p3.intersectSets(filesParaSet2));
//
//			P4Finder p4 = new P4Finder("4");
//			
//			System.out.println("Final Set by "+ strategies.get(3).getName() + ": "
//					+ p4.intersectSets(filesParaSet2));
//
//		}
//		else{
//
//			switch (Integer.parseInt(args[0])){
//
//			case 1:
//
//				P1yP2Finder p1 = new P1yP2Finder("1");
//				
//				System.out.println("Final Set by "+ strategies.get(0).getName() + ": "
//						+ p1.intersectSets(filesParaSet1));
//
//				break;
//
//			case 2:
//
//				P1yP2Finder p2  = new P1yP2Finder("2");
//				System.out.println("Final Set by "+ strategies.get(1).getName() + ": "
//						+ p2.intersectSets(filesParaSet2));
//				break;
//
//			case 3:
//
//				P3Finder p3 = new P3Finder("3");
//				System.out.println("Final Set by "+ strategies.get(2).getName() + ": "
//						+ p3.intersectSets(filesParaSet2));
//				break;
//			case 4:
//
//				P4Finder p4 = new P4Finder("4");
//				System.out.println("Final Set by "+ strategies.get(3).getName() + ": "
//						+ p4.intersectSets(filesParaSet2));
//				break;
//
//			default:
//				p1 =  new P1yP2Finder("1");
//				p1.intersectSets(filesParaSet1);
//				p2  = new P1yP2Finder("2");
//				p2.intersectSets(filesParaSet2);
//				p3 = new P3Finder("3");
//				p3.intersectSets(filesParaSet2);
//				p4 = new P4Finder("4");
//				p4.intersectSets(filesParaSet2);
//
//				break;

			//}
	//	}
		
	}



}



