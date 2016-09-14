package viewAndController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import spellingAid.SpellingAidModel;


public class SpellingAidController{
	private SpellingAidView _view;
	private SpellingAidModel _model;

	ActionListener _mainMenuListener = new MainMenuListener();
	ActionListener _optionsListener = new OptionsListener();
	ActionListener _quizListener = new QuizListener();
	ActionListener _statsListener = new StatsListener();

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
		//add listeners for stats page
		_view._statsMenuButton.addActionListener(_statsListener);
		_view._clearStatsButton.addActionListener(_statsListener);
	}
	
	private void setAccuracyRatings(){
		ArrayList<String> accuracyRatings = _model.getAccuracyRating();
		for(int i=0; i < _view._accuracyRatingQuizLabels.size();i++){
			String rating = accuracyRatings.get(i);
			_view._accuracyRatingQuizLabels.get(i).setText(rating);
			_view._accuracyRatingOptionsLabels.get(i).setText(rating);
		}
	}

	private class MainMenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == _view._startQuizButton){
				_model.startQuiz();
				if(_model.getWordListSize()>0){
					_view._mainLayout.show(_view._mainPanel, "quizPanel");
					_view._wordCountLabel.setText(_model.getWordCount()+"/"+_model.getWordListSize());
					_view._levelLabel.setText(_model.getQuizLevel()+"");
				}else{
					JOptionPane.showMessageDialog(null, "No words to test! Please change settings in the options menu");
				}
			}
			else if(e.getSource() == _view._viewStatsButton){
				_view._mainLayout.show(_view._mainPanel, "statsPanel");
			}
			else if(e.getSource() == _view._optionsButton){
				_view._mainLayout.show(_view._mainPanel, "optionsPanel");
			}
			else if(e.getSource() == _view._quitButton){
				System.exit(0);
			}
			setAccuracyRatings();
		}
	}

	private class OptionsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == _view._quizType){
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String option = (String)cb.getSelectedItem();
				_model.setQuizType(option);
				if(option.equals("Review")){
					JOptionPane.showMessageDialog(null, "Review quiz selected. Quiz level option disabled");
				}
			}
			else if(e.getSource() == _view._quizLevel){
				JComboBox<Integer> cb = (JComboBox<Integer>)e.getSource();
				Integer option = (Integer)cb.getSelectedItem();
				_model.setQuizLevel(option);
			}
			else if(e.getSource() == _view._voiceType){
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String option = (String)cb.getSelectedItem();
				_model.setVoiceType(option);
			}
			else if(e.getSource() == _view._optionsMenuButton){
				_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
			}
			setAccuracyRatings();
		}
	}

	private class QuizListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == _view._input){
				if(!_model.isQuizEnded()){
					_model.quizAttempt(_view._input.getText());
					_view._wordCountLabel.setText(_model.getWordCount()+"/"+_model.getWordListSize());
				}
				_view._input.setText("");
			}
			else if(e.getSource() == _view._quizMenuButton){
				_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
			}
			setAccuracyRatings();
		}
	}

	private class StatsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == _view._statsMenuButton){
				_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
			}
			else if(e.getSource() == _view._clearStatsButton){
				_model.clearStats();
			}
			setAccuracyRatings();
		}

	}
}