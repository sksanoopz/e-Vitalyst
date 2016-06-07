package com.example.e_vitalyst;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebPage extends Activity{
	
	private WebView mWebview ;
	String domain_name,port;
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	 final ProgressDialog pd = ProgressDialog.show(WebPage.this, "e-Vitalyst", "Please wait, your transaction is being processed...", true);
         pd.setCanceledOnTouchOutside(true);
    	 super.onCreate(savedInstanceState);
        setTitle("e-Vitalyst ERP");
        mWebview  = new WebView(this);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        
        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
        	@Override
        	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        	{
        		Log.e(String.valueOf(errorCode), description);
        		// API level 5: WebViewClient.ERROR_HOST_LOOKUP	
        		if (errorCode == -2) 
        		{
        		    String summary = "<html><body style='background:weight;'><p style='color: red;'><br><br><br>     <img src='oops.png' width =200 height 300></img><br><h3><div align='center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unable to load <B><font color ='RED'>e-Vitalyst ERP</font></B>.<br> " +
        		    		"Please check if your network connection is working properly or try again later.</p></body></html>";    
        			view.loadDataWithBaseURL("file:///android_res/drawable/",summary, "text/html",  "utf-8", null);
        			return;
        		}
        	}
            public void onPageFinished(WebView view, String url) 
            {
                if(pd.isShowing())
                    pd.dismiss();
            }
        });
		 loadwebpage();
         setContentView(mWebview );
    }
    private void loadwebpage() {
		
    	 PO obj = new PO(getApplicationContext());
 		final SQLiteDatabase db = obj.getReadableDatabase();
 		 Cursor cur = db.rawQuery("SELECT * FROM Config", null);
 		 if (cur.moveToNext())
 		 {
 		     mWebview .loadUrl("http://"+cur.getString(0)+":"+cur.getString(1)+"/mobile/");
 			// mWebview .loadUrl("http://www.google.com");
 			 //http://172.10.10.77:8080/mobile/
 		 }
 		 db.close();
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		   case R.id.action_config:
			Intent i=new Intent(getApplicationContext(),ConfigurationActivity.class);
			startActivity(i);
		          break;
	      case R.id.action_refresh:
	    	loadwebpage();
		}
		 return true;

	}
	
}
class NoErrorWebViewClient extends WebViewClient 
{
	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
	{
		Log.e(String.valueOf(errorCode), description);
		// API level 5: WebViewClient.ERROR_HOST_LOOKUP	
		if (errorCode == -2) 
		{
			String summary = "<html><body style='background: black;'><p style='color: red;'>Unable to load information. Please check if your network connection is working properly or try again later.</p></body></html>";       view.loadData(summary, "text/html", null);
			return;
		}
	
		// Default behaviour
		super.onReceivedError(view, errorCode, description, failingUrl);

}
	


}

