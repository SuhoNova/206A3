package spellingAid;

public class SpellingAidModel {
	private Festival _voice = new Festival("American");
	private FileManager _fm = new FileManager();
	private int _quizLevel = 1;
	private String _quizType = "Normal";
	private WordList _wordList;

	private final int MAX_ATTEMPTS = 2;
	private int _quizLength = 10;
	private int _nWords;
	private String _attempt;
	private String _word;
	private int _nWordsCount=0;
	private int _nAttempts=0;
	private int _nCorrect=0;
	private boolean _isQuizEnded;
	private int _nTotalAttempts = 0;
	private boolean _isFaulted = false;

	//options logic
	/**
	 * Set the starting level for the quiz
	 * @param level
	 */
	public void setQuizLevel(int level) {
		_quizLevel = level;
		System.out.println(_quizLevel);
	}
	/**
	 * Set the type of quiz that will be taken
	 * @param type
	 */
	public void setQuizType(String type) {
		_quizType = type;
		System.out.println(_quizType);
	}
	/**
	 * Set the voice type used for speech
	 * @param type
	 */
	public void setVoiceType(String type) {
		_voice.changeVoice(type);
		System.out.println(type);
	}

	//---------------------------------------------------------------------------------------
	//Quiz logic
	/**
	 * Starts a new quiz
	 */
	public void startQuiz(){
		//instantiate necessary objects
		_wordList = new WordList(_quizType, _quizLevel);
		_fm = new FileManager();
		//determine quiz length
		if(_wordList.size() < _quizLength){
			_nWords = _wordList.size();
		}
		else{
			_nWords = _quizLength;
		}
		//set quiz variables
		_nWordsCount = 0;
		_isQuizEnded = false;
		_nCorrect=0;
		_nTotalAttempts=0;
		if(_nWords > 0){
			continueQuiz();
		}
	}

	/**
	 * Determines if the attempt was correct or incorrect
	 * @param attempt
	 */
	public void quizAttempt(String attempt) {
		_attempt = attempt;
		_nAttempts++;
		_nTotalAttempts++;
		//if attempt is correct
		if(_attempt.equalsIgnoreCase(_word)){
			
			_nCorrect++;
			if(_quizType.equals("Normal")){
				_fm.updateAccuracyRatings(_quizLevel, true);
			}
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
		//if attempt is incorrect
		else{
			if(_quizType.equals("Normal")){
				_fm.updateAccuracyRatings(_quizLevel, false);
			}
			if(_nAttempts < MAX_ATTEMPTS){
				_isFaulted=true;
				_voice.speakIt("Incorrect, try once more");
				//ice.speakIt(" try once more ");//+_word+" ..... "+_word);
				_voice.speakWord(_word);
				
				System.out.println("Incorrect, try once more ..... "+_word+" ..... "+_word);
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
	/**
	 * Returns whether or not the quiz has ended
	 * @return
	 */
	public boolean isQuizEnded() {
		return _isQuizEnded;
	}
	/**
	 * Continues the current quiz
	 */
	private void continueQuiz(){
		_nWordsCount++;
		_isFaulted = false;
		_word = _wordList.getWord(); //get new word
		_nAttempts = 0;
		_voice.speakIt("Please spell the word: ");
		_voice.speakWord(_word);
		System.out.println("Please spell the word: "+_word+"...... "+_word);
	}
	/**
	 * Returns the quiz level that hasbeen set
	 * @return
	 */
	public int getQuizLevel() {
		return _quizLevel;
	}
	/**
	 * Returns the current word number in the quiz
	 * @return
	 */
	public int getWordCount(){
		return _nWordsCount;
	}
	/**
	 * returns the number of words in the wordlist for this quiz
	 * @return
	 */
	public int getWordListSize() {
		return _nWords;
	}
	/**
	 * Returns the type of quiz that has been set
	 * @return
	 */
	public String getQuizType(){
		return _quizType;
	}
	/**
	 * Returns the number of correct attempts
	 * @return
	 */
	public int getCorrectAttempts(){
		return _nCorrect;
	}
	/**
	 * Speaks the word to be spelled
	 */
	public void hearWord(){
		_voice.speakWord(_word,true);
	}
	/**
	 * Returns the spelling accuracy of the current quiz to 2 decimal places
	 * @return
	 */
	public double getSessionAccuracy(){
		double rating = (_nCorrect+0.0) / (_nTotalAttempts+0.0) *100;
		rating = Math.round(rating*100.0)/100.0;
		return rating;
	}
	/**
	 * Returns if the word spelling is accessible to the user
	 */
	public boolean isSpellEnabled(){
		return _isFaulted&&_quizType.equalsIgnoreCase("review");
	}
	public void _spellWord() {
		_voice.speakLetter(_word);
	}
}