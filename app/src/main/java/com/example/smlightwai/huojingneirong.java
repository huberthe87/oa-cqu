package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;
import com.example.smlightwai.guzhangneirong.QueRenThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class huojingneirong extends Activity {

  private TextView guzhangdizhi;
  private TextView guzhangshebei;
  private TextView fashengshijian;
  private EditText pizhu;
  private Button sheBeiGuZhangQueRenBt;
  private JSONObject jo;//�豸������Ϣ


  //��ʾ�����豸��Ϣ
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:
          try {
            guzhangdizhi.setText(jo.getString("floor"));
            guzhangshebei.setText(jo.getString("resourceName"));
            fashengshijian.setText(jo.getString("alarmTime"));

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
    setContentView(R.layout.huozaibaojingneirong);


    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhui);
    guzhangdizhi = (TextView) findViewById(R.id.guzhangdizhi);
    guzhangshebei = (TextView) findViewById(R.id.guzhangshebei);
    fashengshijian = (TextView) findViewById(R.id.fashengshijian);
    sheBeiGuZhangQueRenBt = (Button) findViewById(R.id.queren);
    pizhu = (EditText) findViewById(R.id.pizhu);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(huojingneirong.this, huozaibaojinglist.class);
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

    try {
      //�����ת������list��ʾ��Ϣ
      Bundle bundle = this.getIntent().getExtras();
      jo = new JSONObject(bundle.getString("huozaijingxaingxixinxi"));

      Message message = new Message();
      message.what = 0;
      message.obj = "";
      handler.sendMessage(message);
    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  class QueRenThread extends Thread {
    public void run() {
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/alarm/"
            + "{alarmId,userId,managerDescription,inhibitionTime}");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("alarmId", jo.getString("id"));
        params.put("userId", Loginpage.yhjo.getString("id"));
        params.put("managerDescription", pizhu.getText());

        if(appapi.getWebServiceRes(params, url).equals("true")) {
          Looper.prepare();
          Toast.makeText(huojingneirong.this, "���ȷ�ϣ���", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(huojingneirong.this, "ȷ��ʧ�ܣ������²�������", Toast.LENGTH_LONG).show();
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


}
