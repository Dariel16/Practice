
package Policies;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import p2MainClasses.Customer;
/**
 * 
 * @author Jeffrey
 *
 */
public class MLMS {


	//	public EnterQVal(int name){
	//		//super(name);
	//	}



	Map<Integer,Customer>clerkMap= new TreeMap<>();
	ArrayDeque<Customer> filaAtender = new ArrayDeque<Customer>();// processing	
	//ArrayDeque<Customer> fila0,fila1,fila2,fila3,fila4 = new ArrayDeque<Customer>();// processing	
	Map<Integer,ArrayDeque<Customer>> filas =  new TreeMap<>();
	//ArrayList<ArrayDeque>[] filas = new ArrayList{fila0,fila1,fila2,fila3,fila4};
	//ArrayList<ArrayDeque> filasEspera=new ArrayList<>();
	//filasEspera.add(fila0);

	int[] tCurrent;   //guarde valores t que aumentan+1 para llegar y comparar con t+s y sacarlos del map



	public MLMS(int servers ) {
		tCurrent=new int[servers];

	}

	public  void  evaluate(ArrayDeque<Customer> input, int servers,int numCust){		

		int atendidos=0;
		double timer = 0;
		double sumOfTimes = 0;//t1


		//create empty servers
		for(int i=0;i<servers;i++){

			clerkMap.put(i, null);
			filas.put(i, new ArrayDeque<Customer>());
			
			tCurrent[i]=-2;

		}


		while(!(atendidos == input.size()))//nos dice cuando atendimos a todos
		{
			//el if chequea si faltan eventos por hacer
			if(!allLinesEmpty() ||atendidos!=input.size())//puede darse el error que pase el tiempo y la fila se vacie
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
					//filaAtender.add(c);
					filas.get(shortestLineIndex()).add(c);


			timer++;
		}
		System.out.println("Total time in system is: " + sumOfTimes);
		double t2 = sumOfTimes/numCust; //promedio de esperar del input
		System.out.printf("Average waiting time for input file:  %.2f" ,t2);
		System.out.println();
		sumOfTimes=0;
	}

	private  int shortestLineIndex() {
		// TODO Auto-generated method stub
		int index =0;
		for(int i = 1; i<filas.size();i++) {
			if(filas.get(index).size()>filas.get(i).size())
				index = i;
		}
		return index;
	}
	
	private boolean allLinesEmpty() {
	
		for(int i=0; i<filas.size(); i++) {
			if(!filas.get(i).isEmpty())
				return false;
		}
		return true;
		
	}

	//sirve para mover solo 1
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		boolean isOccupied=false;

		// while(serverN < numS && isOccupied ==false  && !filaAtender.isEmpty()){// servers de 0 a n
		while(serverN < numS && isOccupied ==false  && !filas.get(serverN).isEmpty()) {

			// significa que el map no tiene a nadie
			if(clerkMap.get(serverN)==null){	
				System.out.println("clerk "+serverN + " is empty");


				clerkMap.put(serverN, filas.get(serverN).poll());

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

