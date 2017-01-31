// Author:		Eric Sylvain and Andrew Stebel
// Date:		Dec 8th, 2013
// Desciption:	Displays all companies and allows sorting and filtering of them


package com.example.td;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


public class CompaniesActivity extends Activity implements OnClickListener {
	public boolean showAllCategories = true;
	
	private Spinner spSorts, spAscDes, spFilters;
	private ToggleButton tglAllCats;
	private List<Company> listItems = new ArrayList<Company>();
	private CompanyItemAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_companies);
		spSorts = (Spinner)findViewById(R.id.lstCompanySorts);
		spAscDes = (Spinner)findViewById(R.id.lstSortDirection);
		spFilters = (Spinner)findViewById(R.id.lstCompanyFilters);
		tglAllCats = (ToggleButton)findViewById(R.id.tglAllCategories);
		tglAllCats.setChecked(true);	 
		
		listItems = CompanyData.get_allCompanies();
		Collections.sort(listItems); // Sort the list of companies alphabetically
		adapter = new CompanyItemAdapter(this, 0, listItems);
		((ListView)findViewById(R.id.lstCompanies)).setAdapter(adapter);
		
		// Add key listener to txtSearch for pressing 'enter'
		((EditText)findViewById(R.id.txtSearch)).setOnKeyListener(new View.OnKeyListener() {			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if(keyCode == KeyEvent.KEYCODE_ENTER) {
						((Button)findViewById(R.id.btnSearch)).callOnClick();
						return true;
					}
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.companies, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.tglAllCategories:
			showAllCategories = tglAllCats.isChecked();
			FilterCompanies();
			break;
			
		case R.id.btnSort:
			SortCompanies();			
			break;		
			
		case R.id.btnSearch:
			FilterCompanies();
			break;
			
		case R.id.btnHelp:
			Intent help = new Intent(this, HelpActivity.class);
			Bundle b = new Bundle();
			b.putString("page", "companies");
			help.putExtras(b);
			startActivity(help);
			break;
		}
	}
	
	// Filters the companies based on the companies filter and text filter
	private void FilterCompanies() {
		int filter = spFilters.getSelectedItemPosition();
		
		switch(filter) {
		case 0: // Default - All
			listItems = CompanyData.get_allCompanies();
			break;
			
		case 1: // Best 10
			listItems = (showAllCategories) ? CompanyData.get_topOverallCompanies() : CompanyData.get_topCategorySelectionBasedCompanies();
			break;
			
		case 2: // Worst 10
			listItems = (showAllCategories) ? CompanyData.get_bottomOverallCompanies() : CompanyData.get_bottomCategorySelectionBasedCompanies();
			break;			
		}
		
		// Now filter what's left by text
		String searchTxt = ((EditText)findViewById(R.id.txtSearch)).getText().toString().toLowerCase();
		String[] filters = searchTxt.split(" ");
		List<Company> tempList = new ArrayList<Company>(); // To hold all matches, then set listItems to it
		
		for(Company c : listItems) {
			boolean isMatch = true;
			String name = c.get_name().toLowerCase();
			
			for(String f : filters) { // Check against each word in the search filter
				if(!name.contains(f)) {
					isMatch = false;
					break;
				}
			}				
			if(isMatch) tempList.add(c);
		}
		listItems = tempList;
		
		// Reset the adapter
		adapter = new CompanyItemAdapter(this, 0, listItems);		
		((ListView)findViewById(R.id.lstCompanies)).setAdapter(adapter);
		
		// Need to sort now since the list will have changed
		SortCompanies();
	}
	
	// Sorts the companies based on the sort type and direction
	private void SortCompanies() {
		int type = spSorts.getSelectedItemPosition();
		int dir = spAscDes.getSelectedItemPosition();
		
		switch(type) {
		case 0: // Default - Name
			Collections.sort(listItems);
			break;
			
		case 1: // Overall Impact
			if(showAllCategories)
				Collections.sort(listItems, Company.CompanyOverallImpactCompare);
			else
				Collections.sort(listItems, Company.CompanyCategoryBasedImpactCompare);
			
			break;
		}

		// Reverse the list order if Ascending is selected
		if(dir == 1) Collections.reverse(listItems);

		adapter.notifyDataSetChanged();
	}
}