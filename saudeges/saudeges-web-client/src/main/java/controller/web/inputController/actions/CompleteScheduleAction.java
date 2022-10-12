package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.exceptions.InstructorNotFoundException;
import facade.remote.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionalActivityModel;

@Stateless
public class CompleteScheduleAction extends Action {

	@EJB
	private IScheduleOccasionalActivityServiceRemote occasionalActService;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionalActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if(validInput(model)) {
			try {
				model.setCost(String.valueOf(occasionalActService.getPayInfo(Integer.parseInt(model.getInstructorID()), model.getEmail()).getValue()));
				
				model.setEntity(occasionalActService.getPayInfo(Integer.parseInt(model.getInstructorID()), model.getEmail()).getEntity());
				
				model.setReference(occasionalActService.getPayInfo(Integer.parseInt(model.getInstructorID()), model.getEmail()).getReference());
				
				model.setPayDay(occasionalActService.getPayDayLimit(Integer.parseInt(model.getInstructorID()), model.getEmail()));
				
				model.clearFields();
				
			} catch (InstructorNotFoundException e) {
				model.addMessage("Error selecting the instructor: " + e.getMessage());
				
			} catch (ApplicationException e) {
				model.addMessage("Error validating data: " + e.getMessage());
			}
		} else 
			model.addMessage("Error validating the information");
		
		request.getRequestDispatcher("/resources/setOccasional/chooseInstructor.jsp").forward(request, response);
		
	}

	private boolean validInput(ScheduleOccasionalActivityModel model) {
		return isFilled(model, model.getInstructorID(), "The instructor must be filled.")
				&& isFilled(model, model.getEmail(), "The user email must be filled.");
	}

	private ScheduleOccasionalActivityModel createHelper(HttpServletRequest request) {
		ScheduleOccasionalActivityModel model = new ScheduleOccasionalActivityModel();
		model.setInstructorID(request.getParameter("instructorID"));
		model.setEmail(request.getParameter("email"));
		return model;
		
	}

}
