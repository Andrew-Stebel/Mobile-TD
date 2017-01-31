// Author:		Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	Content handler to load the cusdata.xml file


package com.example.td;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class CustomerFileReader extends DefaultHandler {
    private ArrayList<Transaction> _transList = new ArrayList<Transaction>();
	private String _companyName = null;
	private double _amount = 0.0;	
    Boolean currentElement = false;
    String currentValue = "";    
 
    public ArrayList<Transaction> getTransactionList() { return _transList; }
    
    
    @Override // Called when tag starts 
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException { 
        currentElement = true;
        currentValue = "";
    } 
    
    @Override // Called when tag closing 
    public void endElement(String uri, String localName, String qName) throws SAXException { 
        currentElement = false;
 
        if(localName.equalsIgnoreCase("BKI-TXN-DESC")) 	_companyName = currentValue; // Company name 
        if(localName.equalsIgnoreCase("BKI-TXN-AMT")) 	_amount = Double.parseDouble(currentValue); // Amount
        else if (localName.equalsIgnoreCase("BLN-GRP-TAG2-ROW"))
        {
        	if (_companyName != null) {
        		_transList.add(new Transaction(_companyName, _amount));
        		_companyName = null;
        		_amount = 0.0;
        	}
        }
    } 
    
    @Override // Called to get tag characters 
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentElement) currentValue += new String(ch, start, length);
    } 
}