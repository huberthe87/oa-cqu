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

public class guanggaofenleipage extends Activity {

  String zhuangtaiflag;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.guanggaofenlei);


    ImageButton btn_back = (ImageButton) findViewById(R.id.guanggaofenleifanhui);
    Button btn_yitiuhuiguanggao = (Button) findViewById(R.id.yitiuhuiguanggao);
    Button btn_yitongguoguanggao = (Button) findViewById(R.id.yitongguoguanggao);
    Button btn_daishenheguanggao = (Button) findViewById(R.id.daishenheguanggao);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(guanggaofenleipage.this, shijian_page2.class);
          startActivity(intent);
        }

      }
    });


    btn_yitiuhuiguanggao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          zhuangtaiflag = "2";
          new Thread(new guanggaofenleiThread()).start();
        }

      }
    });


    btn_yitongguoguanggao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          zhuangtaiflag = "1";
          new Thread(new guanggaofenleiThread()).start();
        }

      }
    });


    btn_daishenheguanggao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          zhuangtaiflag = "0";
          new Thread(new guanggaofenleiThread()).start();
        }

      }
    });


  }

  class guanggaofenleiThread extends Thread {
    public void run() {


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/adverRecord/adverRecordInfo");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put("status", zhuangtaiflag);
        String resStr = appapi.getWebServiceRes(params, url);


        if(zhuangtaiflag.equals("1")) {

          Intent intent = new Intent(guanggaofenleipage.this, lishiguanggaolist.class);

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("lishiguanggaolist", resStr);
          intent.putExtras(bundle);

          startActivity(intent);

        } else if(zhuangtaiflag.equals("2")) {
          Intent intent = new Intent(guanggaofenleipage.this, lishiguanggaolist.class);

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("lishiguanggaolist", resStr);
          intent.putExtras(bundle);

          startActivity(intent);
        } else {
          Intent intent = new Intent(guanggaofenleipage.this, shenheguanggaolist.class);

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shenheguanggaolist", resStr);
          intent.putExtras(bundle);

          startActivity(intent);

        }
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }

}
