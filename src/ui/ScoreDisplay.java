package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import data.Entry;

public class ScoreDisplay extends JPanel{
	static Dimension dim = new Dimension(30, 30);
	JSpinner leftLifeSpinner;
	JSpinner leftPoisonSpinner;
	JSpinner leftRoundScoreSpinner;
	JSpinner rightLifeSpinner;
	JSpinner rightPoisonSpinner;
	JSpinner rightRoundScoreSpinner;
	
	public ScoreDisplay()
	{	
		JLabel leftLifeLabel = new JLabel("Life");
		leftLifeLabel.setHorizontalTextPosition(JLabel.CENTER);
		JLabel leftPoisonLabel = new JLabel("Poison");
		JLabel leftRoundScoreLabel = new JLabel("Wins");
		JLabel rightLifeLabel = new JLabel("Life");
		JLabel rightPoisonLabel = new JLabel("Poison");
		JLabel rightRoundScoreLabel = new JLabel("Wins");
		
		leftLifeSpinner = new JSpinner(new SpinnerNumberModel(20, -100, 100, 1));
		leftLifeLabel.setLabelFor(leftLifeSpinner);
		leftLifeLabel.setPreferredSize(dim);
		leftLifeLabel.setMaximumSize(dim);
		
		leftPoisonSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		leftPoisonLabel.setLabelFor(leftPoisonSpinner);
		
		leftRoundScoreSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 3, 1));
		leftRoundScoreLabel.setLabelFor(leftRoundScoreSpinner);
		
		rightLifeSpinner = new JSpinner(new SpinnerNumberModel(20, -100, 100, 1));
		rightLifeLabel.setLabelFor(rightLifeSpinner);
		
		rightPoisonSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		rightPoisonLabel.setLabelFor(rightPoisonSpinner);
		
		rightRoundScoreSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 3, 1));
		rightRoundScoreLabel.setLabelFor(rightRoundScoreSpinner);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
		GridLayout gridLayout = new GridLayout(2, 0);
		this.setLayout(gridLayout);
		
		this.add(leftLifeLabel);
		this.add(leftPoisonLabel);
		this.add(leftRoundScoreLabel);
		this.add(rightRoundScoreLabel);
		this.add(rightPoisonLabel);
		this.add(rightLifeLabel);
		this.add(rightLifeSpinner);
		this.add(rightPoisonSpinner);
		this.add(rightRoundScoreSpinner);
		this.add(leftRoundScoreSpinner);
		this.add(leftPoisonSpinner);
		this.add(leftLifeSpinner);
	}

    public void update() throws ParseException, FileNotFoundException
    {
        PrintWriter pw = null;
        
        pw = new PrintWriter("dataFiles/leftLife.txt");
        leftLifeSpinner.commitEdit();
        pw.println(this.leftLifeSpinner.getValue());
        pw.close();
        
        pw = new PrintWriter("dataFiles/rightLife.txt");
        rightLifeSpinner.commitEdit();
        pw.println(this.rightLifeSpinner.getValue());
        pw.close();
        
        pw = new PrintWriter("dataFiles/leftPoison.txt");
        leftPoisonSpinner.commitEdit();
        pw.println(this.leftPoisonSpinner.getValue());
        pw.close();
        
        pw = new PrintWriter("dataFiles/rightPoison.txt");
        rightPoisonSpinner.commitEdit();
        pw.println(this.rightPoisonSpinner.getValue());
        pw.close();
        
        pw = new PrintWriter("dataFiles/gameStatus.txt");
        rightRoundScoreSpinner.commitEdit();
        leftRoundScoreSpinner.commitEdit();
        pw.println(this.leftRoundScoreSpinner.getValue() + " vs " + rightRoundScoreSpinner.getValue());
        pw.close();
    }
    
    public void resetLife()
    {
    	leftLifeSpinner.setValue(new Integer(20));
    	leftPoisonSpinner.setValue(new Integer(0));
    	rightLifeSpinner.setValue(new Integer(20));
    	rightPoisonSpinner.setValue(new Integer(00));
    }
    
    public void resetScores()
    {
    	leftRoundScoreSpinner.setValue(new Integer(0));
    	rightRoundScoreSpinner.setValue(new Integer(0));
    }
}
