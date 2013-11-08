package com.cor.devplan;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class XYInputActivity extends Activity {

	private String strAddress = "";
	Geocoder geocoder;
	private String latStr = "";
	private String lngStr = "";
	private String lngLat = "";
	private static final String diameter = "100";
	String addressString = "No address found";
	LocationManager locationManager;
	LocationListener locationListener;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit);
		TextView searchaddress = (TextView)findViewById(R.id.searchdescr);
		searchaddress.setText(Html.fromHtml(getString(R.string.searchaddress)));							
	}
	
	/** Handle address search */
    public void onSubmitClick(View v) {
    	
    	final EditText et;	
		et = (EditText) findViewById(R.id.edittext);
		strAddress = et.getText().toString();
		
		// use geocoder to get lat long from address
		Geocoder coder = new Geocoder(this);
		List<Address> address = null;    			
		
		    try {
				address = coder.getFromLocationName(strAddress,1);
			
		    if (address == null || address.isEmpty()) {
		    	Toast msg1 = Toast.makeText(getBaseContext(), "Invalid Search Entry Please Try Again",
						Toast.LENGTH_LONG);
				msg1.show();		    
			} else {
				Address location = address.get(0);
				Log.i("lat from Address Search",""+location.getLatitude());
				Log.i("lng from Address Search",""+location.getLongitude());
				latStr = Double.toString(location.getLatitude());
				lngStr = Double.toString(location.getLongitude());
				lngLat = lngStr + "," + latStr;
				Intent i = new Intent(XYInputActivity.this, JsonDownloaderActivity.class);   
				i.putExtra("lngLat", lngLat);
				i.putExtra("area", diameter);	
								
				StringBuilder sb = new StringBuilder();
				if (address.size() > 0) {
					 for (int cnt = 0; cnt < location.getMaxAddressLineIndex(); cnt++)
	                        sb.append(location.getAddressLine(cnt)).append("\n");
				}
				addressString = sb.toString();
				Log.i("Address!!!!!!!!",addressString);
				i.putExtra("address", addressString);
				
				startActivity(i);
				
			}	   

		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
    }
    
    	
	/** Handle "schedule" action. */
    public void onLocationClick(View v) {
        //startActivity(new Intent(this, JsonDownloaderActivity.class));
    	Log.i("onLocationClick","onLocationClick");
    	// Acquire a reference to the system Location Manager
    	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    	// Define a listener that responds to location updates
    	locationListener = new LocationListener() {
    		
    	    public void onLocationChanged(Location location) {
    	      // Called when a new location is found by the network location provider.
    	      // makeUseOfNewLocation(location);
    	    	Double lat = location.getLatitude();
    	    	Double lng = location.getLongitude();
    	    	Log.i("lat,lng from Location GPS manager",""+lat+" "+lng);
    	    	latStr = Double.toString(lat);
				lngStr = Double.toString(lng);
				lngLat = lngStr + "," + latStr;
				Intent j = new Intent(XYInputActivity.this, JsonDownloaderActivity.class);   
				j.putExtra("lngLat", lngLat);
				j.putExtra("area", diameter);
				locationManager.removeUpdates(locationListener);
				startActivity(j);
				
    	    }

    	    public void onStatusChanged(String provider, int status, Bundle extras) {
    	    	Log.i("onStatusChanged",provider);
    	    }

    	    public void onProviderEnabled(String provider) {
    	    	Log.i("onProviderEnabled",provider);
    	    }

    	    public void onProviderDisabled(String provider) {
    	    	Log.i("onProviderDisabled",provider);
    	    }
    	  };
    	  
    	// Register the listener with the Location Manager to receive location updates
    	// CHF - 05/15/2012 be careful here the integers control the frequency of the updates to your location
    	// second is the minimum time interval between notifications and the third is the minimum change
    	// in distance between notifications—setting both to zero requests location notifications 
    	// as frequently as possible
    
    	  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    	
    	
    }
	

}
