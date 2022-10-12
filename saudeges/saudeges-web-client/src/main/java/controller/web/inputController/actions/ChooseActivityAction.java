package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.remote.IBuyMonthlyParticipationServiceRemote;
import presentation.web.model.BuyMonthlyParticipationModel;


@Stateless
public class ChooseActivityAction extends Action {

	@EJB
	private IBuyMonthlyParticipationServiceRemote monthlyParticipationService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BuyMonthlyParticipationModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			try {
				model.setSchedules(monthlyParticipationService.getSchedulesForRegular(model.getActivityName()));
				model.addMessage("Activity is valid.");
				
			} catch (ActivityNotFoundException e) {
				model.addMessage("Error selecting the activity: " + e.getMessage());
				
			}
			  catch (ApplicationException e) {
				model.addMessage("Error validating data: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating the information");

		request.getRequestDispatcher("/resources/monthlyParticipation/chooseSchedules.jsp").forward(request, response);

	}
	
	private boolean validInput(BuyMonthlyParticipationModel model) {
		return isFilled(model, model.getActivityName(), "Activity name must be filled.");
	}

	private BuyMonthlyParticipationModel createHelper(HttpServletRequest request) {
		BuyMonthlyParticipationModel model = new BuyMonthlyParticipationModel();
		model.setParticipationService(monthlyParticipationService);
		
		// fill it with data from the request
		model.setSkd(request.getParameter("activityName"));
		return model;
	}

}
