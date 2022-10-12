package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.remote.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionalActivityModel;

@Stateless
public class NewScheduleOccasionalAction extends Action {

	@EJB
	private IScheduleOccasionalActivityServiceRemote scheduleOccasionalService;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ScheduleOccasionalActivityModel model = new ScheduleOccasionalActivityModel();
		model.setOccasionalActivityService(scheduleOccasionalService);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/resources/setOccasional/chooseSpecialty.jsp").forward(request, response);
	}

}
