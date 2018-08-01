package hostpitalQueue;

import hostpitalQueue.PriorityQueue.ListNode;

public class PatientQueue extends PriorityQueue<Patient>{
	
	public double checkArrivalTime() {
		return this.getFront().getData().getArrivalTime();
	}
	
//	public int compareTo(ListNode<Patient> o) {
//		// TODO Auto-generated method stub /
//		return this.compareTo(o);
//	}
//	public class ListNodePatient extends ListNode<Patient>{
//		
//		@Override
//		public int compareTo(ListNode<Patient> o) {
//			if(o.emergencyLevel > this.emergencyLevel) {
//				return 1;
//			}else {
//				return o.emergencyLevel < this.emergencyLevel ? -1 : 0;
//			}
//		}
	
	

	//public PriorityQueue<Patient> myQ = new PriorityQueue<Patient>();

}
