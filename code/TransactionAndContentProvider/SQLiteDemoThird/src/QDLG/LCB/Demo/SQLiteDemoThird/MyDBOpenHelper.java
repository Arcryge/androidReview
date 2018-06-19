package QDLG.LCB.Demo.SQLiteDemoThird;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MyDBOpenHelper extends  SQLiteOpenHelper{
	//���ݿ������Ϣ
	private static final String DB_NAME="people.db";   //���ݿ���
	private static final String DB_TABLE="info";  //����
	private static final int   DB_VERSION=1;	  //���ݿ�汾
	//�ֶ�����
	public static  final  String KEY_ID="id";   
	public static  final  String KEY_NAME="name";
	public static  final  String KEY_AGE="age";
	public static  final  String KEY_HEIGHT="height";
	
	//������д�Ĺ��캯��
	public MyDBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	//������д�Ĵ������ݿ⴦��
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		String createSQL="create table "+DB_TABLE+" ("+KEY_ID+" integer primary key autoincrement,"
		+KEY_NAME+" text not null,"+KEY_AGE+" integer,"+KEY_HEIGHT+" float);";
		arg0.execSQL(createSQL); //ִ�д������
	}

	//������д���������ݿ⴦��
	//��������: ���ݿ����,�ɰ汾��,�°汾��
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS "+DB_TABLE); //һ������²���ֱ��ɾ������,�ȱ���...
        onCreate(arg0);
	}

	//��ѡ�ķ���---ÿ�γɹ������ݿ�����ȱ�ִ��
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}
}
