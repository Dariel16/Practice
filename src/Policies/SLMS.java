package Policies;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import p2MainClasses.Customer;

public class SLMS {




	private int timer = 0;
	
	Map<Integer,Customer>clerkMap= new TreeMap<>();
	ArrayDeque<Customer> line = new ArrayDeque<Customer>();// processing	
	ArrayList<Customer> completed = new ArrayList<>();
	
	int servers; 


	public SLMS(int servers ) {
		
		this.servers=servers;

	}

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
	
		System.out.println();;
		System.out.println("T1 SLMS "+servers+" is: " + timer);
		System.out.printf("T2 SLMS "+servers+" is: %.2f",aveWaitingTime());
		System.out.println();

	}

	//sirve para mover solo 1
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

	private void cleanServer(int server) {
		completed.add(clerkMap.get(server));
		clerkMap.put(server,null);
	


	}
	private double aveWaitingTime() {
		double sum=0;
		for(int i=0; i<completed.size();i++) {
			sum += completed.get(i).getWaitingTime();
		}
		return (sum/completed.size());
	}
	
}