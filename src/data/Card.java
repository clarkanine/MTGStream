package data;
public class Card
{
    String n;
    String src;

    public Card(String name, String imgsrc)
    {
    	n = name;
    	src = imgsrc;
    }

    public String toString()
    {
    	return n;
    }
    
    public String getSource()
    {
    	return src;
    }
}
