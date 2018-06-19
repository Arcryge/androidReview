package QDLG.LCB.Demo.ResourceFileDemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResourceFileDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ʾ�����TextView
        final TextView info=(TextView)findViewById(R.id.info);
        
        //��ȡ��Դ�����ʵ��
		final Resources resources=getResources();
        
        //ԭʼ�ļ�����
        Button raw=(Button)findViewById(R.id.rawBtn);
        raw.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				InputStream input=null;
				input=resources.openRawResource(R.raw.test); //��ȡָ����Դ�ļ�������������
				try { 
					//��ȡ�ļ�����,����������FileInputStream
					byte[] reader=new byte[input.available()]; //������try���Χ
					String out="";
					while (input.read(reader)!=-1){
						out+=new String(reader,"GBK");
						//�ڶ�������Ϊ����,������raw�ļ��������еı������,��������
					}
					
					//��ʾ�ļ�����
					info.setText(out);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{ //�ر�����������
					if (input!=null){
						try {
							input.close(); //������try���Χ
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
        
        //xml�ļ�����
        Button xml=(Button)findViewById(R.id.xmlBtn);
        xml.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//��ȡXML������
				XmlPullParser parser=resources.getXml(R.xml.people);
				
				String msg=""; //��ʾ�ַ���
				String people=null; //People�ڵ�����
				//Ԫ�ص�����ֵ
				String name=null; 
				String age=null;
				String height=null;
				// ��xml�ļ���������Ԫ�ص����ƺ�ֵ
				String attrName;
				String attrValue;
				
				try {
					int result=parser.next();   //��ȡһ���ڵ�
					
					while (result!=XmlPullParser.END_DOCUMENT){ //һֱɨ�赽�ļ�ĩβ
						
						people=parser.getName(); //��ȡ��ǰ�ڵ������
						
						//����һ��Ԫ��----һ��,
						//START_TAG, ��ʼ����һ��ʱ������������,
						if (people!=null && 	people.equals("person")  && 
								result==XmlPullParser.START_TAG){ //����˴���������������,�������һ��ʱҲ�ᴥ�����¼�
							
							int count=parser.getAttributeCount(); //��ȡ���Ը���
							
							for (int i=0; i<count; i++){ //����Ż�ȡ��ǰ�ڵ��ÿ������ֵ
								
								//ȡ����ǰ�ڵ�����ֺ�ֵ
								attrName=parser.getAttributeName(i);
								attrValue=parser.getAttributeValue(i);
								
								//��ȡ����ֵ-->��ͬ������ֵ��ֵ����ͬ�ı���
								if ((attrName!=null) && attrName.equals("name"))
									name=attrValue;
								else if ((attrName!=null) && attrName.equals("age"))
									age=attrValue;
								else if ((attrName!=null) && attrName.equals("height"))
									height=attrValue;
							}
							//�γ�Ҫ��ʾ���ַ���
							msg+="����: "+name+", ����: "+age+", ���: "+height+"\n";
						}
						result=parser.next();
					}
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//��ʾ����
				info.setText(msg);
			}
		});
    }
}