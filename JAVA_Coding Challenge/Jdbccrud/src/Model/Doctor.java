package Model;


public class Doctor {
	private int DoctorID;
	private String firstName;
	private String lastName;
	private String Specialization;
	private String phoneNumber;

	public Doctor() {
	}

	public Doctor(int DoctorID, String firstName, String lastName, String Specialization, String phoneNumber) {
		this.DoctorID = DoctorID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.Specialization =  Specialization;
		this.phoneNumber = phoneNumber;
	}
	

	public int getDoctorID() {
		return DoctorID;
	}

	public void setDoctorID(int doctorID) {
		DoctorID = doctorID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSpecialization() {
		return Specialization;
	}

	public void setSpecialization(String specialization) {
		Specialization = specialization;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Doctor { " +
		       "Doctor ID = " + getDoctorID() +
		       ", Name = " + getFirstName() + ' ' + getLastName() +
		       ", Specialization = " + getSpecialization() +
		       ", Contact = " + getPhoneNumber() + 
		       " }";
	}
	
}
