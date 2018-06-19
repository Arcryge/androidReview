package qdlg.lcb.demo.LocationServiceDemo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationServiceDemo extends Activity {
	
	//ϵͳ�������
	private LocationManager locationManager;
	
	//��λ��λ��
	private Location position;
	
	//��λ��ʽ
	String provider=LocationManager.GPS_PROVIDER;
		
	//�Ƿ����ڶ�λ--GPS�Ƿ����ڹ���?
	boolean isLocating;
	
	//��ť����--��ʼ,ֹͣ
	private Button locate;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        isLocating=false;
        locate=(Button)findViewById(R.id.locate);
        locate.setText("��ʼ��λ");
        
        //��ȡλ�÷���������
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager==null){
        	Toast.makeText(this, "��ȡϵͳ����ʧ��!", Toast.LENGTH_SHORT).show();
        	return;
        }
        
        //���û�д�GPS,��ת��GPS����ҳ��
        if (!locationManager.isProviderEnabled(provider)){
        	(new AlertDialog.Builder(this))
        	.setTitle("��ʾ")
        	.setMessage("��Ҫ������Ӧ�Ķ�λ�豸")
        	.setPositiveButton("ȷ��", new OnClickListener() {	//���ȷ��������öԻ���		
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//������ҳ��
		        	Intent intent = new Intent();
		            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            try 
		            {
		                startActivity(intent);
		            } catch(ActivityNotFoundException ex) 
		            {
		                // The Android SDK doc says that the location settings activity
		                // may not be found. In that case show the general settings.                
		                // General settings activity
		                intent.setAction(Settings.ACTION_SETTINGS);
		                try {
		                       startActivity(intent);
		                } 
		                catch (Exception e) {
		                }
		            }
		        }

			})
        	.create().show();
		}
        //��ȡ��λ��Ϣ
        position=locationManager.getLastKnownLocation(provider);
        
        //��ʾλ����Ϣ
        ShowLocationInfo(position);
        
        locate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isLocating){ //������ڶ�λ
					locationManager.removeUpdates(listener);//ֹͣ��λ
					locate.setText("��ʼ��λ");
					isLocating=false;
				}
				else{
					//����λ�øı�ļ�����
			        //ʱ�����2��,�������10��ʱ֪ͨ�ı�
					locationManager.requestLocationUpdates(provider, 2000, 10, listener);
					locate.setText("ֹͣ��λ");
					isLocating=true;
				}
				
			}
		});
        
    }
    
    //��ʾλ����Ϣ
    private void ShowLocationInfo(Location position){
    	String s="";
    	
    	if (position==null){
    		((TextView)findViewById(R.id.info)).setText("û�п��õ�λ����Ϣ!");
    		return;    		
    	}
    	
    	//��ȡλ����Ϣ
    	s+="����: ";
    	s+=position.getLongitude()+"\n";
    	s+="γ��: ";
    	s+=position.getLatitude()+"\n";
    	s+="�߶�: ";
    	s+=position.getAltitude()+"\n";
    	s+="���ٶ�: ";
    	s+=position.getAccuracy()+"\n";
    	s+="����: ";
    	s+=position.getBearing();
    	
    	//������γ�ȶ�Ӧ�ĵ�ַ��Ϣ
    	List<Address> address=null;
    	Geocoder gc=new Geocoder(LocationServiceDemo.this,Locale.CHINA);
    	try {
			address=gc.getFromLocation(position.getLatitude(), position.getLongitude(), 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String t="";
		if (address!=null && address.size()>0){
			for (int i=0; i<address.size(); i++){
				t+=address.get(i).toString()+"\n";
			}
		}
		else
			t="û�н�������ַ...\n";
    	
    	//��ʾλ����Ϣ
    	((TextView)findViewById(R.id.info)).setText(s+"\n"+t);
    }
    
    //λ�øı�ļ�����--׷���ƶ�
    private LocationListener listener=new LocationListener() {		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			ShowLocationInfo(null);
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			ShowLocationInfo(null);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			ShowLocationInfo(location);
		}
	};

	@Override
	protected void onDestroy() { //�ر�Activityʱֹͣ����GPS��λ��Ϣ
		// TODO Auto-generated method stub
		super.onDestroy();
		if(isLocating){ //������ڶ�λ
			locationManager.removeUpdates(listener);//ֹͣ��λ
			isLocating=false;
		}
	}
	
	
}