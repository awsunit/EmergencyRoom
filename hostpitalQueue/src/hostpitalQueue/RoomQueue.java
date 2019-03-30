package hostpitalQueue;

public class RoomQueue extends PriorityQueue<ExaminationRoom> {

	private boolean hasEmptyRooms = true;

	public void admitPatient(Patient p) {
		//check front if there is a room add the node to it
		if(this.hasEmptyRoom()) {
			this.getFront().getData().admitPatient(p);
			this.moveFrontToEndOfQ();
		}
		//this.printTheQueue();
	}
	public boolean stillHasPatients() {
		//would be nice to cycle through entire q
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
}
