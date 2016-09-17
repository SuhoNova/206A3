package viewAndController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import spellingAid.FileManager;
import spellingAid.SpellingAidModel;
import spellingAid.VideoReward;


public class SpellingAidController{
	private SpellingAidView _view;
	private SpellingAidModel _model;
	private FileManager _fm = new FileManager();

	private ActionListener _mainMenuListener = new MainMenuListener();
	private ActionListener _optionsListener = new OptionsListener();
	private ActionListener _quizListener = new QuizListener();
	private ActionListener _statsListener = new StatsListener();

	public SpellingAidController(SpellingAidView view, SpellingAidModel model){
		_view = view;
		_model = model;
		//add listeners for menu page
		_view._startQuizButton.addActionListener(_mainMenuListener);
		_view._quitButton.addActionListener(_mainMenuListener);
		_view._viewStatsButton.addActionListener(_mainMenuListener);
		_view._optionsButton.addActionListener(_mainMenuListener);
		//add listeners for options page
		_view._optionsMenuButton.addActionListener(_optionsListener);
		_view._quizType.addActionListener(_optionsListener);
		_view._quizLevel.addActionListener(_optionsListener);
		_view._voiceType.addActionListener(_optionsListener);
		//add listeners for quiz page
		_view._quizMenuButton.addActionListener(_quizListener);
		_view._input.addActionListener(_quizListener);
		_view._currentLevelButton.addActionListener(_quizListener);
		_view._nextLevelButton.addActionListener(_quizListener);
		_view._playVideoButton.addActionListener(_quizListener);
		_view._hearWordButton.addActionListener(_quizListener);
		//add listeners for stats page
		_view._statsMenuButton.addActionListener(_statsListener);
		_view._clearStatsButton.addActionListener(_statsListener);
	}

