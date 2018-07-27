package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.smlightwai.R;
import com.example.smlightwai.Roleanbao.tongxinganbaolistThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import jiekou.Utils;
import jiekou.appapi;

public class Rolezongjingli extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.zongjingli);


    ImageButton btn_shijian = (ImageButton) findViewById(R.id.shijianguanli);
    ImageButton btn_baojingguanli = (ImageButton) findViewById(R.id.baojingguanli);
    ImageButton btn_shebeiguanli = (ImageButton) findViewById(R.id.shebeiguanli);
    ImageButton btn_xiaoxiguanli = (ImageButton) findViewById(R.id.xiaoxiguanli);
    ImageButton btn_xunzhiguanli = (ImageButton) findViewById(R.id.xunzhiguanli);
    ImageButton btn_zhanghuguanli = (ImageButton) findViewById(R.id.zhanghuguanli);
    ImageButton btn_shanghuguanli = (ImageButton) findViewById(R.id.shanghuguanli);
    ImageButton btn_tongxingguanli = (ImageButton) findViewById(R.id.tongxingguanli);
    ImageButton btn_back = (ImageButton) findViewById(R.id.fanghui);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(Rolezongjingli.this, Loginpage.class);
          ////����
          startActivity(functionpage);
        }

      }
    });


    btn_baojingguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(Rolezongjingli.this, baojingboth.class);
          ////����
          startActivity(functionpage);
        }

      }
    });


    btn_shebeiguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(Rolezongjingli.this, shebei_page1.class);
          ////����
          startActivity(functionpage);
        }

      }
    });


    btn_xunzhiguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(Rolezongjingli.this, xunzhi_page.class);
          ////����
          startActivity(functionpage);
        }

      }
    });


    btn_zhanghuguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(Rolezongjingli.this, zhanghuguanli.class);
          ////����
          startActivity(functionpage);
        }

      }
    });

    btn_tongxingguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          new Thread(new tongxinganbaolistThread()).start();
        }

      }
    });


    btn_xiaoxiguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Rolezongjingli.this, xiaoxi_page3.class);
          startActivity(functionpage);

        }

      }
    });

    btn_shijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Rolezongjingli.this, shijian_page3.class);
          startActivity(functionpage);

        }

      }
    });

    btn_shanghuguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          Intent functionpage = new Intent(Rolezongjingli.this, guanggaofenleipage.class);
          ////����
          startActivity(functionpage);
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
        Intent intent = new Intent(Rolezongjingli.this, tongxing_anbaolist.class);

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
}
