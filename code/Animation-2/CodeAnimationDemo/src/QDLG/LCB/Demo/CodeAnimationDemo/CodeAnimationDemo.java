package QDLG.LCB.Demo.CodeAnimationDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class CodeAnimationDemo extends Activity {
	
	//��������
	private ImageView view;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        view=(ImageView)findViewById(R.id.test);
        
        Button btn=(Button)findViewById(R.id.testBtn);
        btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
								
				//͸���ȶ���
				//AlphaAnimation(float fromAlpha, float toAlpha) 
				//��һ������fromAlphaΪ ������ʼʱ��͸����
				//�ڶ�������toAlphaΪ ��������ʱ��͸����
//				AlphaAnimation animation=new AlphaAnimation(1.0f,0.1f);
//				//���ö�����ʾʱ��
//				animation.setDuration(3000); 
//				animation.setFillAfter(true);
				
				//�ƶ�����
				//TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) 
				//��һ������fromXDeltaΪ������ʼʱ X�����ϵ��ƶ�λ��
				//�ڶ�������toXDeltaΪ��������ʱ X�����ϵ��ƶ�λ��
				//����������fromYDeltaΪ������ʼʱY�����ϵ��ƶ�λ��
				//���ĸ�����toYDeltaΪ��������ʱY�����ϵ��ƶ�λ��
//				TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
//						Animation.RELATIVE_TO_PARENT, 0.5f,
//						Animation.RELATIVE_TO_PARENT, 0,
//						Animation.RELATIVE_TO_PARENT, 0);
//				//���ö�����ʾʱ��
//				animation.setDuration(3000); 
//				animation.setFillAfter(true);
				
				//��ת����
				//RotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
				//��һ������fromDegreesΪ������ʼʱ����ת�Ƕ�
				//�ڶ�������toDegreesΪ������ת���ĽǶ�
				//����������pivotXTypeΪ������X�����λ������  
				//���ĸ�����pivotXValueΪ���������X����Ŀ�ʼλ��
				//���������pivotXTypeΪ������Y�����λ������
				//����������pivotYValueΪ���������Y����Ŀ�ʼλ��
//				RotateAnimation animation=new RotateAnimation(0.0f, +360.0f,
//			               Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
//				//���ö�����ʾʱ��
//				animation.setDuration(3000); 
				
				//���Ŷ���
				//ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
				//��һ������fromXΪ������ʼʱ X�����ϵ������ߴ�    
				//�ڶ�������toXΪ��������ʱ X�����ϵ������ߴ�     
				//����������fromYΪ������ʼʱY�����ϵ������ߴ�    
				//���ĸ�����toYΪ��������ʱY�����ϵ������ߴ�
				/*˵��:
                	������������ֵ    
                	0.0��ʾ������û�� 
                	1.0��ʾ����������     
                	ֵС��1.0��ʾ����  
                	ֵ����1.0��ʾ�Ŵ�*/
				//���������pivotXTypeΪ������X�����λ������
				//����������pivotXValueΪ���������X����Ŀ�ʼλ��
				//���߸�����pivotXTypeΪ������Y�����λ������
				//�ڰ˸�����pivotYValueΪ���������Y����Ŀ�ʼλ��
//				ScaleAnimation animation=new ScaleAnimation(0f, 1.0f, 0f, 1.0f,
//			             Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_SELF, 0);
//				//���ö�����ʾʱ��
//				animation.setDuration(3000); 
				
				//��϶���
				//���������϶�������ļ���,true��ʾ���ж���������һ��������
				AnimationSet animation=new AnimationSet(true);
				//͸���ȶ���
				AlphaAnimation anim1=new AlphaAnimation(1, 0);
				anim1.setDuration(1000);
				anim1.setRepeatCount(2);
				animation.addAnimation(anim1);
				//��ת����
				RotateAnimation anim2=new RotateAnimation(0.0f, +360.0f,
			               Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				anim2.setDuration(1000);
				anim2.setRepeatCount(2);
				animation.addAnimation(anim2);
				//��ת����
				RotateAnimation anim3=new RotateAnimation(0.0f, +360.0f,
			               Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT, 0);
				anim3.setDuration(3000);
				animation.addAnimation(anim3);

				
				//���ü���ģʽ
				Interpolator i=new DecelerateInterpolator();
				animation.setInterpolator(i);
				//������ʾ����
				view.startAnimation(animation);
			}
		});
    }
}