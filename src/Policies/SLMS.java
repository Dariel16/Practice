package Policies;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.TreeMap;

import p2MainClasses.Customer;

public class SLMS {


	//	public EnterQVal(int name){
	//		//super(name);
	//	}


	
	Map<Integer,Customer>clerkMap= new TreeMap<>();
	ArrayDeque<Customer> filaAtender = new ArrayDeque<Customer>();// processing	
	int[] tCurrent;   //guarde valores t que aumentan+1 para llegar y comparar con t+s y sacarlos del map
	


	public SLMS(int servers ) {
		tCurrent=new int[servers];
		
	}

	public  void  evaluate(ArrayDeque<Customer> input, int servers,int numCust){		

		int atendidos=0;
		double timer = 0;
		double sumOfTimes = 0;//t1


		//create empty servers
		for(int i=0;i<servers;i++){
			
			clerkMap.put(i, null);
			tCurrent[i]=-2;
			
		}

		
		while(!(atendidos == input.size()))//nos dice cuando atendimos a todos
		{
			//el if chequea si faltan eventos por hacer
			if(!filaAtender.isEmpty() ||atendidos!=input.size())//puede darse el error que pase el tiempo y la fila se vacie
			{//or el map contenga un customer	
				

				//**SERVICE COMPLETED EVENT*// #1

				for(int i=0;i<servers;i++){
					if((clerkMap.get(i))!=null &&
							(clerkMap.get(i).getCurrentCustomerS()+ clerkMap.get(i).getArrivalEvent())==tCurrent[i]) {

						atendidos++;
						sumOfTimes+=(timer-clerkMap.get(i).getArrivalEvent());
						//metodo de limpiar posiciones
						cleanServer(i);
					}					
				}
				//chequear si hay algun server vacio(solo sirve para 1 vez)			

				checkEmptyServer(servers);


				//SERVICE STARTS EVENT #3  //atiende a todos los cientes en servers
				for(int s=0;s<servers;s++){
					if(tCurrent[s]!=-2)
					{tCurrent[s] +=1;}
					//quiero aumentar en algun sitio
				}				
			}

			//*ARRIVAL EVENT*// #4
			for(Customer c : input) // acomoda el line queue por el orden de llegada (t) 
				if(c.getArrivalEvent() == timer)
					filaAtender.add(c);

			timer++;
		}
		System.out.println("Total time in system is: " + sumOfTimes);
		double t2 = sumOfTimes/numCust; //promedio de esperar del input
		System.out.printf("Average waiting time for input file:  %.2f" ,t2);
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

				tCurrent[serverN]= clerkMap.get(serverN).getArrivalEvent();

				isOccupied=true;
			}
			serverN++;
		}

		return clerkMap;
	}

	private void cleanServer(int server) {


		clerkMap.put(server,null);
		tCurrent[server]=-2;


	}
}