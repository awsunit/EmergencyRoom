package hostpitalQueue;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import hostpitalQueue.Patient.PatientBuilder;

/**
 * Represents an examination room. Users can also expect an ExaminationRoom to
 * have: a room number, cannot be null a AWSUNIT Patient, which can be null
 * 
 * Clients can admit and discharge patients into the room.
 * 
 * Rep Invariant: roomNumber != null
 * 
 * Abstraction Function: AF(this) = an examination room with a room number =
 * this.roomNumber and a patient = this.patient
 * 
 * @author newdr
 *
 */
public class ExaminationRoom implements Comparable<ExaminationRoom> {
//	public static void main(String[] args) {
//		Patient p = new PatientBuilder("Todd").setArrivalTime(.10).setExamLength(4000).build();
//		ExaminationRoom room = new ExaminationRoom(420);
//		room.admitPatient(p);
//		try{
//			room.admitPatient(p);
//		}catch(IllegalArgumentException e){
//			System.out.println("sorry, room occupied");
//		}
//		System.out.println(p);
//	}

	/** The number to associate with this room */
	private int roomNumber;
	/**
	 * this will be the final time in which the patient is discharged, patients time
	 * is given in minutes
	 */
	private double dischargeTime;
	/** no patient initially */
	private Patient patient;
	/** no patient, nobody in the room */
	private boolean roomOccupied;
	/** length of the current patients exam */
	private double examLength;
	/** timer which removes patient after examLength */
	//private Timer myTimer;
	private ScheduledExecutorService scheduler;

	/**
	 * 
	 * @param roomNum the int to associate with this room
	 */
	public ExaminationRoom(int roomNum) {
		this.roomNumber = roomNum;
	}

	/**
	 * Admits a patient into the room. Sets up a timer task to remove patient after
	 * an examLength amount of time has passed.
	 * 
	 * @param p the Patient being admitted into the room
	 * @modifies this
	 * @effects if this.patient == null: post method: this.patient.equals(p) and a
	 *          TimerTask is running which monitors the patient else: throws new
	 *          IllegalArgumentException
	 */
	public void admitPatient(Patient p) throws IllegalArgumentException {
		if (this.patient != null) {
			throw new IllegalArgumentException("Room is already occupied with " + this.patient);
		} else {
			this.patient = p.copy();
			double curTime = System.currentTimeMillis();
			this.examLength = patient.getExamLength();
			this.dischargeTime = curTime + examLength;
			this.roomOccupied = true;

			System.out.println("admitted patient");

			// schedule patients departure/dismissal
			scheduler = Executors.newScheduledThreadPool(0);
			Runnable task = new DismissPatient(this);
			ScheduledFuture<?> patientHandle = 
					scheduler.schedule(task,(long)this.examLength,TimeUnit.MILLISECONDS);
			//myTimer = new Timer();
			//TimerTask task = new dismissPatient(this);
			//myTimer.schedule(task, (long) this.examLength);
		}
	}

//	/**
//	 * dismisses the rooms current patient when timer from addPatient tells it to
//	 * 
//	 * @author newdr
//	 *
//	 */
//	private class dismissPatient extends TimerTask {
//
//		ExaminationRoom room;
//
//		public dismissPatient(ExaminationRoom room) {
//			this.room = room;
//		}
//
//		@Override
//		public void run() {
//			Patient p = room.patient;
//			p = null;
//			System.out.println("just made p null");
//			room.changeOccupiedStatus(false);
//			room.dischargeTime = 0;
//			room.examLength = 0;
//			System.out.println("room cleared");
//			room.cancelTimer();
//		}
//	}

	private class DismissPatient implements Runnable {

		ExaminationRoom room;

		public DismissPatient(ExaminationRoom room) {
			this.room = room;
		}

		@Override
		public void run() {
			//avoid using this.room -> causes thread confusion
			room.patient = null;
			System.out.println("just made p null");
			room.changeOccupiedStatus(false);
			room.dischargeTime = 0;
			room.examLength = 0;
			System.out.println("room cleared");
		}

	}

//	public void cancelTimer() {
//		this.myTimer.cancel();
//	}

//	public void clearRoom() {
//		this.patient = null;
//		this.roomOccupied = false;
//		this.examLength = 0;
//		this.dischargeTime = 0;
//	}

	public boolean isOccupied() {
		return this.roomOccupied;
	}

	public void setRoomNumber(int roomNum) {
		this.roomNumber = roomNum;
	}

	public double getDischargeTime() {
		// should only be called when room is occupied, check that it is so
		return this.dischargeTime;
	}

	public Patient getPatient() {
		return this.patient;
	}

	@Override
	public String toString() {
		String o = "no";
		if (this.roomOccupied) {
			o = " yes with ";
			o += this.patient;
		}
		String j = "am I occupied? " + o + "\n" + "the current exam will take " + examLength / 60000 + "minutes" + "\n"
				+ "current time is " + System.currentTimeMillis() + "\n" + "discharging patient at " + dischargeTime;
		return j;
	}

	public void changeOccupiedStatus(boolean b) {
		this.roomOccupied = b;
	}

	@Override
	public int compareTo(ExaminationRoom arg0) {
		// rooms with later discharge times return positive
		if (this.dischargeTime > arg0.getDischargeTime()) {
			return 1;
		} else {
			return this.dischargeTime < arg0.getDischargeTime() ? -1 : 0;
		}
	}

	/**
	 * Deemed unnecessary -> roomNum initialized upon object creation and patient
	 * can be null so whats to check?
	 */
//	private void checkRep() throws IllegalArgumentException{
//		if(this.roomNumber) {
//			throw new IllegalArgumentException("room number cannot be null");
//		}
//		
//	}

}
