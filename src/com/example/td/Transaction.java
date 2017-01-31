// Author:		Eric Sylvain and Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	- Holds data for transactions from the cusdata.xml file
//				- Can be compared by transaction number (default), company name, amount spent, and company rating


package com.example.td;

import java.util.Comparator;


public class Transaction implements Comparable<Transaction> {
	
	private static int TransactionCount = 0; // Keeps track of transaction numbers
	private Company	_company;
	private double 	_amount;
	private String 	_companyName;
	private int		_transNum;
	
	public double 	get_amount() 		{ return _amount; }
	public String 	get_companyName() 	{ return _companyName; }
	public int		get_transNum()		{ return _transNum; }
	
	public Company get_company() { return _company; }
	public void set_company(Company c) { this._company = c; }
	
	
	// Ctor - takes CompanyName and Amount Spent - automatically adds the transaction number
	public Transaction(String cn, double amt) {
		this._amount = amt;
		this._companyName = cn.trim();
		this._transNum = TransactionCount;
		++TransactionCount;
	}
	
	
	// Check that both the name and details are the same - no chance of being identical for different items
	public boolean equals(Transaction t) {
		return t.get_companyName().equals(this._companyName) && t.get_amount() == this._amount && t.get_company().equals(this._company);
	}

	@Override // Default comparer - by transaction order
	public int compareTo(Transaction t) {
		int l = this._transNum;
		int r = t.get_transNum();

		// Smaller first, so '<'
		if(l < r) 		return -1;
		else if(l == r) return 0;
		else  			return 1;
	}
	
	// Comparator to sort by amount
	public static Comparator<Transaction> TransactionAmountCompare = new Comparator<Transaction>() {
		@Override
		public int compare(Transaction lhs, Transaction rhs) {
			double l = lhs.get_amount();
			double r = rhs.get_amount();

			// Bigger first, so '>'
			if(l > r) 		return -1;
			else if(l == r) return 0;
			else  			return 1;
		}	    
	};
	
	// Comparator to sort by Company Overall Impact
	public static Comparator<Transaction> TransactionCompanyImpactCompare = new Comparator<Transaction>() {
		@Override
		public int compare(Transaction lhs, Transaction rhs) {
			
			// Get the ratings as doubles - if "N/A" set it to 0.0 - remove the '%'
			double l = (lhs.get_company() == null) ? 0.0 : lhs.get_company().get_ovllImpact();
			double r = (rhs.get_company() == null) ? 0.0 : rhs.get_company().get_ovllImpact();
			
			// Bigger first, so '>'
			if(l > r) 		return -1;
			else if(l == r) return 0;
			else  			return 1;
		}
	};
	
	// Comparator to sort by name alphabetically
	public static Comparator<Transaction> TransactionAlphabeticalCompare = new Comparator<Transaction>() {
		@Override
		public int compare(Transaction lhs, Transaction rhs) {
			String l = lhs.get_companyName();
			String r = rhs.get_companyName();
			
			if(l == null && r == null) 	return 0;
			else if(l == null) 			return -1;
			else if(r == null) 			return 1;
			
			return l.compareToIgnoreCase(r);
		}	    
	};
}