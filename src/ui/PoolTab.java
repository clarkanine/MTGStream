package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.json.JSONWriter;

import data.Match;
import data.Player;
import enums.PlayerStatus;
import streamsrc.MTGPoolButtonListener;

public class PoolTab extends JPanel{
	ActionListener mtgPoolButtonListener;
	PlayerPool playerDatabase;
	PlayerPool leftPlayers;
	PlayerPool rightPlayers;
	
	DefaultListModel<Player> allPlayers;
	MatchPool matchPool;
	
	JButton addPlayerButton;
	JButton pairButton;
	JButton resetMatchPairingsButton;
	JButton resetPlayerParticipantsButton;
	JButton confirmSelectionButton;
	JButton savePlayersButton;
	JButton setActiveMatchButton;
	JButton generateStandingsButton;
	
	JTextField searchField;
	
	public PoolTab(DefaultListModel<Player> players)
	{
		allPlayers = players;
		mtgPoolButtonListener = new MTGPoolButtonListener(this);
		
		//Pools
		matchPool = new MatchPool();
		JScrollPane matchPoolScrollPane = new JScrollPane();
		matchPoolScrollPane.setViewportView(matchPool);
		
		playerDatabase = new PlayerPool();
		JScrollPane playerDatabaseScrollPane = new JScrollPane();
		playerDatabaseScrollPane.setViewportView(playerDatabase);
		try {
			load();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerDatabase.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		playerDatabase.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        PlayerPool players = (PlayerPool)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	int index = players.locationToIndex(evt.getPoint());
		        	editPlayer((Player) players.list.getElementAt(index));
		        }
		    }
		});
		
		leftPlayers = new PlayerPool();
		JScrollPane leftPlayersScrollPane = new JScrollPane();
		leftPlayersScrollPane.setViewportView(leftPlayers);
		leftPlayers.setModel(players);
		
		rightPlayers = new PlayerPool();
		JScrollPane rightPlayersScrollPane = new JScrollPane();
		rightPlayersScrollPane.setViewportView(rightPlayers);
		rightPlayers.setModel(players);
		
		//Search field
		searchField = new JTextField();
	    searchField.setPreferredSize(new Dimension(300, 30));
	    searchField.setMaximumSize(new Dimension(300, 30));
	    searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				searchPlayers();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchPlayers();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
	    });
		
		//Buttons
		addPlayerButton = new JButton("Add Player");
		addPlayerButton.addActionListener(this.mtgPoolButtonListener);
		addPlayerButton.setActionCommand("add_player");
		
		pairButton = new JButton("Pair");
		pairButton.addActionListener(this.mtgPoolButtonListener);
		pairButton.setActionCommand("pair");
		
		resetMatchPairingsButton = new JButton("Reset Pairings");
		resetMatchPairingsButton.addActionListener(this.mtgPoolButtonListener);
		resetMatchPairingsButton.setActionCommand("reset_match_pairings");
		
		resetPlayerParticipantsButton = new JButton("Reset Players");
		resetPlayerParticipantsButton.addActionListener(this.mtgPoolButtonListener);
		resetPlayerParticipantsButton.setActionCommand("reset_participants");
		
		confirmSelectionButton = new JButton("Confirm Selection");
		confirmSelectionButton.addActionListener(this.mtgPoolButtonListener);
		confirmSelectionButton.setActionCommand("confirm_selection");
		
		savePlayersButton = new JButton("Save Players");
		savePlayersButton.addActionListener(this.mtgPoolButtonListener);
		savePlayersButton.setActionCommand("save_players");
		
		setActiveMatchButton = new JButton("Set Active Match");
		setActiveMatchButton.addActionListener(this.mtgPoolButtonListener);
		setActiveMatchButton.setActionCommand("set_active_match");
		
		generateStandingsButton = new JButton("Generate Standings");
		generateStandingsButton.addActionListener(this.mtgPoolButtonListener);
		generateStandingsButton.setActionCommand("generate_standings");
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel playerDatabasePanel = new JPanel();
		playerDatabasePanel.setLayout(new BoxLayout(playerDatabasePanel, BoxLayout.Y_AXIS));
		playerDatabasePanel.add(searchField);
		playerDatabasePanel.add(playerDatabaseScrollPane);
		playerDatabasePanel.add(addPlayerButton);
		playerDatabasePanel.add(confirmSelectionButton);
		playerDatabasePanel.add(savePlayersButton);
		this.add(playerDatabasePanel);
		
		JPanel participantPanelX = new JPanel();
		participantPanelX.setLayout(new BoxLayout(participantPanelX, BoxLayout.X_AXIS));
		participantPanelX.add(leftPlayersScrollPane);
		participantPanelX.add(pairButton);
		participantPanelX.add(rightPlayersScrollPane);
		
		JPanel participantPanelY = new JPanel();
		participantPanelY.setLayout(new BoxLayout(participantPanelY, BoxLayout.Y_AXIS));
		participantPanelY.add(participantPanelX);
		participantPanelY.add(resetPlayerParticipantsButton);
		
		this.add(participantPanelY);
		
		JPanel matchPanel = new JPanel();
		matchPanel.setLayout(new BoxLayout(matchPanel, BoxLayout.Y_AXIS));
		matchPanel.add(matchPoolScrollPane);
		matchPanel.add(resetMatchPairingsButton);
		matchPanel.add(setActiveMatchButton);
		matchPanel.add(generateStandingsButton);
		this.add(matchPanel);
	}
	
	public void searchPlayers()
	{
		System.out.println("Searching for \"" + searchField.getText() + "\"");
		
		if(searchField.getText().equals("")) {
			System.out.println(allPlayers);
			playerDatabase.setModel(allPlayers);
			return;
		}
		DefaultListModel<Player> updatedListModel = new DefaultListModel<Player>();
		
		for(int i = 0; i < allPlayers.getSize(); i++) {
			Player player = allPlayers.getElementAt(i);
			if(player.toString().toLowerCase().contains(searchField.getText().toLowerCase()))
			{
				updatedListModel.addElement(player);
			}
		}
		
		playerDatabase.setModel(updatedListModel);
	}
    
	public void addPairToList()
    {
    	Match m = new Match();
    	if(leftPlayers.getSelectedValue() != null && rightPlayers.getSelectedValue() != null) {
	    	m.setupMatch((Player)leftPlayers.getSelectedValue(), (Player)rightPlayers.getSelectedValue());
	    	
	    	if(m.getP1().getPlayerStatus() != PlayerStatus.Paired && m.getP2().getPlayerStatus() != PlayerStatus.Paired)
	    	{
	        	matchPool.addMatch(m);
	        	m.getP1().setPlayerStatus(PlayerStatus.Paired);
	        	m.getP2().setPlayerStatus(PlayerStatus.Paired);
	    	}
    	}
    }
    
    public void generateStandings()
    {
    	String str = "";
    	
    	for(int i = 0; i < this.matchPool.getModel().getSize(); i++) {
    		str += this.matchPool.list.getElementAt(i).generateMatchStanding();
    	}
    	
    	PrintWriter pw = null;
    	try {
			pw = new PrintWriter("dataFiles/standings.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(pw != null) {
    		pw.println(str);
    		pw.close();
    	}
    	
    }
    
    public void clearStandings()
    {
    	PrintWriter pw = null;
    	
    	try {
			pw = new PrintWriter("dataFiles/standings.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(pw != null) {
    		pw.println("");
    		pw.close();
    	}
    }

    public void confirmPlayerSelection()
    {
    	List<Player> selectedPlayers = playerDatabase.getSelectedValuesList();
    	
    	for(Player player : selectedPlayers) {
    		leftPlayers.addPlayer(player);
    		rightPlayers.addPlayer(player);
    	}	
    }
    
    public void savePlayers() throws JSONException, FileNotFoundException
    {
    	PrintWriter pw = new PrintWriter("players.json");
    	JSONWriter writer = new JSONWriter(pw);
    	writer.array();
    	
    	for(int i = 0; i < allPlayers.getSize(); i++) {
    		Player player = allPlayers.getElementAt(i);
    		System.out.println(player);
    		writer.object();
    		writer.key("firstName");
    		writer.value(player.getFirstName());
    		writer.key("lastName");
    		writer.value(player.getLastName());
    		writer.key("wins");
    		writer.value(player.getWins());
    		writer.key("losses");
    		writer.value(player.getLosses());
    		writer.key("draws");
    		writer.value(player.getDraws());
    		writer.key("health");
    		writer.value(player.getHealth());
    		writer.key("poison");
    		writer.value(player.getPoison());
    		writer.key("deck");
    		writer.value(player.getDeck());
    		writer.key("status");
    		writer.value(player.getPlayerStatus().ordinal());
    		writer.endObject();	
    	}
    	writer.endArray();
    	pw.close();
    
    	load();
    }
    
    public void load() throws JSONException
    {
    	DefaultListModel<Player> players = new DefaultListModel<Player>();
    	Scanner file = null;
    	String contents = "";

    	try {
			file = new Scanner(new FileReader("players.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	while(file.hasNextLine()) {
    		contents = file.nextLine();
    	}
    	JSONTokener tokenizer = new JSONTokener(contents);
    	JSONArray array = null;
    	try {
			array = new JSONArray(tokenizer);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(array != null) {
    		for(int i = 0; i < array.length(); i++) {
    			JSONObject object = null;
    			try {
					object = array.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			Player player = null;
    			if(object.has("firstName")) {
    				player = new Player();
    				player.setFirstName(object.getString("firstName"));
    			}
    			if(object.has("lastName")) {
    				player.setLastName(object.getString("lastName"));
    			}
    			if(object.has("winsName")) {
    				player.setWins(object.getInt("wins"));
    			}
    			if(object.has("losses")) {
    				player.setLosses(object.getInt("losses"));
    			}
    			if(object.has("draws")) {
    				player.setDraws(object.getInt("draws"));
    			}
    			if(object.has("health")) {
    				player.setHealth(object.getInt("health"));
    			}
    			if(object.has("poison")) {
    				player.setPoison(object.getInt("poison"));
    			}
    			if(object.has("deck")) {
    				player.setDeck(object.getString("deck"));
    			}
    			if(object.has("status")) {
    				player.setPlayerStatus(PlayerStatus.values()[object.getInt("status")]);
    			}
    			
    			if(player != null) {
    				players.addElement(player);
    			}
    		}
    	}
    	
    	allPlayers = players;
    	playerDatabase.setupPlayerPool(players);
    	
    }
    
    public void resetParticipants()
    {
    	resetMatchPairings();
    	leftPlayers.reset();
    	rightPlayers.reset();
    }
    
    public void resetMatchPairings()
    {
    	matchPool.reset();
    	clearStandings();
    	try {
			resetActivePlayerData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void addPlayer()
    {
    	JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 2));
		
		JTextField firstName = new JTextField(10);
		firstName.setText("John");
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setLabelFor(firstName);
		dataPanel.add(firstNameLabel);
		dataPanel.add(firstName);
		
		JTextField lastName = new JTextField(10);
		lastName.setText("Doe");
		JLabel lastNameLabel = new JLabel("Last Name");
		firstNameLabel.setLabelFor(lastName);
		dataPanel.add(lastNameLabel);
		dataPanel.add(lastName);
		
		JTextField wins = new JTextField(10);
		wins.setText("0");
		JLabel winsLabel = new JLabel("Total Wins");
		winsLabel.setLabelFor(wins);
		dataPanel.add(winsLabel);
		dataPanel.add(wins);
		
		JTextField losses = new JTextField(10);
		losses.setText("0");
		JLabel lossesLabel = new JLabel("Total Losses");
		lossesLabel.setLabelFor(losses);
		dataPanel.add(lossesLabel);
		dataPanel.add(losses);
		
		JTextField draws = new JTextField(10);
		draws.setText("0");
		JLabel drawsLabel = new JLabel("Total Draws");
		drawsLabel.setLabelFor(draws);
		dataPanel.add(drawsLabel);
		dataPanel.add(draws);
		
		JTextField deck = new JTextField(10);
		deck.setText("");
		JLabel deckLabel = new JLabel("Deck Name");
		deckLabel.setLabelFor(deck);
		dataPanel.add(deckLabel);
		dataPanel.add(deck);
		
		JComboBox<PlayerStatus> statusComboBox = new JComboBox<PlayerStatus>(PlayerStatus.values());
		statusComboBox.setSelectedIndex(1);
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setLabelFor(statusComboBox);
		dataPanel.add(statusLabel);
		dataPanel.add(statusComboBox);
		
		int result = JOptionPane.showConfirmDialog(null,dataPanel,"Information", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION) {
			Player player = new Player();
			player.setFirstName(firstName.getText());
			player.setLastName(lastName.getText());
			if(wins.getText() != null && !wins.getText().equals("")) {
				player.setWins(Integer.parseInt(wins.getText()));
			}
			if(losses.getText() != null) {
				player.setLosses(Integer.parseInt(losses.getText()));
			}
			if(draws.getText() != null) {
				player.setDraws(Integer.parseInt(draws.getText()));
			}
			player.setDeck(deck.getText());
			player.setPlayerStatus(PlayerStatus.values()[statusComboBox.getSelectedIndex()]);
			
			playerDatabase.addPlayer(player);
			allPlayers = (DefaultListModel<Player>) playerDatabase.getModel();
		}
    }
    
    public void editPlayer(Player selectedPlayer)
    {
    	JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 2));
		
		JTextField firstName = new JTextField(10);
		firstName.setText(selectedPlayer.getFirstName());
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setLabelFor(firstName);
		dataPanel.add(firstNameLabel);
		dataPanel.add(firstName);
		
		JTextField lastName = new JTextField(10);
		lastName.setText(selectedPlayer.getLastName());
		JLabel lastNameLabel = new JLabel("Last Name");
		firstNameLabel.setLabelFor(lastName);
		dataPanel.add(lastNameLabel);
		dataPanel.add(lastName);
		
		JTextField wins = new JTextField(10);
		wins.setText(selectedPlayer.getWins() + "");
		JLabel winsLabel = new JLabel("Total Wins");
		winsLabel.setLabelFor(wins);
		dataPanel.add(winsLabel);
		dataPanel.add(wins);
		
		JTextField losses = new JTextField(10);
		losses.setText(selectedPlayer.getLosses() + "");
		JLabel lossesLabel = new JLabel("Total Losses");
		lossesLabel.setLabelFor(losses);
		dataPanel.add(lossesLabel);
		dataPanel.add(losses);
		
		JTextField draws = new JTextField(10);
		draws.setText(selectedPlayer.getDraws() + "");
		JLabel drawsLabel = new JLabel("Total Draws");
		drawsLabel.setLabelFor(draws);
		dataPanel.add(drawsLabel);
		dataPanel.add(draws);
		
		JTextField deck = new JTextField(10);
		deck.setText(selectedPlayer.getDeck());
		JLabel deckLabel = new JLabel("Deck Name");
		deckLabel.setLabelFor(deck);
		dataPanel.add(deckLabel);
		dataPanel.add(deck);
		
		JComboBox<PlayerStatus> statusComboBox = new JComboBox<PlayerStatus>(PlayerStatus.values());
		statusComboBox.setSelectedIndex(selectedPlayer.getPlayerStatus().ordinal());
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setLabelFor(statusComboBox);
		dataPanel.add(statusLabel);
		dataPanel.add(statusComboBox);
		
		int result = JOptionPane.showConfirmDialog(null,dataPanel,"Information", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION) {
			selectedPlayer.setFirstName(firstName.getText());
			selectedPlayer.setLastName(lastName.getText());
			if(wins.getText() != null && !wins.getText().equals("")) {
				selectedPlayer.setWins(Integer.parseInt(wins.getText()));
			}
			if(losses.getText() != null) {
				selectedPlayer.setLosses(Integer.parseInt(losses.getText()));
			}
			if(draws.getText() != null) {
				selectedPlayer.setDraws(Integer.parseInt(draws.getText()));
			}
			selectedPlayer.setDeck(deck.getText());
			selectedPlayer.setPlayerStatus(PlayerStatus.values()[statusComboBox.getSelectedIndex()]);
		}
    }
    
    public void saveActivePlayerData() throws FileNotFoundException
    {
    	Player leftPlayer;
    	Player rightPlayer;
    	
    	if(matchPool.getSelectedValue() == null) {
    		return;
    	}
    	leftPlayer = matchPool.getSelectedValue().getP1();
    	rightPlayer = matchPool.getSelectedValue().getP2();
    	
    	PrintWriter pw;
    	
    	pw = new PrintWriter("dataFiles/leftName.txt");
    	pw.println(leftPlayer.getFirstName() + " " + leftPlayer.getLastName());
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/rightName.txt");
    	pw.println(rightPlayer.getFirstName() + " " + rightPlayer.getLastName());
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/leftDeck.txt");
    	pw.println(leftPlayer.getDeck());
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/rightDeck.txt");
    	pw.println(rightPlayer.getDeck());
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/leftRecord.txt");
    	pw.println(leftPlayer.getRecord());
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/rightRecord.txt");
    	pw.println(rightPlayer.getRecord());
    	pw.close();
    }
    
    public void resetActivePlayerData() throws FileNotFoundException
    {
    	PrintWriter pw;
    	
    	pw = new PrintWriter("dataFiles/leftName.txt");
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/rightName.txt");
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/leftDeck.txt");
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/rightDeck.txt");
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/leftRecord.txt");
    	pw.close();
    	
    	pw = new PrintWriter("dataFiles/rightRecord.txt");
    	pw.close();
    }
}
