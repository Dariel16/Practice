package p2MainClasses;

public  class Customer{
	private int cusID;            // id of this job
	private int arrivalEvent;    // arrival time of this job
	private int customerS;  // remaining service time for this job
	private int serviceCompleted;  // time when the service for this job is completed
	private int currentCustomerTime;
	private int serviceStart;

	public Customer(int id, int t, int s) { 
		cusID = id; 
		arrivalEvent = t;
		currentCustomerTime= t;
		customerS = s; 

	}
	public int getCompletedTime() {
		return serviceCompleted;
	}

	public void setCompletedTime(int departureTime) {
		this.serviceCompleted = departureTime;
	}

	public int getCusID() {
		return cusID;
	}
	public int getArrivalEvent() {
		return arrivalEvent;
	}
	public int getCurrentCustomerS() {// valor a comparar con T+S
		return customerS;
	}
	public int getCurrentCustomerTime() {
		return currentCustomerTime;
	}
	public void setCurrentCustomerTime(int timer) {
		currentCustomerTime = timer;
	}
	public double getServiceStartTime() {
		return serviceStart;
	}
	public void setServiceStartTime(int timer) {
		serviceStart = timer;
	}
	public double getWaitingTime() {
		return arrivalEvent + serviceStart;
	}


	/**
	 * Registers an update of serviced received by this job. 
	 * @param q the time of the service being registered. 
	 */
	public void isServed(int q) { 
		//if (q < 0) return;   // only register positive value of q
		currentCustomerTime += q; 
	}

	/**
	 * Generates a string that describes this job; useful for printing
	 * information about the job.
	 */
	public String toString() { 
		return "cusID = " + cusID + 
				"  Arrival Event Time = " + arrivalEvent +
				"  Remaining Time = " + customerS +
				"  Service Completed Time = " + serviceCompleted;                 
	}
}