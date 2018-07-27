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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class gerenshijianguanli extends Activity {

  private String flagshijian;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.benbumenchaxun);

    Button btn_daishenheshijian = (Button) findViewById(R.id.daishenheshijian);
    Button btn_chulizhongshijian = (Button) findViewById(R.id.chulizhongshijian);
    Button btn_yishentuihuishijian = (Button) findViewById(R.id.yishentuihuishijian);
    Button btn_ershentuihishijian = (Button) findViewById(R.id.ershentuihishijian);
    Button btn_yitongguoshijian = (Button) findViewById(R.id.yitongguoshijian);


    ImageButton btn_back = (ImageButton) findViewById(R.id.benbumenfanhui);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          if(Loginpage.gerenshijiaguanlifrom.equals("shijian_page3")) {

            Intent intent = new Intent(gerenshijianguanli.this, shijian_page3.class);

            startActivity(intent);
          } else {

            Intent intent = new Intent(gerenshijianguanli.this, shijian_page2.class);

            startActivity(intent);
          }
        }
      }
    });


    btn_yitongguoshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          flagshijian = "1";
          new Thread(new gerenshijianchaxunThread()).start();
        }
      }
    });


    btn_daishenheshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          flagshijian = "0";
          new Thread(new gerenshijianchaxunThread()).start();
        }
      }
    });


    btn_chulizhongshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flagshijian = "2";
          new Thread(new gerenshijianchaxunThread()).start();
        }
      }
    });


    btn_yishentuihuishijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flagshijian = "3";
          new Thread(new gerenshijianchaxunThread()).start();
        }
      }
    });


    btn_ershentuihishijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flagshijian = "4";
          new Thread(new gerenshijianchaxunThread()).start();
        }
      }
    });


  }


  class gerenshijianchaxunThread extends Thread {
    public void run() {
      //post����

      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchApply");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("senderId", Long.parseLong(Loginpage.yhjo.getString("id")));
        params.put("checkStatus", flagshijian);

        String resStr = appapi.getWebServiceRes(params, url);


        Intent intent = new Intent(gerenshijianguanli.this, shijianlist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("shijianlist", resStr);
        intent.putExtras(bundle);
        ////����
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
