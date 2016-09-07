package spellingAid;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUIView extends JFrame{

	private JPanel _mainPanel = new JPanel();
	private CardLayout _mainLayout = new CardLayout();
	private JPanel _mainMenuPanel = new JPanel(new GridLayout(5,1));
	private JPanel _quizPanel = new JPanel(new BorderLayout());
	private JPanel _statsPanel = new JPanel(new GridLayout());
	private JPanel _optionsPanel = new JPanel(new BorderLayout());
	
	private JButton _startQuizButton = new JButton("Start New Quiz");
	private JButton _viewStatsButton = new JButton("View Statistics");
	private JButton _optionsButton = new JButton("Options");
	private JButton _quitButton = new JButton("Quit");

	private JButton _quizMenuButton = new JButton("Back to menu");
	private JButton _statsMenuButton = new JButton("Back to menu");
	private JButton _optionsMenuButton = new JButton("Back to menu");
	
	private JLabel _wordCountLabel = new JLabel(" ");
	private JLabel _levelLabel = new JLabel(" ");
	private ArrayList<JLabel> _accuracyRatings = new ArrayList<JLabel>();
	private JTextField _input= new JTextField();
	
	private JComboBox<String> _quizType = new JComboBox<String>();
	private JComboBox<String> _quizLevel = new JComboBox<String>();
	private JComboBox<String> _voiceType = new JComboBox<String>();


	public GUIView(){
		//create SpellingAid window
		super("Spelling Aid");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(400,400);	
		setLocationRelativeTo(null);
		
		_mainPanel.setLayout(_mainLayout);
		setupMainPanel();
		setupOptionsPanel();
		setupQuizPanel();
		setupAccuracyRatings();
		setupMainMenuPanel();
		this.add(_mainPanel);
		_mainLayout.show(_mainPanel, "mainMenuPanel");
		setResizable(false);
		pack();
	}

	//sets up the main panel
	private void setupMainPanel() {
		_mainPanel.add(_quizPanel, "quizPanel");
		_mainPanel.add(_statsPanel , "statsPanel");
		_mainPanel.add(_optionsPanel, "optionsPanel");
		_mainPanel.add(_mainMenuPanel , "mainMenuPanel");
	}
	
	private void setupMainMenuPanel() {
		_mainMenuPanel.add(new JLabel("Welcome to Spelling Aid!\nsup"));
		_mainMenuPanel.add(_startQuizButton);
		_mainMenuPanel.add(_viewStatsButton);
		_mainMenuPanel.add(_optionsButton);
		_mainMenuPanel.add(_quitButton);
	}
	
	private void setupAccuracyRatings(){
		for(int i=0; i < 10; i++){
			_accuracyRatings.add(new JLabel(" "));
		}
	}
	
	public void updateAccuracyRating(int lvl, double accuracy){
		_accuracyRatings.get(lvl-1).setText(accuracy+"%");
	}

	private void setupQuizPanel(){
		JPanel quizInfo = new JPanel(new GridLayout(14,2));
		quizInfo.add(new JLabel("Level"));
		quizInfo.add(new JLabel("Accuracy"));
		quizInfo.add(new JLabel("1:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("2:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("3:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("4:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("5:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("6:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("7:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("8:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("9:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel("10:"));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel(" "));
		
		quizInfo.add(new JLabel("Current Quiz Level: "));
		quizInfo.add(_levelLabel);
		quizInfo.add(new JLabel("Current Word number: "));
		quizInfo.add(_wordCountLabel);
		
		_quizPanel.add(BorderLayout.NORTH,_quizMenuButton);
		_quizPanel.add(BorderLayout.CENTER,quizInfo);
		_quizPanel.add(BorderLayout.SOUTH,_input);
	}
	
	private void setupOptionsPanel(){
		_quizType.addItem("Normal");
		_quizType.addItem("Review");
		_quizLevel.addItem("1");
		_quizLevel.addItem("2");
		_quizLevel.addItem("3");
		_quizLevel.addItem("4");
		_quizLevel.addItem("5");
		_quizLevel.addItem("6");
		_quizLevel.addItem("7");
		_quizLevel.addItem("8");
		_quizLevel.addItem("9");
		_quizLevel.addItem("10");
		_voiceType.addItem("American");
		_voiceType.addItem("British");
		_voiceType.addItem("New Zealander");
		
		JPanel optionGridPanel = new JPanel(new GridLayout(6,2));
		optionGridPanel.add(new JLabel("Quiz Type"));
		optionGridPanel.add(_quizType);
		optionGridPanel.add(new JLabel(" "));
		optionGridPanel.add(new JLabel(" "));
		optionGridPanel.add(new JLabel("Quiz Level"));
		optionGridPanel.add(_quizLevel);
		optionGridPanel.add(new JLabel(" "));
		optionGridPanel.add(new JLabel(" "));
		optionGridPanel.add(new JLabel("Voice"));
		optionGridPanel.add(_voiceType);
		optionGridPanel.add(new JLabel(" "));
		optionGridPanel.add(new JLabel(" "));
		
		_optionsPanel.add(BorderLayout.NORTH, _optionsMenuButton);
		_optionsPanel.add(BorderLayout.CENTER, optionGridPanel);
	}
	
	public CardLayout mainLayout(){
		return _mainLayout;
	}
	
	public JPanel mainPanel(){
		return _mainPanel;
	}
	
	public JButton quiz(){
		return _startQuizButton;
	}
	
	public JButton stats(){
		return _viewStatsButton;
	}
	
	public JButton options(){
		return _optionsButton;
	}
	
	public JButton quit(){
		return _quitButton;
	}
	
	public JButton quizMenu(){
		return _quizMenuButton;
	}
	
	public JButton optionsMenu(){
		return _optionsMenuButton;
	}
	
}
