package hostpitalQueue;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExaminationRoom implements Comparable<ExaminationRoom> {

	private int roomNumber;
	// this will be the final time in which the patient is discharged, patients time
	// is given in minutes
	private double dischargeTime;
	private Patient patient = null;
	private boolean roomOccupied = false;
	private double examLength;
	private Timer myTimer;
	// private ScheduledExecutorService scheduler =
	// Executors.newScheduledThreadPool(1);

	public ExaminationRoom(int roomNum) {
		this.roomNumber = roomNum;
	}

	public void admitPatient(Patient p) {
		this.patient = p;
		double curTime = System.currentTimeMillis();
		this.examLength = patient.getExamLength();
		this.dischargeTime = curTime + examLength;
		this.roomOccupied = true;

		System.out.println("admitted patient");
		// schedule patients departure/dismissal
		myTimer = new Timer();
		TimerTask task = new dismissPatient(this);
		myTimer.schedule(task, (long) this.examLength);

	}

	// dismisses the rooms current patient when timer from addPatient tells it to
	private class dismissPatient extends TimerTask {

		ExaminationRoom room;

		public dismissPatient(ExaminationRoom room) {
			this.room = room;
		}

		@Override
		public void run() {
			Patient p = this.room.getPatient();
			p = null;
			room.changeOccupiedStatus(false);
			room.dischargeTime = 0;
			room.examLength = 0;
			System.out.println("room cleared");
			room.cancelTimer();
		}
	}
	
	public void cancelTimer() {
		this.myTimer.cancel();
	}

	public void clearRoom() {
		this.patient = null;
		this.roomOccupied = false;
		this.examLength = 0;
		this.dischargeTime = 0;
	}

	public boolean isOccupied() {
		return roomOccupied;
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
		String o = this.roomOccupied ? "yes" : "no";
		String j = "am I occupied? " + o + "\n" + "the current exam will take " + examLength/60000 + "minutes" + "\n"
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

}
