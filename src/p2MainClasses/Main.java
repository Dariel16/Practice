package p2MainClasses;



import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

import Policies.MLMS;



public class Main 
{
	@SuppressWarnings({ "unchecked", "resource", "rawtypes" })
	public static void main(String[] args) 
	{ 
		MLMS m1 = new MLMS("MLMS");
		
		//craer 7 queueus
		//usar metodo clear para borrar el completedCostumer cada vez que termine de atender a todos,luego usarlo con otro metodo
		ArrayDeque<Customer> arrCustomers = new ArrayDeque();
		ArrayDeque<Customer> lineArrQueue = new ArrayDeque();// processing
		ArrayList<Customer>	completedCustomer = new ArrayList<>();
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter how many customers you want to consider: ");
         
		int numOfCus = in.nextInt();
		
		int counter = 0;
		//hacer esto como si fuese el metodo execute		
		while(counter != numOfCus)
		{
			System.out.println("Enter the arrival time(t) and the time to complete customer #" + Integer.toString(counter+1));
			int t = in.nextInt();
			int s = in.nextInt();
			
			arrCustomers.add(new Customer(counter+1, t, s));
			
			counter++;
		}
		  
		
		
		 m1.evaluate(arrCustomers, 1,arrCustomers.size());//inputCustomers, servers,#customers
		
		
		//t1- tiempo en que tarda en procesar todos los customers
		//System.out.println("Total time in system is: " + sumOfTimes);
		//double t2 = sumOfTimes/numOfCus; //promedio de esperar del input
		//System.out.printf("Average waiting time for input file:  %.2f" ,t2);

		
	}
	
	//
	//


	
}