package com.example.smlightwai;

import org.json.JSONException;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import jiekou.Utils;

public class Roleshichang extends Activity {

  private long exitTime = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.juese_shichang);

    ImageButton btn_tongxing = (ImageButton) findViewById(R.id.tongxingguanli);
    ImageButton btn_shijian = (ImageButton) findViewById(R.id.shijianguanli);
    ImageButton btn_xiaoxi = (ImageButton) findViewById(R.id.xiaoxiguanli);
    ImageButton btn_zhanghu = (ImageButton) findViewById(R.id.zhanghuguanli);
    ImageButton btn_xunzhi = (ImageButton) findViewById(R.id.xunzhiguanli);


    btn_tongxing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          try {
            Intent functionpage;

            if(Loginpage.yhjo.getString("roleId").equals("4")) {
              //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
              functionpage = new Intent(Roleshichang.this, tongxing_bothyuangong.class);
              startActivity(functionpage);

            } else {
              functionpage = new Intent(Roleshichang.this, tongxing_bothguanli.class);
              startActivity(functionpage);
            }

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }
      }
    });


    btn_shijian.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          try {
            Intent functionpage;

            if(Loginpage.yhjo.getString("roleId").equals("4")) {
              //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
              functionpage = new Intent(Roleshichang.this, shijian_page1.class);
              startActivity(functionpage);

            } else {
              functionpage = new Intent(Roleshichang.this, shijian_page2.class);
              startActivity(functionpage);
            }

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

      }
    });


    btn_xiaoxi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          try {
            Intent functionpage;

            if(Loginpage.yhjo.getString("roleId").equals("4")) {
              //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
              functionpage = new Intent(Roleshichang.this, xiaoxi_page1.class);
              startActivity(functionpage);

            } else {
              functionpage = new Intent(Roleshichang.this, xiaoxi_page2.class);
              startActivity(functionpage);
            }

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

      }
    });


    btn_zhanghu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent functionpage = new Intent(Roleshichang.this, zhanghuguanli.class);
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
          Intent functionpage = new Intent(Roleshichang.this, xunzhi_page.class);
          ////����
          startActivity(functionpage);
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

