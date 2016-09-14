package viewAndController;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class SpellingAidView extends JFrame{
	
	private JPanel _mainMenuPanel = new JPanel(new GridLayout(5,1));
	private JPanel _quizPanel = new JPanel(new BorderLayout());
	private JPanel _statsPanel = new JPanel(new BorderLayout());
	private JPanel _optionsPanel = new JPanel(new BorderLayout());
	
	JPanel _mainPanel = new JPanel();
	CardLayout _mainLayout = new CardLayout();
	JButton _startQuizButton = new JButton("Start New Quiz");
	JButton _viewStatsButton = new JButton("View Statistics");
	JButton _optionsButton = new JButton("Options");
	JButton _quitButton = new JButton("Quit");

	JButton _quizMenuButton = new JButton("End Quiz");
	JButton _statsMenuButton = new JButton("Back to menu");
	JButton _optionsMenuButton = new JButton("Back to menu");
	
	JLabel _wordCountLabel = new JLabel(" ");
	JLabel _levelLabel = new JLabel(" ");
	ArrayList<JLabel> _accuracyRatingQuizLabels = new ArrayList<JLabel>();
	ArrayList<JLabel> _accuracyRatingOptionsLabels = new ArrayList<JLabel>();
	JTextField _input= new JTextField();
	
	JComboBox<String> _quizType = new JComboBox<String>();
	JComboBox<Integer> _quizLevel = new JComboBox<Integer>();
	JComboBox<String> _voiceType = new JComboBox<String>();
	
	JButton _clearStatsButton = new JButton("Clear statistics");
	JTextArea _statsTextArea = new JTextArea();
	
	public SpellingAidView(){
		//create SpellingAid window
		super("Spelling Aid");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(500,400);	
		setLocationRelativeTo(null);
		
		_mainPanel.setLayout(_mainLayout);
		setupAccuracyRatings();
		setupMainPanel();
		setupOptionsPanel();
		setupQuizPanel();
		setupMainMenuPanel();
		setupStatsPanel();
		this.add(_mainPanel);
		_mainLayout.show(_mainPanel, "mainMenuPanel");
		setResizable(false);
		//pack();
	}
	
	//sets up the main panel
	private void setupMainPanel() {
		_mainPanel.add(_quizPanel, "quizPanel");
		_mainPanel.add(_statsPanel , "statsPanel");
		_mainPanel.add(_optionsPanel, "optionsPanel");
		_mainPanel.add(_mainMenuPanel , "mainMenuPanel");
		_mainPanel.add(_statsPanel, "statsPanel");
	}
	
	private void setupStatsPanel() {
		_statsTextArea.setEditable(false);
		
		JPanel infoPanel = new JPanel(new GridLayout(1,2));
		JPanel accuracyPanel = new JPanel(new GridLayout(12,2));
		JPanel statsPanel = new JPanel(new BorderLayout());
		JScrollPane statsPane = new JScrollPane(_statsTextArea);
		
		accuracyPanel.add(new JLabel("Level"));
		accuracyPanel.add(new JLabel("Accuracy"));
		for(int i = 0; i < 11; i++){
			accuracyPanel.add(new JLabel(i+1+":"));
			accuracyPanel.add(_accuracyRatingOptionsLabels.get(i));
		}
		
		statsPanel.add(new JLabel("Word stats"), BorderLayout.NORTH);
		statsPanel.add(statsPane, BorderLayout.CENTER);
		
		infoPanel.add(statsPanel);
		infoPanel.add(accuracyPanel);
		
		_statsPanel.add(infoPanel, BorderLayout.CENTER);
		_statsPanel.add(_clearStatsButton, BorderLayout.SOUTH);
		_statsPanel.add(_statsMenuButton, BorderLayout.NORTH);
	}
	
	private void setupMainMenuPanel() {
		_mainMenuPanel.add(new JLabel("Welcome to Spelling Aid!", SwingConstants.CENTER));
		_mainMenuPanel.add(_startQuizButton);
		_mainMenuPanel.add(_viewStatsButton);
		_mainMenuPanel.add(_optionsButton);
		_mainMenuPanel.add(_quitButton);
	}
	
	private void setupAccuracyRatings(){
		for(int i=0; i < 11; i++){
			_accuracyRatingQuizLabels.add(new JLabel(" "));
			_accuracyRatingOptionsLabels.add(new JLabel(" "));
		}
	}

	private void setupQuizPanel(){
		JPanel quizInfo = new JPanel(new GridLayout(15,2));
		quizInfo.add(new JLabel("Level"));
		quizInfo.add(new JLabel("Accuracy"));
		
		for(int i = 0; i < 11; i++){
			quizInfo.add(new JLabel(i+1+":"));
			quizInfo.add(_accuracyRatingQuizLabels.get(i));
		}
		
		quizInfo.add(new JLabel(" "));
		quizInfo.add(new JLabel(" "));
		
		quizInfo.add(new JLabel("Current Quiz Level: "));
		quizInfo.add(_levelLabel);
		quizInfo.add(new JLabel("Test progress: "));
		quizInfo.add(_wordCountLabel);
		
		_quizPanel.add(BorderLayout.NORTH,_quizMenuButton);
		_quizPanel.add(BorderLayout.CENTER,quizInfo);
		_quizPanel.add(BorderLayout.SOUTH,_input);
	}
	
	private void setupOptionsPanel(){
		_quizType.addItem("Normal");
		_quizType.addItem("Review");
		_quizLevel.addItem(1);
		_quizLevel.addItem(2);
		_quizLevel.addItem(3);
		_quizLevel.addItem(4);
		_quizLevel.addItem(5);
		_quizLevel.addItem(6);
		_quizLevel.addItem(7);
		_quizLevel.addItem(8);
		_quizLevel.addItem(9);
		_quizLevel.addItem(10);
		_quizLevel.addItem(11);
		_voiceType.addItem("American");
		_voiceType.addItem("British");
		_voiceType.addItem("New Zealander");
		
		JPanel optionGridPanel = new JPanel(new GridLayout(6,2));
		optionGridPanel.add(new JLabel("Quiz Type"));
		optionGridPanel.add(_quizType);
		optionGridPanel.add(new JLabel(" "));
		optionGridPanel.add(new JLabel(" "));
		optionGridPanel.add(new JLabel("Quiz Starting Level"));
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
}
