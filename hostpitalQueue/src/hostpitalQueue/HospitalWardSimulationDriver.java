package hostpitalQueue;

public class HospitalWardSimulationDriver {

	

	public static void main(String[] args) {
		
		HospitalWardSimulationDriver hWSD = new HospitalWardSimulationDriver(10);
		while (hWSD.openForBusiness) {
			// dont end the simulation
		}
		
		System.out.println("we saved " + hWSD.numOPSeen + " lives today!");
	}

	/**Note that all Queues are AWSUNIT authored*/
	/**A queue of HospitalRooms -> queued because...*/
	private RoomQueue roomQ;
	/**Prioritized first by emergency level, second by appointment time*/
	private PatientQueue patientQ;
	/**Prioritized first by emergency level, second by appointment time*/
	private ScheduledPatientsQueue scheduledPatientQ = new ScheduledPatientsQueue();
	/**Changes while program runs, doesn't constantly update*/ 
	private double currentTime;
	/**Used to set the maximum limit of patient visit?*/
	// changing from 10 hours to 2 minutes 10 * 60 ->> WTF?
	private final double TIMELIMITINMILLSEC = 1 * 60 * 1000;
	/**What time this ER closes*/
	private double closingTime;
	/**Checks for program*/
	private boolean openForBusiness = false;
	private boolean acceptingNewPatients = false;
	/**Counts the number of Patients this ER has helped*/
	private int numOPSeen = 0;

	/**
	 * 
	 * @param numOfRooms the number of rooms that this ER will have
	 */
	public HospitalWardSimulationDriver(int numOfRooms) {
		//
		roomQ = new RoomQueue();
		patientQ = new PatientQueue();
		//set closing time
		this.currentTime = System.currentTimeMillis();
		this.closingTime = currentTime + TIMELIMITINMILLSEC;
		//
		roomQ.addRooms(numOfRooms);
		openUpShop();
	}
	

	public void openUpShop() {
		//still open
		this.openForBusiness = true;
		//
		this.acceptingNewPatients = true;
		PatientVisitGenerator pVG = new PatientVisitGenerator();
		//why is there a check here too?
		// doesnt worry about scheduledPQ, closes its door on those still in need
		while (openForBusiness) {

			//stop accepting patients at closing time
			if (System.currentTimeMillis() > this.closingTime && this.acceptingNewPatients) {
				acceptingNewPatients = false;
				System.out.println("thats all the patients we will accept today folks");
				System.out.println(this.patientQ.size);
			}
			// generate a patient visit if we are still open
			if (acceptingNewPatients && (scheduledPatientQ.size < 20)) {
				Patient p = pVG.getNextRandomArrival(System.currentTimeMillis());
				if ((p.getArrivalTime()) > System.currentTimeMillis()) {
					scheduledPatientQ.createAndAddNode(p);
				} else if (roomQ.hasEmptyRoom()) {
					roomQ.admitPatient(p);
					numOPSeen++;
				} else {
					patientQ.createAndAddNode(p);
				}
			}

			// start with patients already "here" aka patientQ
				if (!patientQ.isEmpty()) {
					if (patientQ.checkNextArrivalTime() < System.currentTimeMillis()) {
						if (roomQ.hasEmptyRoom()) {
							Patient p = patientQ.getFrontData();
							patientQ.removeNode();
							roomQ.admitPatient(p);
							numOPSeen++;
						}
					}
				}else {
				}
			
			// just checked the patientQ, check scheduledq's only if we are still open
			if (!scheduledPatientQ.isEmpty() && this.acceptingNewPatients) {
				// check frontnodes arrival time
				if (scheduledPatientQ.checkArrivalTime() < System.currentTimeMillis()) {
					Patient p = scheduledPatientQ.getFrontData();
					scheduledPatientQ.removeNode();
					if (roomQ.hasEmptyRoom()) {
						roomQ.admitPatient(p);
						numOPSeen++;
					} else {
						// add to patient q
						patientQ.createAndAddNode(p);
					}
				}

			}

			//how do we know to close?
			if(!roomQ.stillHasPatients() && !this.acceptingNewPatients && patientQ.isEmpty()) {
				this.openForBusiness = false;
			}
		}

	}
}
