package ca.sheridancollege.Ricos.ex62_fullCRUD.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.Ricos.ex62_fullCRUD.beans.Appointment;
import ca.sheridancollege.Ricos.ex62_fullCRUD.database.DatabaseAccess;

@Controller
public class AppointmentController {
	
	@Autowired
	private DatabaseAccess da;

	@GetMapping("/")
	public String index(Model model) {
	    // Retrieve the list of appointments
	    List<Appointment> appointmentList = da.getAllAppointments();

	    // Add the list of appointments to the model
	    model.addAttribute("appointmentList", appointmentList);

	    // Create a new empty appointment and add it to the model
	    Appointment appointment = new Appointment();
	    model.addAttribute("appointment", appointment);

	    return "index";
	}

	
	@PostMapping("/insertAppointment")
	public String insertAppointment(Model model, @ModelAttribute Appointment appointment) {


		List<Appointment> existingAppointments = da.getAppointmentListById(appointment.getAppointmentId());

		if (existingAppointments.isEmpty()) {
			// If the appointment doesn't exist (based on ID), insert a new appointment
			da.insertAppointment(appointment);
		} else {
			// If the appointment exists, update the existing appointment
			da.updateAppointment(appointment);
		}

		model.addAttribute("appointmentList", da.getAllAppointments());
		model.addAttribute("appointment", new Appointment());

		return "index";
	}
	

	@GetMapping("/editAppointmentById/{appointmentId}")
	public String editAppointmentById(Model model, @PathVariable Long appointmentId) {
		
	    Appointment appointment = da.getAppointmentListById(appointmentId).get(0); //retrieving specific appointment at this id
	    
	    model.addAttribute("appointment", appointment);
	    
	    model.addAttribute("appointmentList", da.getAllAppointments());
	    
	    return "index";
	}

	
	@GetMapping("/deleteAppointmentById/{appointmentId}")
	public String deleteAppointmentById(Model model, @PathVariable Long appointmentId) {
		
	    da.deleteAppointmentById(appointmentId);
	    
	    model.addAttribute("appointmentList", da.getAllAppointments());
	    
	    model.addAttribute("appointment", new Appointment());
	    
	    return "index";
	}
	
	
	
}
