package QDLG.LCB.Demo.DrawDemo;

import android.app.Activity;
import android.os.Bundle;

public class DrawDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //�����Զ����View����
        CustomView view=new CustomView(this);
        
        //����Activity����ʾ����Ϊ�մ�����View����
        setContentView(view);        
    }
}