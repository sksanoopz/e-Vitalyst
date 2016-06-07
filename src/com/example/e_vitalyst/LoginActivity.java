package com.example.e_vitalyst;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoginActivity extends Activity {
	  private static final int SPLASH_DURATION = 6000;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("e-Vitalyst ERP");
		 //start: Animation
        ImageView img1_lay=(ImageView)findViewById(R.id.imageView1);
        Animation tabanim=AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        img1_lay.startAnimation(tabanim);
        Animation fade3=AnimationUtils.loadAnimation(this, R.anim.fade_in2);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() 
        {
			@Override
			public void run() 
			{
				 PO obj = new PO(getApplicationContext());
					SQLiteDatabase db = obj.getReadableDatabase();
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				if (checkNetwork())
				{
				     if (login() == true)
				     {
				    	  Cursor cur = db.rawQuery("SELECT * FROM Config", null);
				    	  
				    	  if (cur.moveToNext())
				    	  {
				    		  finish();
				    		  Intent  intent   = new Intent(getApplicationContext(), WebPage.class);
				    		  startActivity(intent);
				    	  }
				    	  else
				    	  {
				    			builder.setMessage("Please set configuration !!");
				    			builder.setTitle("e-Vitalyst");
				    			builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
				    		    {
				    		        @Override
				    		        public void onClick(DialogInterface dialog, int whichButton)
				    		        {
				    		        	  finish();
				    		        	  Intent intent = new Intent(getApplicationContext(), ConfigurationActivity.class);
						    		      startActivity(intent);
				    		           
				    		        }
				    		    });
				    		    builder.create().show(); 
				    	  }
				     }
				}
				else
				{
					builder.setMessage("Please check the internet connection !!");
	    			builder.setTitle("e-Vitalyst");
	    			builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
	    		    {
	    		        @Override
	    		        public void onClick(DialogInterface dialog, int whichButton)
	    		        {
	    		        	android.os.Process.killProcess(android.os.Process.myPid());
	                        System.exit(1);
	    		           
	    		        }
	    		    });
	    		    builder.create().show();
				}
				
			}

			//Check if Internet Network is active
			private boolean checkNetwork() 
			{
				boolean wifiDataAvailable = false;
				boolean mobileDataAvailable = false;
				ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
				for (NetworkInfo netInfo : networkInfo) {
				if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
				    if (netInfo.isConnected())
				        wifiDataAvailable = true;
				    if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
				        if (netInfo.isConnected())
				        mobileDataAvailable = true;
				    }
				return wifiDataAvailable || mobileDataAvailable;
			}

		private boolean login()
	    {
		 
		  Authenticator obj=new Authenticator();
		  obj.execute("select","sa");
		  return true;
	    }
		}, SPLASH_DURATION);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_page, menu);
	        //add MenuItem(s) to ActionBar using Java code
	        MenuItem menuItem_Info = menu.add(0, R.id.menuid_info, 0, "About Us");
	        menuItem_Info.setIcon(android.R.drawable.ic_menu_info_details);
	        MenuItemCompat.setShowAsAction(menuItem_Info, 
	        MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	        
	        return true;
		
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.action_settings:
			Intent i=new Intent(getApplicationContext(),ConfigurationActivity.class);
			startActivity(i);
		}
		return true;

	}
	  private class Authenticator extends AsyncTask<String, String, String> {
	    	ProgressDialog dlg;
	    	@Override
	        protected void onPreExecute()
	        {
	        	dlg=ProgressDialog.show(LoginActivity.this, "e-Vitalyst", "Authenticating");
	        	dlg.setCancelable(false);
	        	
	        }
	    	
	    	@Override
	        protected String doInBackground(String... urls) 
	    	{
	    		PO obj = new PO(getApplicationContext());
	    		SQLiteDatabase db = obj.getReadableDatabase();
	    		 Cursor cur = db.rawQuery("SELECT * FROM Config", null);

	        	 {
	        		publishProgress("Loading");
	        		
	        		try
	        		{
	        	
	        		    publishProgress("fare1");
	        		    if (!cur.moveToNext())
	        		    {
	        		    	return "no";
	        		    }
	        		}
	        		
	        		catch(Exception e){
	        			
	        		}
	        		return "yes";
	        	}
	        }
	    	@Override
	    	protected void onProgressUpdate(String... progress) {
	    		if(progress[0].compareTo("fare1")==0)
	    			dlg.setMessage("Loading VPN settings..");
	    		    dlg.setCancelable(false);
	
	        }
	    	@Override
	    	protected void onPostExecute(String res)
	    	{
	    		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

	    		if(res=="yes")
	    		{
	    			dlg.dismiss();
	    			//Toast.makeText(Login.this, "Failed", 2000).show();
	    			builder.setMessage("Please set configuration !!");
	    			builder.setTitle("e-Vitalyst");
	    			builder.setPositiveButton("OK", null);
	    			builder.show();
	    		}
	    		else
	    		{
	    			dlg.dismiss();

	    		}
	    	}
	    	
	    }

}
