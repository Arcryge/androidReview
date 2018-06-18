package QDLG.LCB.Demo.SharedPreferencesDemoSecond;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class SharedPreferencesDemoSecond extends Activity {
	
	//SharedPreference�İ���
	public static final String PREFERENCE_PACKAGE="QDLG.LCB.Demo.SharedPreferencesDemo";
	//SharedPreference���ļ����ƺʹ�ȡģʽ
	public static final String PREFERENCE_NAME="testpf";
	public static final int PREFERENCE_MODE=Context.MODE_WORLD_READABLE + 
															Context.MODE_WORLD_WRITEABLE;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ȡָ�������л���
        Context c=null;
        try {
			c=this.createPackageContext(PREFERENCE_PACKAGE, CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ͨ�������л�����ȡSharedPreferences����
		SharedPreferences sp=c.getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
    	//��ȡ����
    	long number=sp.getLong("Number", 0);
    	String name=sp.getString("Name", "");
    	int age=sp.getInt("Age", 0);

    	//��ʾ���
    	TextView info=(TextView)findViewById(R.id.info);
    	String s="ѧ��: "+number +"\n����: "+name+"\n����: "+age;
    	info.setText(s);
    	
    	//��ͼ�޸�SharedPreferences�����ݣ��Ͱ汾����ʧ�ܣ�4.0�����Ͽ��ܳɹ���
    	SharedPreferences.Editor editor=sp.edit();
    	editor.putString("Name", "bbbbb"); 
    	editor.commit();
    }
}