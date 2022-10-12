package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDateException;
import facade.exceptions.InvalidDateFormatException;
import facade.remote.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionalActivityModel;

@Stateless
public class ChooseDatesTimesAction extends Action {
	
	@EJB
	private IScheduleOccasionalActivityServiceRemote occasionalActService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionalActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if(validInput(model)) {
			try {
				model.setInstructors(occasionalActService.getInstructors(model.getActivityName(), model.getDates()));
				model.addMessage("Dates are valid");
				
			} catch (InvalidDateException e) {
				model.addMessage("Error choosing dates: " + e.getMessage());
			} catch (ApplicationException e) {
				model.addMessage("Error validating data: " + e.getMessage());
			} catch (InvalidDateFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else 
			model.addMessage("Error validating the informon");
		
		request.getRequestDispatcher("/resources/setOccasional/chooseInstructor.jsp").forward(request, response);
	}

	private boolean validInput(ScheduleOccasionalActivityModel model) {
		Collection<String> dates = model.getDates();
	
		for(String d : dates) 
			if(!isFilled(model, d, "All dates must be filled.")  || !isDate(model, d, "All dates must be valid."))
				return false;
		
		return true;
	}


	private ScheduleOccasionalActivityModel createHelper(HttpServletRequest request) {
		ScheduleOccasionalActivityModel model = new ScheduleOccasionalActivityModel();
		model.setOccasionalActivityService(occasionalActService);
		
		// fill it with data from the request
		model.setDates2(Arrays.asList(request.getParameterValues("dates")));
		return model;
	}

}
