package com.cor.devplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DevPlanPortalActivity extends Activity {
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView welcome = (TextView)findViewById(R.id.welcome);
        welcome.setText(Html.fromHtml(getString(R.string.Welcome)));
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        
    }
    

    /** Handle "schedule" action. */
    public void onNextClick(View v) {
        // Launch overall conference schedule
        startActivity(new Intent(this, XYInputActivity.class));
    }
}