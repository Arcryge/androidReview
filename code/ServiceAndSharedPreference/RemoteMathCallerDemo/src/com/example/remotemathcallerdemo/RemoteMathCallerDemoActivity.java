package com.example.remotemathcallerdemo;

import com.example.remotemathservicedemo.IMathService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RemoteMathCallerDemoActivity extends Activity {
	
	//�ýӿ������������
	private IMathService mathService;
	
    private ServiceConnection mConnection = new ServiceConnection() {                           
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//��IMathService.Stub.asInterface��ȡ�������
			mathService = IMathService.Stub.asInterface(service);                          
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mathService = null; 
		} 
    }; 
    
	private boolean isBound = false;
	TextView labelView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        labelView = (TextView)findViewById(R.id.label);
        Button bindButton = (Button)findViewById(R.id.bind);
        Button unbindButton = (Button)findViewById(R.id.unbind);
        Button computButton = (Button)findViewById(R.id.compute_add);
        
        //��Զ�̷���
        bindButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!isBound){
					final Intent serviceIntent = new Intent();
					serviceIntent.setAction("com.example.RemoteMathServiceDemo.MathService");
					bindService(serviceIntent,mConnection,Context.BIND_AUTO_CREATE);
					isBound = true;
				}
			}  	
        }); 
        
        //������
        unbindButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(isBound){
					isBound = false;
					unbindService(mConnection);
					mathService = null;
				}
			}    	
        });
        
        //����Զ�̷�����м���
        computButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
					if (mathService == null){
						labelView.setText("δ��Զ�̷���");
						return;
					}
					long a = Math.round(Math.random()*100);
					long b = Math.round(Math.random()*100);
					long result = 0;
					try {
						//����Զ�̷��񣬽��м���
						result = mathService.Add(a, b);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					String msg = String.valueOf(a)+" + "+String.valueOf(b)+
									" = "+String.valueOf(result);
					labelView.setText(msg);
			} 	
        });
    }                           
 
}