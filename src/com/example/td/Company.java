// Author:		Eric Sylvain and Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	- Holds data for a company based on the data from the ratings GMI DB
//				- Can be compared by Name, CategoryBasedOverallImpact, and actual OverallImpact
//				- Shortened var/getter names...


package com.example.td;

import java.util.Comparator;

public class Company implements Comparable<Company> {
	
	private String _name;
	private double _govImpact;
	private double _boaImpact;
	private double _payImpact;
	private double _ownImpact;
	private double _accImpact;
	private double _envImpact;
	private double _socImpact;
	private double _ovllImpact;
	private double _ovllImpactIgnCats;
	
	public String get_name() 		{ return _name; }
	public double get_govImpact() 	{ return _govImpact; }
	public double get_boaImpact() 	{ return _boaImpact; }
	public double get_payImpact() 	{ return _payImpact; }
	public double get_ownImpact() 	{ return _ownImpact; }
	public double get_accImpact() 	{ return _accImpact; }
	public double get_envImpact()	{ return _envImpact; }
	public double get_socImpact() 	{ return _socImpact; }
	public double get_ovllImpact() 	{ return _ovllImpact; }
	public double get_ovllImpactIgnCats() { return _ovllImpactIgnCats; }

	
	// Takes CompanyName and array of 7 doubles
	public Company(String n, double[] d) {
		this._name = n;
		this._govImpact = d[0];
		this._boaImpact = d[1];
		this._payImpact = d[2];
		this._ownImpact = d[3];
		this._accImpact = d[4];
		this._envImpact = d[5];
		this._socImpact = d[6];
		this._ovllImpactIgnCats = (d[0] + d[1] + d[2] + d[3] + d[4] + d[5] + d[6]) / 7;
		updateOverallImpact();
	}
	
	
	// Updates this company's overall impact based on the categories the user selected
	public void updateOverallImpact() {
		boolean[] sel = CustomerData.get_categorySelection();		
		double total = 0.0;
		int count = 0;

		if(sel[0]) {
			total += this._govImpact;
			++count;
		}
		if(sel[1]) {
			total += this._boaImpact;
			++count;
		}
		if(sel[2]) {
			total += this._payImpact;
			++count;
		}
		if(sel[3]) {
			total += this._ownImpact;
			++count;
		}
		if(sel[4]) {
			total += this._accImpact;
			++count;
		}
		if(sel[5]) {
			total += this._envImpact;
			++count;
		}
		if(sel[6]) {
			total += this._socImpact;
			++count;
		}
		this._ovllImpact = total / count;
	}
	
	
	@Override // Default compare - by company name
	public int compareTo(Company rhs) {
		String l = this._name;
		String r = rhs.get_name();
		
		if(l == null && r == null) 	return 0;
		else if(l == null) 			return -1;
		else if(r == null) 			return 1;
		
		return l.compareToIgnoreCase(r);
	}
	
	// Comparator to sort by overall impact, based on selected categories
	public static Comparator<Company> CompanyCategoryBasedImpactCompare = new Comparator<Company>() {
		@Override
		public int compare(Company lhs, Company rhs) {
			double l = lhs.get_ovllImpact();
			double r = rhs.get_ovllImpact();

			// Bigger first, so '>'
			if(l > r) 		return -1;
			else if(l == r) return 0;
			else  			return 1;
		}	    
	};
		
	// Comparator to sort by overall impact, NOT based on selected categories
	public static Comparator<Company> CompanyOverallImpactCompare = new Comparator<Company>() {
		@Override
		public int compare(Company lhs, Company rhs) {
			double l = lhs.get_ovllImpactIgnCats();
			double r = rhs.get_ovllImpactIgnCats();

			// Bigger first, so '>'
			if(l > r) 		return -1;
			else if(l == r) return 0;
			else  			return 1;
		}	    
	};
}