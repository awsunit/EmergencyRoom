package hostpitalQueue;

import hostpitalQueue.Patient.PatientBuilder;

/**
 * 
 * WHY DO WE HAVE THIS CLASS? Separation of patient and visit.
 * What are we saving in this class that isn't saved elsewhere?
 * 
 * 
 * A class to describe the visit of a patient, defined by the arrival time, the
 * urgency of the visit, and the length of the visit.
 */
public class PatientVisit {
	
	public static void main(String[] args) {
		Patient p = new PatientBuilder("Todd").setArrivalTime(.10).setExamLength(4000).setEmergencyLevel(420).build();
		ExaminationRoom room = new ExaminationRoom(420);
		room.admitPatient(p);
		System.out.println(p);
		
		PatientVisit pv = new PatientVisitBuilder(p).setArrivalTime(.10).setExamLength(4000.).build();
	}
	
	

	private double arrivalTime; // in minutes

	private int urgency; // 1 to 10 (1 is the most urgent)

	private double examLength; // duration of the visit in minutes
	
	private Patient patient;//why don't we have a patient?

	/**
	 * 
	 * @param pvb the PatientVisitBuilder which contains all valuable information for this P.V.
	 */
	private PatientVisit(PatientVisitBuilder pvb) {
		this.arrivalTime = pvb.arrivalTime;
		this.urgency = pvb.urgency;
		this.examLength = pvb.examLength;
		this.patient = pvb.patient;
	}
	
	/**
	 * Public builder class to instantiate a PatientVisit object.
	 * 
	 * @author newdr
	 *
	 */
	public static class PatientVisitBuilder{
		private Double arrivalTime; // in minutes

		private Integer urgency; // 1 to 10 (1 is the most urgent)

		private Double examLength; // duration of the visit in minutes
		
		private Patient patient;//why don't we have a patient?
		
		/**
		 * Constructor 
		 * @param patient
		 */
		public PatientVisitBuilder(Patient patient)throws IllegalArgumentException {
			if(patient == null) {
				throw new IllegalArgumentException("patient must be initialized");
			}
			this.patient = patient;
			//set up dummy values
			this.arrivalTime = 1.0 * System.currentTimeMillis();
			this.urgency = 10;
			this.examLength = 60.0;
		}
		
		public PatientVisit build() {
			return new PatientVisit(this);
		}
		
		public PatientVisitBuilder setArrivalTime(Double arrivalTime) {
			this.arrivalTime = arrivalTime;
			return this;
		}
		
		public PatientVisitBuilder setExamLength(Double examLength) {
			this.examLength = examLength;
			return this;
		}
		
		public PatientVisitBuilder setUrgency(Integer urgency) {
			this.urgency = urgency;
			return this;
		}
		
		
	}

	/**
	 * Returns the arrival time (in minutes)
	 * 
	 * @return the arrival time (in minutes)
	 */
	public Double getArrivalTime() {
		return this.arrivalTime;
	}

	/**
	 * Returns the duration of the visit (in minutes)
	 * 
	 * @return a Double of the exam length (in minutes)
	 */
	public Double getExamLength() {
		return this.examLength;
	}

	/**
	 * Returns the urgency of the visit (1 to 10, 1 being most urgent)
	 * 
	 * @return the urgency of the visit
	 */
	public Integer getUrgency() {
		return this.urgency;
	}
	
	public Patient getPatient() {
		return this.patient;
	}

}
