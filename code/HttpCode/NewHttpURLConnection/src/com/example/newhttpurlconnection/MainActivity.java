package com.example.newhttpurlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	//����ؼ�
		Button btnTest;
		TextView tvTest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnTest=(Button)findViewById(R.id.btn);
		tvTest=(TextView)findViewById(R.id.txt);
		
		btnTest.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			//�����߳��н����������	
            new Thread(){
            	@Override
				public void run() {
				try {
					//����Post���󵽰ٶȷ�����
					HttpURLConnectUtils http=new HttpURLConnectUtils();
					String rlt=http.DoHttpPost("http://www.baidu.com");
					
					//������صĽ��
					System.out.println(rlt);
					
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
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

	//����HttpURLConnection��ϵHttp������
		public class HttpURLConnectUtils {
			
		    private HttpURLConnectUtils() {
		    }

		    //post
		    public String DoHttpPost(String mUrl) throws IOException {

		        URL url = new URL(mUrl);
		        //��ʼ������HttpURLConnectionʵ��
		        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		        
		        //��˵������ִ���java.io.FileNotFoundException�����˴����д���������ƭ�ٶ�--������Ϊ�����������
//		        httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
//		        httpURLConnection.setRequestProperty("Accept","*/*");

		        //���õ�ǰ���ӵĲ���
		        httpURLConnection.setConnectTimeout(5000);//�Ƽ�����������ʱ����������ô����ȡ��Ӧ״̬��ʱ������������
		        httpURLConnection.setDoOutput(true); //�ɶ�д
		        httpURLConnection.setDoInput(true);
		        httpURLConnection.setUseCaches(false); //���û���
		        
		        //����HttpURLConnection����ͷ���������
		        //�趨���͵����������ǿ����л���java����    
		        //(����������,�ڴ������л�����ʱ,��WEB����Ĭ�ϵĲ�����������ʱ������java.io.EOFException)  
		        httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
		        
		        //��������ķ���
		        httpURLConnection.setRequestMethod("POST");//Post����
		        
		        //�������������ʱ�������Ľ���connect
		        OutputStream outputStream = httpURLConnection.getOutputStream();
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);   //OutputStream-->ObjectOutputStream
		        
		        String params = new String();
		        //��������ò�������ͨ�ַ��� --> application/x-www-form-rulencoded MIME�ַ���
		        params = "name=" + URLEncoder.encode("���", "GBK");
		        //�ύ����
		        objectOutputStream.writeBytes(params);
		        objectOutputStream.flush();
		        objectOutputStream.close();
		        
		        //��ȡ��Ӧ��״̬�룬�ж��Ƿ�����ɹ�
		        int rltCode = httpURLConnection.getResponseCode();
		        String msg = httpURLConnection.getResponseMessage(); //��ȡ��Ӧ״̬�������,   ��������"OK"
		        if (rltCode != 200) //�ж���Ӧ״̬�Ƿ�ɹ�
		        {
		        	System.out.println();
		        	return "Error Code--" + rltCode + ", Error Message--" + msg;
		        }
		        //���շ���ֵ
		        //�����ļ�������, InputStream-->InputStreamReader-->BufferedReader
		        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
		        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		        //��ȡ��Ӧ����
		        StringBuilder builder = new StringBuilder();
		        for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) { //ѭ����ȡ��������
		            builder.append(s);
		        }
		        
		        return builder.toString();
		    }

		    //get
		    public String DoHttpGet(String mUrl) throws IOException {
		    	
		        URL url = new URL(mUrl);
		        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		        httpURLConnection.setConnectTimeout(5000);//�Ƽ�����������ʱ
		        httpURLConnection.setRequestMethod("GET");
		        httpURLConnection.connect(); //�˴�������ʽ��������

		        //����ͬPost
		        //��ȡ��Ӧ��״̬�룬�ж��Ƿ�����ɹ�
		        int rltCode = httpURLConnection.getResponseCode();
		        String msg = httpURLConnection.getResponseMessage(); //��ȡ��Ӧ״̬�������, "OK"
		        if (rltCode != 200) //�ж���Ӧ״̬�Ƿ�ɹ�
		        {
		        	System.out.println();
		        	return "Error Code--" + rltCode + ", Error Message--" + msg;
		        }

		        //��ȡ��Ӧ����
		        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
		        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		        StringBuilder builder = new StringBuilder();
		        for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
		            builder.append(s);
		        }

		        return builder.toString();
		    }
		}
		
}
