package p2MainClasses;

public  class Server{
	private boolean isAvilable;
	private Customer customer;

	public Server() { 
		this.isAvilable = true;
		this.customer = null;
	}

	public void serve(Customer customer, int time)
	{
		this.customer = customer;
		this.customer.setServiceStartTime(time);
		this.isAvilable = false;
	}

	public Customer complete()
	{
		//Customer temp = this.customer;
		//this.customer = null;
		this.isAvilable = true;
		//return temp;
		return this.customer;
	}

	public boolean isAvilable(){
		return isAvilable;
	}

	public Customer getCustomer(){
		return this.customer;
	}

}