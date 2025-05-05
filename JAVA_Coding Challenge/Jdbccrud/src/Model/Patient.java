package Model;


public class Patient {
	private int patientID;
	private String firstName;
	private String lastName;
	private String gender;
	private String phoneNumber;

	public Patient() {
	}

	public Patient(int patientID, String firstName, String lastName, String gender, String phoneNumber) {
		this.patientID = patientID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender =  gender;
		this.phoneNumber = phoneNumber;
	}
	
	
	
	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Patient { " +
		       "Patient ID = " + getPatientID() +
		       ", Name = " + getFirstName() + ' ' + getLastName() +
		       ", Gender = " + getGender() +
		       ", Contact = " + getPhoneNumber() + 
		       " }";
	}
	
}
