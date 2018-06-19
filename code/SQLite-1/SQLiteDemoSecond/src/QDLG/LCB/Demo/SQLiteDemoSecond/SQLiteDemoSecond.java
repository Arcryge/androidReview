package QDLG.LCB.Demo.SQLiteDemoSecond;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SQLiteDemoSecond extends Activity {
	//���ݿ��������,���ڲ������ݿ�
	DBAdapter db=null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //�������ݿ��������
        db=new DBAdapter(this);
        
        //�������ݿ�
        if (!db.createDB()){
        	Toast.makeText(SQLiteDemoSecond.this, "�������ݿ�ʧ��!", Toast.LENGTH_SHORT).show();
        	return;
        }
        
        //��ʾ��Ϣ��TextView
        final TextView info=(TextView)findViewById(R.id.info);
        
        //������
        ((Button)findViewById(R.id.create)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.createTable())
					info.setText("�ɹ�������!");
				else
					info.setText("������ʧ��!");
			}
		});
        
        //�������
        ((Button)findViewById(R.id.insert)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.insert())
					info.setText("�ɹ�����3������!");
				else
					info.setText("��������ʧ��!");
			}
		});
        
        //��ѯ
        ((Button)findViewById(R.id.query)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				info.setText(db.query());
			}
		});
        
        //�޸�
        ((Button)findViewById(R.id.update)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.update())
					info.setText("�ɹ��޸�����!");
				else
					info.setText("�޸�����ʧ��!");
			}
		});
        
        //ɾ��
        ((Button)findViewById(R.id.delete)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.delete())
					info.setText("�ɹ�ɾ������!");
				else
					info.setText("ɾ������ʧ��!");
			}
		});
        
        //ɾ����
        ((Button)findViewById(R.id.drop)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.drop())
					info.setText("�ɹ�ɾ����!");
				else
					info.setText("ɾ����ʧ��!");
			}
		});
        
    }

    //�˳�����ʱ�ر����ݿ�
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
	}
    
}