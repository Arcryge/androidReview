package QDLG.LCB.Demo.SQLiteDemoSecond;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBAdapter {
	//���ݿ������Ϣ
	private static final String DB_NAME="people.db";   //���ݿ���
	private static final String DB_TABLE="info";  //����
	private static final int   DB_VERSION=1;	  //���ݿ�汾
	//�ֶ�����
	public static  final  String KEY_ID="id";   
	public static  final  String KEY_NAME="name";
	public static  final  String KEY_AGE="age";
	public static  final  String KEY_HEIGHT="height";
	
	private SQLiteDatabase db=null;  //���ݿ����
	private Context context; //��ס�ɹ��캯������������Ķ���
	
	//���ݿ⸨����������
	private MyDBOpenHelper helper;
	
	public DBAdapter(Context context){
		this.context=context;  //��ס�ɹ��캯������������Ķ���
		helper=new MyDBOpenHelper(context, DB_NAME, null, DB_VERSION); //���ڴ˴��������ݿ�汾,��ᴥ����������
	}
	
	//����/�����ݿ�
	public boolean createDB(){
		try{
			//��/����ָ���ļ����µ����ݿ�,��Ҫ��Ӧ·����дȨ��
			db=helper.getWritableDatabase();
		}catch(SQLiteException e){
			return false;
		}
		return true;
	}
	
	//������--�˴�����Ҫ���κδ���, �ڴ������ݿ�ʱ�����֮����
	public boolean createTable(){		
		return true;
	}
	
	//���������,����3����¼
	public boolean insert(){
		ContentValues cv=new ContentValues();
		
		//���ò�����ֶ���ֵ,�����������ֶβ���Ҫ����
		cv.put(KEY_NAME, "Tom");
		cv.put(KEY_AGE, 21);
		cv.put(KEY_HEIGHT, 1.75);
		if (db.insert(DB_TABLE, null, cv)==-1)
			return false;
			
		cv.put(KEY_NAME, "Jack");
		cv.put(KEY_AGE, 22);
		cv.put(KEY_HEIGHT, 1.80);
		if (db.insert(DB_TABLE, null, cv)==-1)
			return false;
	
		cv.put(KEY_NAME, "Lily");
		cv.put(KEY_AGE, 20);
		cv.put(KEY_HEIGHT, 1.70);
		if (db.insert(DB_TABLE, null, cv)==-1)
			return false;
	
		return true;
	}
	
	//��ѯ����
	public String query(){
		String rlt=""; //����ַ���
		
		//��ѯ---����ʹ��ռλ��--SQL�е�?��Ӧ�����ַ��������е��ַ���,�˴�����,�ڶ�����������Ϊnull
		Cursor cursor=db.query(DB_TABLE,new String[]{KEY_ID,KEY_NAME,KEY_AGE,KEY_HEIGHT},
				null,null,null,null,null); //���Ҫ��ѯ�ı������������쳣
		if (cursor.getCount()==0) //��ѯ���Ϊ���򷵻�
			return "��ѯ���Ϊ��!";
		while (cursor.moveToNext()){ //ѭ��ȡ���α��еĲ�ѯ���
			//ȡ�����и��е�ֵ
			int id=cursor.getInt(0);
			String name=cursor.getString(1);
			int age=cursor.getInt(2);
			float height=cursor.getFloat(3);
			//д������ַ�����
			rlt+=id+"\t"+name+"\t"+age+"\t"+height+"\n";
		}; //����ȡ��һ��
		
		cursor.close(); //�ر��α�
		
		return rlt;
	}
	
	//�޸�
	public boolean update(){
		ContentValues cv=new ContentValues();
		cv.put(KEY_HEIGHT, 1.85); //�趨Ҫ�޸ĵ�ֵ
		if (db.update(DB_TABLE, cv, KEY_NAME+"=?", new String[]{"Lily"})>0)
			return true;
		else
			return false;
	}
	
	//ɾ��
	public boolean delete(){
		if (db.delete(DB_TABLE, KEY_HEIGHT+"<=?", new String[]{"1.85"})>0)
			return true;
		else
			return false;
	}
	
	//ɾ����
	public boolean drop(){
		String dropSQL="drop table ["+DB_TABLE+"] ;";
		try{
			db.execSQL(dropSQL); //ִ��ɾ�������
			}catch(SQLException e){
				return false;
			}
			
		return true;
	}
	
	//�ر����ݿ����
	public void close(){
		if (db!=null){
			db.close();
			db=null;
		}
	}
}
