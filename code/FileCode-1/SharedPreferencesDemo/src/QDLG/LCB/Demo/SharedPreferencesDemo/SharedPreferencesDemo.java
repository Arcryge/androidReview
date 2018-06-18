package QDLG.LCB.Demo.SharedPreferencesDemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SharedPreferencesDemo extends Activity {
		
	//���ڲ���Ա����
	private long number;
	private String name;
	private int age;
	
	//SharedPreference���ļ����ƺʹ�ȡģʽ
	public static final String PREFERENCE_NAME="testpf";
	public static final int PREFERENCE_MODE=Context.MODE_WORLD_READABLE + 
											Context.MODE_WORLD_WRITEABLE;
	//�ؼ�����
	private EditText et_num;
	private EditText et_name;
	private EditText et_age;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ȡ�ؼ�����
        et_num=(EditText)findViewById(R.id.number);
        et_name=(EditText)findViewById(R.id.name);
        et_age=(EditText)findViewById(R.id.age);
        
        Button exit=(Button)findViewById(R.id.exit);
        //�˳�Ӧ�ó���
        exit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish(); //������ǰӦ�ó���--���˳�
			}
		});        
    }

	@Override
	protected void onStart() { //��ʾ����֮ǰ����ȡ�洢�����ݣ����¿ؼ�����
		// TODO Auto-generated method stub
		super.onStart();
		
		//��ȡ�洢������
		loadSharedPreference();
		//���½���
		et_num.setText(number+"");
		et_name.setText(name);
		et_age.setText(age+"");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		//�洢����
		saveSharedPreference();
	}
    
	//��ȡ����
    private void loadSharedPreference(){
    	
    	//��ȡSharedPreferences����
    	SharedPreferences sp=getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
    	
    	//��ȡ����
    	number=sp.getLong("Number", 0);
    	name=sp.getString("Name", "");
    	age=sp.getInt("Age", 0);
    }
    
    //��������
    private void saveSharedPreference(){    	
    	//��ȡSharedPreferences����
    	SharedPreferences sp=getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
    	//��ȡEditor����
    	SharedPreferences.Editor editor=sp.edit();
    	
    	//д������
    	editor.putLong("Number", Long.parseLong(et_num.getText().toString()));
    	editor.putString("Name", et_name.getText().toString());
    	editor.putInt("Age", Integer.parseInt(et_age.getText().toString()));
    	
    	//�ύ����
    	editor.commit();
    }
}