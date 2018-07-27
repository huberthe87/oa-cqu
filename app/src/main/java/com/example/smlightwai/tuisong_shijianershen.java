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

public class tuisong_shijianershen extends Activity {

  EditText tuihuiyuanyin;
  TextView shenqingren;
  TextView shenqingshijian;
  TextView shenqingneirong;
  TextView zhipairenyuan;

  String tuihuiyuanying;
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
            zhipairenyuan.setText(shijianrenyuanlist.zhipairenname);

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
    setContentView(R.layout.shijianzhipai);


    shenqingren = (TextView) this.findViewById(R.id.wclshenqingren);
    shenqingshijian = (TextView) this.findViewById(R.id.wclshenqingshijian);
    shenqingneirong = (TextView) this.findViewById(R.id.wclshenqingneirong);
    zhipairenyuan = (TextView) this.findViewById(R.id.zhipairenyuan);
    tuihuiyuanyin = (EditText) this.findViewById(R.id.wcltuihuiyuanyin);
    Button btn_zhipai = (Button) this.findViewById(R.id.shenqing);
    Button btn_tuihui = (Button) this.findViewById(R.id.tuihui);
    Button chakantupian = (Button) this.findViewById(R.id.chakantupian);
    ImageButton fanghui = (ImageButton) this.findViewById(R.id.fanghui);


    fanghui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(tuisong_shijianershen.this, tabumenzongheshijianguanli.class);
          ////����
          startActivity(functionpage);
        }

      }
    });

    chakantupian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tuisong_shijianershen.this, shijian_chakantupian.class);
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


    btn_zhipai.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new SjzpThread()).start();
        }
      }
    });


    btn_tuihui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new SjthThread()).start();
        }
      }
    });


    zhipairenyuan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new renyuanThread()).start();
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


  class SjzpThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/eventDeal/addDeal");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("personId", shijianrenyuanlist.zhipairen);
        params.put("eventId", Long.parseLong(jo.getString("id")));
        params.put("administratorTwoId", Long.parseLong(Loginpage.yhjo.getString("id")));


        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("SjzpThread", resStr);
        if(resStr.equals("true")) {

          Looper.prepare();
          Toast.makeText(tuisong_shijianershen.this, "ָ�ɳɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tuisong_shijianershen.this, "ָ��ʧ�ܣ�������ָ��", Toast.LENGTH_LONG).show();
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
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/updateTwo");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", jo.getString("id"));
        params.put("administratorTwoId", Loginpage.yhjo.getString("id"));
        params.put("returnReason", tuihuiyuanyin.getText().toString());


        String resStr = appapi.getWebServiceRes(params, url);


        Log.d("SjtgThread", resStr);


        if(resStr.equals("true")) {

          Looper.prepare();
          Toast.makeText(tuisong_shijianershen.this, "�˻سɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tuisong_shijianershen.this, "�˻�ʧ�ܣ��������˻�", Toast.LENGTH_LONG).show();
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


  class renyuanThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchDepartEmployee");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("organizationId", Loginpage.yhjo.getString("organizationId"));

        String resStr = appapi.getWebServiceRes(params, url);

        Intent intent = new Intent(tuisong_shijianershen.this, shijianrenyuanlist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp


        bundle.putString("renarenarenaxinxi", jo.toString());
        bundle.putString("renarenarena", resStr);
        intent.putExtras(bundle);

        startActivity(intent);


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

