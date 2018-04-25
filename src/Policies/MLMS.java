package Policies;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;


//1 server 1 fila
//3 servers 1 fila
//5 servers 1 fila
//si un server se vacia,el customer que entra entra a esa fila
//un vez hace fila,no puede cambiarse

import p2MainClasses.Customer;

public class MLMS extends AbstractPolicy{

	
	
	
	public MLMS(String name){
		super(name);
		
		
	}

	public  ArrayList<Integer> evaluate(ArrayDeque<Customer> input, int servers,int numCust){		
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

		
		
		return resT1T2M;


	}
// metodo booleano que verifica si un server se vacia
	//hacer metodo que me diga cual es el server vacio con min index
	//hacer metodo que lo mueva al server vacio
	

}