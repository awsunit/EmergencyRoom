package hostpitalQueue;

public class Patient extends PriorityQueue<Patient> implements Comparable<Patient>{

	
//	public static void main(String [] args) {
//		Patient p = new PatientBuilder("Todd", 420).setArrivalTime(.10).build();
//		System.out.println(p);
//	}
	/**Patients name*/
	private String name;
	/**Used for priority comparison*/
	private Integer emergencyLevel;
	/**Duration of exam*/
	private Double examLength;
	/**Time arrived at clinic*/
	private Double arrivalTime;
	
	public Patient copy() {
		Patient p = new PatientBuilder(this.name).setEmergencyLevel(this.emergencyLevel).
				setExamLength(this.examLength).setArrivalTime(this.arrivalTime).build();
		return p;
	}
	
	/**
	 * 
	 * @param pB the PatientBuilder which creates this
	 */
	private Patient(PatientBuilder pB){
		this.name = pB.name;
		this.emergencyLevel = pB.emergencyLevel;
		this.examLength = pB.examLength;
		this.arrivalTime = pB.arrivalTime;
	}
	
	/**
	 * Public builder class to instantiate a Patient Object
	 * @author newdr
	 *
	 */
	public static class PatientBuilder{
		/**Patients name*/
		private String name;
		/**Used for priority comparison*/
		private Integer emergencyLevel;
		/**Duration of exam*/
		private Double examLength;
		/**Time arrived at clinic*/
		private Double arrivalTime;
		
		/**
		 * Construct to create a PatientBuilder, which then can create a Patient with a
		 * call to build.
		 * @param name a String that represents this patients name
		 * @param eLevel the emergency level of this patient
		 * @throws IllegalArgumentException if name is of length 0 or eLevel is less than 0
		 */
		public PatientBuilder(String name) throws IllegalArgumentException{
			if(name.length() == 0) {
				throw new IllegalArgumentException("Patient name must have at least one character");
			}
			this.name = name;
			//initialize fields now dummy
			this.emergencyLevel = 10;
			this.examLength = 0.0;
			
		}
		/**
		 * 
		 * @param eLevel
		 * @return
		 */
		public PatientBuilder setEmergencyLevel(Integer eLevel) {
			this.emergencyLevel = eLevel;
			return this;
		}
		/**
		 * 
		 * @param arrivalTime the time which the patient arrives
		 * @modifies this
		 * @effects post method: this.arrivalTime.equals(arrivalTime)
		 * @return this
		 */
		public PatientBuilder setArrivalTime(double arrivalTime) {
			this.arrivalTime = arrivalTime;
			return this;
		}
		
		/**
		 * 
		 * @param examLength the length of the patients next exam
		 * @modifies this
		 * @effects post method: this.examLength.equals(examLength)
		 * @return this
		 */
		public PatientBuilder setExamLength(double examLength) {
			this.examLength = examLength;
			return this;
		}
		
		public Patient build(){
			return new Patient(this);
		}
	}
	
	
	
	public void setExamLength(double examLen) {
		this.examLength = examLen;
	}
	public double getExamLength() {
		return this.examLength;
	}
	public double getArrivalTime() {
		return this.arrivalTime;
	}
	public long getEmergencyLevel() {
		return this.emergencyLevel;
	}
	
	public void changeELevel(int i) {
		this.emergencyLevel = i;
	}

	@Override
	public String toString() {
		String j = "I am a Patient, my name is " + this.name; //+ "\n" + "I have an emergency level of " + emergencyLevel + "\n" + "my exam will take " + this.examLength/60000 + " minutes" + "\n" + "I arrive at " + this.arrivalTime ;
		return j;
	}
	@Override
	public int compareTo(Patient o) {
		//returns 1 if this.patients eLevel is greater that passed arg
		//what if the e levels are the same? need to base on arrival time		
		if(this.emergencyLevel > o.emergencyLevel) {
			return 1;//for sure the compared should go in front of this.node
		}else if(this.emergencyLevel < o.emergencyLevel){
			//the compared node should go after
			return -1;
		}else {
			//we have the same elevel, lets check our arrival times
			if(this.arrivalTime > o.arrivalTime) {
				//compared node should go in front
				return 1;
			}else if(this.arrivalTime < o.arrivalTime) {
				return -1;
			}else {
				//we are the exact same
				return 0;
			}
		}
		
	}
	

}
