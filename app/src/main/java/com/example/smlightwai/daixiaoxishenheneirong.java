package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;


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
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.Utils5000;
import jiekou.appapi;

public class daixiaoxishenheneirong extends Activity {


  TextView daishenhexiaoxizhuti;
  TextView daishenhexiaoxishijian;
  TextView daishenhexiaoxineirong;
  TextView daishenhexiaoxifaburen;
  EditText daichuxixiaoxishenheyijian;
  Button btn_daichulixiaoxitongguo;
  Button btn_daichulixiaoxidahui;
  JSONObject jsonObject;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            daishenhexiaoxizhuti.setText(jsonObject.getString("title"));
            daishenhexiaoxishijian.setText(jsonObject.getString("submissionDate"));
            daishenhexiaoxineirong.setText(jsonObject.getString("content"));
            daishenhexiaoxifaburen.setText(jsonObject.getString("senderName"));


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
    setContentView(R.layout.daichulixiaoxishenghe);

    daishenhexiaoxizhuti = (TextView) this.findViewById(R.id.daishenhexiaoxizhuti);
    daishenhexiaoxishijian = (TextView) this.findViewById(R.id.daishenhexiaoxishijian);
    daishenhexiaoxineirong = (TextView) this.findViewById(R.id.daishenhexiaoxineirong);
    daishenhexiaoxifaburen = (TextView) this.findViewById(R.id.daishenhexiaoxifaburen);
    daichuxixiaoxishenheyijian = (EditText) this.findViewById(R.id.dachuxixiaoxishenheyijian);
    btn_daichulixiaoxitongguo = (Button) this.findViewById(R.id.daichulixiaoxitongguo);
    btn_daichulixiaoxidahui = (Button) this.findViewById(R.id.daichulixiaoxidahui);

    btn_daichulixiaoxitongguo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils5000.isFastClick()) {

          new Thread(new shenhexiaoxitongguoThread()).start();

        }


      }
    });

    btn_daichulixiaoxidahui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if(Utils5000.isFastClick()) {

          if(daichuxixiaoxishenheyijian.getText().toString().trim().length() == 0) {

            Toast.makeText(daixiaoxishenheneirong.this, "�˻���Ϣ������д�˻�ԭ�򣡣�", Toast.LENGTH_LONG).show();
          } else {

            new Thread(new shenehxiaoxituihiThread()).start();

          }

        }

      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String clxx = bundle.getString("chulixiaoxineirong");

    Message message = new Message();
    message.what = 0;
    message.obj = clxx.toString();
    handler.sendMessage(message);
  }


  class shenehxiaoxituihiThread extends Thread {
    public void run() {

      //post����

      try {

        URL url = new URL("http://222.178.109.129:9082/ibs/api/message/update");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("messageId", jsonObject.getString("id"));
        params.put("returnReason", daichuxixiaoxishenheyijian.getText().toString());
        params.put("administratorId", Loginpage.yhjo.getString("id"));

        String resStr = appapi.getWebServiceRes(params, url);
        if(resStr.equals("false")) {
          Looper.prepare();
          Toast.makeText(daixiaoxishenheneirong.this, "�˻ز��ɹ����������˻�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(daixiaoxishenheneirong.this, "�˻سɹ�", Toast.LENGTH_LONG).show();
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


  class shenhexiaoxitongguoThread extends Thread {
    public void run() {

      //post����

      try {

        URL url = new URL("http://222.178.109.129:9082/ibs/api/message/update");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("messageId", jsonObject.getString("id"));
//							params.put("returnReason", null);
        params.put("administratorId", Loginpage.yhjo.getString("id"));

        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("dsdadasda", resStr);
        if(resStr.equals("false")) {
          Looper.prepare();
          Toast.makeText(daixiaoxishenheneirong.this, "ͨ�����ɹ���������ȷ��", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(daixiaoxishenheneirong.this, "ͨ���ɹ�", Toast.LENGTH_LONG).show();
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