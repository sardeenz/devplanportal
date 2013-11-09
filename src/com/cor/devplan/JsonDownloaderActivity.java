package com.cor.devplan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class JsonDownloaderActivity extends Activity {
	
	
	String address = "no address available";
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        ProgressDialog dialog = ProgressDialog.show(JsonDownloaderActivity.this, "", 
                "Loading. Please wait...", true);
        dialog.setCancelable(true);
        dialog.show();
        
//        String xycoords = "-78.6776144615359, 35.7765311188116";
//		String diameter = "100";
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
        		return;
        		}
        // Get data via the key
        String lngLat = extras.getString("lngLat");
        if (lngLat != null) {
        	// Do something with the data
        	Log.i("lngLat............",lngLat);
        }
        
        String area = extras.getString("area");
        if (area != null) {
        	// Do something with the data
        	Log.i("area............",area);
        }
        
        address = extras.getString("address");
        if (address != null) {
        	// Do something with the data
        	Log.i("address............",address);
        }
		
        new DownloadJsonTask().execute(lngLat, area);
        
        Log.i("inside JsonDownloadActivity","inside JsonDownloadActivity");
    }
    
    private void runOnPostExecute(ArrayList<Development> developments) {
//		ListView listView = (ListView) findViewById(R.id.ListViewId);
//        listView.setAdapter(new TweetItemAdapter(this, R.layout.feedslistitem, tweets));
    	Iterator<Development> iter = developments.iterator();
    	String name = "";
    	Double lat = 0.0;
    	Double lng = 0.0;
    	String file = "";
    	String year = "";
    	while (iter.hasNext()) {
    		Development dev = iter.next();
    		name = dev.getCaseName();
    		year = dev.getCaseYear();
    		lat = dev.getLatitude();
    		lng = dev.getLongitude();
    		
    	}
//    	TextView namesview = (TextView)findViewById(R.id.CaseName);
//    	namesview.setText(name);
//    	Log.i("names",name);
//    	
//    	TextView yearview = (TextView)findViewById(R.id.Year);
//    	yearview.setText(year);
//    	
//    	Intent i = new Intent(JsonDownloaderActivity.this, PlacesActivity.class);   
////		i.putExtra("lng", lng);
////		i.putExtra("lat", lat);
////		i.putExtra("name", name);
////		i.putExtra("year", year);
//		Bundle b = new Bundle();
//		b.putParcelableArrayList("developmentsObj", developments);
//		i.putExtras(b);
//		startActivity(i);
    	if (developments.size() < 1){
            Toast msg = Toast.makeText(getBaseContext(), "No results found, Please try again",
                                Toast.LENGTH_LONG);
                   msg.show();
                   Intent i = new Intent(JsonDownloaderActivity.this, XYInputActivity.class);
                   startActivity(i);
     } else {      
            Intent i = new Intent(JsonDownloaderActivity.this, PlacesActivity.class);   
                   Bundle b = new Bundle();
                   b.putParcelableArrayList("developmentsObj", developments);
                   i.putExtras(b);      
                   i.putExtra("address", address);
                   startActivity(i);
     }

    	
	}
    
	private class DownloadJsonTask extends AsyncTask<String, Void, ArrayList<Development>> {
//		public TwitterDownloader activity;
//		public DownloadTwitterTask(TwitterDownloader a){
//			 activity = a;
//		}
	     protected ArrayList<Development> doInBackground(String... parms) {
	         return loadDevelopmentsFromNetwork(parms[0],parms[1]);
	     }

	     protected void onPostExecute(ArrayList<Development> developments) {
	         Log.i("onPostExecute","do the page layout here");
	         runOnPostExecute(developments);
//	         ListView listView = (ListView) findViewById(R.id.ListViewId);
//	         listView.setAdapter(new TweetItemAdapter(this, R.layout.feedslistitem, developments));
	     }
	 }
	 
