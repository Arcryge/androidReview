package com.example.udpsocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private static final int MAX_UDP_DATAGRAM_LEN = 1024;
	private static final int UDP_SERVER_PORT = 9999;
		
	//���ڽ������ݱ����ֽ�����
	private byte[] Msg;
	//���ݱ�
	private DatagramPacket dp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				//�����������ݱ�������
				Msg=new byte[MAX_UDP_DATAGRAM_LEN];
				//�������ݱ�����
				dp=new DatagramPacket(Msg, Msg.length);
				
				DatagramSocket ds=null;
				try{
					//����һ���������˵�UDP Socket--��Ҫָ���˿ڣ�IP��ַʹ�ñ���Ĭ��IP��ַ����Ҳ�����ڴ�ָ��ip��ַ
					ds=new DatagramSocket(UDP_SERVER_PORT);
					
					System.out.println("Ready to receive...");
					//�ȴ�����UDP���ݱ�--������������
					ds.receive(dp);
					
					//�����ݰ���ȡ���յ�������--substring(0,dp.getLength())�ܹ�ȥ����������÷���
					System.out.println("Received---"  + new String(dp.getData()).substring(0,dp.getLength()));
					
				}catch(SocketException e){
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if (ds!=null){
						ds.close();
						System.out.println("Close Server socket.");
					}
				}
			}
		}.start();
		
		final EditText et=(EditText)findViewById(R.id.input);
		Button btnTest=(Button)findViewById(R.id.btnTest);
		btnTest.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread()
				{
					@Override
					public void run() {
						DatagramSocket ds=null;
						try{
							//�����ͻ��˵�UDP Socket--һ�㲻��Ҫָ���˿�
							ds=new DatagramSocket();
							
							//��ʼ��һ����������ַ����
							InetAddress serverAddr=InetAddress.getByName("localhost");
							
							DatagramPacket dp;
							String info=et.getText().toString();
							//�������ݱ�����--ָ��Ҫ���͵����ݡ����ݳ��ȡ���������ַ �� �˿�
							dp=new DatagramPacket(info.getBytes(), info.length(), serverAddr, UDP_SERVER_PORT);
							
							System.out.println("Send---"+info);
							//�������ݱ�
							ds.send(dp);
							
						}catch(SocketException e){
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (Exception e){
							e.printStackTrace();
						}finally{
							if (ds!=null)
							{
								ds.close();
								System.out.println("Close Client socket.");
							}
						}
					}					
				}.start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
