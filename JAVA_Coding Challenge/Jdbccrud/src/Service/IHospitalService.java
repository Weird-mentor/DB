package Service;

import Model.Appointment;
import java.util.List;
public interface IHospitalService {
 
	Appointment getAppointmentById(int appointmentId);
	boolean scheduleAppointment(Appointment appointment);
    boolean updateAppointment(Appointment appointment);
    boolean cancelAppointment(int appointmentId);
    List<Appointment> getAppointmentsForPatient(int patientId);
    List<Appointment> getAppointmentsForDoctor(int doctorId);
    
}
