package spellingAid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {
	public FileManager(){
		//create necessary files on startup
		PrintWriter outputFile;
		try{
			outputFile = new PrintWriter(new FileWriter(".mastered", true));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".faulted", true));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".failed", true));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".stats", true));
			outputFile.close();
			File f = new File(".accuracy");
			if(!f.exists()){
				outputFile = new PrintWriter(new FileWriter(".accuracy", true));
				for(int i=0; i<11; i++){
					outputFile.println("0-0");
				}
				outputFile.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * update the word with it's specified word type (".mastered",".faulted",".failed") in the necessary files
	 * @param word
	 * @param wordType
	 */
	public void handleQuizzedWords(String word, String wordType) {
		try {
			//appends the word to the necessary file ("mastered", "faulted", "failed") 
			if(!isInFile(word,wordType)){
				PrintWriter file = new PrintWriter(new FileWriter(wordType, true));
				file.println(word);
				file.close();
			}
			//removes the word of the necessary file ("mastered","faulted","failed")
			if(wordType.equals(".mastered")){
				updateQuizzedWords(word, ".failed");
				updateQuizzedWords(word, ".faulted");
			}
			else if(wordType.equals(".failed")){
				updateQuizzedWords(word, ".mastered");
				updateQuizzedWords(word, ".faulted");
			}
			else if(wordType.equals(".faulted")){
				updateQuizzedWords(word, ".mastered");
				updateQuizzedWords(word, ".failed");
			}

			//update "stats" file
			updateStatistics(word, wordType);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the accuracy rating of the specified level depending if whether or not the attempt was correct
	 * @param level
	 * @param isCorrect
	 */
	public void updateAccuracyRatings(int level, boolean isCorrect){
		try {
			ArrayList<String> correctnessRatio = new ArrayList<String>(); 
			BufferedReader inputFile = new BufferedReader(new FileReader(".accuracy"));
			String line;
			while((line = inputFile.readLine())!=null){
				correctnessRatio.add(line);
			}
			inputFile.close();
			//update the ratios
			String[] ratio = correctnessRatio.get(level-1).split("-");
			ratio[1] = Integer.parseInt(ratio[1])+1+""; //+1 to number of attempts
			if(isCorrect){
				ratio[0] = Integer.parseInt(ratio[0])+1+""; //+1 to number of correct attempts is the answer was correct
			}
			correctnessRatio.set(level-1, ratio[0]+"-"+ratio[1]);
			//rewrite accuracy file
			PrintWriter outputFile = new PrintWriter(new FileWriter(".accuracy", false));
			for(String s: correctnessRatio){
				outputFile.println(s);
			}
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the accuracy ratings of all the quiz levels
	 * @return
	 */
	public ArrayList<String> getAccuracyRating(){
		ArrayList<String> correctnessRatio = new ArrayList<String>();
		//read accuracy file
		try { 
			BufferedReader inputFile = new BufferedReader(new FileReader(".accuracy"));
			String line;
			while((line = inputFile.readLine())!=null){
				correctnessRatio.add(line);
			}
			inputFile.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		//get accuracy ratings
		ArrayList<String> accuracyRatings = new ArrayList<String>();
		for(String r: correctnessRatio){
			String[] ratio = r.split("-");
			//if total attempts is 0, make value "-"
			if(ratio[1].equals("0")){
				accuracyRatings.add("-");
			}

			else{
				//calculate accuracy rating
				double rating = Double.parseDouble(ratio[0]+".0") / Double.parseDouble(ratio[1]+".0")*100;
				rating = Math.round(rating*100.0)/100.0;
				accuracyRatings.add(rating+"%");
			}
		}
		return accuracyRatings;
	}

	/**
	 * Updates the specified word and word type in the stats file
	 * @param word
	 * @param wordType
	 * @throws IOException
	 */
	private void updateStatistics(String word, String wordType) throws IOException {
		ArrayList<String> statsList = new ArrayList<String>();
		BufferedReader inputFile = new BufferedReader(new FileReader(".stats"));
		//store "stats" contents into an ArrayList
		String line;
		while((line = inputFile.readLine())!=null){
			if(line.charAt(0)=='%'){
				break;
			}
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
		if(wordType.equals(".mastered")){
			int value = Integer.parseInt(statsList.get(i+1));
			value++;
			statsList.set(i+1, value+"");
		}
		else if(wordType.equals(".faulted")){
			int value = Integer.parseInt(statsList.get(i+2));
			value++;
			statsList.set(i+2, value+"");
		}
		else if(wordType.equals(".failed")){
			int value = Integer.parseInt(statsList.get(i+3));
			value++;
			statsList.set(i+3, value+"");
		}
		//rewrite "stats" file
		PrintWriter outputFile = new PrintWriter(new FileWriter(".stats", false));
		for(int j=0; j < statsList.size()-3;j+=4){
			outputFile.println(statsList.get(j)+"-"+statsList.get(j+1)+"-"+statsList.get(j+2)+"-"+statsList.get(j+3));
		}
		outputFile.close();
	}
	/**
	 * Checks if the word is a line in the specified source file
	 * @param word
	 * @param source
	 * @return
	 * @throws IOException
	 */
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

	/**
	 * Removes a line in a file that is the specified word 
	 * @param word
	 * @param fileToUpdate
	 * @throws IOException
	 */
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

	/**
	 * Clears all files that store quiz statistics
	 */
	public void clearStats() {
		PrintWriter outputFile;
		try{
			outputFile = new PrintWriter(new FileWriter(".mastered", false));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".faulted", false));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".failed", false));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".stats", false));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".accuracy", false));
			for(int i=0; i<11; i++){
				outputFile.println("0-0");
			}
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the statistics of all attempted words in the order of attempt
	 * @return
	 */
	public String getStats() {
		String stats = "";
		try {
			stats+="(in order of attempt)\n";
			stats+="----------------------------------------------\n";
			stats+="word-mastered-faulted-failed\n";
			stats+="----------------------------------------------\n";
			BufferedReader inputFile = new BufferedReader(new FileReader(".stats"));
			String line;
			while((line=inputFile.readLine())!= null){
				stats+=line+"\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stats;
	}
}
