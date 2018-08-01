package hostpitalQueue;

public class Patient extends PriorityQueue<Patient> implements Comparable<Patient>{

	public String name;
	private Integer emergencyLevel;
	private double examLength;
	private double arrivalTime;
	
	public Patient(String name,Integer eLevel) {
		this.name = name;
		this.emergencyLevel = eLevel;
	}
	
	//appointmentTime is in milliseconds, examlen comes in as mins, gets converted
	public Patient(String name,double appointmentTime,int eLevel,double examLen) {
		this.name = name;
		this.emergencyLevel = eLevel;
		this.arrivalTime = (appointmentTime);
		setExamLength(examLen * 60000);
	}
	public Patient(double arrivalTime,int eLevel,double examLength) {
		this("johnDoe",arrivalTime,eLevel,examLength);
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
