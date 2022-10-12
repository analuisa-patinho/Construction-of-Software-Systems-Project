package facade.dtos;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import business.schedule.Schedule;
import business.schedule.WeekDayTimeSlot;
import business.utils.UtilDate;

/**
 * A class DTO representing a simplified version of a Schedule (business side
 * class)
 */
public class ScheduleDTO implements Serializable {

	private int id;
	private int duration;
	private LocalDate startDate;
	private List<String> days;
	private String daysString;
	private List<Pair<DayOfWeek,LocalTime>> when;

	/**
	 * Creates a new Schedule DTO given an id, duration and beginSchedule
	 * 
	 * @param id            The schedule's id
	 * @param duration      The schedule's duration in months
	 * @param startDate		 The schedule's begin date
	 * @param when
	 */
	public ScheduleDTO(int id, int duration, LocalDate beginSchedule, List<Pair<DayOfWeek,LocalTime>> when) {
		this.id = id;
		this.duration = duration;
		this.startDate = beginSchedule;
		this.when = Collections.unmodifiableList(when);
	}
	
	public ScheduleDTO(Schedule sch) {
		this.id = sch.getId();
		this.duration = sch.getDurationInMonths();
		this.startDate = UtilDate.toLocalDate(sch.getStartDate());
		List<Pair<DayOfWeek,LocalTime>> dates = new ArrayList<>();
		for(WeekDayTimeSlot wds: sch.getWeekDayTimeSlots())
			dates.add(new Pair<>(wds.getDay(),UtilDate.toLocalTime(wds.getTime())));
		this.when = Collections.unmodifiableList(dates);
	}

	/**
	 * Returns the begin date
	 * 
	 * @return The begin date
	 */
	public LocalDate getDate() {
		return this.startDate;
	}

	/**
	 * Returns the schedule's id
	 * 
	 * @return The schedule's id
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Returns the schedule monthly duration
	 * 
	 * @return The schedule monthly duration
	 */
	public int getDuration() {
		return this.duration;
	}
	
	public void setDaysString() {
		for(int i = 0; i<days.size(); i+=2) {
			daysString.concat(days.get(i) + " " + days.get(i++) + "/n");
		}
	}
	
	public String getDaysString() {
		return this.daysString;
	}
	
	public void setDays() {
		for(Pair p : when) {
			days.add(p.getValue0().toString() + " " + p.getValue1().toString());
		}
	}
	
	public List<String> getDays(){
		return this.days;
	}

	/**
	 * Returns the begin date
	 * @return 
	 * 
	 * @return The begin date
	 */
	public  List<Pair<DayOfWeek, LocalTime>> getWhen() {
		return this.when;
	}
	
	@Override
	public String toString() {
		setDays();
		setDaysString();
		String result = "Schedule ID: " + id + "\n" 
				+ "\n" + "Days of Week: " + daysString + "\n" + "Duration: " + duration + "\n";
		
		return result;

	}


}
