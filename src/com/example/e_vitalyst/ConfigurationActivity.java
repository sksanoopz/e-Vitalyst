package com.example.e_vitalyst;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends Activity 
{
	//EditText domain =(EditText)findViewById(R.id.domain);
	//EditText port =(EditText)findViewById(R.id.port);

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		setTitle("e-Vitalyst ERP");
		final EditText domain =(EditText)findViewById(R.id.domain);
		final EditText port =(EditText)findViewById(R.id.port);
		Button submit = (Button)findViewById(R.id.button2);
		PO obj = new PO(getApplicationContext());
		final SQLiteDatabase db = obj.getReadableDatabase();
		 Cursor cur = db.rawQuery("SELECT * FROM Config", null);
		 if (cur.moveToNext())
		 {
			domain.setText(""+cur.getString(0)); //doamin name
			 port.setText(""+cur.getString(1));
		 }
		submit.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				String domain_name = domain.getText().toString();
				String port_name = port.getText().toString();
				/*if (domain_name.length() > 0 || port_name.length() > 0)
				{
					Toast.makeText(getApplicationContext(), "Fill both domain name and port", 4000).show();
				}*/
				db.execSQL("DELETE FROM config");
				db.execSQL("INSERT INTO config VALUES('"+domain_name+"','"+port_name+"')");
				Toast.makeText(getApplicationContext(), "Scessfully saved !!!", 2000).show();
				db.close();
				finish();
				Intent i=new Intent(getApplicationContext(),WebPage.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.configuration, menu);
		return true;
	}

}
