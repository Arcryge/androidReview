package qdlg.lcb.demo.ThreadDemoJava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThreadDemoJava extends Activity {
	
	//��ť�ؼ�����
	private  Button start,end;
	
	//�̶߳���
	private  MyThread t;
	
	//�߳��Ƿ��˳���־����, Thread.interrupt()�����ж���ֹ
	private static Boolean toExit;
	private static synchronized Boolean getExitFlag(){ //��ȡ���������ֵ
		return toExit;
	}
	private static synchronized void setExitFlag(Boolean exit){ //���û��������ֵ
		toExit=exit;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ȡ�ؼ�����
        start=(Button)findViewById(R.id.startThread);
        end=(Button)findViewById(R.id.stopThread);

        //��������ʼ�����߳�
        start.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//�˳���־��Ϊ��--��ʼʱ���˳�
				setExitFlag(false);
				
				//�����߳�
		        t=new MyThread();
		        t.setName("WorkerThread"); //�����߳�����
		        t.setPriority(Thread.MIN_PRIORITY);  //�����߳�Ϊ�����ȼ�
		        t.start();   //�����߳�
			}
		});
        
        //�����̵߳�����
        end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setExitFlag(true); //�����˳���־
			}
		});
        
    }
    
    //�߳���
    private class MyThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			int cnt=1;  //ѭ������������
			
			//�߳�ѭ���ṩ����, �˳���־Ϊ��ʱ�˳�ѭ��
			while (!getExitFlag()){
 	
				//���ѭ�������д�����ʾ
				System.out.println("Thread is running..." + cnt++);
				
				try {
					Thread.sleep(1000); //��ʱ1��, ģ�������ʱ����
				}
				catch (InterruptedException e) {
						// TODO Auto-generated catch block
				    e.printStackTrace();
				}
			}
			
		}
    	
    }
}