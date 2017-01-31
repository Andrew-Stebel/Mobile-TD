// Author:		Eric Sylvain
// Date:		Dec 8th, 2013
// Description:	Transaction Overview - lists all transactions and some quick details for them


package com.example.td;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class TransactionOverviewActivity extends Activity implements OnClickListener {
	private List<Transaction> listItems = new ArrayList<Transaction>();
	private TransactionItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_overview);
		
		listItems = CustomerData.get_transactions();
		adapter = new TransactionItemAdapter(this, 0, listItems);		
		((ListView)findViewById(R.id.lstTransactions)).setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.transaction_overview, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSort:		
			
			switch(((Spinner)findViewById(R.id.lstSortType)).getSelectedItemPosition()) {
			
			case 0: // Default - Date
				Collections.sort(listItems);
				adapter.notifyDataSetChanged();
				break;
				
			case 1: // Company Rating
				Collections.sort(listItems, Transaction.TransactionCompanyImpactCompare);
				adapter.notifyDataSetChanged();
				break;
				
			case 2: // Money Spent
				Collections.sort(listItems, Transaction.TransactionAmountCompare);
				adapter.notifyDataSetChanged();
				break;
				
			case 3: // Alphabetical
				Collections.sort(listItems, Transaction.TransactionAlphabeticalCompare);
				adapter.notifyDataSetChanged();
				break;				
			}			

			// Reverse the list order if Ascending is selected			
			if(((Spinner)findViewById(R.id.lstSortDirection)).getSelectedItemPosition() == 1) Collections.reverse(listItems);			
			break;
			
		case R.id.btnHelp:
			Intent help = new Intent(this, HelpActivity.class);
			Bundle b = new Bundle();
			b.putString("page", "overview");
			help.putExtras(b);
			startActivity(help);
			break;
		}
	}
}