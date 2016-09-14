package spellingAid;

import java.util.ArrayList;

public class SpellingAidModel {
	private Festival _voice = new Festival("American");
	private FileManager _fm = new FileManager();
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
	private int _nCorrect=0;
	private boolean _isQuizEnded;

	//options logic
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

	//---------------------------------------------------------------------------------------
	//Quiz logic
	public void startQuiz(){
		_wordList = new WordList(_quizType, _quizLevel);
		_fm = new FileManager();
		if(_wordList.size() < _quizLength){
			_nWords = _wordList.size();
		}
		else{
			_nWords = _quizLength;
		}
		_nWordsCount = 0;
		_isQuizEnded = false;
		_nCorrect=0;
		if(_nWords > 0){
			continueQuiz();
		}
	}

	public void quizAttempt(String attempt) {
		_attempt = attempt;
		_nAttempts++;
		if(_attempt.equalsIgnoreCase(_word)){
			_nCorrect++;
			_fm.updateAccuracyRatings(_quizLevel, true);
			_voice.speakIt("Correct");
			System.out.println("Correct");
			if(_nAttempts == 1){
				_fm.handleQuizzedWords(_word, ".mastered");
			}
			else{
				_fm.handleQuizzedWords(_word, ".faulted");
			}
			if(_nWordsCount>=_nWords){
				_isQuizEnded = true;
			}
			else{
				continueQuiz();
			}
		}
		else{
			_fm.updateAccuracyRatings(_quizLevel, false);
			if(_nAttempts < MAX_ATTEMPTS){
				_voice.speakIt("Incorrect, try once more..... "+_word+"..... "+_word);
				System.out.println("Incorrect, try once more..... "+_word+"..... "+_word);
			}
			else{
				_fm.handleQuizzedWords(_word, ".failed");
				_voice.speakIt("Incorrect");
				System.out.println("Incorrect");
				if(_nWordsCount>=_nWords){
					_isQuizEnded = true;
				}
				else{
					continueQuiz();
				}
			}
		}
	}
	public boolean isQuizEnded() {
		return _isQuizEnded;
	}

	private void continueQuiz(){
		_nWordsCount++;
		_word = _wordList.getWord();
		_nAttempts = 0;
		_voice.speakIt("Please spell the word: "+_word+"...... "+_word);
		System.out.println("Please spell the word: "+_word+"...... "+_word);
	}
	
	public int getQuizLevel() {
		return _quizLevel;
	}
	public int getWordCount(){
		return _nWordsCount;
	}
	public int getWordListSize() {
		return _nWords;
	}
	
	//------------------------------------------------------------------------------------------------------
	//Stats logic
	public ArrayList<String> getAccuracyRating(){
		return _fm.getAccuracyRating();
	}
	public void clearStats() {
		_fm.clearStats();
	}
}