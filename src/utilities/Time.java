package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * 
 * @author Steven Heidel
 *
 */
public class Time 
{
	/**
	 * Holds the date object. The terms time and date are interchangeable.
	 */
	private Date date;
	
	private Calendar calendar;
	
	private boolean hasDay;
	
	private boolean hasMonth;
	
	private boolean hasYear;
	
	public Time(String text)
	{
		String[] dateFormats = {"dd MMM yy", "dd MMM", "MMM yy", "yy"};
		
		hasDay = false;
		hasMonth = false;
		hasYear = false;
		
		calendar = new GregorianCalendar();
		
		for (String df : dateFormats)
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat(df);
				date = sdf.parse(text);
				
				calendar.setTime(date);
				
				// check if there was a day or month
				if (df.toUpperCase().contains("D"))
					hasDay = true;
				if (df.toUpperCase().contains("M"))
					hasMonth = true;
				if (df.toUpperCase().contains("Y"))
					hasYear = true;
				
				return;
			}
			catch (ParseException e) {}
		}
		
		// none of the date formats have worked
		throw new IllegalArgumentException("Given date was not in the correct format");
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public boolean hasDay()
	{
		return hasDay;
	}
	
	public int getDay()
	{
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public boolean hasMonth()
	{
		return hasMonth;
	}
	
	public int getMonth()
	{
		// January is 0
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public boolean hasYear()
	{
		return hasYear;
	}
	
	public int getYear()
	{
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Check if two times are equal. Will be considered equal if all the fields
	 * which they both have are equal and their day and month equal or their
	 * years equal.
	 * 
	 * @param otherTime
	 * @return
	 */
	public boolean equals(Time otherTime)
	{
		boolean dayEquals = false;
		boolean monthEquals = false;
		boolean yearEquals = false;
		
		if (hasDay() && otherTime.hasDay())
			if (otherTime.getDay() == getDay())
				dayEquals = true;
			else
				return false;
		if (hasMonth() && otherTime.hasMonth())
			if (otherTime.getMonth() == getMonth())
				monthEquals = true;
			else
				return false;
		if (hasYear() && otherTime.hasYear())
			if (otherTime.getYear() == getYear())
				yearEquals = true;
			else
				return false;
		
		return (dayEquals && monthEquals) || yearEquals;
	}
	
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
		return sdf.format(date);
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		String[] testDates = {"15 Jan 90", "15 January 90", "15 Jan 1990", "15 January 1990",
				"Jan 90", "January 90", "Jan 1990", "January 1990", "90", "1990",
				"15 Jan", "15 January"};
		
		for (String test : testDates)
		{
			Time time = new Time(test);
			System.out.println(time);
			System.out.println(time.hasDay());
			System.out.println(time.getDay());
			System.out.println(time.hasMonth());
			System.out.println(time.getMonth());
			System.out.println(time.hasYear());
			System.out.println(time.getYear());
		}
		
		// TODO: use assertions in testing
		
		Time test = new Time("15 Jan 90");
		System.out.println(test.equals(new Time("15 Jan")));
		System.out.println(test.equals(new Time("14 Jan")));
		System.out.println(test.equals(new Time("Jan 90")));
		System.out.println(test.equals(new Time("Jan 89")));
		System.out.println(test.equals(new Time("1990")));
		test = new Time("15 Jan");
		System.out.println(test.equals(new Time("Jan 90")));
		System.out.println(test.equals(new Time("1990")));
	}
}
