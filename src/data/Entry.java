package data;
import javax.swing.*;
import java.io.*;

public class Entry
{
    private JLabel label;
    private JTextField textField;
    private String filename;
    
    public Entry(String lab, String fname)
    {
        label = new JLabel(lab);
        filename = fname;
        textField = new JTextField(5);
    }
    
    public void addToPanel(JPanel p)
    {
        p.add(label);
        p.add(textField);
    }
    
    public void apply()
    {
        PrintWriter pw = null;
        
        try
        {
            pw = new PrintWriter(this.filename);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        pw.println(this.textField.getText() );
        pw.close();
    }
    
    public String toString()
    {
        return label.getText();
    }
    
    public String getText()
    {
        return textField.getText();
    }
    
    public JLabel getLabel()
    {
        return label;
    }
    
    public String getFileName()
    {
        return filename;
    }
    
    public JTextField getField()
    {
        return textField;
    }
    
}