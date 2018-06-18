package com.example.remotemathservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


public class MathService extends Service {
	
    //ͨ��ʹ��IMathService.java�ڲ���Stub��ʵ��
	private final IMathService.Stub mBinder = new IMathService.Stub() {
		
	  public long Add(long a, long b) { //��һʵ����IMathService.aidl�ӿ��ļ�����ĺ���
		  System.out.println("���мӷ����� " + a + "+" + b + "=" + (a+b));
		  return a + b; 
	  }
	  
	}; 
	
	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("Զ�̰󶨣�MathService");
		return mBinder;
	}
	
	@Override
	public boolean  onUnbind  (Intent intent){
		System.out.println("ȡ��Զ�̰󶨣�MathService");
		return false;
	}

}
