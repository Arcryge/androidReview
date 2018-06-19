package QDLG.LCB.Demo.SQLiteDemoThird;

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
		helper=new MyDBOpenHelper(context, DB_NAME, null, DB_VERSION); //���ڴ˴��������ݿ�汾,��������������
	}
	
	//�������ݿ�
	public boolean createDB(){
		try{
			//��/����ָ���ļ����µ����ݿ�,��Ҫ��Ӧ·����дȨ��
			db=helper.getWritableDatabase();
		}catch(SQLiteException e){
			return false;
		}
		return true;
	}
	
	//������
	public boolean createTable(){		
		return true;
	}
	
	//���������,����3����¼
	public boolean insert(){
		String insertSQL="insert into ["+DB_TABLE+"]  values(null,'Tom',21,1.75);";
		try{
			db.execSQL(insertSQL); //ִ�����
			insertSQL="insert into ["+DB_TABLE+"]  values(null,'Jack',22,1.80);";
			db.execSQL(insertSQL);
			insertSQL="insert into ["+DB_TABLE+"]  values(null,'Lily',20,1.70);";
			db.execSQL(insertSQL);
		}catch(SQLException e){
			return false;
		}		
		return true;
	}
	
	//��ѯ����
	public String query(){
		String rlt=""; //����ַ���
		
		//��ѯ---����ʹ��ռλ��--SQL�е�?��Ӧ�����ַ��������е��ַ���,�˴�����,�ڶ�����������Ϊnull
		Cursor cursor=null;
		cursor=db.rawQuery("select * from ["+DB_TABLE+"]", null); //���Ҫ��ѯ�ı������������쳣
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
		String updateSQL="update ["+DB_TABLE+"]  set "+KEY_HEIGHT+
			"=1.85 where "+KEY_NAME+"=\"Lily\";";
//		String updateSQL="update ["+DB_TABLE+"]  set "+KEY_HEIGHT+
//			"=1.85 where "+KEY_NAME+"=?;";  //ʹ��ռλ��
	try{
		db.execSQL(updateSQL); //ִ���޸����
//		db.execSQL(updateSQL,new String[]{"Lily"}); //����ռλ����Ӧ������
		}catch(SQLException e){
			return false;
		}		
	return true;
	}
	
	//ɾ��
	public boolean delete(){
		String deleteSQL="delete from ["+DB_TABLE+"] ;";
	try{
		db.execSQL(deleteSQL); //ִ��ɾ�����
		}catch(SQLException e){
		return false;
		}		
	return true;
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
	
	//�������
	public  void execTransaction(boolean rollback){
		db.beginTransaction();  //��ʼ����
		update();
		delete(); //ȫ��ɾ������
		if (!rollback) //ģ����������������ع��������������,�ڽ�������ʱ����ȷ�ύ
			db.setTransactionSuccessful(); //�粻ִ�д���,���ڽ�������ʱ��ع�����
		db.endTransaction(); //��������
	}
}