	/**
	 * Sets the accuracy rating labels
	 */
	private void setAccuracyRatings(){
		ArrayList<String> accuracyRatings = _fm.getAccuracyRating();
		for(int i=0; i < _view._accuracyRatingQuizLabels.size();i++){
			String rating = accuracyRatings.get(i);
			_view._accuracyRatingQuizLabels.get(i).setText(rating);
			_view._accuracyRatingStatsLabels.get(i).setText(rating);
		}
	}
	/**
	 * Updates gui components and starts a new quiz
	 */
	private void startQuiz(){
		_model.startQuiz();
		if(_model.getWordListSize()>0){
			_view._mainLayout.show(_view._mainPanel, "quizPanel");
		}
		else{
			JOptionPane.showMessageDialog(null, "No words to test! Please change settings in the options menu");
			_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
		}
		_view._wordCountLabel.setText(_model.getWordCount()+"/"+_model.getWordListSize());
		_view._quizEndPanel.setVisible(false);
		_view._nextLevelButton.setVisible(false);
		_view._playVideoButton.setVisible(false);
		if(_model.getQuizType().equals("Review")){
			_view._levelLabel.setText("Not available");
		}else{
			_view._levelLabel.setText(_model.getQuizLevel()+"");
		}
	}
	private void endQuiz(){
		_view._quizEndPanel.setVisible(true);
		_view._sessionAccuracyLabel.setText(_model.getSessionAccuracy()+"%");
		_view._sessionCorrectnessLabel.setText(_model.getCorrectAttempts()+"/"+_model.getWordListSize());
		if(_model.getCorrectAttempts() >= 9){
			int lvl = _model.getQuizLevel();
			if(lvl+1 > 11){
				_view._quizEndMessageLabel.setText("Highest level mastered!");
				_view._currentLevelButton.setText("New Quiz");
			}
			else{
				_view._quizEndMessageLabel.setText("Level mastered! Go to next level?");
				_view._currentLevelButton.setText("Same Level");
				_view._nextLevelButton.setVisible(true);
				_view._playVideoButton.setVisible(true);
			}
		}
		else{
			_view._quizEndMessageLabel.setText("Start new quiz?");
			_view._currentLevelButton.setText("New quiz");
		}
	}
	/**
	 * ActionListener for the menu page
	 * @author gillon
	 *
	 */
	private class MainMenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//logic for clicking startQuizButton
			if(e.getSource() == _view._startQuizButton){
				startQuiz();
			}
			//Logic for clicking view stats button
			else if(e.getSource() == _view._viewStatsButton){
				_view._statsTextArea.setText(_fm.getStats());
				_view._mainLayout.show(_view._mainPanel, "statsPanel");
			}
			//Logic for clicking options button
			else if(e.getSource() == _view._optionsButton){
				_view._mainLayout.show(_view._mainPanel, "optionsPanel");
			}
			//Logic for clicking the quit button
			else if(e.getSource() == _view._quitButton){
				System.exit(0);
			}
			setAccuracyRatings(); //update accuracy ratings
		}
	}
	/**
	 * ActionListener for the options page
	 * @author gillon
	 *
	 */
	private class OptionsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//logic for clicking quiz type combo box
			if(e.getSource() == _view._quizType){
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String option = (String)cb.getSelectedItem();
				_model.setQuizType(option);
				if(option.equals("Review")){
					_view._quizLevel.setVisible(false);
				}
				else{
					_view._quizLevel.setVisible(true);
				}
				_view._quizSessionPanel.setBorder(BorderFactory.createTitledBorder(option+" Quiz"));
			}
			//logic for clicking quizLevel combobox
			else if(e.getSource() == _view._quizLevel){
				JComboBox<Integer> cb = (JComboBox<Integer>)e.getSource();
				Integer option = (Integer)cb.getSelectedItem();
				_model.setQuizLevel(option);
			}
			//Logic for clicking voice type combobox
			else if(e.getSource() == _view._voiceType){
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String option = (String)cb.getSelectedItem();
				_model.setVoiceType(option);
			}
			//logic for clicking menu button
			else if(e.getSource() == _view._optionsMenuButton){
				_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
			}
			setAccuracyRatings(); //update accuracy ratings
		}
	}
	/**
	 * ActionListener for the quiz page
	 * @author gillon
	 *
	 */
	private class QuizListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//Logic for pressing enter on the input textfield
			if(e.getSource() == _view._input){
				if(!_model.isQuizEnded()){
					_model.quizAttempt(_view._input.getText());
					_view._wordCountLabel.setText(_model.getWordCount()+"/"+_model.getWordListSize());
				}
				_view._input.setText("");
			}
			//logic for clicking the end quiz button
			else if(e.getSource() == _view._quizMenuButton){
				_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
			}
			//logic for clicking hear word button
			else if(e.getSource() == _view._hearWordButton){
				_model.hearWord();
			}
			//if faulted and review quiz
			if(_model.isSpellEnabled()){
				_view._spellWordButton.setVisible(true);
			}
			else{
				_view._spellWordButton.setVisible(false);
			}
			//logic for clicking the spell word button
			if(e.getSource()==_view._spellWordButton){
				_model._spellWord();
			}

			//end of quiz
			if(_model.isQuizEnded()){
				endQuiz();
				//listener logic for end quiz buttons
				if(e.getSource() == _view._currentLevelButton){
					startQuiz();
				}
				else if(e.getSource() == _view._nextLevelButton){
					int lvl = _model.getQuizLevel();
					_model.setQuizLevel(lvl+1);
					startQuiz();
				}
				if(e.getSource() == _view._playVideoButton){
					VideoReward vr = new VideoReward();
				}
			}
			setAccuracyRatings(); //update accuracy ratings
		}
	}

	/**
	 * ActionListener fo the stats page
	 * @author gillon
	 *
	 */
	private class StatsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			//logic for clicking the menu button
			if(e.getSource() == _view._statsMenuButton){
				_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
			}
			//logic for clicking the clear stats button
			else if(e.getSource() == _view._clearStatsButton){
				_fm.clearStats();
			}
			_view._statsTextArea.setText(_fm.getStats()); //update the stats text area
			setAccuracyRatings();//update accuracy ratings
		}

	}
}