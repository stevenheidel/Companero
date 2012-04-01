package question;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question
{
	private String type;
	
	private String text;
		
	private String place;
	
	private String time;
	
	public Question(String input)
	{
		input = input.toUpperCase();
		
		type = input.split(" ")[0];
		
		Matcher m = Pattern.compile("\\[(.*?)\\]").matcher(input);
		
		m.find();
		text = m.group(1);
				
		m.find();
		if (type.equals("WHO") || type.equals("WHAT"))
		{
			place = m.group(1);
			m.find();
			time = m.group(1);
		}
		else if (type.equals("WHERE"))
		{
			place = null;
			time = m.group(1);
		}
		else if (type.equals("WHEN"))
		{
			place = m.group(1);
			time = null;
		}
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getPlace()
	{
		return place;
	}
	
	public String getTime()
	{
		return time;
	}
	
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
		
		System.out.println(what.getType());
		System.out.println(what.getText());
		System.out.println(what.getPlace());
		System.out.println(what.getTime());
		
		System.out.println(where.getType());
		System.out.println(where.getText());
		System.out.println(where.getPlace());
		System.out.println(where.getTime());
		
		System.out.println(when.getType());
		System.out.println(when.getText());
		System.out.println(when.getPlace());
		System.out.println(when.getTime());
	}
}
