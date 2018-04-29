package Policies;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import p2MainClasses.Customer;
/**
 * 
 * @author Jeffrey
 *
 */
public class MLMSBLL {
	private int timer;
	Stack<Customer> monitor = new Stack<>();
	Map<Integer,Customer>serversMap= new TreeMap<>();
	Map<Integer,ArrayDeque<Customer>> lines =  new TreeMap<>();
	ArrayList<Customer> completed = new ArrayList<>();
	private int servers;
	int[] tCurrent;   //guarde valores t que aumentan+1 para llegar y comparar con t+s y sacarlos del map

	private int atendidos=0;


	public MLMSBLL(int servers ) {
		this.servers = servers;
		tCurrent=new int[servers];

	}

	public  void  evaluate(ArrayDeque<Customer> input, int numCust){		


		double timer = 0;
		double sumOfTimes = 0;//t1


		//create empty servers
		for(int i=0;i<servers;i++){

			serversMap.put(i, null);
			lines.put(i, new ArrayDeque<Customer>());			
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

				//Service Event #2
				monitorLines();

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
					
					lines.get(shortestLineIndex()).add(c);
					


			timer++;
		}
		System.out.println();
		System.out.println("T1 MLMSBLL "+servers+" is  : " + timer);
		double t2 = sumOfTimes/numCust; //promedio de esperar del input
		System.out.printf("Avg time MLMBLL "+servers+" is :" + aveWaitingTime());
		System.out.println();
		sumOfTimes=0;
	}





	//sirve para mover muchos 
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		boolean isOccupied=false;

		// while(serverN < numS && isOccupied ==false  && !filaAtender.isEmpty()){// servers de 0 a n
		while(serverN < numS   ) {//si fila 1 de server 1 no esta vacia


			if(serversMap.get(serverN)==null &&!lines.get(serverN).isEmpty()){	// server n de n fila esta vacio?
				//System.out.println("line "+serverN+" is not empty, server "+serverN+" is available");



				serversMap.put(serverN, lines.get(serverN).poll());
				serversMap.get(serverN).setServiceStartTime(timer);

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

	private  int shortestLineIndex() {

		int index =0;//fila 0 es el que menos personas tienes
		for(int i = 1; i<lines.size();i++) {
			if(lines.get(index).size()>lines.get(i).size())//0 y 1
				index = i;
		}
		return index;
	}
	private  int longestLineIndex() {

		int index =0;//fila 0 es el que menos personas tienes
		for(int i = 1; i<lines.size();i++) {
			if(lines.get(index).size()<lines.get(i).size())//0 y 1
				index = i;
		}
		return index;
	}

	private boolean allLinesEmpty() {

		for(int i=0; i<lines.size(); i++) {
			if(!lines.get(i).isEmpty())
				return false;
		}
		return true;
	}

	private void cleanServer(int server) {

		completed.add(serversMap.get(server));
		serversMap.put(server,null);
		tCurrent[server]=-2;
	}

	private double aveWaitingTime() {
		int sum=0;
		for(int i=0; i<completed.size();i++) {
			sum += completed.get(i).getWaitingTime();
		}
		return (sum/completed.size())*100;
	}

	private void monitorLines() {
		int sli = shortestLineIndex();
		int lli= longestLineIndex();
		if(lines.get(lli).size()>lines.get(sli).size()+1) {
			lines.get(sli).add(lines.get(lli).pollLast());
		}

	}
}