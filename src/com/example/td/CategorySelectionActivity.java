// Author:		Eric Sylvain
// Date:		Dec 8th, 2013
// Description:	Display check boxes for the user to select which categories are most important to them


package com.example.td;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


public class CategorySelectionActivity extends Activity implements OnClickListener {
	CheckBox[] chkBoxes = new CheckBox[7]; // To hold all our check boxes for simpler access

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_selection);
		
		chkBoxes[0] = (CheckBox)findViewById(R.id.chkGovernance);		
		chkBoxes[1] = (CheckBox)findViewById(R.id.chkBoard);
		chkBoxes[2] = (CheckBox)findViewById(R.id.chkPay);
		chkBoxes[3] = (CheckBox)findViewById(R.id.chkOwnership);
		chkBoxes[4] = (CheckBox)findViewById(R.id.chkAccounting);
		chkBoxes[5] = (CheckBox)findViewById(R.id.chkEnvironmental);
		chkBoxes[6] = (CheckBox)findViewById(R.id.chkSocial);

		// Check the boxes whose categories are selected
		for(int i = 0; i < 7; ++i) chkBoxes[i].setChecked( CustomerData.get_categorySelection()[i] );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category_selection, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSelectAll:
			for(int i = 0; i < 7; ++i) chkBoxes[i].setChecked( true );	 // Set all to true
			break;
			
		case R.id.btnClearSelection:
			for(int i = 0; i < 7; ++i) chkBoxes[i].setChecked( false );	 // Set all to false
			break;
			
		case R.id.btnDone:
			boolean[] sel = new boolean[7];
			
			boolean allFalse = true;
			for(int i = 0; i < 7; ++i) {				
				sel[i] = chkBoxes[i].isChecked(); 	// Get all the checks
				if(sel[i]) allFalse = false; 		// Track if they're all unselected
			}
			
			if(allFalse) { // Don't let them continue with no categories selected
				Toast.makeText(this, "You must select at least 1 category!", Toast.LENGTH_LONG).show();
				return;
			}			
			CustomerData.updateCategorySelection(sel);
			CompanyData.refreshCategorySelectionBasedLists();
			finish(); // Closes activity and brings previous one to the front
			break;
		}
	}
}