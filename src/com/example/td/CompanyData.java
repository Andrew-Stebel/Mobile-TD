// Author:		Eric Sylvain and Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	- Holds static data about all the companies from the database
//				- Lists contained are top/bottom for overall impact, and top/bottom for overall impact based on the selected categories


package com.example.td;

import java.util.*;
import android.database.Cursor;


public class CompanyData {
	private static List<Company> _allCompanies = new ArrayList<Company>();
	private static List<Company> _topOverallCompanies = new ArrayList<Company>();
	private static List<Company> _topCategorySelectionBasedCompanies = new ArrayList<Company>();
	private static List<Company> _bottomOverallCompanies = new ArrayList<Company>();
	private static List<Company> _bottomCategorySelectionBasedCompanies = new ArrayList<Company>();

	
	public static List<Company> get_allCompanies() { return _allCompanies; }
	public static List<Company> get_topOverallCompanies() { return _topOverallCompanies; }
	public static List<Company> get_topCategorySelectionBasedCompanies() { return _topCategorySelectionBasedCompanies; }
	public static List<Company> get_bottomOverallCompanies() { return _bottomOverallCompanies; }
	public static List<Company> get_bottomCategorySelectionBasedCompanies() { return _bottomCategorySelectionBasedCompanies; }
	public static Company get_topOverallCompany() {		
		for(Company c : _topOverallCompanies) { // Ignore ones with 0.0 (perfect) ... likely only because no data has been collected for them
			if(c.get_ovllImpactIgnCats() != 0.0) return c; 
		}
		return null;
	}
	public static Company get_topCategorySelectionBasedCompany() {		
		for(Company c : _topCategorySelectionBasedCompanies) { // Ignore ones with 0.0 (perfect) ... likely only because no data has been collected for them
			if(c.get_ovllImpact() != 0.0) return c; 
		}
		return null;
	}
	public static Company get_bottomOverallCompany() 				{ return _bottomOverallCompanies.get(0); }
	public static Company get_bottomCategorySelectionBasedCompany() { return _bottomCategorySelectionBasedCompanies.get(0); }
	
	
	// Loads all companies to the _allCompanies List
	public static void loadAllCompanies(DBAdapter db) {
		if(_allCompanies.size() < 1) { // Don't load the companies more than once
			db.open();
			Cursor c = db.getAllCompanies();
			
		    if (c.moveToFirst()) {		    	
		    	do {
		    		double[] ratings = new double[7];				
					for(int r = 0; r < 7; r++) ratings[r] = c.getDouble(r+1);				
					_allCompanies.add(new Company(c.getString(0),ratings));					
		    	} while (c.moveToNext());
		    }
		    db.close();
		}

		// Sort then collect top/bottom companies for both overall and based on the selected categories
		Collections.sort(_allCompanies, Company.CompanyOverallImpactCompare);
		gatherTopOverallCompanies();
		gatherBottomOverallCompanies();
		Collections.sort(_allCompanies, Company.CompanyCategoryBasedImpactCompare);
		gatherTopCompaniesBasedOnCategorySelection();
		gatherBottomCompaniesBasedOnCategorySelection();
	}
	
	// Refreshes the category selection based lists - should be called each time categories are changed - also updates _overallImpact's on all companies
	public static void refreshCategorySelectionBasedLists() {
		for(Company c : _allCompanies) c.updateOverallImpact();
		Collections.sort(_allCompanies, Company.CompanyCategoryBasedImpactCompare);
		gatherTopCompaniesBasedOnCategorySelection();
		gatherBottomCompaniesBasedOnCategorySelection();
	}

	// Gets the ten best companies, NOT based on selected categories
	private static void gatherTopOverallCompanies() {		
		for(int i = _allCompanies.size() - 1; i > _allCompanies.size() - 11; --i) { // Get the last ten (lowest impact)
			_topOverallCompanies.add(_allCompanies.get(i));
		}
	}
	
	// Gets the ten worst companies, NOT based on selected categories
	private static void gatherBottomOverallCompanies() {
		for(int i = 0; i < 10; ++i) _bottomOverallCompanies.add(_allCompanies.get(i));
	}
	
	// Gets the ten best companies, based on selected categories
	private static void gatherTopCompaniesBasedOnCategorySelection() {		
		for(int i = _allCompanies.size() - 1; i > _allCompanies.size() - 11; --i) { // Get the last ten (lowest impact)
			_topCategorySelectionBasedCompanies.add(_allCompanies.get(i));
		}
	}
	
	// Gets the ten worst companies, based on selected categories
	private static void gatherBottomCompaniesBasedOnCategorySelection() {
		for(int i = 0; i < 10; ++i) _bottomCategorySelectionBasedCompanies.add(_allCompanies.get(i));
	}
}