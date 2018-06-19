package QDLG.LCB.Demo.ContentProviderDatabaseDemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

public class MyContentProvider extends ContentProvider{//�Զ����ContentProvider��
	//Ҫ������SQLite���ݿ����
	private SQLiteDatabase db;
	//�������ݿ�ĸ�������
	private MyDBOpenHelper helper;
	
	//�ڴ�����ʼ��������׼���ײ����ݲ���
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Context context=getContext(); //��ȡ�����Ķ���
		helper=new MyDBOpenHelper(context, MyDBOpenHelper.DB_NAME, null, MyDBOpenHelper.DB_VERSION);
		try{
			db=helper.getWritableDatabase(); //��/�������ݿ�
		}catch(SQLiteException e){
			return false;
		}
		
		return true; //��ȷ��ʼ��,����
	}
	
	//�������ڼ��URI��UriMatcher����,UriMatcher.NO_MATCH��ʾƥ��ʧ��ʱ����-1
	private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
	static{
		//�����UriMatcher������Խ���ƥ��2�����͵�URI
		//ǰ2����������Ҫƥ���URI, ��3����������ƥ��ɹ���ķ���ֵ
		uriMatcher.addURI(ConstValue.AUTHORITY, ConstValue.PATH_MULTIPLE, ConstValue.MULTIPLE_PEOPLE);
		uriMatcher.addURI(ConstValue.AUTHORITY, ConstValue.PATH_SINGLE, ConstValue.SINGLE_PEOPLE);
	}
	
	//��ȡContentProvider�ṩ��ָ��URI�����ݵ�MIME����
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch(uriMatcher.match(uri)){ //����ƥ��������MIME����
		case ConstValue.MULTIPLE_PEOPLE:
			return ConstValue.MIME_MULTIPLE;
		case ConstValue.SINGLE_PEOPLE:
			return ConstValue.MIME_SINGLE;
			default:
				throw new IllegalArgumentException("�޷�ʶ���URI: "+uri); 
		}
	}

	//��������(���ܲ���һ����¼) 
	//��Ҫ���������¼,ContentResolver.bultInsert(CONTENT_URI, arrayValues)���ε��ô˷���
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub		
		//�����ݿ��в�������, ���ڶ����ʱ,������Ҫ����uri��ֵȷ��Ҫ�����ı�
		long id=db.insert(MyDBOpenHelper.DB_TABLE, null, values);
		if (id>0){ //����ɹ�ʱ���ظ��е�_idֵ
			Uri newUri=ContentUris.withAppendedId(ConstValue.CONTENT_URI_, id); //�����¼��е�URI
			getContext().getContentResolver().notifyChange(newUri, null); //֪ͨ���ݸ���
			return newUri; //�����¼��е�URI
		}
		//����ʧ��--id<=0
		throw new SQLiteException("����ʧ��!--"+uri);
	}

	//ɾ������
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count=0; //ɾ��������
		
		switch (uriMatcher.match(uri)){ //����URI������ѡ��ɾ����ʽ
		case ConstValue.MULTIPLE_PEOPLE:
			count=db.delete(MyDBOpenHelper.DB_TABLE, selection, selectionArgs);
			break;
		case ConstValue.SINGLE_PEOPLE:
			String id=uri.getPathSegments().get(1);//��ȡURI����IDֵ
			count=db.delete(MyDBOpenHelper.DB_TABLE, MyDBOpenHelper.KEY_ID+
					"="+id, null); //ɾ��ָ��ID��һ����¼
			break;
			default:
				throw new IllegalArgumentException("��֧�ֵ�URI: "+uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null); //֪ͨ���ݸ���
		return count;
	}

	//�޸Ĳ���
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count; //�޸ĵ�����
		
		switch (uriMatcher.match(uri)){ //����URI������ѡ���޸ķ�ʽ
		case ConstValue.MULTIPLE_PEOPLE:
			count=db.update(MyDBOpenHelper.DB_TABLE, values, selection, selectionArgs);
			break;
		case ConstValue.SINGLE_PEOPLE:
			String id=uri.getPathSegments().get(1);//��ȡURI����IDֵ
			count=db.update(MyDBOpenHelper.DB_TABLE, values, MyDBOpenHelper.KEY_ID+
					"="+id, null); //�޸�ָ��ID��һ����¼
			break;
			default:
				throw new IllegalArgumentException("��֧�ֵ�URI: "+uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null); //֪ͨ���ݸ���
		return count;
	}
	
	//��ѯ����
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Cursor cursor=null;

		switch (uriMatcher.match(uri)){
		case ConstValue.MULTIPLE_PEOPLE:
			cursor=db.query(MyDBOpenHelper.DB_TABLE, projection, 
					selection, selectionArgs, null, null, sortOrder);
			break;
		case ConstValue.SINGLE_PEOPLE:
			cursor=db.query(MyDBOpenHelper.DB_TABLE, projection, 
					MyDBOpenHelper.KEY_ID+"="+uri.getPathSegments().get(1), null, 
					null, null, sortOrder);
			break;
			default:
				break;
		}
		
		if (cursor!=null)  //ע�����ݸı�ļ�����, �Ա����ݸı��ʱ֪ͨCursor--setNotificationUri�ڲ��ǵ���registerContentObserver
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

}
