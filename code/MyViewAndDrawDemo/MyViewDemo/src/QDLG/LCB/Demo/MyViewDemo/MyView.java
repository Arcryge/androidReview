package QDLG.LCB.Demo.MyViewDemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MyView extends View{

	//�����ֵı�
	private Paint mTextPaint; 
	//���ߵĻ���
	private Paint cPaint;
	
	//Ҫ��ʾ���ַ���
	private String mStr;
	
	//�Ƿ���ʾ����?
	private boolean showLine;
	//���ߵ��յ�
	private int endX,endY;

	public MyView(Context context){
		super(context);
	}
	
	//����ʵ�ִ˹��캯�������ڲ����ļ��ʹ���������ʹ�ô�View
	public MyView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		// TODO Auto-generated constructor stub

		//���������Զ�������У�������Ҫʹ��һ����ΪobtainStyledAttributes�ķ�������ȡ�����ļ��е����Զ��塣
		//�ڶ����������������ļ��ж�����������Ͷ�������
        TypedArray params = context.obtainStyledAttributes(attrs,R.styleable.TestView);
        
        //�õ��Զ���ؼ�������ֵ, getResourceId�Ĳ�����ϵͳ���������ļ��еĶ����Զ�����   
        //�������Ƶ�����----R.styleable.�������Ͷ�������_��������
        int backgroudId = params.getResourceId(R.styleable.TestView_imgBackground, 0);   
        if (backgroudId != 0)   
            setBackgroundResource(backgroudId);   //����View�ı���ͼƬ
        //����д���õĻ��ʵ���ɫ�������С
        mTextPaint=new Paint(); //�������ʶ���
        int textColor = params.getColor(R.styleable.TestView_textColor,0XFFFFFF);   
        mTextPaint.setColor(textColor); //���û��ʵ���ɫ
        float textSize = params.getDimension(R.styleable.TestView_textSize, 36);   
        mTextPaint.setTextSize(textSize);  //���û���ʹ�õ�����
        
        //����ʾ����
        showLine=false;
        endX=0;
        endY=0;
        
        //��ʼ��ʾ���ַ���
        mStr="�����ı�!";
        
        //��ʼ�����ߵı�
		 cPaint = new Paint();//����Paintʵ��
	     cPaint.setColor(Color.BLUE);//ʹ����ɫ��ͼ
	     cPaint.setStrokeWidth(3); //���û��ʵĴֶ�
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
	     //"��"�ı�
	     canvas.drawText(mStr, 50, 290, mTextPaint);
	     
	     //����
	     if (showLine) //ȷ��Ҫ��Ҫ����
	    	 canvas.drawLine(0, 0, endX, endY, cPaint);
	     
	     //"��"�ı�
	     canvas.drawText(mStr, 50, 350, mTextPaint);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		//��ȡ����λ��
		int x=(int)event.getX();
		int y=(int)event.getY();
		
		int action=event.getAction();
		switch (action){ //���ഥ��������ͨ��������ʾ��ʶ�ͻ����յ������ƻ��߶���
		case MotionEvent.ACTION_DOWN:
			showLine=true;
			endX=x;
			endY=y;
			break;
		case MotionEvent.ACTION_UP: //�������
			showLine=false;
			endX=0;
			endY=0;
			break;
		case MotionEvent.ACTION_MOVE: //��������
			showLine=true;
			endX=x;
			endY=y;
			break;
		}
		
		//�����ػ���ܸ���View
		invalidate();
		
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		//��������������return super.onTouchEvent(event); 
		//���ø���ķ������õ�����ֵ���� ��
		//�������������, ��Ϊ���ø����onTouchEvent�������ܻ᷵��false
		//����һ�����޷���Ӧ�����ƶ��¼��ʹ���̧���¼�����ΪAndroid�����¼����ڲ������Ƿ���fase�Ļ�������¼��ͻᡰ��ʧ�������ҽ��ղ�����һ���¼���
		return true;
	}

	//����Ҫ��ʾ���ı�
	public void setText(String info){
		mStr=info;
		invalidate(); //���������ı�������ػ�
	}
	//���������ʾ���ı�
	public String getText(){
		return mStr;
	}
}
