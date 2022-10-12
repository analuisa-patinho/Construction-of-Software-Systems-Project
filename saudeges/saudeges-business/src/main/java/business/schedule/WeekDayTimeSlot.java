package business.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import business.utils.UtilDate;

import static javax.persistence.EnumType.STRING;

@Embeddable
public class WeekDayTimeSlot {

	@Enumerated(STRING)
	private DayOfWeek day;
	
	@Temporal(TemporalType.TIME)
	private Date time;

	public WeekDayTimeSlot() {
	}

	public WeekDayTimeSlot(DayOfWeek day, LocalTime time) {
		this.day = day;
		this.time = UtilDate.toDate(time);
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}
