package QDLG.LCB.Demo.DrawDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class CustomView extends View{
	
	//�����κ�Բ�Ļ��ʺ���ɫ
	Paint paint1;
	int color1;
	//��Path1�Ļ��ʺ���ɫ
	Paint paint2;
	int color2;
	//��Path2�Ļ��ʺ���ɫ
	Paint paint3;
	int color3;
	
	//���ͼ��
	Path path1,path2;
	
	//��Ҫ�ڳ����л��Ƶ�ͼ��
	Bitmap bitmap;

	//��������ڲ����ļ��ж���View,��ʵ�ִ˹��캯�����㹻��---�ڴ˴�׼���û�ͼʹ�õ����ж��������
	public CustomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		//���ð׵�
		setBackgroundColor(Color.WHITE);
		
		//��ʼ�����ʺ���ɫ;
		color1=Color.BLUE;
		paint1=new Paint();
		paint1.setStyle(Style.FILL);
		paint1.setStrokeWidth(5);    //ʵо���ʵĿ����Ч
		paint1.setColor(color1);
		paint1.setAlpha(255);
		
		color2=Color.argb(255, 200, 200, 0);
		paint2=new Paint();
		paint2.setStyle(Style.STROKE); //�������ʿ������ÿ��
		paint2.setStrokeWidth(5);
		paint2.setColor(color2);

		color3=getResources().getColor(R.color.mycolor); //����Դ�ļ��л�ȡ��ɫֵ
		paint3=new Paint();
		paint3.setStyle(Style.STROKE);
		paint3.setStrokeWidth(3);
		paint3.setColor(color3);
		paint3.setTextSize(70);
		

		//�������ͼ�ζ��󣬻�������
		path1=new Path();
		path1.moveTo(80, 90); //��ͼ��ʼλ��
		path1.lineTo(10, 180);
		path1.lineTo(140, 180);
		path1.lineTo(80, 90);
		
		//�������ͼ�ζ��󣬻���Բ���߶�
		path2=new Path();
		RectF rec=new RectF();
		rec.set(150, 90, 200, 180);
		path2.addOval(rec, Direction.CW); 
		path2.moveTo(150, 135);
		path2.lineTo(250, 135);
		Shader mShader=new LinearGradient(150, 90, 200, 180,    //����ɫ��Χ
                new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},   //ɫֵ 
                null, //����ÿ����ɫ���ڵĽ������λ��,���Ϊnull��ʾ���е���ɫ��˳����ȵķֲ�
                Shader.TileMode.REPEAT); //��ɫ�仯�ķ�ʽ
		paint3.setShader(mShader); //���ý���ɫ,�˴����ú�,ǰ���setColor����Ч��
		
		//����Դ�л�ȡͼ��
		bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.test)).getBitmap();
	}

	//���еĻ�ͼ�������ڴ˽���
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//�������κ�Բ��
		canvas.drawRect(10, 10, 150, 80, paint1); //���Ͻ����꣬���½�����
		canvas.drawCircle(200, 40, 30, paint1); //Բ�����꣬�뾶
		
		
		//�������ͼ����ı�
		canvas.drawPath(path1, paint2);
		
		paint1.setTextSize(18); //���û��ʵ������С
		canvas.drawTextOnPath("1234567890123456789012345", path1, 10, 10, paint1);//Path��д��
		
		canvas.drawPath(path2, paint3); //���ý���ɫ����Path2--��Բ+�߶�
		
		
		//�����ı�����--����ɫд�ı�
		canvas.drawText("�����ı�", 20, 270, paint3);
		
		//��ͼ�β���
		paint3.setAlpha(120);  //�ı�ͼƬ��͸����, 0--��ȫ͸��, 255--��ȫ��͸��
		//��ԭʼͼ��
		canvas.drawBitmap(bitmap, 40, 300, paint3);
		
		//����/�ü�ͼ��
		Matrix matrix=new Matrix();  //ͼƬ���ŵľ���
		matrix.postScale((float)0.8,(float)0.8);  //ͼƬ��С��0.8��
//		matrix.postScale((float)1.1,(float)1.1);  //ͼƬ�Ŵ�1.1��
		
		//���ݸ����ľ����С����ͼ��--Filter����ͼ������
		//sourceBitmap: ������λͼ��Դλͼ��
		//x int: ��λͼ��һ��������Դλͼ��X����
		//y int: ��λͼ��һ��������Դλͼ��y����
		//width int: ��λͼÿһ�е����ظ���
		//height int:��λͼ������
		//m Matrix: ������ֵ���б任�Ŀ�ѡ����
		//filter boolean: ���Ϊtrue��ԴͼҪ������
		Bitmap bp=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		
		//ȡ��ͼƬ��һ����
		//Bitmap bp=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()/2, bitmap.getHeight()/2); 
		
		//��ʾ���ź��ͼ��
		canvas.drawBitmap(bp, 190, 300, paint3); //��ָ�������괦����ͼ��
	}

}
