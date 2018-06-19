package QDLG.LCB.Demo.AnimationListenerDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AnimationListenerDemo extends Activity {
	
	//���Զ�̬ɾ����View�ؼ�
	private  ImageView view;
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //�ҵ�View����
        view=(ImageView)findViewById(R.id.test);
        
        //�ҵ���View����, ɾ���ؼ�ֻ���ɸ��ؼ������
        final ViewGroup parent=(ViewGroup)findViewById(R.id.parent);
        
        //����xml�ļ�������������
		final Animation  animation=AnimationUtils.loadAnimation(AnimationListenerDemo.this,R.anim.alpha);
		
		//Ϊ����������������
		animation.setAnimationListener(new AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) { //��ʼʱ����
				// TODO Auto-generated method stub		
				System.out.println("Start");
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {//�ظ�ʱ����
				// TODO Auto-generated method stub	
				System.out.println("Repeat");
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {//����ʱ����
				// TODO Auto-generated method stub
				
				parent.removeView(view);   //ɾ���ؼ�
				view=null; //�Ѿ���ɾ�����ÿ�
				
				System.out.println("End");
			}
		});
        
		//��ť������
        ((Button)findViewById(R.id.testBtn)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (view!=null)
					view.startAnimation(animation); //��ʼ���Ŷ���
				else
					Toast.makeText(AnimationListenerDemo.this, "Deleted!!!", Toast.LENGTH_SHORT).show();
			}
		});
    }
}