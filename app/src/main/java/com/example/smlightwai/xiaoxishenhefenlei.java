package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import jiekou.Utils;
import jiekou.appapi;

public class xiaoxishenhefenlei extends Activity {


  private String zhuangtaiflag;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shenhexiaoxifenlei);


    ImageButton btn_back = (ImageButton) findViewById(R.id.shenhexiaoxifenleifanhui);
    Button btn_tuihuishenhe = (Button) findViewById(R.id.tuihuishenhexiaoxi);
    Button btn_tongguoshenhe = (Button) findViewById(R.id.tongguoshenhexiaoxi);
    Button btn_daishenhe = (Button) findViewById(R.id.daishenhexiaoxi);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(xiaoxishenhefenlei.this, xiaoxi_page2.class);
          startActivity(intent);
        }
      }
    });


    btn_tuihuishenhe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          zhuangtaiflag = "2";
          new Thread(new xiaoxishenhefenleiThread()).start();
        }
      }
    });


    btn_tongguoshenhe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          zhuangtaiflag = "1";
          new Thread(new xiaoxishenhefenleiThread()).start();
        }
      }
    });


    btn_daishenhe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          zhuangtaiflag = "0";
          new Thread(new xiaoxishenhefenleiThread()).start();
        }
      }
    });


  }

  class xiaoxishenhefenleiThread extends Thread {
    public void run() {

      //post����

      try {

        URL url = new URL("http://222.178.109.129:9082/ibs/api/message/message");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        ;
        params.put("userId", Loginpage.yhjo.getString("id"));
        params.put("type", "audit");
        params.put("checkStatus", zhuangtaiflag);
        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("dsadasddddddddddddddddasda", resStr);

        if(zhuangtaiflag.equals("0")) {

          Intent intent = new Intent(xiaoxishenhefenlei.this, chulixiaoxishenhelist.class);

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shenhexiaoxilist", resStr);
          intent.putExtras(bundle);

          startActivity(intent);

        } else if(zhuangtaiflag.equals("1")) {
          Intent intent = new Intent(xiaoxishenhefenlei.this, lishixiaoxishenhelist.class);

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shenhexiaoxilist", resStr);
          intent.putExtras(bundle);

          startActivity(intent);
        } else {
          Intent intent = new Intent(xiaoxishenhefenlei.this, lishixiaoxishenhelist.class);

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shenhexiaoxilist", resStr);
          intent.putExtras(bundle);

          startActivity(intent);

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
