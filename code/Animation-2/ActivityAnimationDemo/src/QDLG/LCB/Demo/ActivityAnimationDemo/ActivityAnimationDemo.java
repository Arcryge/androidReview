package QDLG.LCB.Demo.ActivityAnimationDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityAnimationDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //�л�Activity
        ((Button)findViewById(R.id.btn)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//������ʽIntent����������һ��Activity
				Intent intent=new Intent();
				intent.setClass(ActivityAnimationDemo.this, SecondActivity.class);
				startActivity(intent);
				
				//�����������startActivity�ĺ���,�ֱ����2��Activity��ʧ�����ֵĶ���Ч��
				overridePendingTransition(R.anim.enter,R.anim.out);
			}
		});
    }
}