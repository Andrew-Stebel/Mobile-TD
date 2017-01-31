// Author:		Eric Sylvain
// Date:		Dec 8th, 2013
// Description:	ListView adapter for the Company class, and CompaniesActivity, using the company_line.xml layout


package com.example.td;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CompanyItemAdapter extends ArrayAdapter<Company> {
	private TextView[] tvs = new TextView[8];
	private Context context;
	private List<Company> items;

	public CompanyItemAdapter(Context context, int resource, List<Company> objects) {
		super(context, resource, objects);
		this.context = context;
		this.items = objects;		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.company_line, null);
        }
		tvs[0] = (TextView)view.findViewById(R.id.txtGov);
		tvs[1] = (TextView)view.findViewById(R.id.txtBoa);
		tvs[2] = (TextView)view.findViewById(R.id.txtPay);
		tvs[3] = (TextView)view.findViewById(R.id.txtOwn);
		tvs[4] = (TextView)view.findViewById(R.id.txtAcc);
		tvs[5] = (TextView)view.findViewById(R.id.txtEnv);
		tvs[6] = (TextView)view.findViewById(R.id.txtSoc);
		tvs[7] = (TextView)view.findViewById(R.id.txtOverall);

        final Company c = items.get(position);
        if (c != null) {
        	DecimalFormat df = new DecimalFormat("##0.00");
        	
        	((TextView)view.findViewById(R.id.txtName)).setText(c.get_name());
        	tvs[0].setText("G: " + df.format(c.get_govImpact()) + "%");
        	tvs[1].setText("B: " + df.format(c.get_boaImpact()) + "%");
        	tvs[2].setText("P: " + df.format(c.get_payImpact()) + "%");
        	tvs[3].setText("O: " + df.format(c.get_ownImpact()) + "%");
        	tvs[4].setText("A: " + df.format(c.get_accImpact()) + "%");
        	tvs[5].setText("E: " + df.format(c.get_envImpact()) + "%");
        	tvs[6].setText("S: " + df.format(c.get_socImpact()) + "%");

        	// Hide/show categories depending on the toggle to only show selected categories - also change the overall impact number
        	if( ((CompaniesActivity)context).showAllCategories ) {
        		for(int i = 0; i < 7; ++i) tvs[i].setVisibility(View.VISIBLE); // Show all categories
    			tvs[7].setText("Overall: " + df.format(c.get_ovllImpactIgnCats()) + "%");
        	}
        	else {
        		boolean[] bools = CustomerData.get_categorySelection();
        		
        		for(int i = 0; i < bools.length; ++i) { // Show/hide category selection
        			if(bools[i]) 	tvs[i].setVisibility(View.VISIBLE);
        			else 			tvs[i].setVisibility(View.GONE);        			
        		}
    			tvs[7].setText("Overall: " + df.format(c.get_ovllImpact()) + "%");
        	}
         }
        return view;
    }
}