package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


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

public class tuisong_shijianyishen extends Activity {

  EditText tuihuiyuanyin;
  TextView shenqingren;
  TextView shenqingshijian;
  TextView shenqingneirong;
  TextView zhipairenyuan;

  String tuihuiyuanying;
  String shijianid;
  private JSONObject jo;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {

            //���������UI�������������ʾ��������
            shenqingren.setText(jo.getString("senderName"));
            shenqingshijian.setText(jo.getString("submissionDate"));
            shenqingneirong.setText(jo.getString("content"));


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
    setContentView(R.layout.shijianyicishenhe);


    shenqingren = (TextView) this.findViewById(R.id.wclyishentongguoren);
    shenqingshijian = (TextView) this.findViewById(R.id.wclyishentongguoshijian);
    shenqingneirong = (TextView) this.findViewById(R.id.wclyishentongguoneirong);
    tuihuiyuanyin = (EditText) this.findViewById(R.id.wclyishentuihuiyuanyin);
    Button btn_yishentongguo = (Button) this.findViewById(R.id.yishentongguo);
    Button btn_yishentuihuii = (Button) this.findViewById(R.id.yishentuihui);


    ImageButton fanghui = (ImageButton) this.findViewById(R.id.fanghui);


    fanghui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(tuisong_shijianyishen.this, benbumenzongheshijianguanli.class);
          ////����
          startActivity(functionpage);
        }

      }
    });


    btn_yishentongguo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new SjtgThread()).start();
        }
      }
    });


    btn_yishentuihuii.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new SjthThread()).start();
        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    shijianid = bundle.getString("shijianid");
    new Thread(new shijianThread()).start();

  }


  class shijianThread extends Thread {
    public void run() {

      try {


        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchById");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", shijianid);

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("wahahahahaahhahahah", resStr);
        jo = new JSONObject(resStr);

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


  class SjtgThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/update");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", Long.parseLong(jo.getString("id")));
        params.put("checkStatus", "1");
        params.put("administratorId", Long.parseLong(Loginpage.yhjo.getString("id")));


        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("SjtgThread", resStr);

        if(resStr.equals("true")) {

          Looper.prepare();
          Toast.makeText(tuisong_shijianyishen.this, "ͨ���ɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tuisong_shijianyishen.this, "ͨ��ʧ�ܣ�������ͨ��", Toast.LENGTH_LONG).show();
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


  class SjthThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/update");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", Long.parseLong(jo.getString("id")));
        params.put("checkStatus", "3");
        params.put("administratorId", Long.parseLong(Loginpage.yhjo.getString("id")));
        params.put("returnReason", tuihuiyuanyin.getText().toString());


        String resStr = appapi.getWebServiceRes(params, url);

        if(resStr.equals("true")) {

          Looper.prepare();
          Toast.makeText(tuisong_shijianyishen.this, "�˻سɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tuisong_shijianyishen.this, "�˻�ʧ�ܣ��������˻�", Toast.LENGTH_LONG).show();
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


