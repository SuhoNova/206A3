package viewAndController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JComboBox;

import spellingAid.ReviewQuiz;
import spellingAid.SpellingAidModel;
import spellingAid.SpellingQuiz;

public class SpellingAidController{
	private SpellingAidView _view;
	private SpellingAidModel _model;

	ActionListener _mainMenuListener = new MainMenuListener();
	ActionListener _optionsListener = new OptionsListener();
	ActionListener _quizListener = new QuizListener();

	public SpellingAidController(SpellingAidView view, SpellingAidModel model){
		_view = view;
		_model = model;

		_view._startQuizButton.addActionListener(_mainMenuListener);
		_view._quitButton.addActionListener(_mainMenuListener);
		_view._viewStatsButton.addActionListener(_mainMenuListener);
		_view._optionsButton.addActionListener(_mainMenuListener);
		
		_view._optionsMenuButton.addActionListener(_optionsListener);
		_view._quizType.addActionListener(_optionsListener);
		_view._quizLevel.addActionListener(_optionsListener);
		_view._voiceType.addActionListener(_optionsListener);
		
		_view._quizMenuButton.addActionListener(_quizListener);
		_view._input.addActionListener(_quizListener);
	}

	private class MainMenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == _view._startQuizButton){
				_view._mainLayout.show(_view._mainPanel, "quizPanel");
				int lvl = _model.getQuizLevel();
				_view._levelLabel.setText(lvl+"");
				_model.startQuiz();
			}
			else if(e.getSource() == _view._viewStatsButton){
				//TODO
			}
			else if(e.getSource() == _view._optionsButton){
				_view._mainLayout.show(_view._mainPanel, "optionsPanel");
			}
			else if(e.getSource() == _view._quitButton){
				System.exit(0);
			}
		}
	}

	private class OptionsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == _view._quizType){
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String option = (String)cb.getSelectedItem();
				_model.setQuizType(option);
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
		}
	}
	
	private class QuizListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == _view._input){
				_model.quizAttempt(_view._input.getText());
				//_model.calculateAccuracy(_accuracyRating);
				_view._input.setText("");
			}
			else if(e.getSource() == _view._quizMenuButton){
				_view._mainLayout.show(_view._mainPanel, "mainMenuPanel");
			}
		}
	}
}