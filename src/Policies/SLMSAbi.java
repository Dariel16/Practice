package Policies;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.TreeMap;

import p2MainClasses.Customer;

public class SLMS {



	//private int atendidos=0;
	private int timer = 0;
	private double aveWT=0;
	Map<Integer,Customer>clerkMap= new TreeMap<>();
	ArrayDeque<Customer> filaAtender = new ArrayDeque<Customer>();// processing	
	ArrayDeque<Customer> completed = new ArrayDeque<>();
	//int[] tCurrent;   //guarde valores t que aumentan+1 para llegar y comparar con t+s y sacarlos del map
	//double t2sum;
	int servers; 


	public SLMS(int servers ) {
		//tCurrent=new int[servers];
		this.servers=servers;

	}

	public  void  evaluate(ArrayDeque<Customer> input,int numCust){		


		double timer = 0;
		double sumOfTimes = 0;//t1


		//create empty servers
		for(int i=0;i<servers;i++){

			clerkMap.put(i, null);
			
			//tCurrent[i]=-2;
			//t2Times[i]= 0;

		}


		while(!(completed.size() == input.size()))//nos dice cuando atendimos a todos
		{
			//el if chequea si faltan eventos por hacer
			if(!filaAtender.isEmpty())//puede darse el error que pase el tiempo y la fila se vacie
			{//or el map contenga un customer	


				//**SERVICE COMPLETED EVENT*// #1

				for(int i=0;i<servers;i++){
					if((clerkMap.get(i))!=null &&
							(clerkMap.get(i).getCurrentCustomerS()+ clerkMap.get(i).getArrivalEvent())==clerkMap.get(i).getCurrentCustomerTime()) {

						
						sumOfTimes+=(timer-clerkMap.get(i).getArrivalEvent());
						//metodo de limpiar posiciones
						cleanServer(i);
					}					
				}
				//chequear si hay algun server vacio(solo sirve para 1 vez)			

				checkEmptyServer(servers);


				//SERVICE STARTS EVENT #3  //atiende a todos los cientes en servers
				for(int s=0;s<servers;s++){
					if(clerkMap.get(s).getCurrentCustomerTime()!=-2)
					{clerkMap.get(s).setCurrentCustomerTime(clerkMap.get(s).getCurrentCustomerTime()+1);}
					//quiero aumentar en algun sitio
				}				
			}

			//*ARRIVAL EVENT*// #4
			for(Customer c : input) // acomoda el line queue por el orden de llegada (t) 
				if(c.getArrivalEvent() == timer) {
					filaAtender.add(c);
					
					//TODO T2
					//t2Times.add(timer);
					
				}
			timer++;
		}
		System.out.println("Total time in system is: " + sumOfTimes);
		
	//	double t2 = sumOfTimes/numCust; //promedio de esperar del input
		System.out.println("Average waiting time per customer: " );
		//System.out.printf("Average waiting time for input file:  %.2f" ,t2);
		
		System.out.println();
		sumOfTimes=0;
	}

	//sirve para mover solo 1
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		boolean isOccupied=false;

		while(serverN < numS && isOccupied ==false  && !filaAtender.isEmpty()){// servers de 0 a n

			// significa que el map no tiene a nadie
			if(clerkMap.get(serverN)==null){	
				System.out.println("clerk "+serverN + " is empty");


				clerkMap.put(serverN, filaAtender.poll());
				//t2sum+= timer- t2Times.poll();

				//tCurrent[serverN]= clerkMap.get(serverN).getArrivalEvent();
				clerkMap.get(serverN).setServiceStartTime(timer);

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
	//	tCurrent[server]=-2;


	}
	private void aveWaitingTime() {
		int sum=0;
		for(int i=0; i<completed.size();i++) {
			//sum += completed
		}
	}
	
}