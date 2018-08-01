package hostpitalQueue;

public class ScheduledPatientsQueue extends PriorityQueue<Patient>{
	
	public double checkArrivalTime() {
		return this.getFront().getData().getArrivalTime();
	}

}
