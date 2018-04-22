package dataGenerator2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author pedroirivera-vega
 *
 */
public class DataReader {

	private int n;    // number of clients to attend
	//private int m;    // number of data sets produced per data generator
	private Integer[][] dataSet; 
	private String parentDirectory; 


	public DataReader() throws FileNotFoundException {
		parentDirectory = "inputFiles"; 
		Scanner parameters = new Scanner(new File(parentDirectory, "dataFiles.txt")); 
		// the values of n and m shall be read from file: "inputFiles/parameters.txt". 
		this.n = parameters.nextInt(); 
		//this.m = parameters.nextInt();
		parameters.close();
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException 
	 */
	public Object[][] readDataFiles() throws FileNotFoundException {
		dataSet = new Integer[n][];

		for (int i=1; i<n; i++) { 

			String fileName = "F_" + i +".txt"; 
			Scanner inputFile = new Scanner(new File(parentDirectory, fileName)); 
			ArrayList<Integer> fileContent = new ArrayList<>(); 
			while (inputFile.hasNext())
				fileContent.add(inputFile.nextInt());
			inputFile.close();
			dataSet[i] = (Integer[]) fileContent.toArray(new Integer[0]);  

		}	
		return dataSet; 
	}

	public void printSets() { 
		System.out.println("Set 't' 's' for Fi are: " ); 
		for (int i=1; i<=n; i++){

			System.out.print("Set["+i+"]= ");
			if(i<15)//no entiendo porq esto me tira error en 15
			printArray((Integer[]) dataSet[i]);
		}			
	}

	private void printArray(Integer[] numbers) {
		for (int i=1; i<=numbers.length; i++) 
		System.out.print(numbers[i] + "  "); 
		System.out.println(); 
	}


}
