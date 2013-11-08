package com.cor.devplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class EventsActivity extends Activity{

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        Log.i("inside EventsActivity","inside EventsActivity");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
        		return;
        		}
        // Get data via the key
        String title = extras.getString("title");
        if (title != null) {
        	// Do something with the data
        	Log.i("title............",title);
        }
        
        String snippet = extras.getString("snippet");
        if (snippet != null) {
        	// Do something with the data
        	Log.i("snippet............",snippet);
        }
        
    	TextView titleview = (TextView)findViewById(R.id.casename);
    	titleview.setText(title);
        
    	TextView snippetview = (TextView)findViewById(R.id.caseyear);
    	snippetview.setText(snippet);

	}
	
	public void onDownloadClick(View v) {
		Log.i("download button was clicked","download button was clicked");
		Intent intent = new Intent(this, WebViewActivity.class);
	    startActivity(intent);
		//"http://www.raleighnc.gov/content/PlanCurrent/Documents/DevelopmentPlansReview/PlansInReview/2011/PlansSubmittalMapsByType/SitePlan/SP-001-11.pdf"
	}
	
}	
	
