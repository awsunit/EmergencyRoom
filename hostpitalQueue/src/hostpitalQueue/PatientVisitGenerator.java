package hostpitalQueue;

/**
 * A class to generate patient visits.
 * 
 */

public class PatientVisitGenerator {

	// ***********************************
	// LEAVE THE FOLLOWING CODE UNCHANGED
	// ***********************************

	// Random number generator
	private static java.util.Random random = new java.util.Random();

	// programmed arrivals
	private static int generatedSoFar = 0;
	private static int currentUrgency = 1;

	/**
	 * Generates the next patient arrival for the simulation given the current
	 * time. Average time between arrivals is about 19 minutes. Average duration
	 * is 30 minutes.
	 */
	public static Patient getNextRandomArrival(double currentTime) {
		int u = (int) (11.0 - 10.8 * Math.exp(-0.08 * (random.nextInt(50) + 1)));
//		double interval = 80.0 * Math.exp(-0.08 * (random.nextInt(50) + 1));
//		double d = 8.0 + 72.0 * Math.exp(-0.06 * (random.nextInt(50) + 1));
		//System.out.println("interval " + interval + "\n" + "currentTime " + System.currentTimeMillis() );
		double interval = .2;
		double d = .3;
		//return new Patient((currentTime + (60000*interval)), u, d);
		
		return new Patient.PatientBuilder("jDoe").setEmergencyLevel(1).setArrivalTime(currentTime + 12000).
					setExamLength(.3).build();
	}

	/**
	 * Programmed arrivals: urgency level i waits i - 1 minutes
	 * 
	 * @param currentTime
	 *            the time when this arrival is generated (the arrival will
	 *            occur some time after that time)
	 * @param numberOfRooms
	 *            the number of treatment rooms in the simulation
	 * @throws IllegalArgumentException
	 *             if numberOfRooms is less than 1
	 */
//	public static PatientVisit getNextProgrammedArrival(double currentTime,
//			int numberOfRooms) {
//
//		if (numberOfRooms < 1) {
//			throw new IllegalArgumentException();
//		}
//
//		int interval = 0;
//
//		if (generatedSoFar >= numberOfRooms) {
//			generatedSoFar = 0;
//			currentUrgency++;
//			if (currentUrgency > 10) {
//				currentUrgency = 1;
//				interval = 20; 
//				// busy time is about 10 / interval * 100 (up to 100%)
//				// It depends on the actual duration, but the above is true for
//				// large durations.
//				// If the interval is less than 10, the waiting time for each
//				// urgency goes up and it is no longer i for urgency i + 1.
//			}
//		}
//		generatedSoFar++;
//		return new PatientVisit(currentTime + interval, currentUrgency, 1);
//		return new PatientVisit.PatientVisitBuilder()
//	}
}
