package com.example.td;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class HelpActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		Bundle b = getIntent().getExtras();
		String page = b.getString("page");
		
		if (page.equalsIgnoreCase("overview")) helpOverview();
		else if(page.equalsIgnoreCase("main")) helpMain();	
		else if(page.equalsIgnoreCase("details")) helpDetails();
		else if(page.equalsIgnoreCase("companies")) helpCompanies();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

	// Displays help for the CompanyOverview activity
		private void helpOverview()	{
			((TextView)findViewById(R.id.textView1)).setText("All of your transactions are displayed here. The higher a company's negative impact is, the worse they're rated.");
			((TextView)findViewById(R.id.textView2)).setText("'Sort' - Click to sort the transactions based on the selected sort method in the drop down menu.");
			((TextView)findViewById(R.id.textView3)).setText("'Details' - Click to examine a company's details in full, and compare them to the best and worst companies.");
			((TextView)findViewById(R.id.textView4)).setText("");
		}
		
		// Displays help for the Main activity
		private void helpMain() {
			((TextView)findViewById(R.id.textView1)).setText("'Select Categories' - Click to choose which categories you feel are the most important for a company to be ethical in.");
			((TextView)findViewById(R.id.textView2)).setText("'Begin Analysis' - Click to view and sort your transaction data.");
			((TextView)findViewById(R.id.textView3)).setText("'Companies' - Click to view all companies in the database that are being monitored and rated.");
			((TextView)findViewById(R.id.textView4)).setText("");
		}
		
		// Display help for the CompanyDetails activity
		private void helpDetails() {
			((TextView)findViewById(R.id.textView1)).setText("Provides a detailed look at a company's negative impact ratings in each field.");
			((TextView)findViewById(R.id.textView2)).setText("The higher the percent (%) the more negative rating they have.");
			((TextView)findViewById(R.id.textView3)).setText("Compare this company to the best and worst companies in the database.");
			((TextView)findViewById(R.id.textView4)).setText("");
		}
		
		// Display help for the Companies activity
		private void helpCompanies() {
			((TextView)findViewById(R.id.textView1)).setText("Provides a list of all the companies and their impact ratings. The higher the percents (%) the more negative rating they have in that category.");
			((TextView)findViewById(R.id.textView2)).setText("'Show all categories' - Filter the company data based on the selected categories");
			((TextView)findViewById(R.id.textView3)).setText("'Sort' - Order the information based on the sort options provided.");
			((TextView)findViewById(R.id.textView4)).setText("'Search' - Filter the companies based on text search or top / bottom companies.");
		}
}