package spellingAid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WordList {
	private String _source = "NZCER-spelling-lists.txt";
	private ArrayList<String> _wordList;
	private int _level;
	
	public WordList(String quizType, int lvl){
		if(quizType.equals("Normal")){
			_source = "NZCER-spelling-lists.txt";
		}
		else if(quizType.equals("Review")){
			_source = ".failed";
		}
		_level = lvl;
		createWordList();
	}
	
	public int size(){
		return _wordList.size();
	}
	
	//sets the word being tested
	public String getWord() {
		Random r = new Random();
		int pos = r.nextInt(_wordList.size());
		String word = _wordList.get(pos);
		_wordList.remove(pos);
		return word;
	}
	

	//creates the list of possible words that can be used for the quiz
	private void createWordList(){
		try {
			_wordList = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(_source));
			String word;
			while((word = br.readLine())!= null){
				word = word.trim();
				if(word.length()>0){
					_wordList.add(word);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}