package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
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

public class shenheguanggaoneirong extends Activity {
  TextView shangpuname;
  TextView shenqingshijian;
  TextView shenqingneirong;
  EditText chuliyijian;
  Button shqueren;
  Button shtuihui;
  private JSONObject jsonObject;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            shangpuname.setText(jsonObject.getString("storeName"));
            shenqingshijian.setText(jsonObject.getString("submissionDate"));
            shenqingneirong.setText(jsonObject.getString("description"));


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
    setContentView(R.layout.guanggaoneirong);

    shangpuname = (TextView) this.findViewById(R.id.shshangpuname);
    shenqingshijian = (TextView) this.findViewById(R.id.shshenqingshijian);
    shenqingneirong = (TextView) this.findViewById(R.id.shshenqingneirong);
    ;
    chuliyijian = (EditText) this.findViewById(R.id.shchuliyijian);
    shqueren = (Button) this.findViewById(R.id.shqueren);
    shtuihui = (Button) this.findViewById(R.id.shtuihui);


    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.ggshfanghui);

    shqueren.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new guanggaotongguoThread()).start();
        }


      }
    });

    shtuihui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new guanggaotuihuiThread()).start();

        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String jsxx = bundle.getString("shenheguanggaoneirong");

    Message message = new Message();
    message.what = 0;
    message.obj = jsxx.toString();
    handler.sendMessage(message);


  }


  class guanggaotongguoThread extends Thread {
    public void run() {

      //post����

      try {

        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/updateIbsPassRecordStatus ");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", Long.parseLong(jsonObject.getString("id")));
        params.put("status", 1);
        params.put("adminId", Long.parseLong(Loginpage.yhjo.getString("id")));


        String resStr = appapi.getWebServiceRes(params, url);
        if(resStr.equals("false")) {
          Looper.prepare();
          Toast.makeText(shenheguanggaoneirong.this, "ͨ�����ɹ���������ͨ��", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(shenheguanggaoneirong.this, "ͨ���ɹ�", Toast.LENGTH_LONG).show();
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


  class guanggaotuihuiThread extends Thread {
    public void run() {

      //post����

      try {

        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/updateIbsPassRecordStatus ");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", Long.parseLong(jsonObject.getString("id")));
        params.put("status", 1);
        params.put("adminId", Long.parseLong(Loginpage.yhjo.getString("id")));
        params.put("reason", chuliyijian.getText().toString());

        String resStr = appapi.getWebServiceRes(params, url);
        if(resStr.equals("false")) {
          Looper.prepare();
          Toast.makeText(shenheguanggaoneirong.this, "�˻ز��ɹ����������˻�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(shenheguanggaoneirong.this, "�˻سɹ�", Toast.LENGTH_LONG).show();
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



