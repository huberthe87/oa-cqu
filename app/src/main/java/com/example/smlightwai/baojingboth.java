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
import android.widget.Toast;

import jiekou.Utils;
import jiekou.Utils5000;
import jiekou.appapi;

public class baojingboth extends Activity {

  private String departmentflag;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.baojin_both);


    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhuibaojing);
    Button btn_huozai = (Button) findViewById(R.id.huozaibaojing);
    Button btn_shebei = (Button) findViewById(R.id.shebeibaojing);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


        try {
          if(Loginpage.yhjo.getString("organizationId").equals("8")) {
            Intent intent1 = new Intent(baojingboth.this, Rolezongjingli.class);
            ////����
            startActivity(intent1);
          } else if(Loginpage.yhjo.getString("organizationId").equals("4")) {
            //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
            Intent intent1 = new Intent(baojingboth.this, Roleengineer.class);
            ////����
            startActivity(intent1);
          } else {
            Intent intent1 = new Intent(baojingboth.this, Roleanbao.class);
            ////����
            startActivity(intent1);
          }
        } catch(JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }

    });


    btn_huozai.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        try {
          if(Loginpage.yhjo.getString("organizationId").equals("3")) {
            departmentflag = "HUOZAI";

            new Thread(new baojingThread()).start();


          } else if(Loginpage.yhjo.getString("organizationId").equals("8")) {
            departmentflag = "HUOZAI";

            new Thread(new baojingThread()).start();

          } else {
            Toast.makeText(getApplicationContext(), "��Ȩ�޲鿴", Toast.LENGTH_SHORT).show();
          }
        } catch(JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

    });


    btn_shebei.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        try {
          if(Loginpage.yhjo.getString("organizationId").equals("4")) {

            departmentflag = "SHEBEI";
            new Thread(new baojingThread()).start();
          } else if(Loginpage.yhjo.getString("organizationId").equals("8")) {
            departmentflag = "SHEBEI";

            new Thread(new baojingThread()).start();
          } else {
            Toast.makeText(getApplicationContext(), "��Ȩ�޲鿴", Toast.LENGTH_SHORT).show();
          }
        } catch(JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

    });

  }


  class baojingThread extends Thread {
    public void run() {


      //post����


      try {
        Intent intent;
        if(departmentflag.equals("HUOZAI")) {


          URL url = new URL("http://222.178.109.129:9082/ibs/api/alarm/{organizationId,managerStatus}");


          Map<String, Object> params = new LinkedHashMap<String, Object>();
          params.put("organizationId", "3");
          params.put("managerStatus", "0");

          String resStr = appapi.getWebServiceRes(params, url);


          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          intent = new Intent(baojingboth.this, huozaibaojinglist.class);
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("huojingbaojing", resStr);
          Log.d("baojingboth", "����������ֵ��" + resStr);
          intent.putExtras(bundle);
          startActivity(intent);
        } else if(departmentflag.equals("SHEBEI")) {


          URL url = new URL("http://222.178.109.129:9082/ibs/api/alarm/{organizationId,managerStatus}");


          Map<String, Object> params = new LinkedHashMap<String, Object>();
          params.put("organizationId", "4");
          params.put("managerStatus", "0");

          String resStr = appapi.getWebServiceRes(params, url);


          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          intent = new Intent(baojingboth.this, shebeibaojinglist.class);
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shebeibaojing", resStr);

          intent.putExtras(bundle);
          startActivity(intent);
        } else {
          //???????????????
        }
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }

}


