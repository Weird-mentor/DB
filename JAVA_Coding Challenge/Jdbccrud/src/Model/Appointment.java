package Model;


public class Appointment {
	private int AppointmentID;
	private int DoctorID;
	private int PatientID;
    private String Description;
	private String Appoint_Date;

	public  Appointment() {
	}

	public  Appointment( int AppointmentID, int DoctorID, int PatientID, String Description,String Appoint_Date) {
		this.DoctorID = DoctorID;
		this.AppointmentID = AppointmentID;
		this.PatientID = PatientID;
		this.Description =  Description;
		this.Appoint_Date = Appoint_Date;
	}


	public int getAppointmentID() {
		return AppointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		AppointmentID = appointmentID;
	}

	public int getDoctorID() {
		return DoctorID;
	}

	public void setDoctorID(int doctorID) {
		DoctorID = doctorID;
	}

	public int getPatientID() {
		return PatientID;
	}

	public void setPatientID(int patientID) {
		PatientID = patientID;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getAppoint_Date() {
		return Appoint_Date;
	}

	public void setAppoint_Date(String appoint_Date) {
		Appoint_Date = appoint_Date;
	}
	@Override
	public String toString() {
		return "Appointment{ " +
		       " Appointment_ID = " + getAppointmentID() +
		       ", Doctor_ID= " + getDoctorID() +
		       ", Patient_ID = " + getPatientID() +
		       ", Description = " + getDescription() +
		       ", Appointment_DATE =" + getAppoint_Date() +
		       " }";
	}
	
}
