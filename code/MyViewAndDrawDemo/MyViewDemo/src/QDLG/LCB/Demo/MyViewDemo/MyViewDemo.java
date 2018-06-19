package QDLG.LCB.Demo.MyViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyViewDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);
        
        //�ҵ��½���View����
        final MyView myview=(MyView)findViewById(R.id.myview);
        
        //��ť������
        Button btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener() {	
        	String newText="������ʾ!";
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myview.getText()==newText) //�л��ı���ʾ
					myview.setText("�����ı�!");
				else
					myview.setText(newText);
			}
		});
    }
}