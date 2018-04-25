package Policies;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;




import p2MainClasses.Customer;
//import p2MainClasses.Main.Customer;

public class MLMS {

	
	
	
	public MLMS(int name){
		//super(name);
		
		
	}

	public static  ArrayList<Integer> evaluate(ArrayDeque<Customer> input, int servers,int numCust){		
		double timer = 0;
		double sumOfTimes = 0;//t1


		ArrayList<Integer>   resT1T2M = new ArrayList<>();  // para t1 t2 y m

		ArrayDeque<Customer> lineArrQueue = new ArrayDeque();// processing
		ArrayList<Customer>	completedCustomer = new ArrayList<>();

		while(!(completedCustomer.size() == input.size()))
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
					lineArrQueue.poll();
				}

				//				else
				//					processingQueue.add(ctr);
			}

			for(Customer c : input) // acomoda el line queue por el orden de llegada (t) 
				if(c.getArrivalEvent() == timer)
					lineArrQueue.add(c);

			timer++;
		}
		System.out.println("Total time in system is: " + sumOfTimes);
		double t2 = sumOfTimes/numCust; //promedio de esperar del input
		System.out.printf("Average waiting time for input file:  %.2f" ,t2);

		//int T1 =resT1T2M.get(0);
		//int T2=resT1T2M.get(1);
		
		return resT1T2M;


	}



}