package controller.web.inputController.actions;

import java.io.IOException;
import java.time.LocalDate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDateException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidReservationException;
import facade.exceptions.ScheduleNotFoundException;
import facade.remote.IBuyMonthlyParticipationServiceRemote;
import presentation.web.model.BuyMonthlyParticipationModel;

@Stateless
public class CompleteBuyAction extends Action {

	@EJB
	private IBuyMonthlyParticipationServiceRemote monthlyParticipationService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BuyMonthlyParticipationModel model = createHelper(request);
		request.setAttribute("model", model);

		if (validInput(model)) {
			try {
				LocalDate startDate = LocalDate.parse(model.getStartDate());
				
				//monthlyParticipationService.getPayInfo(intValue(model.getSkd()), startDate, intValue(model.getDuration()),
						//model.getEmail());
				
				model.setCost(String.valueOf(monthlyParticipationService.getPayInfo(intValue(model.getSkd()), startDate, intValue(model.getDuration()),
						model.getEmail()).getValue()));
				
				model.setEntity(String.valueOf(monthlyParticipationService.getPayInfo(intValue(model.getSkd()), startDate, intValue(model.getDuration()),
						model.getEmail()).getEntity()));
				
				model.setReference(String.valueOf(monthlyParticipationService.getPayInfo(intValue(model.getSkd()), startDate, intValue(model.getDuration()),
						model.getEmail()).getReference()));
				
				model.setPayDay(monthlyParticipationService.getPayDayLimit(intValue(model.getSkd()), startDate, intValue(model.getDuration()),
						model.getEmail()));
				
				model.clearFields();
				
				model.addMessage("Reservation succeded!");
			} catch (ScheduleNotFoundException e) {
				model.addMessage("Error choosing schedule: " + e.getMessage());
				
			} catch (InvalidDateException e) {
				model.addMessage("Error choosing date: " + e.getMessage());
				
			} catch (InvalidDurationException e) {
				model.addMessage("Invalid duration: " + e.getMessage());
				
			} catch(InvalidReservationException e) {
				model.addMessage("Invalid reservation: " + e.getMessage());
			}
			  catch (ApplicationException e) {
				model.addMessage("Registration error: " + e.getMessage());
			}
			
		} else
			model.addMessage("Registration Error");

		request.getRequestDispatcher("/resources/monthlyParticipation/finalInfo.jsp").forward(request, response);
	}

	private boolean validInput(BuyMonthlyParticipationModel model) {
		return  isFilled(model, model.getDuration(), "Duration must be filled.")
			    && isInt(model, model.getDuration(), "Duration must be a number.")
				&& isFilled(model, model.getStartDate(), "Start date must be filled.")
				&& isDate(model, model.getStartDate(), "Invalid start date.")
				&& isFilled(model, model.getEmail(), "Email must be filled.");
	}

	private BuyMonthlyParticipationModel createHelper(HttpServletRequest request) {
		BuyMonthlyParticipationModel model = new BuyMonthlyParticipationModel();
		model.setSkd(request.getParameter("skd"));
		return model;
	}
}