//		private ArrayList<Development> loadDevelopmentsFromNetwork(String coords, String distance) {
//			ArrayList<Development> developments = new ArrayList<Development>();
//
//			JSONObject fullResponseJSONObject = null;
//			JSONArray resultsJSONArray = null;
//			//JSONParser parser=new JSONParser();		
//			String responseBody = pointToPolygon(coords, distance);
//			Log.i("JSON Response pointToPolygon", responseBody);
//			try {
//				fullResponseJSONObject = new JSONObject(responseBody);	
//				resultsJSONArray = fullResponseJSONObject.getJSONArray("geometries");
//				JSONObject rings = resultsJSONArray.getJSONObject(0);
//				
//				responseBody = getDevelopmentPlans(rings.toString());
//				Log.i("JSON Response getDevelopmentPlans", responseBody);
//				if(responseBody != null){
//					fullResponseJSONObject = new JSONObject(responseBody);
//					resultsJSONArray = fullResponseJSONObject.getJSONArray("features");
//					
//					for(int i = 0; i < resultsJSONArray.length(); i++){
//						JSONObject developmentInfo = resultsJSONArray.getJSONObject(i);
//						JSONObject attributes = developmentInfo.getJSONObject("attributes");
//						Development dev = new Development();
//						dev.setId(attributes.getString("OBJECTID"));
//						dev.setCaseName(attributes.getString("FILE_NAME"));
//						dev.setCaseNum(attributes.getString("CASE_NUMBER"));
//						dev.setCaseYear(attributes.getString("CASE_YEAR"));
//						developments.add(dev);
//					}
//				}
//				if (resultsJSONArray != null && resultsJSONArray.length() != 0){
//					Log.i("results = are not null","results are not null"); 
//				} else {
//					Log.i("results = are null","results are null"); 
//				}
//			}catch(Exception ex){
//				Log.v("results","Exception: " + ex.getMessage());
//			}			
//			return developments;
//		}	 
		
	private ArrayList<Development> loadDevelopmentsFromNetwork(String coords, String distance) {
        ArrayList<Development> developments = new ArrayList<Development>();

        JSONObject fullResponseJSONObject = null;
        JSONArray resultsJSONArray = null;
        //JSONParser parser=new JSONParser();           
        String responseBody = pointToPolygon(coords, distance);
        Log.i("JSON Response pointToPolygon", responseBody);
        try {
              fullResponseJSONObject = new JSONObject(responseBody); 
              resultsJSONArray = fullResponseJSONObject.getJSONArray("geometries");
              JSONObject rings = resultsJSONArray.getJSONObject(0);
              
              responseBody = getDevelopmentPlans(rings.toString());
              Log.i("JSON Response getDevelopmentPlans", responseBody);
              if(responseBody != null){
                     fullResponseJSONObject = new JSONObject(responseBody);
                     resultsJSONArray = fullResponseJSONObject.getJSONArray("features");
                     
                     for(int i = 0; i < resultsJSONArray.length(); i++){
                            JSONObject developmentInfo = resultsJSONArray.getJSONObject(i);
                            JSONObject attributes = developmentInfo.getJSONObject("attributes");
                            JSONObject geometryrings = developmentInfo.getJSONObject("geometry");
                            Development dev = new Development();
                            dev.setId(attributes.getString("OBJECTID"));
                            dev.setCaseName(attributes.getString("FILE_NAME"));
                            dev.setCaseNum(attributes.getString("FILE_NUM"));
                            dev.setCaseYear(attributes.getString("CASE_YEAR"));
                            JSONObject xycoords = areaToPoint(geometryrings);
                            xycoords = xycoords.getJSONArray("labelPoints").getJSONObject(0);
                            dev.setLat(xycoords.getDouble("y"));
                            dev.setLong(xycoords.getDouble("x"));
                            developments.add(dev);
                     }
              }
              if (resultsJSONArray != null && resultsJSONArray.length() != 0){
                     Log.i("results = are not null","results are not null"); 
              } else {
                     Log.i("results = are null","results are null"); 
              }
        }catch(Exception ex){
              Log.v("results","Exception: " + ex.getMessage());
        }                    
        return developments;
 }      

	
