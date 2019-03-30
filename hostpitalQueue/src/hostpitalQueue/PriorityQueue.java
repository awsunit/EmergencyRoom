package hostpitalQueue;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author newdr
 *
 * @param <E> The data type associated with this node
 */
public class PriorityQueue<E extends Comparable<? super E>> {
	// implements Comparable<E>
	// List<E> list;
	
	/**The leading ListNode&lt;E&gt; of this*/
	private ListNode<E> frontNode;
	/**initial size 5*/
	public int size = 5;

	/**
	 * Constructor 
	 */
	public PriorityQueue() {
		// list = new ArrayList<E>();
		frontNode = null;
		// list.add((E)front);
	}

	/**
	 * 
	 * @param n1 the Object of type E which will be associated with a ListNode&lt;E&gt;
	 * and added as the leading node of this
	 */
	public PriorityQueue(E n1) {
		frontNode = new ListNode<E>(n1);
		size++;
	}

	public void createAndAddNode(E data) {
		ListNode<E> n = new ListNode<E>(data);
		this.addNode(n);
	}

	public void addNode(ListNode<E> newNode) {

		//check the first one
		if (this.frontNode == null) {
			this.frontNode = newNode;
		} else {
			//how does t
			Integer i = frontNode.data.compareTo(newNode.data);
			//System.out.println("compare returned " + i);
			if (i > 0) {
				// swapFrontNode(n);
				ListNode<E> holder = frontNode;
				frontNode = newNode;
				newNode.next = holder;
				holder.previous = newNode;
			} else if (frontNode.next == null) {
				frontNode.next = newNode;
				newNode.previous = frontNode;
			} else {
				//System.out.println(front.data.toString());
				// need to find node that is last of higher priority and add n1 to it
				ListNode<E> lastNode = findLastNode(newNode);
				lastNode.addNode(newNode);
			}
		}
		size++;
	}

	// need to compare, remember, this is a priorityqueue
	public ListNode<E> findLastNode(ListNode<E> n) {
		// compare the data of both
		// already compared front and checked that front.next wasn't null in addNode

		ListNode<E> n1 = frontNode;
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
		if (this.frontNode == null) {
			System.out.println("queue is empty");
		} else {
			if (frontNode.next != null) {
				ListNode<E> hold = frontNode.next;
				hold.previous = null;
				this.frontNode = null;
				frontNode = hold;
			} else {
				this.frontNode = null;
			}
			this.size--;
		}
	}

	
//	public void moveToFront(ListNode<E> n) {
//		if (this.frontNode != n) {
//			if (n.next != null) {
//				ListNode<E> hold1 = n.previous;
//				ListNode<E> hold2 = n.next;
//				hold1.next = hold2;
//				hold2.previous = hold1;
//			} else {
//				ListNode<E> hold1 = n.previous;
//				ListNode<E> g = null;
//				hold1.next = g;
//				n.previous = g;
//			}
//			frontNode.previous = n;
//			n.next = frontNode;
//			frontNode = n;
//		}
//	}

	
	//WHY IS THIS A METHOD?
	// takes front node and moves its comparable place in q
	public void moveFrontToEndOfQ() {
		if (frontNode.next != null) {
			// remove front from q -> make front.next front
			ListNode<E> g = null;
			ListNode<E> hold = frontNode;
			this.frontNode = frontNode.next;
			frontNode.previous = g;
			hold.next = g;
			addNode(hold);
		}
		// because addNode will add one to size
		size--;
	}

	// do we want to detach the front here?
	public ListNode<E> getFront() {
		return this.frontNode;
	}

	public boolean isEmpty() {
		return frontNode == null ? true : false;
	}
	
	public E getFrontData() {
		return this.frontNode.getData();
	}

	public void printTheQueue() {
		System.out.println("my size is " + size);
		if (frontNode == null) {
			System.out.println("no queue to print boss");
		} else {
			ListNode<E> n1 = frontNode;
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

		public void clearData() {
			this.data = null;
		}

		/**
		 * Returns a String representation of this nodes, may not be useful
		 */
		@Override
		public String toString() {
			return this.data.toString();
		}
		public void printMe() {
			System.out.println(data.toString());
			
		}

	}

	public static void main(String[] args) {
		//save for easier testing of project ?
		
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
}
