package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.huojingneirong.QueRenThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class tuisong_huojing extends Activity {

  private TextView guzhangdizhi;
  private TextView guzhangshebei;
  private TextView fashengshijian;
  private EditText pizhu;
  private Button sheBeiGuZhangQueRenBt;
  private JSONObject jo;//�豸������Ϣ
  TextView guzhangmiaoshu;
  TextView guzhangdengji;
  String baojingid;

  //��ʾ�����豸��Ϣ
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:
          try {


            guzhangdizhi.setText(jo.getString("floor"));
            guzhangshebei.setText(jo.getString("itemName"));
            fashengshijian.setText(jo.getString("alarmTime"));
            guzhangdengji.setText(jo.getString("alarmLevel"));
            guzhangmiaoshu.setText(jo.getString("alarmDescription"));
          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

          break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shebeibaojingneirong);


    ImageButton btn_back = (ImageButton) findViewById(R.id.baojingfanhui);
    guzhangdizhi = (TextView) findViewById(R.id.guzhangdizhi);
    guzhangshebei = (TextView) findViewById(R.id.guzhangshebei);
    fashengshijian = (TextView) findViewById(R.id.fashengshijian);
    guzhangdengji = (TextView) findViewById(R.id.guzhangdengji);
    guzhangmiaoshu = (TextView) findViewById(R.id.guzhangmiaoshu);
    sheBeiGuZhangQueRenBt = (Button) findViewById(R.id.queren);
    pizhu = (EditText) findViewById(R.id.pizhu);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tuisong_huojing.this, baojingboth.class);
          startActivity(intent);
        }

      }
    });


    sheBeiGuZhangQueRenBt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //���豸����ȷ����Ϣ���ظ�������
        if(Utils.isFastClick()) {
          new Thread(new QueRenThread()).start();
        }
      }
    });


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    baojingid = bundle.getString("baojingid");
    new Thread(new baojingneirongThread()).start();
//			Message message=new Message();
//			message.what=0;	
//			message.obj="";
//			handler.sendMessage(message);


  }

  class QueRenThread extends Thread {
    public void run() {
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/alarm/{alarmId,userId,managerDescription,inhibitionTime}");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("alarmId", jo.getString("id"));
        params.put("userId", Loginpage.yhjo.getString("id"));
        params.put("managerDescription", pizhu.getText());
        params.put("inhibitionTime", "8");


        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("managerDescription", resStr);
        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(tuisong_huojing.this, "���ȷ�ϣ���", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tuisong_huojing.this, "ȷ��ʧ�ܣ������²�������", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        }
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }
  }


  class baojingneirongThread extends Thread {
    public void run() {

      try {

        Log.d("wahahahahaahhahahah", "11111111111111111111111111");
        URL url = new URL("http://222.178.109.129:9082/ibs/api/alarm/{alarmId}");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("alarmId", baojingid);

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("wahahahahaahhahahah", resStr);
        jo = new JSONObject(resStr);
        Log.d("wahahahahaahhahahah", "shiyxia");
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      Message message = new Message();
      message.what = 0;
      message.obj = "";
      handler.sendMessage(message);


    }
  }

}

