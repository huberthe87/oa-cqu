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

public class tuisong_jixuchuli extends Activity {

  TextView shenqingren;
  TextView shenqingshijian;
  TextView shenqingneirong;
  TextView zhipairenyuan;


  EditText jixutuihuiyuanyin;

  String tupianone;
  String tupiantwo;
  String tupianthree;

  String shijianid;
  private JSONObject jo;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {


            tupianone = jo.getString("imageOne");
            tupiantwo = jo.getString("imageOne");
            tupianthree = jo.getString("imageOne");

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
    setContentView(R.layout.jixuchulishijianneirong);


    shenqingren = (TextView) this.findViewById(R.id.shenqingneirong19);
    shenqingshijian = (TextView) this.findViewById(R.id.shenqingneirong20);
    shenqingneirong = (TextView) this.findViewById(R.id.shenqingshijian4333530);
    jixutuihuiyuanyin = (EditText) this.findViewById(R.id.jixutuihuiyuanyin);

    Button btn_queren = (Button) this.findViewById(R.id.jxclqueren);
    Button btn_wanchen = (Button) this.findViewById(R.id.jxclwanchen);
    Button btn_tuihui = (Button) this.findViewById(R.id.jxcltuihui);

    Button chakantupian = (Button) this.findViewById(R.id.chakantupian);

    ImageButton fanghui = (ImageButton) this.findViewById(R.id.fanghui);


    fanghui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(tuisong_jixuchuli.this, shijian_page1.class);
          ////����
          startActivity(functionpage);
        }

      }
    });

    chakantupian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tuisong_jixuchuli.this, shijian_chakantupian.class);
          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tupiandizhi1", tupianone);
          bundle.putString("tupiandizhi2", tupiantwo);
          bundle.putString("tupiandizhi3", tupianthree);
          intent.putExtras(bundle);
          ////����
          startActivity(intent);
        }


      }
    });

    btn_queren.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new SjqrThread()).start();
        }
      }
    });


    btn_wanchen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new SjclwanchengThread()).start();
        }
      }
    });


    btn_tuihui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new SjcltuihuiThread()).start();
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


  class SjqrThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/eventDeal/updateStatus");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", jo.getString("id"));

        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("neirong", resStr);

        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "ȷ�ϳɹ�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "ȷ��ʧ�ܣ�������ȷ��",
              Toast.LENGTH_SHORT).show();
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


  class SjclwanchengThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/eventDeal/update");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", Long.parseLong(jo.getString("id")));
        params.put("content", jixutuihuiyuanyin.getText());


        String resStr = appapi.getWebServiceRes(params, url);

        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "����ɹ�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "����ʧ�ܣ������´���",
              Toast.LENGTH_SHORT).show();
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

  class SjcltuihuiThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/eventDeal/update");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", Long.parseLong(jo.getString("id")));
        params.put("returnReason", jixutuihuiyuanyin.getText());


        String resStr = appapi.getWebServiceRes(params, url);

        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "�˻سɹ�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "�˻�ʧ�ܣ��������˻�",
              Toast.LENGTH_SHORT).show();
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
