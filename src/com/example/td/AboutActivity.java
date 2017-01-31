// Author:		Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	Shows the about page text


package com.example.td;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
}