//		/**
//		 * Gets the development plans within the given poly (usually a circle)
//		 * @param geometry the circle coordinates
//		 * @return any development plans within the polygon
//		 */
//		private String getDevelopmentPlans(String geometry){
//			String result = null;
//			String url = "http://maps.raleighnc.gov/ArcGIS/rest/services/Geoportal/DataMapService/MapServer/16/query";
//			List<NameValuePair> parms = new ArrayList<NameValuePair>(9);
//			parms.add(new BasicNameValuePair("geometry", geometry));
//	        parms.add(new BasicNameValuePair("geometryType", "esriGeometryPolygon"));
//	        parms.add(new BasicNameValuePair("spatialRel", "esriSpatialRelIntersects"));
//	        parms.add(new BasicNameValuePair("returnIdsOnly","false"));
//	        parms.add(new BasicNameValuePair("returnCountOnly","false"));
//	        parms.add(new BasicNameValuePair("returnGeometry", "true"));
//	        parms.add(new BasicNameValuePair("outFields", "*"));
//	        parms.add(new BasicNameValuePair("inSR", "4326"));
//	        parms.add(new BasicNameValuePair("f", "pjson"));
//			result = doPost(url, parms);
//			return result;
//		}
		
		/**
         * Gets the development plans within the given poly (usually a circle)
         * @param geometry the circle coordinates
         * @return any development plans within the polygon
         */
         private String getDevelopmentPlans(String geometry){
                String result = null;
                //String url = "http://maps.raleighnc.gov/ArcGIS/rest/services/Geoportal/DataMapService/MapServer/16/query";
                String url = "http://maps.raleighnc.gov/arcgis/rest/services/Geoprocessing/DataMapService/MapServer/16/query";
                List<NameValuePair> parms = new ArrayList<NameValuePair>(9);
                parms.add(new BasicNameValuePair("geometry", geometry));
          parms.add(new BasicNameValuePair("geometryType", "esriGeometryPolygon"));
          parms.add(new BasicNameValuePair("spatialRel", "esriSpatialRelIntersects"));
          parms.add(new BasicNameValuePair("returnIdsOnly","false"));
          parms.add(new BasicNameValuePair("returnCountOnly","false"));
          parms.add(new BasicNameValuePair("returnGeometry", "true"));
          parms.add(new BasicNameValuePair("outFields", "*"));
          parms.add(new BasicNameValuePair("inSR", "4326"));
          parms.add(new BasicNameValuePair("outSR", "4326"));
          parms.add(new BasicNameValuePair("f", "pjson"));
                result = doPost(url, parms);
                return result;
         }


	

		/**
		 * Takes in the given url and post form values and gets the string response
		 * 
		 * @param url   - rest service URL
		 * @param parms - field values for service
		 * @return String representation of json response
		 */
		private static String doPost(String url, List<NameValuePair> parms){
			String result = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response;
	        try {
	        	httppost.setEntity(new UrlEncodedFormEntity(parms));
				response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				if (entity != null) {    			
					// A Simple JSON Response Read
		            InputStream instream = entity.getContent();
		            result= convertStreamToString(instream);
//		            System.out.println(result);
				}
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return result;
		}
		
		/**
		 * 
		 * @param xycoords - the x,y coords in the format X,Y
		 * @return the polygon json response
		 */
		private static String pointToPolygon(String xycoords, String diameter){
			String result = null;
			//String url = "http://maps.raleighnc.gov/ArcGIS/rest/services/Geometry/GeometryServer/buffer";
			String url = "http://maps.raleighnc.gov/arcgis/rest/services/Utilities/Geometry/GeometryServer/buffer";
			List<NameValuePair> parms = new ArrayList<NameValuePair>(9);
			parms.add(new BasicNameValuePair("geometries", xycoords));
			parms.add(new BasicNameValuePair("inSR", "4326"));
			parms.add(new BasicNameValuePair("outSR", "4326"));
			parms.add(new BasicNameValuePair("bufferSR", "2264"));
			parms.add(new BasicNameValuePair("distances", "100"));
			parms.add(new BasicNameValuePair("unit", "9002"));
			parms.add(new BasicNameValuePair("unionResults", "false"));
			parms.add(new BasicNameValuePair("geodesic", "true"));
			parms.add(new BasicNameValuePair("f", "pjson"));
			
			result = doPost(url, parms);
			return result;
		}
		
		private static String convertStreamToString(InputStream is) {
	        /*
	         * To convert the InputStream to String we use the BufferedReader.readLine()
	         * method. We iterate until the BufferedReader return null which means
	         * there's no more data to read. Each line will appended to a StringBuilder
	         * and returned as String.
	         * TODO: Add toast message when downloading
	         */
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	 
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
		
		/**
         * Given an area, this will give back a point at the center
         * @param rings - the polygon
         * @return the gps point as a json object
         */
         private JSONObject areaToPoint(JSONObject rings){
                JSONObject point =  null;
                String result;
                try {
                      JSONArray array = new JSONArray();
                      array.put(rings);
                      String url = "http://maps.raleighnc.gov/ArcGIS/rest/services/Geometry/GeometryServer/labelPoints";
                      List<NameValuePair> parms = new ArrayList<NameValuePair>(9);
                      parms.add(new BasicNameValuePair("polygons", array.toString()));
                 parms.add(new BasicNameValuePair("sr", "4326"));
                 parms.add(new BasicNameValuePair("f", "pjson"));
                      result = doPost(url, parms);
                      point = new JSONObject(result);
                } catch (JSONException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                }
                
                return point;
         }

}
