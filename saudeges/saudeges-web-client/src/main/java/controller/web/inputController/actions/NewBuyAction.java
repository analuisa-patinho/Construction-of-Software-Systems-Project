package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.remote.IBuyMonthlyParticipationServiceRemote;
import presentation.web.model.BuyMonthlyParticipationModel;


@Stateless
public class NewBuyAction extends Action {

	@EJB
	private IBuyMonthlyParticipationServiceRemote monthlyParticipationService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BuyMonthlyParticipationModel model = new BuyMonthlyParticipationModel();
		model.setParticipationService(monthlyParticipationService);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/resources/monthlyParticipation/chooseActivity.jsp").forward(request, response);
	}

}
