// Author:		Eric Sylvain
// Date:		Dec 8th, 2013
// Description:	ListView adapter that will hold Transaction items and display them in company_line.xml layouts


package com.example.td;

import java.text.DecimalFormat;
import java.util.List;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;


public class TransactionItemAdapter extends ArrayAdapter<Transaction> {
	private Context context;
	private List<Transaction> items;

	public TransactionItemAdapter(Context context, int resource, List<Transaction> objects) {
		super(context, resource, objects);
		this.context = context;
		this.items = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.transaction_line, null);
        }

        final Transaction t = items.get(position);
        if (t != null) {
        	DecimalFormat df = new DecimalFormat("##0.00");
        	String impact = (t.get_company() == null) ? "N/A" : df.format(t.get_company().get_ovllImpact()) + "%"; // N/A if no company
        	
            ((TextView)view.findViewById(R.id.txtName)).setText(t.get_companyName());            
            ((TextView)view.findViewById(R.id.txtRating)).setText( "Negative Impact: " + impact );
            
            df = new DecimalFormat("$###,###,##0.00");
            ((TextView)view.findViewById(R.id.txtAmount)).setText( df.format(t.get_amount()) );
            
            ((Button)view.findViewById(R.id.btnDetails)).setOnClickListener(new OnClickListener() { // Add click event to the button
				@Override public void onClick(View v) {
					Intent details = new Intent(context, CompanyDetailsActivity.class);
					Bundle b = new Bundle();
					b.putInt("transidx", t.get_transNum());
					details.putExtras(b);
					context.startActivity(details);
				}
        	});
         }
        return view;
    }
}