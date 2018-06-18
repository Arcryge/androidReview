package QDLG.LCB.Demo.ThreadDemoHandlerThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ThreadDemoHandlerThread extends Activity {
	
	//�̶߳���
	HandlerThread ht;
	//Handler����--�ֱ����ڹ����̺߳�UI�߳�,Ҳ���Խ�Handler��ֿ�д
	MyHandler myHandler,mainHandler;
	
	//�����ͬһ��Handler��������̺߳͹����̵߳���Ϣ--��������ID,ʵ��ʹ��ʱ�Ƽ�����2����ͬ��Handler�������ֱ����2����Ϣ
	private static final int MAIN_THREAD=1;
	private static final int WORKER_THREAD=2;	
	
	//�߳��Ƿ��˳���־����
	private static Boolean toExit;
	private static synchronized Boolean getExitFlag(){ //��ȡ���������ֵ
		return toExit;
	}
	private static synchronized void setExitFlag(Boolean exit){ //���û��������ֵ
		toExit=exit;
	}
	
	//��ť�������
	private Button btnStart,btnStop;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        //������̵߳�ID
        System.out.println("UI-->Primary thread ID-->"+Thread.currentThread().getId());
        
        //��ȡ��ť����
        btnStart=(Button)findViewById(R.id.startThread);
        btnStop=(Button)findViewById(R.id.stopThread);
        
        //��ȡ���̵߳�Handler--���������Ĺ��캯�����ĸ��߳���ִ�У����Handler�Ͱ��ĸ��̵߳���Ϣѭ��
        mainHandler=new MyHandler();
        
        //�����̵߳İ�ť�ļ�����
        btnStart.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//���������߳�
				ht=new HandlerThread("WorkerThread");
				setExitFlag(false); //�����˳���ʶ
				ht.start(); //���������߳�
				//ͨ����ȡLooper���������̶߳�Ӧ��Handler������Handler���������Ϣѭ����
				myHandler=new MyHandler(ht.getLooper()); 
				
				//�����̷߳�����Ϣ
				Message msg=myHandler.obtainMessage(); //��ȡ��Ϣʵ��
				msg.what=WORKER_THREAD; //������ϢID
		        Bundle b=new Bundle();  //ͨ��Bundle���󴫵�����(�ַ������͵ļ�ֵ��)
		        b.putInt("age", 20);
		        b.putString("name", "ZhangSan");
		        msg.setData(b);
		        myHandler.sendMessage(msg);  //������Ϣ
		        
		        //�����̷߳���Runnable����----�ڶ�Ӧ�Ĺ����߳�������Runnable����
		        myHandler.post(myThread);
			}
		});
        
        //ֹͣ�̰߳�ť�ļ�����
        btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				setExitFlag(true); //�����˳���־����
				
				//�˳���Ϣѭ���ͽ�����HandlerThread
				//���˴���������Ϣѭ������Ὣ֮ǰ����δ�������Ϣһһ�������
				ht.getLooper().quit(); 
			}
		});

    }
    
    //�Զ����Handler��--���Դ�����̴߳�������Ϣ
    private class MyHandler extends Handler{
		public MyHandler(){
    	}
    	public MyHandler(Looper looper){
    		super(looper);
    	}
    	//����ǰHandler����󶨵���Ϣ�������յ�����Ϣ
		@Override
		public void handleMessage(Message msg) { 
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			//��ʾ�̵߳�ID---�ص��������ڵ��߳̾����ڵ������߳�
			System.out.println("handleMessage: Thread ID--"+Thread.currentThread().getId());			

			//������ϢID�������ĸ��̴߳�������Ϣ
			switch(msg.what){
				case MAIN_THREAD: //���̴߳�������Ϣ���˷�֧�����߳���ִ��
					int result=msg.arg1+msg.arg2;
					System.out.println("Main-thread Data: "+result);
					break;
				case WORKER_THREAD:  //�����̴߳�������Ϣ���˷�֧�ڹ����߳���ִ��
					Bundle b=msg.getData();
					int age=b.getInt("age");
					String name=b.getString("name");
					System.out.println("Worker-thread Data: "+name+"--"+age+"--"+msg.arg1);
					break;
			}
		}
    }
    
    //�����̵߳����е����岿��
    Runnable myThread=new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub		

			//�����ǰ�̵߳�ID
			System.out.println("Runnable: Thread ID--"+Thread.currentThread().getId());
	       
			int cnt=0; //�������� 
			
			//ѭ������--���岿��
			while (!getExitFlag()){ 
				
		        try
		        {
		        	Thread.sleep(3000);  //��ʱ3��,ģ���ʱ����
		        }
		        catch(InterruptedException e)
		        {
		        	e.printStackTrace();
		        }

		        //�������̣߳��Լ� --���Լ���������Ϣ
		        Message msg=myHandler.obtainMessage();
		        msg.what=WORKER_THREAD;
		        Bundle b=new Bundle();
		        b.putInt("age", 21);
		        b.putString("name", "LiSi");
		        msg.setData(b);
		        msg.arg1=cnt;
		        //��Ϣ�ķ�����/������λ��ͬһ���̣߳���Ϣ���ᱻ��ʱ���������ۻ�����Ϣ������
		        msg.sendToTarget(); 
		        
		        //�����̷߳�����Ϣ
			    msg=mainHandler.obtainMessage();
			    msg.what=MAIN_THREAD;
			    msg.arg1=cnt++;
			    msg.arg2=100;
			    msg.sendToTarget(); //���̻߳ἰʱ������Ϣ
			}
		}    	
    };
}