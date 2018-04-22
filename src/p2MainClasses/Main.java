package p2MainClasses;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	@SuppressWarnings({ "unchecked", "resource", "rawtypes" })
	public static void main(String[] args) 
	{
		
		ArrayDeque<Customer> arrCustumers = new ArrayDeque();
		ArrayDeque<Customer> lineArrQueue = new ArrayDeque();
		ArrayList<Customer>	completedCustomer = new ArrayList<>();
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter how many jobs you want to consider: ");
         
		int numOfCus = in.nextInt();
		
		int counter = 0;
				
		while(counter != numOfCus)
		{
			System.out.println("Enter the arrival time and the time to complete job #" + Integer.toString(counter+1));
			int t = in.nextInt();
			int s = in.nextInt();
			
			arrCustumers.add(new Customer(counter+1, t, s));
			
			counter++;
		}
		
		double timer = 0;
		double sumOfTimes = 0;
		
		
		while(!(completedCustomer.size() == arrCustumers.size()))
		{
			if(!lineArrQueue.isEmpty())
			{	
				//int currentCustomerTime = processingQueue.peek().getArrivalEvent();// guarda valor de t
				
				lineArrQueue.peek().isServed(1); // aumenta currentCustomerTime++
				Customer ctr = lineArrQueue.peek();
				
				if(ctr.getCurrentCustomerTime() == ctr.getArrivalEvent()+ctr.getCurrentCustomerS())
				{
					completedCustomer.add(ctr);
					sumOfTimes += (timer - ctr.getArrivalEvent());
				}
				
//				else
//					processingQueue.add(ctr);
			}
			
			for(Customer c : arrCustumers) // acomoda el line queue por el orden de llegada (t) 
				if(c.getArrivalEvent() == timer)
					lineArrQueue.add(c);
			
			timer++;
		}
		
		double result = sumOfTimes/numOfCus;
		System.out.println("Average time in system is: " + result);
	}
	
		private static class Customer{
			private int cusID;            // id of this job
			private int arrivalEvent;    // arrival time of this job
			private int customerS;  // remaining service time for this job
			private int serviceCompleted;  // time when the service for this job is completed
			private int currentCustomerTime;
			public Customer(int id, int t, int s) { 
				cusID = id; 
				arrivalEvent = t;
				currentCustomerTime= t;
				customerS = s; 
			}
			@SuppressWarnings("unused")
			public int getCompletedTime() {
				return serviceCompleted;
			}
			@SuppressWarnings("unused")
			public void setCompletedTime(int departureTime) {
				this.serviceCompleted = departureTime;
			}
			@SuppressWarnings("unused")
			public int getCusID() {
				return cusID;
			}
			public int getArrivalEvent() {
				return arrivalEvent;
			}
			public int getCurrentCustomerS() {// valor a comparar con T+S
				return customerS;
			}
			public int getCurrentCustomerTime() {
				return currentCustomerTime;
			}
			public void setCurrentCustomerTime(int timer) {
				currentCustomerTime = timer;
			}
			
	
			/**
			 * Registers an update of serviced received by this job. 
			 * @param q the time of the service being registered. 
			 */
			public void isServed(int q) { 
				//if (q < 0) return;   // only register positive value of q
				currentCustomerTime += q; 
			}
	
			/**
			 * Generates a string that describes this job; useful for printing
			 * information about the job.
			 */
			public String toString() { 
				return "cusID = " + cusID + 
						"  Arrival Event Time = " + arrivalEvent +
						"  Remaining Time = " + customerS +
						"  Service Completed Time = " + serviceCompleted;                 
			}
		}


	
}