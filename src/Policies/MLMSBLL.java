package Policies;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import p2MainClasses.Customer;
/**
 * Third policy -This case is similar to the previous policy but a customer 
 * is not stuck to the line he/she initially chooses as in that one. 
 * This scheme includes a monitor (a person or a specialized device) that decides when, 
 * and to what line, a person already in one of the lines can transfer to.
 * @author Jeffrey Ramos
 * @author Dariel Ramos
 */
public class MLMSBLL {
	private int timer;
	Stack<Customer> monitor = new Stack<>();
	Map<Integer,Customer>serversMap= new TreeMap<>();
	Map<Integer,ArrayDeque<Customer>> lines =  new TreeMap<>();
	ArrayList<Customer> completed = new ArrayList<>();
	private int servers;




	/**
	 * Constructor
	 * get the amount of server to use at the time
	 * @param servers
	 */
	public MLMSBLL(int servers ) {
		this.servers = servers;

	}
	/**
	 * Method that analyze the customers with their different values, and different servers
	 * @param input - ArrayQueue of all the customers to set on the line to be served 
	 * 
	 */
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
							&&(serversMap.get(i).getCurrentCustomerS()+ serversMap.get(i).getServiceStartTime())==serversMap.get(i).getCurrentCustomerTime()) {


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
	@SuppressWarnings("unused")
	public Map<Integer ,Customer> checkEmptyServer(int numS){//verifica de menor a mayor los servers,si estan vacios 
		int serverN=0;
		boolean isOccupied=false;


		while(serverN < numS   ) {//si fila 1 de server 1 no esta vacia


			if(serversMap.get(serverN)==null &&!lines.get(serverN).isEmpty()){	// server n de n fila esta vacio?
				serversMap.put(serverN, lines.get(serverN).poll());
				serversMap.get(serverN).setServiceStartTime(timer);
				serversMap.get(serverN).setCurrentCustomerTime(timer);


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
	/**
	 * Method to analyze which of all lines is the shortest
	 * @return index - the index of the shortest line
	 */
	private  int shortestLineIndex() {

		int index =0;//fila 0 es el que menos personas tienes
		for(int i = 1; i<lines.size();i++) {
			if(lines.get(index).size()>lines.get(i).size())//0 y 1
				index = i;
		}
		return index;
	}
	/**
	 * Method to analyze which of all lines is the longest
	 * @return index - the index of the longest line
	 */
	private  int longestLineIndex() {

		int index =0;//fila 0 es el que menos personas tienes
		for(int i = 1; i<lines.size();i++) {
			if(lines.get(index).size()<lines.get(i).size())//0 y 1
				index = i;
		}
		return index;
	}
	/**
	 * Method to analyze if all the lines are empty
	 * @return true if the lines are empty.
	 */
	private boolean allLinesEmpty() {

		for(int i=0; i<lines.size(); i++) {
			if(!lines.get(i).isEmpty())
				return false;
		}
		return true;
	}
	/**
	 * method to clean the server given and move the customer to completed
	 * @param server - server to be clean
	 */
	private void cleanServer(int server) {

		completed.add(serversMap.get(server));
		serversMap.put(server,null);

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
	 * method that monitors the lines, and balanced the lines lenghts
	 */
	private void monitorLines() {
		int sli = shortestLineIndex();
		int lli= longestLineIndex();
		if(lines.get(lli).size()>lines.get(sli).size()+1) {
			lines.get(sli).add(lines.get(lli).pollLast());
		}

	}
	/**
	 * Method to calculate the number of customer that got in line after certain customer but got served before
	 * @return 
	 */
	public double calculateSumFastPeople(){
		int fastPeople=0;
		for(int pEvaluar=completed.size()-1;pEvaluar>0;pEvaluar--){		
			for(int ipAntes=0;ipAntes < pEvaluar;ipAntes ++){			
				if(completed.get(ipAntes).getArrivalEvent()>completed.get(pEvaluar).getArrivalEvent() ){				
					fastPeople++;
				}
			}
		}	
		return fastPeople;
	}
}