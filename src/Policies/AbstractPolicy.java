package Policies;

import java.util.ArrayDeque;
import java.util.ArrayList;

import p2MainClasses.Customer;

public abstract class AbstractPolicy {


	private String name;  // para identificar el policy 

	public AbstractPolicy(String name) {
		this.name = name; 
	}

	public String getName() { 
		return name; 
	}
	abstract ArrayList<Integer> evaluate(ArrayDeque<Customer> input, int servers,int numCust);





}
