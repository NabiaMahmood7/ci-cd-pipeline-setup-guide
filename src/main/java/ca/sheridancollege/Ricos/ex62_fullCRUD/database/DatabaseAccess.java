package ca.sheridancollege.Ricos.ex62_fullCRUD.database;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.Ricos.ex62_fullCRUD.beans.Appointment;

@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	
	//insert the appointment
	public void insertAppointment(Appointment appointment) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		namedParameters.addValue("firstName", appointment.getFirstName());
		namedParameters.addValue("email", appointment.getEmail());
		namedParameters.addValue("appointmentDate", appointment.getAppointmentDate());
		namedParameters.addValue("appointmentTime", appointment.getAppointmentTime());
		
		
		String query = "INSERT INTO appointment(firstName, email, appointmentDate, appointmentTime)"
				+ " VALUES (:firstName, :email, :appointmentDate, :appointmentTime)"; // Using the named parameter
		
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		
		if (rowsAffected > 0) System.out.println("Hard coded appointment inserted into database");
		
		}
	
	
	//retrieve the appointment list by ID
	public List<Appointment> getAppointmentListById(Long appointmentId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	
		String query = "SELECT * FROM appointment WHERE appointmentId = :appointmentId";
		namedParameters.addValue("appointmentId", appointmentId);
		
		return jdbc.query(query, namedParameters, new
		BeanPropertyRowMapper<Appointment>(Appointment.class));
		}
	
	
	//delete the appointment by Id
	public void deleteAppointmentById(Long appointmentId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "DELETE FROM appointment WHERE appointmentId = :appointmentId";
		namedParameters.addValue("appointmentId", appointmentId);
		
		if (jdbc.update(query, namedParameters) > 0) {
		System.out.println("Deleted appointment " + appointmentId + " from the database.");
		}
		
		}
	
	
	//Retrieve list of appointments
	public List<Appointment> getAllAppointments() { //maps list from database to java side
			
			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			
			String query = "SELECT * FROM appointment";
			
			return jdbc.query(query, namedParameters, new
			BeanPropertyRowMapper<Appointment>(Appointment.class)); //automatically maps relational data to the List<Appointment>
			
			} //retrieves data from the database^
	
	
	//edit the appointment
	public void updateAppointment(Appointment updatedAppointment) {
		
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();

	    String query = "UPDATE appointment SET firstName = :firstName, email = :email, appointmentTime = :appointmentTime, appointmentDate = :appointmentDate"
	    		+ " WHERE appointmentId = :appointmentId";
	    namedParameters.addValue("firstName", updatedAppointment.getFirstName());
	    namedParameters.addValue("email", updatedAppointment.getEmail());
	    namedParameters.addValue("appointmentTime", updatedAppointment.getAppointmentTime());
	    namedParameters.addValue("appointmentDate", updatedAppointment.getAppointmentDate());
	    namedParameters.addValue("appointmentId", updatedAppointment.getAppointmentId());

	    int rowsAffected = jdbc.update(query, namedParameters);

	    if (rowsAffected > 0) {
	        System.out.println("Updated appointment with ID " + updatedAppointment.getAppointmentId() + " in the database.");
	    }
	}
}
