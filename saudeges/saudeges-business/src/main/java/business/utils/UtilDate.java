package business.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import facade.exceptions.InvalidDateFormatException;

/**
 * An utility class with date utilities.
 *
 */
public class UtilDate {

	private static SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final LocalDateTime MOCK_CURRENT_DATE = LocalDate.of(2022, 3, 20).atTime(10, 15, 0);

	/**
	 * An utility class should not have public constructors
	 */
	private UtilDate() {
	}

	/**
	 * Converts a string with the previously specified format to the respective date
	 * 
	 * @param date A string representing the date
	 * @return A string with the previously specified format to the respective date
	 * @throws InvalidDateFormatException When the input is not in the expected
	 *                                    format
	 */
	public static Date parse(String date) throws InvalidDateFormatException {
		try {
			return parser.parse(date);
		} catch (ParseException e) {
			throw new InvalidDateFormatException("The input was not in the expected format.");
		}
	}

	/**
	 * For test purposes only.
	 * 
	 * Converts a string with the previously specified format to the respective date
	 * 
	 * @param date A string representing the date
	 * @return A string with the previously specified format to the respective date
	 */
	public static Date fakeParse(String date) {
		try {
			return parser.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * Converts the date component of a Date to LocalDate
	 * 
	 * @param date The Date to convert
	 * @return The corresponding LocalDate
	 */
	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Converts the LocalDate to a Date WARNING: Note that the time component of the
	 * resulting Date is random.
	 * 
	 * @param date The LocalDate to convert
	 * @return The corresponding Date
	 */
	public static Date toDate(LocalDate date) {
		return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Converts the time component of a Date to LocalTime
	 * 
	 * @param date The Date to convert
	 * @return The corresponding LocalTime
	 */
	public static LocalTime toLocalTime(Date date) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).toLocalTime();
	}

	/**
	 * Converts the LocalTime to a Date WARNING: Note that the date component of the
	 * resulting Date is random.
	 * 
	 * @param date The LocalTime to convert
	 * @return The corresponding Date of the given date
	 */
	public static Date toDate(LocalTime time) {
		return Date.from(time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Converts the LocalDateTime to a Date WARNING: Note that the date component of
	 * the resulting Date is random.
	 * 
	 * @param dateToConvert The LocalDateTime to convert
	 * @return The corresponding Date of the given date
	 */
	public static Date toDate(LocalDateTime dateToConvert) {
		return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Returns a mock, fixed date
	 * 
	 * @return The mock current date
	 */
	public static LocalDateTime getMockCurrentDate() {
		return MOCK_CURRENT_DATE;
	}
	
	/**
	 * Given a day of week, returns the correspondent Integer
	 *
	 * @return the correspondent int for the day of week, if error return 0
	 */
	//TODO: ordinal nao serve? expressao com relação entre um e outro?
	public static int dayOfWeekToInt(DayOfWeek dow) {
		int d = 0;

		switch (dow) {
		case SUNDAY:
			d = 1;
			break;
		case MONDAY:
			d = 2;
			break;
		case TUESDAY:
			d = 3;
			break;
		case WEDNESDAY:
			d = 4;
			break;
		case THURSDAY:
			d = 5;
			break;
		case FRIDAY:
			d = 6;
			break;
		case SATURDAY:
			d = 7;
			break;
		default:
			break;
		}
		return d;
	}

	public static Date addMinutes(Date begin, int duration) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		calendar.add(Calendar.MINUTE, duration);
		return calendar.getTime();
	}	
	
}
