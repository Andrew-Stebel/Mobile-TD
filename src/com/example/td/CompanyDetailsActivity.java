// Author:		Eric Sylvain and Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	Displays all data for a specific company, and compares them to the top and worst companies


package com.example.td;

import java.text.DecimalFormat;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


public class CompanyDetailsActivity extends Activity implements OnClickListener {
	TextView[] tvs = new TextView[20]; // Total of 20 TextViews to work with...this helps only when setting them all to "N/A"

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_details);
		Bundle b = getIntent().getExtras();
		int transIdx = b.getInt("transidx");
		Transaction t = CustomerData.get_transactions().get(transIdx);
		
		tvs[0] = (TextView)findViewById(R.id.txtName);
		tvs[1] = (TextView)findViewById(R.id.txtGovernanceRating);
		tvs[2] = (TextView)findViewById(R.id.txtBoardRating);
		tvs[3] = (TextView)findViewById(R.id.txtPayRating);
		tvs[4] = (TextView)findViewById(R.id.txtOwnershipRating);
		tvs[5] = (TextView)findViewById(R.id.txtAccountingRating);
		tvs[6] = (TextView)findViewById(R.id.txtEnvironmentalRating);
		tvs[7] = (TextView)findViewById(R.id.txtSocialRating);
		tvs[8] = (TextView)findViewById(R.id.txtOverallRating);
		tvs[9] = (TextView)findViewById(R.id.txtOverallIgnoreCat);
		tvs[10] = (TextView)findViewById(R.id.txtBestWorst);
		tvs[11] = (TextView)findViewById(R.id.txtGovCompare);
		tvs[12] = (TextView)findViewById(R.id.txtBoaCompare);
		tvs[13] = (TextView)findViewById(R.id.txtPayCompare);
		tvs[14] = (TextView)findViewById(R.id.txtOwnCompare);
		tvs[15] = (TextView)findViewById(R.id.txtAccCompare);
		tvs[16] = (TextView)findViewById(R.id.txtEnvCompare);
		tvs[17] = (TextView)findViewById(R.id.txtSocCompare);
		tvs[18] = (TextView)findViewById(R.id.txtCategorySelectCompare);
		tvs[19] = (TextView)findViewById(R.id.txtOverallCompare);
		
		tvs[0].setText(t.get_companyName());
		
		if(t.get_company() == null) {
			for(TextView tv : tvs) tv.setText("N/A"); 	// Set all txt views to N/A
			tvs[10].setText("No data to compare to.");
		}
		else {
			// Top and bottom companies to compare data to
			Company top = CompanyData.get_topCategorySelectionBasedCompany();
			Company bot = CompanyData.get_bottomCategorySelectionBasedCompany();
			Company c = t.get_company();
			
			DecimalFormat df = new DecimalFormat("##0.00");
			tvs[1].setText(df.format(c.get_govImpact()));
			tvs[2].setText(df.format(c.get_boaImpact()));
			tvs[3].setText(df.format(c.get_payImpact()));
			tvs[4].setText(df.format(c.get_ownImpact()));
			tvs[5].setText(df.format(c.get_accImpact()));
			tvs[6].setText(df.format(c.get_envImpact()));
			tvs[7].setText(df.format(c.get_socImpact()));
			tvs[8].setText(df.format(c.get_ovllImpact()) + "%");
			tvs[9].setText(df.format(c.get_ovllImpactIgnCats()) + "%");
			
			setColour(tvs[1], c.get_govImpact());			
			setColour(tvs[2], c.get_boaImpact());			
			setColour(tvs[3], c.get_payImpact());			
			setColour(tvs[4], c.get_ownImpact());			
			setColour(tvs[5], c.get_accImpact());			
			setColour(tvs[6], c.get_envImpact());			
			setColour(tvs[7], c.get_socImpact());			
			setColour(tvs[8], c.get_ovllImpact());			
			setColour(tvs[9], c.get_ovllImpactIgnCats());
			
			tvs[10].setText(top.get_name() + " and " + bot.get_name());
			
			// Create all the calculation texts for the rest of the boxes in an array
			String[] calcTexts = new String[9];
			calcTexts[0] = 	df.format(top.get_govImpact()) + "% (-" +
				df.format(c.get_govImpact()-top.get_govImpact()) +  ")\t\t" +
				df.format(bot.get_govImpact()) + "% (" + addPlus(c.get_govImpact()-bot.get_govImpact())+
				df.format(c.get_govImpact()-bot.get_govImpact()) +  ")";
			
			calcTexts[1] = 	df.format(top.get_boaImpact()) + "% (-" +
				df.format(c.get_boaImpact()-top.get_boaImpact()) +  ")\t\t" +
				df.format(bot.get_boaImpact()) + "% (" + addPlus(c.get_boaImpact()-bot.get_boaImpact())+
				df.format(c.get_boaImpact()-bot.get_boaImpact()) +  ")";
			
			calcTexts[2] = 	df.format(top.get_payImpact()) + "% (-" +
				df.format(c.get_payImpact()-top.get_payImpact()) +  ")\t\t" +
				df.format(bot.get_payImpact()) + "% (" + addPlus(c.get_payImpact()-bot.get_payImpact())+
				df.format(c.get_payImpact()-bot.get_payImpact()) +  ")";
			
			calcTexts[3] = 	df.format(top.get_ownImpact()) + "% (-" +
				df.format(c.get_ownImpact()-top.get_ownImpact()) +  ")\t\t" +
				df.format(bot.get_ownImpact()) + "% (" + addPlus(c.get_ownImpact()-bot.get_ownImpact())+
				df.format(c.get_ownImpact()-bot.get_ownImpact()) +  ")";
			
			calcTexts[4] = 	df.format(top.get_accImpact()) + "% (-" +
				df.format(c.get_accImpact()-top.get_accImpact()) +  ")\t\t" +
				df.format(bot.get_accImpact()) + "% (" + addPlus(c.get_accImpact()-bot.get_accImpact())+
				df.format(c.get_accImpact()-bot.get_accImpact()) +  ")";
			
			calcTexts[5] = 	df.format(top.get_envImpact()) + "% (-" +
				df.format(c.get_envImpact()-top.get_envImpact()) +  ")\t\t" +
				df.format(bot.get_envImpact()) + "% (" + addPlus(c.get_envImpact()-bot.get_envImpact())+
				df.format(c.get_envImpact()-bot.get_envImpact()) +  ")";
			
			calcTexts[6] = 	df.format(top.get_socImpact()) + "% (-" +
				df.format(c.get_socImpact()-top.get_socImpact()) +  ")\t\t" +
				df.format(bot.get_socImpact()) + "% (" + addPlus(c.get_socImpact()-bot.get_socImpact())+
				df.format(c.get_socImpact()-bot.get_socImpact()) +  ")";
			
			calcTexts[7] = 	df.format(top.get_ovllImpact()) + "% (-" +
				df.format(c.get_ovllImpact()-top.get_ovllImpact()) +  ")\t\t" +
				df.format(bot.get_ovllImpact()) + "% (" + addPlus(c.get_ovllImpact()-bot.get_ovllImpact())+
				df.format(c.get_ovllImpact()-bot.get_ovllImpact()) +  ")";
			
			calcTexts[8] = 	df.format(top.get_ovllImpactIgnCats()) + "% (-" +
				df.format(c.get_ovllImpactIgnCats()-top.get_ovllImpactIgnCats()) +  ")\t\t" +
				df.format(bot.get_ovllImpactIgnCats()) + "% (" + addPlus(c.get_ovllImpactIgnCats()-bot.get_ovllImpactIgnCats())+
				df.format(c.get_ovllImpactIgnCats()-bot.get_ovllImpactIgnCats()) +  ")";
			
			// Set all the last texts
			for(int i = 0; i < 9; ++i) tvs[i+11].setText(calcTexts[i]);			
		}
		
		if(CustomerData.allCategoriesAreSelected()) {
			// Hide the category selection specific views since they will be the same as the overalls
			((LinearLayout)findViewById(R.id.categorySelectionOverall)).setVisibility(View.GONE);
			((TextView)findViewById(R.id.txtBasedCatSel)).setVisibility(View.GONE);
			tvs[18].setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.company_details, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnHelp:
			Intent help = new Intent(this, HelpActivity.class);
			Bundle b = new Bundle();
			b.putString("page", "details");
			help.putExtras(b);
			startActivity(help);
			break;
		}
	}
	
	// Returns a '+' depending on whether the number is positive
	public String addPlus(double total) {
		if (total > 0) 	return "+";
		else 			return "";
	}
	
	// Sets the colour of a text box based on a double's size
	public void setColour(TextView tv, double n) {
		String colour = "";
		
		if (n > 85) 				colour = "#FF0000";	//red
		else if ( n > 65 && n < 85) colour = "#FF6600";	//orange
		else if ( n > 45 && n < 65) colour = "#CC0099";	//purple
		else if ( n > 25 && n < 45) colour = "#3399FF";	//blue
		else 						colour = "#00CC00";	//green		

		tv.setTextColor(Color.parseColor(colour));
	}
}