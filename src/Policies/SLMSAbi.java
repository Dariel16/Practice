package Policies;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;

import p2MainClasses.Customer;
import p2MainClasses.Server;

public class SLMSAbi {

	int servers;
	ArrayDeque<Customer> filaAtender;
	ArrayDeque<Customer> completed;
	HashMap<Server, ArrayDeque<Customer>> clerkMap;

	public SLMSAbi(int servers) 
	{
		this.servers=servers;
		this.clerkMap = new HashMap<Server, ArrayDeque<Customer>>();
	}

	public  void  evaluate(ArrayDeque<Customer> input)
	{		

		int timer = 0;

		//create empty servers
		for(int i=0;i<servers;i++)
		{
			clerkMap.put(new Server(), null);
		}

		this.filaAtender = new ArrayDeque<Customer>();
		this.completed = new ArrayDeque<Customer>();

		while(completed.size() != input.size())//nos dice cuando atendimos a todos
		{
			
			for(Server serv : clerkMap.keySet())
			{
				if(!serv.isAvilable())
				{
					Customer cCustomer = serv.getCustomer();
					if(timer == cCustomer.getServiceStartTime() + cCustomer.getCurrentCustomerS())
					{
						completed.add(serv.complete());
					}
				}
				else
				{
					Customer nextCustomer = filaAtender.poll();
					if(nextCustomer != null)
						serv.serve(nextCustomer, timer);
				}
			}

			for(Customer cus : input)
			{
				if(timer==cus.getArrivalEvent())
				{
					filaAtender.add(cus);
				}
			}

			timer++;
		}

		System.out.println("T1 "+servers+" is: " + timer);
		
		System.out.println("T2(Avg) "+servers+" is: " + aveWaitingTime() );
		
		System.out.println("M: 0");

	}

	private double aveWaitingTime() {
		int sum=0;
		for(Customer c : completed) {
			sum += c.getWaitingTime();
		}
		return sum / completed.size();
	}
	
}