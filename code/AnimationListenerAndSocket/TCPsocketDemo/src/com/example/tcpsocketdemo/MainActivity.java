package com.example.tcpsocketdemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	private static final int TCP_SERVER_PORT = 9998; //��������TCP�˿�
	private ServerSocket serverSocket=null; //������Socket

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					System.out.println("Server accepting...");
					
					//1������������Socket�ӿ�--�˴�ʹ�õ��Ƿ�������Ĭ��ip��ַ��Ҳ�����ڹ��캯����ָ��ip��ַ
					serverSocket = new ServerSocket(TCP_SERVER_PORT);
					//2��������������--�Ⱥ�--�ͻ������ӳɹ��󣬷���ר����ͻ���ͨ�ŵ�Socket����
         			Socket socket = serverSocket.accept();
					
					System.out.println("Server accepted...");
					
					//3�����巽ʽ��ȡ�ı�
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));//�ֽ�������-->�ַ���-->������ķ�ʽ��ȡ�ı�
					//���巽ʽд���ı�
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream()));//�ֽ������-->�ַ���-->������ķ�ʽд�ı�
					
					System.out.println("Server Read...");
					
//					out.write("test--"); //д��ʱ���ȴ�
//					out.flush();
					
					//���巽ʽ��ȡ�ı���һ��һ��--�������򷵻�null�����ȴ�--��
					String incomingMsg = in.readLine() + System.getProperty("line.separator"); //������Ϣ+�зָ���
					
					System.out.println("Server get -- " + incomingMsg);
					
					//Ҫ�ӷ�����������ͻ��˵���Ϣ--д
					String outgoingMsg = "googbye from port " + TCP_SERVER_PORT + 
							System.getProperty("line.separator");
					
					System.out.println("Server writing... "+outgoingMsg);
					
					//׼����BufferWriter�����ַ���
					out.write(outgoingMsg);
					//ˢ�£�����
					out.flush();
					
					//4���ر��׽���
					socket.close();
					
					System.out.println("Server closed...");
					
				} catch (InterruptedIOException e) {//��ʱ����
					e.printStackTrace();
				} catch (IOException e) {//IO�쳣
					e.printStackTrace();
				} finally {
					if (serverSocket != null) { //���������ServerSocket�����ڴ˹ر�
						try {
							serverSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();		
		
		final EditText et=(EditText)findViewById(R.id.input); //����Ҫ�������ݵ�EditText
		
		Button btn=(Button)findViewById(R.id.btnTest);
		btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
							
							System.out.println("Client connecting...");
							
							//1�������ͻ���Socket��ͬʱָ��Ҫ���ӵķ����������ƺͶ˿ں�--���ӷ�����
							Socket socket=new Socket("localhost", TCP_SERVER_PORT);
							
							System.out.println("Client connected...");
							
							//2�����巽ʽ��ȡ�ı�
							BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
							//���巽ʽд���ı�
							BufferedWriter out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
							
							//�ӿͻ��˷��͵����������ַ���
							String outMsg=et.getText().toString()+System.getProperty("line.separator");
							
							System.out.println("Client write... "+outMsg);
							
							//д�룬׼������
							out.write(outMsg);
							//����
							out.flush();
							
							System.out.println("Client read... ");
							
							//���շ���������������--�������򷵻�null�����ȴ�
							String inMsg=in.readLine()+System.getProperty("line.separator");
							
							System.out.println("Client get...  "+ inMsg);
							
							//3���ر��׽���
							socket.close();
							
							System.out.println("Client closed...");
							
						}catch(UnknownHostException e){
							e.printStackTrace();
						}catch (IOException e){
							e.printStackTrace();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
