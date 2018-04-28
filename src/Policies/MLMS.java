package Policies;
import java.util.ArrayDeque;
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


private int servers;
	Map<Integer,Customer>serversMap= new TreeMap<>();
	Map<Integer,ArrayDeque<Customer>> filas =  new TreeMap<>();
	int[] tCurrent;   //guarde valores t que aumentan+1 para llegar y comparar con t+s y sacarlos del map

	private int atendidos=0;

	public MLMS(int servers ) {
		tCurrent=new int[servers];
		this.servers = servers;

	}

	public  void  evaluate(ArrayDeque<Customer> input,int numCust){		

	
		double timer = 0;
 		double sumOfTimes = 0;//t1


		//create empty servers
		for(int i=0;i<servers;i++){

			serversMap.put(i, null);
			filas.put(i, new ArrayDeque<Customer>());			
			tCurrent[i]=-2;

		}


		while(!(atendidos == input.size()))//nos dice cuando atendimos a todos
		{
			//el if chequea si faltan eventos por hacer
			if(!allLinesEmpty() ||atendidos!=input.size())//puede darse el error que pase el tiempo y la fila se vacie
			{//or el map contenga un customer	


				//*SERVICE COMPLETED EVENT// #1

				for(int i=0;i<servers;i++){
					if((serversMap.get(i))!=null 
							&&(serversMap.get(i).getCurrentCustomerS()+ serversMap.get(i).getArrivalEvent())==tCurrent[i]) {

						atendidos++;
						sumOfTimes+=(timer-serversMap.get(i).getArrivalEvent());
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

			//ARRIVAL EVENT// #4
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
		
		int index =0;//fila 0 es el que menos personas tienes
		for(int i = 1; i<filas.size();i++) {
			if(filas.get(index).size()>filas.get(i).size())//0 y 1
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

	//sirve para mover muchos 
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		@SuppressWarnings("unused")
		boolean isOccupied=false;

		// while(serverN < numS && isOccupied ==false  && !filaAtender.isEmpty()){// servers de 0 a n
		while(serverN < numS   ) {//si fila 1 de server 1 no esta vacia

			
			if(serversMap.get(serverN)==null &&!filas.get(serverN).isEmpty()){	// server n de n fila esta vacio?
				System.out.println("fila"+serverN+" contiene a alguien y el server"+serverN+" esta disponible");


				serversMap.put(serverN, filas.get(serverN).poll());

				tCurrent[serverN]= serversMap.get(serverN).getArrivalEvent();

				if(serversMap.get(serverN).getCurrentCustomerS()==0)
			{
				atendidos++;
				cleanServer(serverN);
				isOccupied = false;
				serverN--;
			}
			}
			
			serverN++;
		}

		return serversMap;
	}

	private void cleanServer(int server) {


		serversMap.put(server,null);
		tCurrent[server]=-2;


	}
}