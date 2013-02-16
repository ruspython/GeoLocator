package com.example.coomap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, LocationListener{
	Button btn_find, btn_get;
	EditText et1,et2;
	String x,y;
	public static final String MYLOG = "My logs";
	private LocationManager locMan;
	private String provider;
	
	public static double latitude; //широта
	public static double longitude; //долгота
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_find = (Button) findViewById(R.id.button1);
        btn_find.setOnClickListener(this);
        
        btn_get = (Button) findViewById(R.id.button2);
        btn_get.setOnClickListener(this);
        
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        
		locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locMan.getBestProvider(criteria, false);
		Location location = locMan.getLastKnownLocation(provider);
		
		if (location!=null){
			onLocationChanged(location);
		}
		else 
		{
			Toast.makeText(getApplicationContext(), "Location unavailable", Toast.LENGTH_LONG).show();
		}
		
		
		Toast.makeText(getApplicationContext(),
				"LOCATION = "+location+"\nPROVIDER = "+provider,
				Toast.LENGTH_LONG).show();
    }
	public void onClick(View v) {



		Intent intent;
		x = et1.getText().toString();
		y = et2.getText().toString();
		
		switch(v.getId()){
		
		case R.id.button1:
			if (!x.equals("")&&!y.equals("")){
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("geo:"+x+","+y));
			startActivity(intent);
			}
			break;
	
		case R.id.button2:
			et1.setText(latitude+"");
			et2.setText(longitude+"");	
			Toast.makeText(getApplicationContext(), "Клик", Toast.LENGTH_LONG).show();
			break;
		}
	
	}
	public void onLocationChanged(Location loc) {
		latitude = loc.getLatitude();
		longitude = loc.getLongitude(); 
	}
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS is disabled", Toast.LENGTH_LONG).show();//  не работает
	}
	public void onProviderEnabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS works", Toast.LENGTH_LONG).show();  //  работает
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		locMan.removeUpdates(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		locMan.requestLocationUpdates(provider, 400, 1, this);
	}


}
