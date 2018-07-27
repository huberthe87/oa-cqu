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
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.Utils5000;
import jiekou.appapi;

public class zhanghuguanli extends Activity {

  EditText btn_zhanghu;
  EditText btn_yuanmima;
  EditText btn_xinmima1;
  EditText btn_xinmima2;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.zhanghumimaxiugai);


    ImageButton btn_back = (ImageButton) this.findViewById(R.id.fanhui);
    Button btn_queding = (Button) this.findViewById(R.id.queding);
    Button btn_quxiao = (Button) this.findViewById(R.id.quxiao);
    btn_zhanghu = (EditText) this.findViewById(R.id.zhanghu);
    btn_yuanmima = (EditText) this.findViewById(R.id.yuanmima);
    btn_xinmima1 = (EditText) this.findViewById(R.id.xinmima1);
    btn_xinmima2 = (EditText) this.findViewById(R.id.xinmima2);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          try {
            Intent intent;

            if(Loginpage.yhjo.getString("organizationId").equals("2")) {
              intent = new Intent(zhanghuguanli.this, Roleshichang.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("3")) {
              intent = new Intent(zhanghuguanli.this, Roleanbao.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("4")) {
              intent = new Intent(zhanghuguanli.this, Roleengineer.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("5")) {
              intent = new Intent(zhanghuguanli.this, Rolecehua.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("6")) {
              intent = new Intent(zhanghuguanli.this, Rolexingzheng.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("7")) {
              intent = new Intent(zhanghuguanli.this, Rolekefu.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("8")) {
              intent = new Intent(zhanghuguanli.this, Rolezongjingli.class);
              startActivity(intent);
            }
          } catch(Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    });


    btn_queding.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //�޸�����
          if(btn_xinmima1.getText().toString().trim().equals(btn_xinmima2.getText().toString().trim())) {
            try {
              if(btn_zhanghu.getText().toString().trim().equals(Loginpage.yhjo.getString("loginName"))) {

                if(Utils5000.isFastClick()) {
                  new Thread(new XiiugaimimaThread()).start();
                } else {
                  Toast.makeText(getApplicationContext(), "�����ظ��޸�", Toast.LENGTH_SHORT).show();
                }

              } else {
                Toast.makeText(getApplicationContext(), "�û�������", Toast.LENGTH_LONG).show();
              }
            } catch(JSONException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          } else {
            Toast.makeText(getApplicationContext(), "�����벻һ��", Toast.LENGTH_LONG).show();
          }

        }
      }
    });


  }


  class XiiugaimimaThread extends Thread {
    public void run() {

      try {
        //post����
        URL url = new URL("http://222.178.109.129:9082/ibs/api/user/changePassword");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", Loginpage.yhjo.getString("id"));
        params.put("oldPassword", btn_yuanmima.getText().toString().trim());
        params.put("newPassword", btn_xinmima1.getText().toString().trim());

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("zhanghuguanli", "�޸Ľ����" + resStr);
        if(resStr.equals("true")) {
          System.out.println(">>>>>>>>");
          Looper.prepare();
          Toast.makeText(zhanghuguanli.this, "�޸ĳɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(zhanghuguanli.this, "�޸�ʧ�ܣ��������ύ", Toast.LENGTH_LONG).show();
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