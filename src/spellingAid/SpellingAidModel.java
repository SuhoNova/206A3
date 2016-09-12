package spellingAid;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SpellingAidModel {
	private Festival _voice = new Festival("American");
	private int _quizLevel = 1;
	private String _quizType = "Normal";
	private WordList _wordList;
	
	private final int MAX_ATTEMPTS = 2;
	private int _quizLength = 10;
	private final static int _DEFAULT_QUIZ_LENGTH =10;
	private int _nWords;
	private String _attempt;
	private String _word;
	private int _nWordsCount=0;
	private int _nAttempts=0;

	public void startQuiz(){
		_wordList = new WordList(_quizType, _quizLevel);
		if(_wordList.size() < _quizLength){
			_nWords = _wordList.size();
		}
		else{
			_nWords = _quizLength;
		}
		_nWordsCount = 0;
		continueQuiz();
	}

	public void quizAttempt(String attempt) {
		_attempt = attempt;
		_nAttempts++;
		if(_attempt.equalsIgnoreCase(_word)){
			_voice.speakIt("Correct");
			System.out.println("Correct");
			if(_nAttempts == 1){
				FileManager fm = new FileManager();
				fm.handleQuizzedWords(_word, ".mastered");
			}
			else{
				FileManager fm = new FileManager();
				fm.handleQuizzedWords(_word, ".faulted");
			}
			if(_nWordsCount>=_nWords){
			}
			else{
				continueQuiz();
			}
		}
		else{
			if(_nAttempts < MAX_ATTEMPTS){
				_voice.speakIt("Incorrect, try once more..... "+_word+"..... "+_word);
				System.out.println("Incorrect, try once more..... "+_word+"..... "+_word);
			}
			else{
				FileManager fm = new FileManager();
				fm.handleQuizzedWords(_word, ".failed");
				_voice.speakIt("Incorrect");
				System.out.println("Incorrect");
				if(_nWordsCount>=_nWords){
				}
				else{
					continueQuiz();
				}

			}
		}
	}
	private void continueQuiz(){
		_nWordsCount++;
		_word = _wordList.getWord();
		_nAttempts = 0;
		//_voice.speakIt("Spell word "+_nWordsCount+" of word "+_nWords);
		_voice.speakIt("Please spell the word: "+_word+"...... "+_word);
		System.out.println("Please spell the word: "+_word+"...... "+_word);
	}
	
	public void setQuizLevel(int level) {
		_quizLevel = level;
		System.out.println(_quizLevel);
	}
	public void setQuizType(String type) {
		_quizType = type;
		System.out.println(_quizType);
	}
	public void setVoiceType(String type) {
		_voice.changeVoice(type);
		System.out.println(type);
	}
	public int getQuizLevel() {
		return _quizLevel;
	}
	public int getWordCount(){
		return _nWordsCount;
	}

}
