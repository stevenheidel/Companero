package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Stores a time. More convenient than always having to use Java's annoying
 * built-in date feature.
 * 
 * @author Steven Heidel
 *
 */
public class Time 
{
	/**
	 * The original input
	 */
	public String original;
	
	/**
	 * The calendar object, the new way to do dates in Java
	 */
	private Calendar calendar;
	
	/**
	 * Whether or not the day is relevant
	 */
	private boolean hasDay;
	
	/**
	 * Whether or not the month is relevant
	 */
	private boolean hasMonth;
	
	/**
	 * Whether or not the year is relevant
	 */
	private boolean hasYear;
	
	/**
	 * Parse a string in one of 5 possible formats and then turn it into a time
	 * @param text the string to parse
	 */
	public Time(String text)
	{
		text = text.trim();
		original = text;
		
		String[] dateFormats = {"dd MMM yy", "dd MMM", "MMM yy", "MMMM", "yy"};
		
		hasDay = false;
		hasMonth = false;
		hasYear = false;
		
		calendar = new GregorianCalendar();
		
		for (String df : dateFormats)
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat(df);
				Date date = sdf.parse(text);
				
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
	
	/**
	 * Return the original string
	 * @return the original string
	 */
	public String getOriginal()
	{
		return original;
	}
		
	/**
	 * Check if the day is significant
	 * @return whether or not the day is significant
	 */
	public boolean hasDay()
	{
		return hasDay;
	}
	
	/**
	 * Return the day
	 * @return the day
	 */
	public int getDay()
	{
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Check if the month is significant
	 * @return whether or not the month is significant
	 */
	public boolean hasMonth()
	{
		return hasMonth;
	}

	/**
	 * Return the month
	 * @return the month
	 */
	public int getMonth()
	{
		// January is 0
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * Check if the year is significant
	 * @return whether or not the year is significant
	 */
	public boolean hasYear()
	{
		return hasYear;
	}

	/**
	 * Return the year
	 * @return the year
	 */
	public int getYear()
	{
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Set the year
	 * @param newYear the new year to change to
	 */
	public void setYear(int newYear)
	{
		calendar.set(Calendar.YEAR, newYear);
		hasYear = true;
	}
	
	/**
	 * Check if two times are equal. Will be considered equal if all the fields
	 * which they both have are equal and their day and month equal or their
	 * years equal.
	 * 
	 * @param otherTime
	 * @return whether or not they're equal
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
		SimpleDateFormat sdf = null;
		
		if (!hasDay() && !hasMonth())
			sdf = new SimpleDateFormat("yyyy");
		else if (!hasDay() && !hasYear())
			sdf = new SimpleDateFormat("MMMM");
		else if (!hasDay())
			sdf = new SimpleDateFormat("MMMM yyyy");
		else if (!hasYear())
			sdf = new SimpleDateFormat("dd MMMM");
		else
			sdf = new SimpleDateFormat("dd MMMM yyyy");
		
		return sdf.format(calendar.getTime()).toUpperCase();
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
		
		Time test = new Time("15 Jan 90");
		System.out.println(test.equals(new Time("15 Jan")));
		System.out.println(test.equals(new Time("14 Jan")));
		System.out.println(test.equals(new Time("Jan 90")));
		System.out.println(test.equals(new Time("Jan 89")));
		System.out.println(test.equals(new Time("1990")));
		test = new Time("15 Jan");
		System.out.println(test.equals(new Time("Jan 90")));
		System.out.println(test.equals(new Time("1990")));
		test.setYear(1990);
		System.out.println(test.equals(new Time("15 Jan 1990")));
	}
}
