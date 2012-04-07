package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import entities.Corpus;
import entities.Question;

import solver.Solver;
import utilities.FileReader;

public class Main 
{
	private static final boolean TESTING = false;
	
	public static void main(String[] args)
	{
		System.out.println("Processing the corpus...");
		
		Corpus corpus = new Corpus(FileReader.convertToString("corpus.txt"));
		
		System.out.println("\t\t\t...ready for question answering!\n");
		
		Scanner scanner = null;
		if (TESTING)
		{
			try 
			{
				scanner = new Scanner(new File("TestingQuestions"));
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		else
			scanner = new Scanner(System.in);
		
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (! line.equals(""))
			{
				Question question = new Question(line);
				
				System.out.println("[QUESTION] " + line);
				System.out.println(Solver.solve(question, corpus));
			}
		}
	}
}
