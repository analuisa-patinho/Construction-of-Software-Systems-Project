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
public class ChooseScheduleAction extends Action {

	@EJB
	private IBuyMonthlyParticipationServiceRemote monthlyParticipationService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BuyMonthlyParticipationModel model = createHelper(request);
		request.setAttribute("model", model);

		if (validInput(model)) {
			try {
				model.setSchedules(monthlyParticipationService.getSchedulesForRegular(model.getActivityName()));
				model.addMessage("Schedule is valid");
			} catch (ActivityNotFoundException e) {
				model.addMessage("Error selecting activity: " + e.getMessage());
			} catch (ApplicationException e) {
				model.addMessage("Error validating data: " + e.getMessage());
			}
		} else
			model.addMessage("Error validating data");
		model.clearFields();
		request.getRequestDispatcher("/resources/monthlyParticipation/finalInfo.jsp").forward(request, response);
	}

	private boolean validInput(BuyMonthlyParticipationModel model) {

		boolean result = isFilled(model, model.getActivityName(), "Activity name must be filled.");

		return result;
	}

	private BuyMonthlyParticipationModel createHelper(HttpServletRequest request) {
		BuyMonthlyParticipationModel model = new BuyMonthlyParticipationModel();
		model.setParticipationService(monthlyParticipationService);
		
		// fill it with data from the request
		model.setActivityName(request.getParameter("activityName"));
		
		return model;
	}
}
