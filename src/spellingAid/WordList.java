package spellingAid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

public class WordList {
	private String _source = "NZCER-spelling-lists.txt";
	private ArrayList<String> _wordList;
	private int _level;
	private String _path=null;

	public WordList(String quizType, int lvl){
		//create necessary files on startup
		try {
			_path = SpellingAid.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		_path = _path.replace("Voxspell_prototyype.jar", "");

		_level = lvl;
		if(quizType.equals("Normal")){
			_source = _path+"NZCER-spelling-lists.txt";
			createNormalWordList();
		}
		else if(quizType.equals("Review")){
			_source = _path+".failed";
			createReviewWordList();
		}
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

	//creates the list of possible words that can be used for review quiz
	private void createReviewWordList(){
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

	//creates the list of possible words that can be used for spelling quiz
	private void createNormalWordList() {
		try {
			_wordList = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(_source));
			String word;
			while((word = br.readLine())!= null){
				if(word.equals("%Level "+_level)){
					break;
				}
			}
			while((word = br.readLine())!= null){
				if(word.charAt(0)=='%'){
					break;
				}
				else{
					word = word.trim();
					if(word.length()>0){
						_wordList.add(word);
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}