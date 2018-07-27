package com.example.smlightwai;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import jiekou.Utils;

public class Roleengineer extends Activity {
  private ImageButton btn_back;
  private ImageButton btn_shijian;
  private ImageButton btn_baojing;
  private ImageButton btn_shebei;
  private ImageButton btn_zhanghu;
  private ImageButton btn_xunzhi;
  private ImageButton btn_xiaoxi;


  private long exitTime = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.juese_wuye);

    btn_back = (ImageButton) findViewById(R.id.fanghui);
    btn_shijian = (ImageButton) findViewById(R.id.shijianguanli);
    btn_baojing = (ImageButton) findViewById(R.id.baojingguanli);
    btn_shebei = (ImageButton) findViewById(R.id.shebeiguanli);
    btn_zhanghu = (ImageButton) findViewById(R.id.zhanghuguanli);
    btn_xunzhi = (ImageButton) findViewById(R.id.xunzhiguanli);
    btn_xiaoxi = (ImageButton) findViewById(R.id.xiaoxiguanli);


//        onKeyDown(KeyEvent.KEYCODE_BACK, null);


    btn_shijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          try {


            Intent functionpage;

            if(Loginpage.yhjo.getString("roleId").equals("8")) {
              //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
              functionpage = new Intent(Roleengineer.this, shijian_page1.class);
              startActivity(functionpage);

            } else {
              functionpage = new Intent(Roleengineer.this, shijian_page2.class);
              startActivity(functionpage);
            }

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }
      }
    });


    btn_xunzhi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Roleengineer.this, xunzhi_page.class);
          ////����
          startActivity(functionpage);
        }
      }
    });


    btn_shebei.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Roleengineer.this, shebei_page1.class);
          ////����
          startActivity(functionpage);
        }
      }
    });


    btn_baojing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Roleengineer.this, baojingboth.class);
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
          Intent functionpage = new Intent(Roleengineer.this, zhanghuguanli.class);
          ////����
          startActivity(functionpage);
        }
      }
    });


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent fanhuilogin = new Intent(Roleengineer.this, Loginpage.class);
          ////����
          startActivity(fanhuilogin);
        }
      }
    });

    btn_xiaoxi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          try {
            Intent functionpage;

            if(Loginpage.yhjo.getString("roleId").equals("8")) {
              //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
              functionpage = new Intent(Roleengineer.this, xiaoxi_page1.class);
              startActivity(functionpage);

            } else {
              functionpage = new Intent(Roleengineer.this, xiaoxi_page2.class);
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
