package streamsrc;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;

import data.Entry;
import data.Match;
import data.Player;
import ui.CardDisplay;
import ui.MatchPool;
import ui.PlayerPool;
import ui.PoolTab;
import ui.ScoreDisplay;
import enums.PlayerStatus;

public class MTGCardFinder extends JFrame
{
    ArrayList<Entry> entry = new ArrayList<>();
    JTabbedPane tabbedPane;
    
    String[] filenames                   = new String[17];
    ListSelectionListener listlistener   = new MTGListListener(this);
    PlayerPool playerPoolLeft            = new PlayerPool();
    PlayerPool playerPoolRight           = new PlayerPool();
    CardDisplay cardDisplay;
    ScoreDisplay scoreDisplay;
    
    String configfile = "./config.txt";
    MatchPool matchPool;
  
    public MTGCardFinder(String arg)
    {
        super();
        
        //Buttons
        MTGListener updateButtonListener = MTGListener.getInstance();
        updateButtonListener.setScoreDisplay(scoreDisplay);
        JButton applyButton = new JButton("Apply Everything");
        applyButton.setActionCommand("update");
        applyButton.addActionListener(updateButtonListener);
        
        ActionListener MTGPairListener = new MTGPairListener(this);
        JButton pairButton = new JButton();
        pairButton.setActionCommand("pair");
        pairButton.addActionListener(MTGPairListener);
        
        JButton resetLifeButton = new JButton("Reset Life");
        resetLifeButton.setActionCommand("reset_life");
        resetLifeButton.addActionListener(updateButtonListener);
        
        JButton resetAllButton = new JButton("Reset All");
        resetAllButton.setActionCommand("reset_all");
        resetAllButton.addActionListener(updateButtonListener);

        //Card
        cardDisplay = new CardDisplay();
        JPanel completeCardPanel = new JPanel();
        completeCardPanel.setLayout(new BoxLayout(completeCardPanel, BoxLayout.Y_AXIS));
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Card Panel", completeCardPanel);
        
        //Score display view
        ScoreDisplay scoreDisplay = new ScoreDisplay();
        completeCardPanel.add(scoreDisplay);
        completeCardPanel.add(cardDisplay);
        completeCardPanel.add(resetLifeButton);
        completeCardPanel.add(resetAllButton);
        completeCardPanel.add(applyButton);
        updateButtonListener.setScoreDisplay(scoreDisplay);

        //Player pool
        DefaultListModel<Player> players = new DefaultListModel<Player>();

        pairButton.setText("Pair");
     
        PoolTab poolTab = new PoolTab(players);
        
        tabbedPane.addTab("Players", poolTab);
        
        this.add(tabbedPane);
        this.pack();
        this.setVisible(true);
    }
    
    public static void addEntryListToPanel(ArrayList<Entry> e, JPanel p)
    {
        for(Entry entry : e)
        {
            p.add(entry.getLabel() );
            p.add(entry.getField() );
        }
    }
    
    public static void main(String[] args)
    {
        String arg = "config.txt";
        if(args.length == 1)
            arg = args[0];
        MTGCardFinder t = new MTGCardFinder(arg);
    }
}