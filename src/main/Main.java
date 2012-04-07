package main;

import java.io.IOException;
import java.util.Scanner;

import entities.Corpus;
import entities.Question;

import solver.Solver;
import utilities.FileReader;

public class Main 
{
	public static void main(String[] args)
	{	
		Corpus corpus = null;
		try 
		{
			corpus = new Corpus(FileReader.convertToString("corpus.txt"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(System.in);
		
		while (scanner.hasNextLine())
		{
			Question question = new Question(scanner.nextLine());
			
			System.out.println("\t" + Solver.solve(question, corpus));
		}
	}
}
