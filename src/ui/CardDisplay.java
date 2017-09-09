package ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data.Card;
import streamsrc.MTGListener;
import streamsrc.MTGListListener;

public class CardDisplay extends JPanel{
	private static final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
    private static final ColorModel RGB_OPAQUE =
    	new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
    
	public JList<Card> cardlist;
	public JPanel cardchoicepanel;
	JTextField cardSearchField;
	JPanel cardpanel;
	JPanel cardUserInteractionPanel;
	DefaultListModel<Card> cardListModel;
    Image img;
    int width, height;
    DataBuffer buffer = null;
    WritableRaster raster = null;
    BufferedImage myPicture = null;
	
	public CardDisplay() 
	{
		JButton search = new JButton("Search");
		cardListModel = new DefaultListModel<Card>();
		cardUserInteractionPanel = new JPanel();
		cardpanel = new JPanel();
		cardchoicepanel = new JPanel();
		
	    MTGListener buttonlistener = MTGListener.getInstance();
	    buttonlistener.setCardDisplay(this);
		search.addActionListener(buttonlistener);
	    search.setActionCommand("search");
	    
	    cardlist = new JList<Card>(cardListModel);
	    cardlist.setVisibleRowCount(15);
	    cardSearchField = new JTextField();
	    cardSearchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				System.out.println("changed card text...");
				if(cardSearchField.getText().length() == 4) {
					search();
				}
				else if(cardSearchField.getText().length() > 4) {
					searchCurrentList();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
	    });
	    cardSearchField.setPreferredSize(new Dimension(300, 30));
	    cardSearchField.setMaximumSize(new Dimension(300, 30));
	    cardpanel = new JPanel();
	    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    
	    cardUserInteractionPanel.setLayout(new BoxLayout(cardUserInteractionPanel, BoxLayout.Y_AXIS));
	    cardUserInteractionPanel.add(cardSearchField);
	    cardUserInteractionPanel.add(search);
	    cardUserInteractionPanel.add(new JScrollPane(cardlist));
	    this.add(cardUserInteractionPanel);
	    this.add(cardpanel);
	    this.updateImage("https://image.deckbrew.com/mtg/multiverseid/0.jpg");
	}
	
	public void search()
    {
		DefaultListModel<Card> theCards = new DefaultListModel<Card>();
        URL url = null;
        BufferedReader reader = null;
        String cardname = cardSearchField.getText();
        String result = "https://image.deckbrew.com/mtg/multiverseid/0.jpg";
        String display = "";
        cardname = cardname.replace(' ', '+');
        String urlStr = "https://api.deckbrew.com/mtg/cards?name=" + cardname;
        cardListModel.clear();
        
        try {
            url = new URL(urlStr);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8") );
        }
        
        catch(Exception e) {
            e.printStackTrace();
        }
        
        try {
            for(String line; (line = reader.readLine()) != null;) {
                if(line.contains("\"name\"") ) {
                    display = line.substring(13, line.length() - 2);
                }
                if(line.contains("image_url") && !line.contains("/0.jpg")) {
                    result = line.substring(line.indexOf("https"), line.length() - 2);
                    System.out.println(result + "\n");
                    theCards.addElement(new Card(display, result));
                }
            }
        }
   
        catch(Exception e) {
            e.printStackTrace();
        }
        
        cardListModel = theCards;
        cardlist.setModel(cardListModel);
        cardlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cardlist.addListSelectionListener(new MTGListListener(this));
        
        updateImage(result);
        this.setVisible(true);
    }
	
	public void searchCurrentList()
	{
		DefaultListModel<Card> updatedCardListModel = new DefaultListModel<Card>();
		for(int i = 0; i < cardlist.getModel().getSize(); i++) {
			Card card = cardlist.getModel().getElementAt(i);
			if(card.toString().toLowerCase().contains(cardSearchField.getText().toLowerCase()))
			{
				updatedCardListModel.addElement(card);
			}
		}
		cardlist.setModel(updatedCardListModel);
		cardListModel = updatedCardListModel;
	}
    
    public void updateImage(String address)
    {
        System.out.println("getting image at " + address);
        cardpanel.removeAll();
        try {
            img = Toolkit.getDefaultToolkit().createImage( new URL(address) );
            PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);
            pg.grabPixels();
            width = pg.getWidth();
            height = pg.getHeight();
            buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            myPicture = new BufferedImage(RGB_OPAQUE, raster, false, null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        cardpanel.add(picLabel);
        cardpanel.setVisible(false);
        cardpanel.setVisible(true);
        this.setVisible(true);
    }
    
    public void update()
    {
        try
        {
            File outputfile = new File("dataFiles/card.jpg");
            ImageIO.write(myPicture, "jpg", outputfile);
        }
        catch(Exception e) {
        	System.err.println("Couldn't write image to file");
        	e.printStackTrace();
        }
    }
}
