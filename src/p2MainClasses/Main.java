package p2MainClasses;



import java.util.ArrayDeque;
import java.util.Scanner;

import Policies.*;



/**
 * Main class - Select between the different policies,
 * and different servers. Get the inputs from the customer 
 * and give them to the other classes to be analyze.
 * @author Jeffrey Pagan
 * @author Dariel Ramos
 *
 */

public class Main 
{
	@SuppressWarnings({ "unchecked", "resource", "rawtypes" })
	public static void main(String[] args) 
	{ 			
		ArrayDeque<Customer> arrCustomers = new ArrayDeque();
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter how many customers you want to consider: ");
		int numOfCus = in.nextInt();
		int counter = 0;
		
		while(counter != numOfCus)
		{
			System.out.println("Enter the arrival time(t) and the time to complete customer #" + Integer.toString(counter+1));
			int t = in.nextInt();
			int s = in.nextInt();

			arrCustomers.add(new Customer(counter+1, t, s));

			counter++;
		}
		System.out.println("Number of customers is: "+numOfCus);
		for(int servers=1;servers<=5;servers+=2){
			SLMS a = new SLMS(servers);	
			a.evaluate(arrCustomers);
			
		}
		System.out.println("---------------------------");
		for(int servers=1;servers<=5;servers+=2){				
			MLMS b = new MLMS(servers);			
			b.evaluate(arrCustomers);		
		}
		System.out.println("---------------------------");
		for(int servers=1;servers<=5;servers+=2){		
			MLMSBLL c= new MLMSBLL(servers);
			c.evaluate(arrCustomers);
		}
		System.out.println("---------------------------");
		for(int servers=1;servers<=5;servers+=2){		
			MLMSBWT d= new MLMSBWT(servers);
			d.evaluate(arrCustomers);
		}
	}

}