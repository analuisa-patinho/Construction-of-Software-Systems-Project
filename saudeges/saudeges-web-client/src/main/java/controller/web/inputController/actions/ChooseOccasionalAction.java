package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.remote.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionalActivityModel;

@Stateless
public class ChooseOccasionalAction extends Action {
	
	@EJB
	private IScheduleOccasionalActivityServiceRemote occasionalActService;
	
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionalActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			try {
				
				model.setNrsSessions(occasionalActService.getNrSessions());
				model.addMessage("Activity is valid.");
				
			} catch (ActivityNotFoundException e) {
				model.addMessage("Error selecting the activity: " + e.getMessage());
				
			}
			  catch (ApplicationException e) {
				model.addMessage("Error validating the information: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating the information");
        
		
		request.getRequestDispatcher("/resources/setOccasional/chooseDatesTimes.jsp").forward(request, response);
	}


	private boolean validInput(ScheduleOccasionalActivityModel model) {
		return isFilled(model, model.getActivityName(), "Activity name must be filled.");
	}


	private ScheduleOccasionalActivityModel createHelper(HttpServletRequest request) {
		ScheduleOccasionalActivityModel model = new ScheduleOccasionalActivityModel();
		model.setOccasionalActivityService(occasionalActService);
		
		// fill it with data from the request
		model.setActivityName(request.getParameter("activityName"));
		return model;
	}
}
