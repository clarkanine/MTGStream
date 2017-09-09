//package ui;
//
//import java.awt.GridLayout;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//import enums.PlayerStatus;
//
//public class PlayerDataOptionPane extends JOptionPane{
//
//	JTextField firstName, lastName, wins, losses, draws, deck, status;
//	
//	public PlayerDataOptionPane()
//	{
//		super();
//		
//		JPanel dataPanel = new JPanel();
//		dataPanel.setLayout(new GridLayout(0, 2));
//		
//		firstName = new JTextField(10);
//		JLabel firstNameLabel = new JLabel();
//		firstNameLabel.setLabelFor(firstName);
//		dataPanel.add(firstNameLabel);
//		dataPanel.add(firstName);
//		
//		lastName = new JTextField(10);
//		wins = new JTextField(10);
//		losses = new JTextField(10);
//		draws = new JTextField(10);
//		deck = new JTextField(10);
//		status = new JTextField(10);
//		
//		this.add(dataPanel);
//	}
//}
