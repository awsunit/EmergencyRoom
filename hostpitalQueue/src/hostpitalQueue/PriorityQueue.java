package hostpitalQueue;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<E extends Comparable<? super E>> {
	// implements Comparable<E>
	// List<E> list;
	private ListNode<E> front;
	public int size = 0;

	public PriorityQueue() {
		// list = new ArrayList<E>();
		front = null;
		// list.add((E)front);
	}

	public PriorityQueue(E n1) {
		front = new ListNode<E>(n1);
		size++;
	}

	public void createNode(E data) {
		ListNode<E> n = new ListNode<E>(data);
		this.addNode(n);
	}

	public void addNode(ListNode<E> n) {

		if (this.front == null) {
			this.front = n;
		} else {
			ListNode<E> n1 = front;
			Integer i = n1.data.compareTo(n.data);
			//System.out.println("compare returned " + i);
			if (i > 0) {
				// swapFrontNode(n);
				ListNode<E> holder = front;
				front = n;
				n.next = holder;
				holder.previous = n;
			} else if (front.next == null) {
				front.next = n;
				n.previous = front;
			} else {
				//System.out.println(front.data.toString());
				// need to find node that is last of higher priority and add n1 to it
				ListNode<E> lastNode = findLastNode(n);
				lastNode.addNode(n);
			}
		}
		size++;
	}

	// need to compare, remember, this is a priorityqueue
	public ListNode<E> findLastNode(ListNode<E> n) {
		// compare the data of both
		// already compared front and checked that front.next wasn't null in addNode

		ListNode<E> n1 = front;
		while (n1.next != null) {
			n1 = n1.next;
			// is n1 a higher priority?

			Integer i = n1.data.compareTo(n.data);
			if (i > 0) {
				// this means that we should return the previous node, to insert
				// before this one
				return n1.previous;
			}
		}
		return n1;
	}

	// this should only happen at front of q... watch out, what if we want to keep
	// the data?? ->make generic? overload? how about just delete the fucking node
	public void removeNode() {
		if (this.front == null) {
			System.out.println("queue is empty");
		} else {
			if (front.next != null) {
				ListNode<E> hold = front.next;
				hold.previous = null;
				this.front = null;
				front = hold;
			} else {
				this.front = null;
			}
			this.size--;
		}
	}

	// move to the front should maybe only do that
	// leave clearing of data to another function? -> nodeclass
	// is passed a null node -> but still connected to previous and next
	public void moveToFront(ListNode<E> n) {
		if (this.front != n) {
			if (n.next != null) {
				ListNode<E> hold1 = n.previous;
				ListNode<E> hold2 = n.next;
				hold1.next = hold2;
				hold2.previous = hold1;
				// just make front = n
			} else {
				ListNode<E> hold1 = n.previous;
				ListNode<E> g = null;
				hold1.next = g;
				n.previous = g;
			}
			front.previous = n;
			n.next = front;
			front = n;
		}
	}

	// takes front node and moves its comparable place in q
	public void moveFrontToEndOfQ() {
		if (front.next != null) {
			// remove front from q -> make front.next front
			ListNode<E> g = null;
			ListNode<E> hold = front;
			this.front = front.next;
			front.previous = g;
			hold.next = g;
			addNode(hold);
		}
		// because addNode will add one to size
		size--;
	}

	// do we want to detach the front here?
	public ListNode<E> getFront() {
		return this.front;
	}

	public boolean isEmpty() {
		return front == null ? true : false;
	}
	
	public E getFrontData() {
		return this.front.getData();
	}

	public void printTheQueue() {
		System.out.println("my size is " + size);
		if (front == null) {
			System.out.println("no queue to print boss");
		} else {
			ListNode<E> n1 = front;
			n1.printMe();
			while (n1.next != null) {
				n1 = n1.next;
				n1.printMe();
			}
		}
	}

	public static class ListNode<E extends Comparable<? super E>> {
		// contains a previous and next, as well as a data field
		private E data;
		public ListNode<E> previous, next;

		// construct
		public ListNode() {
			data = null;
			previous = null;
			next = null;
		}

		// previous to next
		public ListNode(E data) {
			this.data = data;
			previous = null;
			next = null;
		}

		public ListNode(E data, ListNode<E> n1) {
			this.data = data;
			previous = n1;
			next = null;
		}

		public ListNode(E data, ListNode<E> n1, ListNode<E> n2) {
			this.data = data;
			previous = n1;
			next = n2;
		}

		public void addNode(ListNode<E> n) {
			// gotta check if my next is null
			ListNode<E> holder = null;
			if (this.next != null) {
				holder = this.next;
			}
			this.next = n;
			n.previous = this;
			n.next = holder;
			if (holder != null) {
				holder.previous = n;
			}
		}

		public E getData() {
				return this.data;
		}
		public boolean dataIsNull() {
			return this.data == null;
		}

		// breaks ties to other nodes, clears data
		public void clearData() {
			this.data = null;
		}

		public void printMe() {
			System.out.println(data.toString());
			// if (this.previous != null) {
			// System.out.println("my previous is " + this.previous.data.toString());
			// }
			// if (this.next != null) {
			// System.out.println("my next is " + this.next.data.toString());
			// }
		}

	}

	/*
	 * public static Patient getNextRandomArrival(double currentTime) { int u =
	 * (int) (11.0 - 10.8 * Math.exp(-0.08 * (random.nextInt(50) + 1))); double
	 * interval = 80.0 * Math.exp(-0.08 * (random.nextInt(50) + 1)); double d = 8.0
	 * + 72.0 * Math.exp(-0.06 * (random.nextInt(50) + 1));
	 * //System.out.println("interval " + interval + "\n" + "currentTime " +
	 * System.currentTimeMillis() ); return new Patient((currentTime +
	 * (60000*interval)), u, d); }
	 * 
	 * public Patient(String name,double appointmentTime,int eLevel,double examLen)
	 * { this.name = name; this.emergencyLevel = eLevel; this.arrivalTime =
	 * (appointmentTime) + System.currentTimeMillis(); setExamLength(examLen *
	 * 60000); }
	 */
	public static void main(String[] args) {
//		// patientq testing area
//		PatientQueue myQ = new PatientQueue();
//
//		// appointment time, elevel, apptLength -> keep appointments below a minute
//		Patient john = new Patient("john", System.currentTimeMillis() + 60000, 5, .5);
//		ListNode<Patient> n1 = new ListNode(john);
//		myQ.addNode(n1);
//
//		Patient j = new Patient("j", System.currentTimeMillis() + 180000, 4, .4);
//		ListNode<Patient> n3 = new ListNode(j);
//		myQ.addNode(n3);
//
////		ListNode<Patient> n2 = new ListNode(jill);
////		myQ.addNode(n2);
//
//		// checking createNode
//		// Patient billy = new Patient("billy", System.currentTimeMillis() + 20000, 2,
//		// .1);
//		// myQ.createNode(billy);
//
//		// myQ.printTheQueue();
//		//
//		// myQ.getFront().getData().changeELevel(5 );
//		// myQ.moveFrontToEndOfQ();
//		//
//		// myQ.printTheQueue();
//
//		// examroom testing
//		RoomQueue roomQ = new RoomQueue();
//		roomQ.addRooms(3);
//
//		// roomQ.printTheQueue();
//
//		Patient billy = new Patient("billy", System.currentTimeMillis() + 20000, 2, .2);
//		Patient jill = new Patient("jill", System.currentTimeMillis() + 120000, 4, .5);
//
//
//		roomQ.printTheQueue();
//		//roomQ.getFront().getData().admitPatient(billy);
//		roomQ.admitPatient(billy);
//		roomQ.admitPatient(jill);

		

		

		//
		

		// ExaminationRoom room = new ExaminationRoom(1);
		// Patient billy = new Patient("billy", System.currentTimeMillis() + 20000, 2,
		// .1);
		// room.admitPatient(billy);
	}

	// public static void main(String[] args) {
	//
	// PatientQueue myQ = new PatientQueue();
	//
	// //
	// // Patient john = new Patient("john", 3);
	// // ListNode<Patient> n1 = new ListNode(john);
	// // myQ.addNode(n1);
	// //
	// // Patient jill = new Patient("jill", 2);
	// // ListNode<Patient> n2 = new ListNode(jill);
	// // myQ.addNode(n2);
	//
	// // ListNode<Patient> n = new ListNode(john.emergencyLevel);
	// // myQ.addNode(n);
	// // // n.printMe();
	// // System.out.println();
	// //
	// // Patient jill = new Patient("jill", 2);
	// // ListNode<Patient> n1 = new ListNode(jill.emergencyLevel);
	// // myQ.addNode(n1);
	// // // n1.printMe();
	// //
	// // Patient bill = new Patient("bill",4);
	// // ListNode<Patient> n2 = new ListNode(bill.emergencyLevel);
	// // myQ.addNode(n2);
	// //
	// // Patient trill = new Patient("bill",3);
	// // ListNode<Patient> n3 = new ListNode(trill.emergencyLevel);
	// // myQ.addNode(n3);
	// //
	// // Patient billy = new Patient("bill",3);
	// // ListNode<Patient> n4 = new ListNode(billy.emergencyLevel);
	// // myQ.addNode(n4);
	// //
	// // System.out.println();
	// // System.out.println();
	// // myQ.printTheQueue();
	//
	// PatientVisitGenerator pVG = new PatientVisitGenerator();
	//
	// Patient p =
	// PatientVisitGenerator.getNextRandomArrival(System.currentTimeMillis() / (1000
	// * 60));
	// ListNode<Patient> n3 = new ListNode<Patient>(p);
	// myQ.addNode(n3);
	//
	// Patient p2 =
	// PatientVisitGenerator.getNextRandomArrival(System.currentTimeMillis() / (1000
	// * 60));
	// ListNode<Patient> n4 = new ListNode<Patient>(p2);
	// myQ.addNode(n4);
	//
	// myQ.printTheQueue();
	//
	// // myQ.removeNode();
	// myQ.moveFrontToEndOfQ();
	//
	// myQ.printTheQueue();
	//
	// System.out.println("all done boss");
	//
	// }

}
