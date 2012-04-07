package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class Answer 
{
	
	private static final int ANSWER_LENGTH = 30;
	
	private static final int TERMINAL_WIDTH = 80;
	
	private static HashMap<String, Double> answers;
	
	public Answer()
	{
		answers = new HashMap<String, Double>();
	}
	
	public void add(String answer, double weight)
	{
		answers.put(answer, weight);
	}
		
	public double getWeight(String answer)
	{
		return answers.get(answer);
	}
		
	public LinkedHashMap<String, Double> sortHashMapByValues(HashMap<String, Double> original) {
	 	List<String> mapKeys = new ArrayList<String>(original.keySet());
		List<Double> mapValues = new ArrayList<Double>(original.values());
		Collections.sort(mapValues);	
		Collections.sort(mapKeys);	
	
		Collections.reverse(mapValues);
	
		LinkedHashMap<String, Double> sorted = new LinkedHashMap<String, Double>();
		Iterator<Double> valueIt = mapValues.iterator();
		
		while (valueIt.hasNext()) 
		{
			Double val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();
			while (keyIt.hasNext()) 
			{
				String key = keyIt.next();
				if (original.get(key).toString().equals(val.toString())) 
				{
					original.remove(key);
					mapKeys.remove(key);
					sorted.put(key, val);
					break;
				}
			}
		}
		return sorted;
	}
	
	public String toString()
	{
		if (answers.size() == 0)
			return "No Answers Found\n";
		
		if (answers.size() == 1)
			return answers.keySet().toArray(new String[1])[0] + "\n";
		
		String toReturn = "";
		
		for (Entry<String, Double> answer : sortHashMapByValues(answers).entrySet())
		{
			toReturn += String.format("%" + ANSWER_LENGTH + "s", answer.getKey());
			
			if (answer.getKey().length() > ANSWER_LENGTH)
			{
				toReturn += "\n";
				toReturn += String.format("%" + ANSWER_LENGTH + "s", " ");
			}
			
			toReturn += " |";
			
			for (int i = 0; i < (TERMINAL_WIDTH - ANSWER_LENGTH) * answer.getValue() - 2; i++)
				toReturn += "=";
			
			toReturn += "\n";
		}
		
		return toReturn;
	}
	
	public static void main(String[] args)
	{
		Answer blank = new Answer();
		System.out.println(blank);
		
		Answer single = new Answer();
		single.add("SINGLE ANSWER", 1);
		System.out.println(single);
		
		Answer answer = new Answer();
		answer.add("TEST 1", 0.27);
		answer.add("TEST WHERE THE LENGTH OF THE STRING IS MUCH LONGER THAN 30 CHARACTERS", 1);
		answer.add("TEST 2", 0.78);
		System.out.println(answer);
	}
}