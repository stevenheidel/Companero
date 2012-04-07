package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class Answer 
{
	
	private static final int ANSWER_LENGTH = 30;
	
	private static final int TERMINAL_WIDTH = 80;
	
	private static HashMap<String, Double> answers;
	
	public Answer()
	{
		answers = new HashMap<String, Double>();
	}
	
	public void add(String answer)
	{
		answers.put(answer, 0.0);
	}
	
	public void setWeight(String answer, double weight)
	{
		answers.put(answer, weight);
	}
	
	public double getWeight(String answer)
	{
		return answers.get(answer);
	}
	
	private HashMap<String, Double> sortHashMap(HashMap<String, Double> input)
	{
	    Map<String, Double> tempMap = new HashMap<String, Double>();
	    
	    for (String wsState : input.keySet())
	    {
	        tempMap.put(wsState,input.get(wsState));
	    }

	    List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
	    List<Double> mapValues = new ArrayList<Double>(tempMap.values());
	    HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
	    TreeSet<Double> sortedSet = new TreeSet<Double>(mapValues);
	    Object[] sortedArray = sortedSet.toArray();
	    
	    int size = sortedArray.length;
	    
	    for (int i = size - 1; i >= 0; i--)
	    {
	        sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), 
	                      (Double) sortedArray[i]);
	    }
	    
	    return sortedMap;
	}
	
	public String toString()
	{
		if (answers.size() == 0)
			return "No Answers Found";
		
		String toReturn = "";
		
		for (Entry<String, Double> answer : sortHashMap(answers).entrySet())
		{
			System.out.format("%" + ANSWER_LENGTH + "s", answer.getKey());
			
			if (answer.getKey().length() > ANSWER_LENGTH)
			{
				System.out.print("\n");
				System.out.format("%" + ANSWER_LENGTH + "s", " ");
			}
			
			System.out.print(" |");
			
			for (int i = 0; i < (TERMINAL_WIDTH - ANSWER_LENGTH) * answer.getValue() - 2; i++)
				System.out.print("=");
			
			System.out.print("\n");
		}
		
		return toReturn;
	}
	
	public static void main(String[] args)
	{
		Answer blank = new Answer();
		System.out.println(blank);
		
		Answer answer = new Answer();
		
		answer.add("TEST 1");
		answer.setWeight("TEST 1", 0.27);
		
		answer.add("TEST WHERE THE LENGTH OF THE STRING IS MUCH LONGER THAN 30 CHARACTERS");
		answer.setWeight("TEST WHERE THE LENGTH OF THE STRING IS MUCH LONGER THAN 30 CHARACTERS", 1);
		
		answer.add("TEST 2");
		answer.setWeight("TEST 2", 0.78);
		
		System.out.println(answer);
	}
}