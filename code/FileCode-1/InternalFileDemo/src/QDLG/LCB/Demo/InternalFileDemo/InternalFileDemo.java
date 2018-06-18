package QDLG.LCB.Demo.InternalFileDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class InternalFileDemo extends Activity {
	
	//����������
	private TextView info;
	private EditText readText,writeText;
	private CheckBox append;
	private Button readBtn,writeBtn;
	
	//Ҫ��д���ļ���
	private String fileName="DemoFile.txt";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ȡ�ؼ�����
        info=(TextView)findViewById(R.id.info);
        readText=(EditText)findViewById(R.id.read_content);
        writeText=(EditText)findViewById(R.id.write_content);
        append=(CheckBox)findViewById(R.id.append);
        readBtn=(Button)findViewById(R.id.read);
        writeBtn=(Button)findViewById(R.id.write);
        
        //д�ļ�����
        writeBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Ϊд�ļ��򿪵����������
				FileOutputStream fos=null;
				try {  //���ܲ�������,�˴�������Try���Χ
					if (append.isChecked())				
						fos=openFileOutput(fileName, Context.MODE_APPEND);
					else
						fos=openFileOutput(fileName, Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try {
					//д�������
					fos.write(writeText.getText().toString().getBytes());
					
					//д���ļ�
					fos.flush();
					fos.close();
					
					info.setText("�ļ�д��ɹ���д�볤�ȣ�"+writeText.getText().toString().length()); //��ʾ
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        
        //���ļ�����
        readBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Ϊ�����ļ����ݶ����õ�����������
				FileInputStream fis=null;
				
				try {
					fis=openFileInput(fileName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//��Ŷ����ļ����ݵ�����
				byte[] readBytes=null;
				try {
					readBytes = new byte[fis.available()]; //�����ļ���С��������
					while(fis.read(readBytes) != -1){  //�����ļ�����ֱ���ļ�ĩβ��������������ֽ������СΪ�ο���Ҳ���Լ���2�����������������ݵĿ��С
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				//��ʾ�ļ�����
				readText.setText(new String(readBytes)); //�ֽ�����--���ַ���
				info.setText("�ļ���ȡ�ɹ����ļ����ȣ�"+readBytes.length);				
			}
		});
        
    }
}