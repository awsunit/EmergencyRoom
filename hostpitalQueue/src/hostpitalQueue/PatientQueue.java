package hostpitalQueue;

import hostpitalQueue.PriorityQueue.ListNode;

public class PatientQueue extends PriorityQueue<Patient>{
	
	public double checkNextArrivalTime() {
		return this.getFront().getData().getArrivalTime();
	}
}
