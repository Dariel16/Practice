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


	


	public MLMSBLL(int servers ) {
		this.servers = servers;

	}

	public  void  evaluate(ArrayDeque<Customer> input){		

		//create empty servers
		for(int i=0;i<servers;i++){

			serversMap.put(i, null);
			lines.put(i, new ArrayDeque<Customer>());			
		

		}


		while(!(completed.size()== input.size()))//nos dice cuando atendimos a todos
		{
			//el if chequea si faltan eventos por hacer
			if(!allLinesEmpty() ||completed.size()!=input.size())//puede darse el error que pase el tiempo y la fila se vacie
			{//or el map contenga un customer	


				//*SERVICE COMPLETED EVENT// #1

				for(int i=0;i<servers;i++){
					if((serversMap.get(i))!=null 
							&&(serversMap.get(i).getCurrentCustomerS()+ serversMap.get(i).getArrivalEvent())==serversMap.get(i).getCurrentCustomerTime()) {

						
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
					if(serversMap.get(s)!=null) {
						serversMap.get(s).setCurrentCustomerTime(serversMap.get(s).getCurrentCustomerTime()+1);
					}
				}				
			}

			//ARRIVAL EVENT// #4
			for(Customer c : input) // acomoda el line queue por el orden de llegada (t) 
				if(c.getArrivalEvent() == timer)	
					lines.get(shortestLineIndex()).add(c);
					
			timer++;
		}
		System.out.println();
		System.out.println("T1 MLMSBLL "+servers+" is  : " + timer);
		System.out.printf("T2 MLMBLL "+servers+" is :" + aveWaitingTime());
		System.out.println();
	
	}





	//sirve para mover muchos 
	@SuppressWarnings("unused")
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		boolean isOccupied=false;

		
		while(serverN < numS   ) {//si fila 1 de server 1 no esta vacia


			if(serversMap.get(serverN)==null &&!lines.get(serverN).isEmpty()){	// server n de n fila esta vacio?
				serversMap.put(serverN, lines.get(serverN).poll());
				serversMap.get(serverN).setServiceStartTime(serversMap.get(serverN).getArrivalEvent());
				serversMap.get(serverN).setCurrentCustomerTime(serversMap.get(serverN).getArrivalEvent());


				if(serversMap.get(serverN).getCurrentCustomerS()==0)
				{

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