package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import facade.exceptions.ApplicationException;
import facade.exceptions.SpecialtyNotFoundException;
import facade.remote.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionalActivityModel;

@Stateless
public class ChooseSpecialtyAction extends Action {
	
	@EJB
	private IScheduleOccasionalActivityServiceRemote occasionalActService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionalActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			try {
				model.setOccasionalActivities(occasionalActService.getOccasionalActivities(model.getSpecialty()));
				model.addMessage("Specialty is valid.");
				
				
			} catch (SpecialtyNotFoundException e) {
				model.addMessage("Error selecting the specialty: " + e.getMessage());
				
			}
			  catch (ApplicationException e) {
				model.addMessage("Error validating the information: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating the information");
        
		request.getRequestDispatcher("/resources/setOccasional/chooseOccasional.jsp").forward(request, response);
	}
	
	private boolean validInput(ScheduleOccasionalActivityModel model) {
		return isFilled(model, model.getSpecialty(), "Specialty name must be filled.");
	}

	
	private ScheduleOccasionalActivityModel createHelper(HttpServletRequest request) {
		ScheduleOccasionalActivityModel model = new ScheduleOccasionalActivityModel();
		model.setOccasionalActivityService(occasionalActService);
		
		// fill it with data from the request
		model.setSpecialty(request.getParameter("specialty"));
		return model;
	}

}
