// Author:		Eric Sylvain and Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	- Holds all transactions in a static object (since the app will be for a single customer)


package com.example.td;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.util.Log;

public class CustomerData {
	private static List<Transaction> _customerTransactions = new ArrayList<Transaction>(); 	// List of this customer's transactions
	private static boolean[] _categorySelection = new boolean[7]; // Array of selected categories
	
	public static List<Transaction> get_transactions() { return _customerTransactions; }
	public static boolean[] get_categorySelection() { return _categorySelection; }
	public static boolean allCategoriesAreSelected() {
		for(boolean b : _categorySelection) if(!b) return false; // Return false if any are false
		return true;
	}

	
	// Loads customer data from the cusdata.xml file
	public static void loadCustomerData(AssetManager am) {
		try {
			XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			CustomerFileReader cfr = new CustomerFileReader();			
			xr.setContentHandler(cfr);
			xr.parse(new InputSource( am.open("cusdata.xml") ));
			_customerTransactions = cfr.getTransactionList();
			
		} catch (SAXException e) {
			Log.w("AndroidParseXMLActivity", "SAXException - " + e.getMessage());			
		} catch (ParserConfigurationException e) {
			Log.w("AndroidParseXMLActivity", "ParserConfigurationException - " + e.getMessage());
		} catch (IOException e) {
			Log.w("AndroidParseXMLActivity", "IOException - " + e.getMessage());
		}
		
		// Select all categories by default
		for(int i = 0; i < _categorySelection.length; ++i) _categorySelection[i] = true;
	}
	
	// Loads data from the companies from the Customer's transactions
	public static void loadCompanyData(DBAdapter db) {
		
		// First check if this has been ran already - if it has, don't bother doing it again
		for(Transaction t : _customerTransactions) if(t.get_company() != null) return; // If any transaction has a company already, it's been done
		
		db.open();
		
		for(Transaction t : CustomerData.get_transactions()) {
			Cursor c = db.getCompanyImpactRatings(t.get_companyName());
			
			if (c.isBeforeFirst() != true) //if before first, no results were found (not in database)
				t.set_company(new Company(t.get_companyName(), getCompanyImpacts(c)));
			
		}
		db.close();
	}
	
	// Updates category selection based on a boolean[7] - also forces the companies to recalculate their overall impacts
	public static void updateCategorySelection(boolean[] sel) {
		_categorySelection = sel;
		
		// Update all the relative companies to show a different overall impact
		for(Transaction t : _customerTransactions) if(t.get_company() != null) t.get_company().updateOverallImpact();		
	}
	
	// Returns double array of impacts from a database cursor
	private static double[] getCompanyImpacts(Cursor c) {
		double [] impacts = new double[7];		
		for(int r = 0; r < 7; r++) impacts[r] = c.getDouble(r); // Gather the doubles
		return impacts;
	}
}