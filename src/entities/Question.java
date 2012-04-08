/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to store a question which was inputted using square brackets to
 * identify to important elements. It will find the type of question, text to
 * search for, date, and place if applicable.
 * 
 * @author Steven Heidel
 *
 */
public class Question
{
	/**
	 * The type of question. Four possible options: WHO, WHAT, WHEN, and WHERE
	 */
	private String type;
	
	/**
	 * The important text of the question
	 */
	private String text;
	
	/**
	 * The location specified in the question
	 */
	private String place;
	
	/**
	 * The time or date specified in the question
	 */
	private String time;
	
	/**
	 * Create a new question
	 * @param input A question in the form specified using square brackets
	 */
	public Question(String input)
	{
		input = input.toUpperCase();
		
		type = input.split(" ")[0];
		
		// get the elements in square brackets
		Matcher m = Pattern.compile("\\[(.*?)\\]").matcher(input);
		
		// the important text will always be in the first bracket set
		m.find();
		text = m.group(1);
				
		m.find();
		// if WHO or WHAT, get both time and place
		if (type.equals("WHO") || type.equals("WHAT"))
		{
			place = m.group(1);
			m.find();
			time = m.group(1);
		}
		// if WHERE, get just time
		else if (type.equals("WHERE"))
		{
			place = null;
			time = m.group(1);
		}
		// if WHEN, get just place
		else if (type.equals("WHEN"))
		{
			place = m.group(1);
			time = null;
		}
	}
	
	/**
	 * Return the type of question
	 * @return the type of question
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Return the important text
	 * @return the important text
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * Return the place
	 * @return the place
	 */
	public String getPlace()
	{
		return place;
	}
	
	/**
	 * Return the time or date
	 * @return the time or date as a String
	 */
	public String getTime()
	{
		return time;
	}
		
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		Question who = new Question("Who was [the army judge advocate general] of [Chile] in [1990]?");
		Question what = new Question("What is [an example Torres cited] in [Chile] in [1990]?");
		Question where = new Question("Where is it that [the government junta yesterday approved a draft bill] on [5 Jan 90]?");
		Question when = new Question("When did a Chilean spokesman say that [more than 1,000 prisoners might benefit from the modifications] to the bill approved in [Chile]?");
		
		System.out.println(who.getType());
		System.out.println(who.getText());
		System.out.println(who.getPlace());
		System.out.println(who.getTime());
		System.out.println("");
		
		System.out.println(what.getType());
		System.out.println(what.getText());
		System.out.println(what.getPlace());
		System.out.println(what.getTime());
		System.out.println("");
		
		System.out.println(where.getType());
		System.out.println(where.getText());
		System.out.println(where.getPlace());
		System.out.println(where.getTime());
		System.out.println("");
		
		System.out.println(when.getType());
		System.out.println(when.getText());
		System.out.println(when.getPlace());
		System.out.println(when.getTime());
	}
}
