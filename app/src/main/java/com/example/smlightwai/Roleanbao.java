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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class Roleanbao extends Activity {


  private long exitTime = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.juese_anbaobu);


    ImageButton btn_back = (ImageButton) findViewById(R.id.anbaojiemianfanghui);
    ImageButton btn_shijian = (ImageButton) findViewById(R.id.shijianguanli);
    ImageButton btn_baojing = (ImageButton) findViewById(R.id.baojingguanli);

    ImageButton btn_zhanghu = (ImageButton) findViewById(R.id.zhanghuguanli);
    ImageButton btn_xunzhi = (ImageButton) findViewById(R.id.xunzhiguanli);
    ImageButton btn_xiaoxi = (ImageButton) findViewById(R.id.xiaoxiguanli);
    ImageButton btn_tongxing = (ImageButton) findViewById(R.id.tongxingguanli);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(Roleanbao.this, Loginpage.class);
          ////����
          startActivity(functionpage);
        }

      }
    });


    btn_tongxing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


        Log.d("111111111", "111111111111");

        new Thread(new tongxinganbaolistThread()).start();


      }
    });

    btn_shijian.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          try {
            Intent functionpage;

            if(Loginpage.yhjo.getString("roleId").equals("6")) {
              //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
              functionpage = new Intent(Roleanbao.this, shijian_page1.class);
              startActivity(functionpage);

            } else {
              functionpage = new Intent(Roleanbao.this, shijian_page2.class);
              startActivity(functionpage);
            }

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

      }
    });


    btn_baojing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Roleanbao.this, baojingboth.class);
          ////����
          startActivity(functionpage);
        }
      }
    });


    btn_zhanghu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Roleanbao.this, zhanghuguanli.class);
          ////����
          startActivity(functionpage);
        }
      }
    });


    btn_xunzhi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Roleanbao.this, xunzhi_page.class);
          ////����
          startActivity(functionpage);
        }
      }
    });


    btn_xiaoxi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          try {
            Intent functionpage;

            if(Loginpage.yhjo.getString("roleId").equals("5")) {
              //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
              functionpage = new Intent(Roleanbao.this, xiaoxi_page2.class);
              startActivity(functionpage);

            } else {
              functionpage = new Intent(Roleanbao.this, xiaoxi_page1.class);
              startActivity(functionpage);
            }

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }
      }
    });

  }


  class tongxinganbaolistThread extends Thread {
    public void run() {

      //post����

      try {
        Log.d("111111111", "222222222222222222");
        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/getAllCheckedPassRecordListForApp");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("111111111", "resStr");
        Log.d("111111111", "333333333333333333");
        Intent intent = new Intent(Roleanbao.this, tongxing_anbaolist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("tongxinganbaolist", resStr);
        intent.putExtras(bundle);

        startActivity(intent);


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }


  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

      if((System.currentTimeMillis() - exitTime) > 2000) {

        Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();

        exitTime = System.currentTimeMillis();

      } else {

        finish();

        System.exit(0);

      }

      return true;

    }

    return super.onKeyDown(keyCode, event);

  }


}
