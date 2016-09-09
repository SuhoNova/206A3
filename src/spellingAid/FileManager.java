package spellingAid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {
	
	//Performs the logic for updating the necessary files
	public void handleQuizzedWords(String word, String fileName) {
		try {
			//appends the word to the necessary file ("mastered", "faulted", "failed") 
			if(!isInFile(word,fileName)){
				PrintWriter file = new PrintWriter(new FileWriter(fileName, true));
				file.println(word);
				file.close();
			}
			//removes the word of the necessary file ("mastered","faulted","failed")
			if(fileName.equals("mastered")){
				updateQuizzedWords(word, "failed");
				updateQuizzedWords(word, "faulted");
			}
			else if(fileName.equals("failed")){
				updateQuizzedWords(word, "mastered");
				updateQuizzedWords(word, "faulted");
			}
			else if(fileName.equals("faulted")){
				updateQuizzedWords(word, "mastered");
				updateQuizzedWords(word, "failed");
			}

			//update "stats" file
			updateStatistics(word, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//updates the word statistics in the "stats" file
	private void updateStatistics(String word, String wordType) throws IOException {
		ArrayList<String> statsList = new ArrayList<String>();
		BufferedReader inputFile = new BufferedReader(new FileReader("stats"));
		//store "stats" contents into an ArrayList
		String line;
		while((line = inputFile.readLine())!=null){
			line=line.trim();
			String[] stats = line.split("-");
			statsList.addAll(Arrays.asList(stats));
		}
		//add stats of word if word isn't in "stats"
		if(!statsList.contains(word)){
			statsList.add(word);
			statsList.add("0");
			statsList.add("0");
			statsList.add("0");
		}
		//update stats values
		int i = statsList.indexOf(word);
		if(wordType.equals("mastered")){
			int value = Integer.parseInt(statsList.get(i+1));
			value++;
			statsList.set(i+1, value+"");
		}
		else if(wordType.equals("faulted")){
			int value = Integer.parseInt(statsList.get(i+2));
			value++;
			statsList.set(i+2, value+"");
		}
		else if(wordType.equals("failed")){
			int value = Integer.parseInt(statsList.get(i+3));
			value++;
			statsList.set(i+3, value+"");
		}
		//rewrite "stats" file
		PrintWriter outputFile = new PrintWriter(new FileWriter("stats", false));
		for(int j=0; j < statsList.size()-3;j+=4){
			outputFile.println(statsList.get(j)+"-"+statsList.get(j+1)+"-"+statsList.get(j+2)+"-"+statsList.get(j+3));
		}
		outputFile.close();
	}
	//check is the String word is in the specified file
	private boolean isInFile(String word, String source) throws IOException{
		ArrayList<String> wordsList = new ArrayList<String>();
		BufferedReader inputFile = new BufferedReader(new FileReader(source));
		String line;
		while((line = inputFile.readLine())!=null){
			line=line.trim();
			wordsList.add(line);
		}
		inputFile.close();
		return wordsList.contains(word);
	}

	//removes the specified word from the file
	private void updateQuizzedWords(String word, String fileToUpdate) throws IOException{
		ArrayList<String> tempWordsList = new ArrayList<String>();
		BufferedReader inputFile = new BufferedReader(new FileReader(fileToUpdate));
		String line;
		while((line = inputFile.readLine())!=null){
			line=line.trim();
			if(!line.equalsIgnoreCase(word)){
				tempWordsList.add(line);
			}
		}
		inputFile.close();
		PrintWriter outputFile = new PrintWriter(new FileWriter(fileToUpdate, false));
		for(String s: tempWordsList){
			outputFile.println(s);
		}
		outputFile.close();
	}
}
