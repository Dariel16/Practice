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
public class MLMSBWT {


	private int timer = 0;
	Map<Integer,Customer>serversMap= new TreeMap<>();
	Map<Integer,ArrayDeque<Customer>> lines =  new TreeMap<>();
	ArrayList<Customer> completed = new ArrayList<>();
	private int servers;
	int[] sLines;



	public MLMSBWT(int servers ) {
		this.servers = servers;
		sLines = new int[servers];

	}

	public  void  evaluate(ArrayDeque<Customer> input){		

		//create empty servers
		for(int i=0;i<servers;i++){
			serversMap.put(i, null);
			lines.put(i, new ArrayDeque<Customer>());			
		}


		while(!(completed.size() == input.size()))//nos dice cuando atendimos a todos
		{
			//el if chequea si faltan eventos por hacer
			if(!allLinesEmpty() ||completed.size()!=input.size())//puede darse el error que pase el tiempo y la fila se vacie
			{//or el map contenga un customer	

				//*SERVICE COMPLETED EVENT// #1

				for(int i=0;i<servers;i++){
					if((serversMap.get(i))!=null 
							&&(serversMap.get(i).getCurrentCustomerS()+ serversMap.get(i).getArrivalEvent())==serversMap.get(i).getCurrentCustomerTime()) {
						cleanServer(i);
					}					
				}
				//chequear si hay algun server vacio(solo sirve para 1 vez)			

				checkEmptyServer(servers);


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
				{	
					int index=lessWaitingTimeIndex();
					lines.get(index).add(c);
					addSToIndex(index,c.getCurrentCustomerS());
				}

			timer++;
		}
		System.out.println();;
		System.out.println("T1 MLMSBWT "+servers+" is : " + timer);
		System.out.printf("T2 MLMSBWT "+servers+" is  :" + aveWaitingTime());
		System.out.println();

	}



	private int lessWaitingTimeIndex(){// devuelve la fila con menos s time
		int index=0;
		for(int i=1;i<servers;i++){
			if(sLines[index] > sLines[i]){
				index=i;

			}	

		}

		return index;
	}
	//anade s a tal fila n
	public int[] addSToIndex(int index,int s){

		sLines[index] = sLines[index]+ s;
		return sLines;
	}

	private boolean allLinesEmpty() {

		for(int i=0; i<lines.size(); i++) {
			if(!lines.get(i).isEmpty())
				return false;
		}
		return true;

	}

	//sirve para mover muchos 
	@SuppressWarnings("unused")
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		boolean isOccupied=false;

		while(serverN < numS) {//si fila 1 de server 1 no esta vacia

			if(serversMap.get(serverN)==null &&!lines.get(serverN).isEmpty()){	// server n de n fila esta vacio?
	
				serversMap.put(serverN, lines.get(serverN).poll());
				serversMap.get(serverN).setServiceStartTime(serversMap.get(serverN).getArrivalEvent());
				serversMap.get(serverN).setCurrentCustomerTime(serversMap.get(serverN).getArrivalEvent());
				sLines[serverN] = sLines[serverN]-serversMap.get(serverN).getCurrentCustomerS();

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
}