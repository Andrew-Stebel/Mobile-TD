// Author:		Eric Sylvain and Andrew Stebel
// Date:		Dec 8th, 2013
// Description: Main activity when user opens the app - loads customer data, and when go is pressed, it gathers company data


package com.example.td;

import java.io.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {
	DBAdapter db;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        db = new DBAdapter(this);     
        makeDBDirectory();
		
		CustomerData.loadCustomerData(getAssets()); // Takes AssetManager to load asset file cusdata.xml
		CompanyData.loadAllCompanies(db); // Takes DBAdapter to access the DB with the company data
    }
	
	// Make the directory if it does not exist
    public void makeDBDirectory() {
    	try {
            String destPath = "/data/data/com.example.td/databases/";
            File f = new File(destPath);
            f.delete();
            
            if (!f.exists()) { // Copy the db from the assets folder into the databases folder
            	f.mkdirs();
                f.createNewFile();
                CopyDB(getBaseContext().getAssets().open("GMI_DB"), new FileOutputStream(destPath + "GMI_DB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Copy the db contents to the new file
    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        // Copy 1Kb at a time
        byte[] buffer = new byte[1024];
        int length;
        
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSelectCategories:
			Intent categories = new Intent(this, CategorySelectionActivity.class);
			startActivity(categories);
			break;
		
		case R.id.btnStartAnalysis:
			CustomerData.loadCompanyData(db);
			Intent toOverview = new Intent(this, TransactionOverviewActivity.class);
			startActivity(toOverview);
			break;
			
		case R.id.btnCompanies:
			Intent companies = new Intent(this, CompaniesActivity.class);
			startActivity(companies);
			break;
			
		case R.id.btnAbout:
			Intent about = new Intent(this, AboutActivity.class);
			startActivity(about);
			break;
			
		case R.id.btnHelp:
			Intent help = new Intent(this, HelpActivity.class);
			Bundle b = new Bundle();
			b.putString("page", "main");
			help.putExtras(b);
			startActivity(help);
			break;
		}		
	}
}