package QDLG.LCB.Demo.ThreadDemoClassical;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThreadDemoClassical extends Activity {
	
	//�Զ���Ĺ����̶߳���
	MyThread myThread;
	//�Զ���Ĺ����̵߳�Handler--ÿ��Handler��������Բ�ͬ����Ϣ
	Handler mChildThreadHandler, testChildHandler;
	
	//UI�߳̽��յ���Ϣ����
	private static final int UI_UPDATE_ID1=1;
	private static final int UI_UPDATE_ID2=2;
	//�����߳̽��յ���Ϣ����
	private static final int  SERVER_DATA1=1;
	private static final int SERVER_DATA2=2;
	
	//����ؼ�����
	private EditText info;
	
	//UI�̵߳�Handler
	Handler mainThreadHandler = new Handler() {
	    public void handleMessage(Message msg) {
	    	switch (msg.what){ //������Ϣ���ʹ���
	    	case UI_UPDATE_ID1:
	    		String data=msg.getData().getString("result");        //����Ϣ�л�ȡ����
	    		updateUI(data);     //�ڴ˷����н���UI���²���,����Ϊ��Ҫ���µ�����
	    		break;
	    	case UI_UPDATE_ID2:
	    		break;
	    	}
	    }
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ȡ����ؼ�����
        info=(EditText)findViewById(R.id.info);
        Button start=(Button)findViewById(R.id.btnStart);
        Button request=(Button)findViewById(R.id.btnRequest);
        Button stop=(Button)findViewById(R.id.btnStop);
        
        //��ʼ�̰߳�ť�ļ�����
        start.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			    //�����߳�--- ����û������
		        myThread=new MyThread();
				//�����̵߳�����
				myThread.setName("WorkerThread"); //�߳�����
				myThread.setPriority(Thread.MIN_PRIORITY);  //�����̵߳����ȼ�����
				//�����߳�
				myThread.start();
				
				Toast.makeText(ThreadDemoClassical.this, "�߳��Ѿ�����!", Toast.LENGTH_SHORT).show();
			}
		});
        
        //�������ݴ���ť�ļ�����
        request.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//ͨ�������̷߳�����Ϣ�����ù����߳�������ݴ���--��һ��Handler
		        Message msg = mChildThreadHandler.obtainMessage();
		        msg.what=SERVER_DATA1; //��Ϣ����
	            Bundle b = new Bundle();
	            b.putString("info", info.getText().toString() + "-1-");
	            msg.setData(b);  //��Ϣ��Я��Ҫ���������
		        mChildThreadHandler.sendMessage(msg);
		        
		        //ͨ�������̷߳�����Ϣ�����ù����߳�������ݴ���--�ڶ���Handler
		        msg = testChildHandler.obtainMessage();
		        msg.what=SERVER_DATA1; //��Ϣ����
	            Bundle b2 = new Bundle();
	            b2.putString("info", info.getText().toString() + "-2-");
	            msg.setData(b2);  //��Ϣ��Я��Ҫ���������
		        testChildHandler.sendMessage(msg);
			}
		});
        
        //ֹͣ�̰߳�ť�ļ�����
        stop.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//ֹͣ��Ϣѭ��, Ҳ��ֹͣ���߳�
				mChildThreadHandler.getLooper().quit();		
			}
		});
        
    }
    
    //�Զ�����߳���
    private class MyThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			InitWorkThread(); //�˵��ó�ʼ���̹߳�������
        	
        	Looper.prepare(); //׼����Ϣѭ��--������һ��Looper����
        	
        	//�˴����������̵߳�Handler�����Զ������潨������Ϣѭ��
        	mChildThreadHandler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					
					//����߳����ݵı���
					String data;
					
					switch (msg.what){ //������Ϣ���ͷֱ���
					case SERVER_DATA1:
						data=msg.getData().getString("info"); //����Ϣ�л�ȡ����
					
			            data=data+data;
			            
			            //ͨ����Ϣ��UI�̷߳��ؽ��
			            Message m = mainThreadHandler.obtainMessage();
			            m.what=UI_UPDATE_ID1; //��ϢID
			            Bundle b = new Bundle();
			            b.putString("result", data);  
			            m.setData(b);    // ����Ϣ����ӽ������
			            mainThreadHandler.sendMessage(m);    // �����̷߳�����Ϣ������UI
						break;
					case SERVER_DATA2:
						break;
					}					
				}        		
        	};
        	
        	testChildHandler=new Handler(){//��ǰ�Զ����̵߳ĵڶ���Handler
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					
					String data=msg.getData().getString("info"); //����Ϣ�л�ȡ����
					data="test--"+data;
		            doStuff();         // �÷�����ִ�к�ʱ����,����UI�̴߳���������
		            
					//ͨ����Ϣ��UI�̷߳��ؽ��
		            Message m = mainThreadHandler.obtainMessage();
		            m.what=UI_UPDATE_ID1; //��ϢID
		            Bundle b = new Bundle();
		            b.putString("result", data);  
		            m.setData(b);    // ����Ϣ����ӽ������
		            mainThreadHandler.sendMessage(m);    // �����̷߳�����Ϣ������UI
				}        		
        	};
            
        	//��ʼ��, ׼����Ϣѭ��, ����Handler�����  -->  ���빤���̵߳���Ϣѭ��
            Looper.loop();
		}
    	
    }
    
    //�����߳��еĳ�ʼ������
    private void InitWorkThread(){  
    	try {
			Thread.sleep(100);  //��ʱ0.1�룬ģ���߳����л����ĳ�ʼ������
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //�ڹ����߳��е����ݴ������
    private void doStuff(){  
    	try {
			Thread.sleep(3000); //��ʱ3��, ģ���ʱ����
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //UI�߳��и��½���
    private void updateUI(String data){    	
    	info.setText(data);
    	System.out.println(data);
    }
}