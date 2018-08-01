package hostpitalQueue;

public class RoomQueue extends PriorityQueue<ExaminationRoom> {

	private boolean hasEmptyRooms = true;

	// do we want a method that can add patients?
	// would help reorganizing the q...
	//possibly make a boolean to notify that it actually worked?
	public void admitPatient(Patient p) {
		//check front if there is a room add the node to it
		if(this.hasEmptyRoom()) {
			this.getFront().getData().admitPatient(p);
			this.moveFrontToEndOfQ();
		}
		//this.printTheQueue();
	}
	public boolean stillHasPatients() {
		ExaminationRoom firstRoom = this.getFront().getData();
		return firstRoom.isOccupied();
	}

	public void addRooms(int numOfRooms) {
		for (int i = 0; i < numOfRooms; i++) {
			ExaminationRoom r = new ExaminationRoom(i + 1);
			ListNode<ExaminationRoom> n = new ListNode<ExaminationRoom>(r);
			this.addNode(n);
		}
		// this.printTheQueue();
	}

	public boolean hasEmptyRoom() {
		ListNode<ExaminationRoom> n = this.getFront();
		ExaminationRoom r = n.getData();
		return !r.isOccupied();
	}

//	public void reorganize() {
//		// check all the nodes data aka rooms, if empty, move to from
//		ListNode<ExaminationRoom> n = this.getFront();
//		for (int i = 0; i < this.size; i++) {
//			// check that the data isnt null
//			if (!n.dataIsNull()) {
//				if (!n.getData().isOccupied()) {
//					this.moveToFront(n);
//				}
//			}
//			n = n.next;
//		}
//	}

}
