package QDLG.LCB.Demo.XmlAnimationDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class XmlAnimationDemo extends Activity {
	
	//�����õĶ�������--һ��ͼƬ
	private ImageView test;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
        
        test=(ImageView)findViewById(R.id.test);
        //����ImageView��λ��,��(0,0)����(50,50)
//        test.setPadding(test.getLeft()+50, test.getTop()+50,
//        		test.getRight(), test.getBottom());
        
        //�ҳ���ť�����ü�����
        Button btn=(Button)findViewById(R.id.testBtn);
        btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				
				//����xml�ļ�������������
				Animation  animation=AnimationUtils.loadAnimation(XmlAnimationDemo.this,R.anim.scale);
				animation.setFillAfter(true);  //�������Ķ������
				//������ʾ����
				test.startAnimation(animation);
			}
		});
    }
}