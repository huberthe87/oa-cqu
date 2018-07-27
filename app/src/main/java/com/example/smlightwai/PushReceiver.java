package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.tuisong_shijianyishen.shijianThread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import jiekou.appapi;

public class PushReceiver extends BroadcastReceiver {
  private static final String TAG = "PushReceiver";

  String type;
  String id;
  String resStr;


  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO Auto-generated method stub
    Bundle bundle = intent.getExtras();
    Log.d(TAG, "onReceive - " + intent.getAction());

    //ע��
    if(JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

      //�յ��Զ�����Ϣ
    } else if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
      //�Զ�����Ϣ���Է���json����
      System.out.println("�յ����Զ�����Ϣ����Ϣ�����ǣ�" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
      // �Զ�����Ϣ����չʾ��֪ͨ������ȫҪ������д����ȥ����
    } else if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
      System.out.println("�յ���֪ͨ");
      // �����������Щͳ�ƣ�������Щ��������(֪ͨ�������)
    } else if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
      System.out.println("�û��������֪ͨ");


      openNotification(context, bundle);
      // ����������Լ�д����ȥ�����û���������Ϊ
      //������������������ĸ����ֶΡ����Ǹ� JSON �ַ�����
//	            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);  
//	            System.out.println("������Ϣ��"+extras);  
//	            try {  
//	                JSONObject obj = new JSONObject(extras);                  
//	                String id = obj.getString("id"); 
//	                String type = obj.getString("type"); 
//                    if (type.equals("������Ϣ")) {
//                    	  System.out.println("id��Ϣ��"+id);  
//                    	Intent  wahaha= new Intent(context, Loginpage.class);  //?????????
      ////����
//    					
//    					Bundle bundle1=new Bundle();
//    				    //����name����Ϊtinyphp
//    				    bundle1.putString("jixuchulishijianneirong", id);
//    				    intent.putExtras(bundle1);   					
//    					context.startActivity(wahaha); 		
//                    } 
//	                //�������  
//	            } catch (JSONException e) {  
//	                e.printStackTrace();  
//	            }  
//	        } else {  
//	            Log.d(TAG, "Unhandled intent - " + intent.getAction());  
    }
  }


  private void openNotification(Context context, Bundle bundle) {
    String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

    try {
      JSONObject extrasJson = new JSONObject(extras);
      id = extrasJson.optString("id");
      Log.d("id", id);
      type = extrasJson.optString("type");
      Log.d("type", type);

    } catch(Exception e) {

      return;
    }
    if(type.equals("������Ϣ")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_huojing.class);
      bundle.putString("baojingid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mIntent);
    } else if(type.equals("��Ϣ����")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_xiaoxichakan.class);
      bundle.putString("xiaoxiid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mIntent);
    } else if(type.equals("��������")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_shijianyishen.class);
      bundle.putString("shijianid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mIntent);
    } else if(type.equals("һ�����")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_shijianershen.class);
      bundle.putString("shijianid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    } else if(type.equals("����ָ��")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_jixuchuli.class);
      bundle.putString("shijianid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mIntent);
    } else if(type.equals("һ���˻�")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_yishentuihui.class);
      bundle.putString("shijianid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mIntent);
    } else if(type.equals("�����˻�")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_yishentuihui.class);
      bundle.putString("shijianid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mIntent);
    } else if(type.equals("�豸ά��")) {
      System.out.println("id��Ϣ��" + id);
      Intent mIntent = new Intent(context, tuisong_weibaoqueren.class);

      bundle.putString("weibaoid", id);
      mIntent.putExtras(bundle);
      mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mIntent);
//    } else if (TYPE_ANOTHER.equals(myValue)){
//        Intent mIntent = new Intent(context, AnotherActivity.class);
//        mIntent.putExtras(bundle);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(mIntent);
    }
  }

}