package Policies;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import p2MainClasses.Customer;
/**
 * First Policy - Under this policy, there is only one waiting line and one or more service posts. 
 * Whenever a post is available, the first person in line, if any, will start to be served by the service person at the post. 
 * In the case in which there are more than one server available at a moment, 
 * then the first person in line will go to the available post having  min index value among those available. 
 * @author Jeffrey Pagan
 *@author Dariel Ramos
 */
public class SLMS {




	private int timer = 0;
	
	Map<Integer,Customer>clerkMap= new TreeMap<>();
	ArrayDeque<Customer> line = new ArrayDeque<Customer>();// processing	
	ArrayList<Customer> completed = new ArrayList<>();
	
	int servers; 

	/**
	 * Constructor
	 * get the amount of server to use at the time
	 * @param servers
	 */
	public SLMS(int servers ) {
		
		this.servers=servers;

	}
	/**
	 * Method that analyze the customers with their different values, and different servers
	 * @param input - ArrayQueue of all the customers to set on the line to be served 
	 * 
	 */
	public  void  evaluate(ArrayDeque<Customer> input){		


		//create empty servers
		for(int i=0;i<servers;i++){

			clerkMap.put(i, null);

		}


		while(!(completed.size() == input.size()))//nos dice cuando atendimos a todos
		{
			//el if chequea si faltan eventos por hacer
			if(!line.isEmpty()||completed.size()!=input.size())//puede darse el error que pase el tiempo y la fila se vacie
			{//or el map contenga un customer	


				//**SERVICE COMPLETED EVENT*// #1

				for(int i=0;i<servers;i++){
					if((clerkMap.get(i))!=null &&
							(clerkMap.get(i).getCurrentCustomerS()+ clerkMap.get(i).getServiceStartTime())==clerkMap.get(i).getCurrentCustomerTime()) {
						cleanServer(i);
					}					
				}
				//chequear si hay algun server vacio(solo sirve para 1 vez)			

				checkEmptyServer(servers);


				//SERVICE STARTS EVENT #3  //atiende a todos los cientes en servers
				for(int s=0;s<servers;s++){
					if(clerkMap.get(s)!=null){
						clerkMap.get(s).setCurrentCustomerTime(clerkMap.get(s).getCurrentCustomerTime()+1);
						}
				}				
			}

			//*ARRIVAL EVENT*// #4
			for(Customer c : input) // acomoda el line queue por el orden de llegada (t) 
				if(c.getArrivalEvent() == timer) {
					line.add(c);
					
				}
			timer++;
		}
	
		double T2=aveWaitingTime();
		double m=calculateSumFastPeople()/completed.size();
		DecimalFormat f=new DecimalFormat("#.00");
		System.out.println("SLMS "+servers+": " + timer+"  "+f.format(T2)+" "+f.format(m));

	}

	/**
	 * Method to check if the server is empty to get another customer
	 * @param numS - number of server to check
	 * @return serversMap - map of clerks(servers) that analyze each customer. serversMap< # of server, customer>
	 */
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		boolean isOccupied=false;

		while(serverN < numS && isOccupied ==false  && !line.isEmpty()){// servers de 0 a n

			// significa que el map no tiene a nadie
			if(clerkMap.get(serverN)==null){	
			
				clerkMap.put(serverN, line.poll());
				clerkMap.get(serverN).setServiceStartTime(timer);
				clerkMap.get(serverN).setCurrentCustomerTime(timer);

				isOccupied=true;
				if(clerkMap.get(serverN).getCurrentCustomerS()==0)
				{
					cleanServer(serverN);
					isOccupied = false;
					serverN--;
				}
			}

			serverN++;
		}

		return clerkMap;
	}
	/**
	 * method to clean the server given and move the customer to completed
	 * @param server - server to be clean
	 */
	private void cleanServer(int server) {
		completed.add(clerkMap.get(server));
		clerkMap.put(server,null);

	}
	/**
	 * Method to calculate the average waiting time of all the customers
	 * @return the average waiting time
	 */
	private double aveWaitingTime() {
		double sum=0;
		for(int i=0; i<completed.size();i++) {
			sum += completed.get(i).getWaitingTime();
		}
		return (sum/completed.size());
	}
	/**
	 * Method to calculate the number of customer that got in line after certain customer but got served before
	 * @return 
	 */
	public double calculateSumFastPeople(){
		int fastPeople=0;
		for(int pEvaluar=completed.size()-1;pEvaluar>0;pEvaluar--){		
			for(int ipAntes=0;ipAntes < pEvaluar;ipAntes ++){			
				if(completed.get(ipAntes).getArrivalEvent()>completed.get(pEvaluar).getArrivalEvent()
						&& completed.get(ipAntes).getServiceStartTime()<=completed.get(pEvaluar).getServiceStartTime()){				
					fastPeople++;
				}
			}
		}	
		return fastPeople;
	}
	
}