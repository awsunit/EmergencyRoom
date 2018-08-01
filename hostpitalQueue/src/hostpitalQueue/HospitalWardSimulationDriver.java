package hostpitalQueue;

public class HospitalWardSimulationDriver {

	// need a patientQ,roomQ

	// patientQ
	/*
	 * continue to check patientQ -> peek method finds patient waiting-> first go
	 * through roomQ - any empty rooms? if not -> finish out while loop, check on
	 * next iter. orrrrr tell program at x&x time a room will be available, set
	 * program into while loop that breaks when time reached room available -> take
	 * room out of roomQ add patient to room insert room at end of roomQ
	 * 
	 * 
	 */

	// roomQ ->
	/*
	 * 
	 * needs to take a room out of roomQ, add patient to it, add back into Q on
	 */

	public static void main(String[] args) {

		// PatientVisitGenerator pVG = new PatientVisitGenerator();
		//
		// //RoomQueue roomQ = new RoomQueue();
		// //roomQ.addRooms(4);
		//
		// PatientQueue pQ = new PatientQueue();
		//
		// for(int i = 0; i < 4; i ++) {
		// Patient p = pVG.getNextRandomArrival(System.currentTimeMillis() / 60000);
		// pQ.createNode(p);
		//
		// }
		//
		// pQ.printTheQueue();

		// Patient p = pVG.getNextRandomArrival(System.currentTimeMillis() / 60000);
		// ExaminationRoom r = new ExaminationRoom(1);

		// r.admitPatient(p);

		// System.out.println(r.toString());

		// System.out.println(p.toString());

		HospitalWardSimulationDriver hWSD = new HospitalWardSimulationDriver(4);
		while (hWSD.openForBusiness) {
			// dont end the simulation
		}
		System.out.println("we saved " + hWSD.numOPSeen + " lives today!");
	}

	private RoomQueue roomQ;
	private PatientQueue patientQ;
	private ScheduledPatientsQueue scheduledPatientQ = new ScheduledPatientsQueue();
	private double currentTime;
	// changing from 10 hours to 2 minutes 10 * 60
	private final double TIMELIMITINMILLSEC = 1 * 60 * 1000;
	private double closingTime;
	private boolean openForBusiness = false;
	private boolean acceptingNewPatients = false;
	private int numOPSeen = 0;

	public HospitalWardSimulationDriver(int numOfRooms) {
		roomQ = new RoomQueue();
		patientQ = new PatientQueue();
		this.currentTime = System.currentTimeMillis();
		this.closingTime = currentTime + TIMELIMITINMILLSEC;
		roomQ.addRooms(numOfRooms);
		openUpShop();
	}

	public void openUpShop() {
		this.openForBusiness = true;
		this.acceptingNewPatients = true;
		PatientVisitGenerator pVG = new PatientVisitGenerator();
		// doesnt worry about scheduledPQ, closes its door on those still in need
		while (openForBusiness) {

			//System.out.println("did we check at all");
			// check time
			if (System.currentTimeMillis() > this.closingTime && this.acceptingNewPatients) {
				acceptingNewPatients = false;
				//openForBusiness = false;
				System.out.println("thats all the patients we will accept today folks");
				System.out.println(this.patientQ.size);
			}
			// generate a patient visit if we are still open
			if (acceptingNewPatients) {
				Patient p = pVG.getNextRandomArrival(System.currentTimeMillis());
				// check arrival time of patient, if not now, place into scheduledPatientQ
				// Patient p = new Patient(.5,2,1);
				if ((p.getArrivalTime()) > System.currentTimeMillis()) {
					scheduledPatientQ.createNode(p);
				} else if (roomQ.hasEmptyRoom()) {
					roomQ.admitPatient(p);
					numOPSeen++;
					// roomQ.getFront().getData().admitPatient(p);
					// roomQ.moveFrontToEndOfQ();
				} else {
					// add to patient q
					patientQ.createNode(p);
				}
			}

			// start with patients already "here" aka patientQ
			
				if (!patientQ.isEmpty()) {
					if (patientQ.checkArrivalTime() < System.currentTimeMillis()) {
						if (roomQ.hasEmptyRoom()) {
							Patient p = patientQ.getFrontData();
							//Patient p = patientQ.getFront().getData();
							patientQ.removeNode();
							roomQ.admitPatient(p);
							numOPSeen++;
							// roomQ.getFront().getData().admitPatient(p);
							// //need to reorganize roomQ
							// roomQ.moveFrontToEndOfQ();
						}
					}
				}else {
					//System.out.println("last patient admitted already");
				}
			
			// just checked the patientQ, check scheduledq's only if we are still open
			if (!scheduledPatientQ.isEmpty() && this.acceptingNewPatients) {
				// check frontnodes arrival time
				if (scheduledPatientQ.checkArrivalTime() < System.currentTimeMillis()) {
					Patient p = scheduledPatientQ.getFrontData();
					//Patient p = scheduledPatientQ.getFront().getData();
					scheduledPatientQ.removeNode();
					if (roomQ.hasEmptyRoom()) {
						roomQ.admitPatient(p);
						numOPSeen++;
						// roomQ.getFront().getData().admitPatient(p);
					} else {
						// add to patient q
						patientQ.createNode(p);
					}
				}

			}

			//how do we know to close?
			if(!roomQ.stillHasPatients() && !this.acceptingNewPatients) {
				this.openForBusiness = false;
			}
			// checked patientQ, scheduledPQ,rearranged roomQ,made sure eroom is still open
			// -> anything else?
		}

	}
}
