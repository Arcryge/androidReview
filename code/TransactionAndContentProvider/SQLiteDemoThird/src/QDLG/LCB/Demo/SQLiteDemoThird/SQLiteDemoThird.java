package QDLG.LCB.Demo.SQLiteDemoThird;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SQLiteDemoThird extends Activity {
	
	//���ݿ��������
	private DBAdapter db;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ʾ��Ϣ�õ�TextView
        final TextView info=(TextView)findViewById(R.id.info);
        
        //�ع�����
        final CheckBox rollback=(CheckBox)findViewById(R.id.rollback);
        
        //�������ݿ��������
        db=new DBAdapter(SQLiteDemoThird.this);
        //�������ݿ�
        if (!db.createDB()){
        	Toast.makeText(SQLiteDemoThird.this, "�������ݿ�ʧ��!", Toast.LENGTH_SHORT).show();
        	return;
        }
        db.insert(); //Ԥ�Ȳ�����������
        
        //��ѯ��ť����
        ((Button)findViewById(R.id.query)).setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				info.setText(db.query()); //��ʾ��ѯ���
				Toast.makeText(SQLiteDemoThird.this, "��ѯ�������!", Toast.LENGTH_SHORT).show();
			}
		});
        
        //�ύ����İ�ť����
        ((Button)findViewById(R.id.commit)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//�ύ����ͬʱ�����Ƿ�ع���ʶ
				db.execTransaction(rollback.isChecked());
				Toast.makeText(SQLiteDemoThird.this, "����������!", Toast.LENGTH_SHORT).show();
			}
		});
    }
}