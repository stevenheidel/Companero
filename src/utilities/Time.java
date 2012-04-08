package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	private boolean hasDay;
	
	private boolean hasMonth;
	
	private boolean hasYear;
	
	public Time(String text)
	{
		String[] dateFormats = {"dd MMM yy", "dd MMM", "MMM yy", "yy"};
		
		hasDay = false;
		hasMonth = false;
		hasYear = false;
		
		for (String df : dateFormats)
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat(df);
				date = sdf.parse(text);
				
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
	
	public boolean hasMonth()
	{
		return hasMonth;
	}
	
	public boolean hasYear()
	{
		return hasYear;
	}
	
	public boolean equals(Time otherTime)
	{
		return date.equals(otherTime.getDate());
	}
	
	public String toString()
	{
		return date.toString();
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
			System.out.println(time.hasMonth());
			System.out.println(time.hasYear());
		}
	}
}
