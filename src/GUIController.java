package spellingAid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JComboBox;

public class GUIController implements Controller{
	private GUIView _view;
	private GUIModel _model;

	MainMenuListener _mainMenuListener = new MainMenuListener();

	public GUIController(GUIView view, GUIModel model){
		_view = view;
		_model = model;

		_view.quiz().addActionListener(_mainMenuListener);
		_view.quit().addActionListener(_mainMenuListener);
		_view.stats().addActionListener(_mainMenuListener);
		_view.options().addActionListener(_mainMenuListener);
		
	}

	public class MainMenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == _view.quiz()){
				_view.mainLayout().show(_view.mainPanel(), "quizPanel");
			}
			else if(e.getSource() == _view.quit()){
				System.exit(0);
			}
			else if(e.getSource() == _view.options()){
				_view.mainLayout().show(_view.mainPanel(), "optionsPanel");
			}
		}

	}
}