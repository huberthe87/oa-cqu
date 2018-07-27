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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import jiekou.Utils;
import jiekou.appapi;

public class benbumenzongheshijianguanli extends Activity {


  private String mubiaobumenname;
  private String flagshijian;
  private String bumenid;
  private String fasomgbumenID;
  private String shijianleixing;


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
    Spinner spinner_mubiaobumen = (Spinner) findViewById(R.id.fasongbumen);

    spinner_mubiaobumen.setOnItemSelectedListener(new ProvOnItemSelectedListener_mubiaobumen());

    ImageButton btn_back = (ImageButton) findViewById(R.id.benbumenfanhui);

    try {
      if(Loginpage.yhjo.getString("organizationId").equals("1")) {
        Log.d("2312312asdsad", "111111111111111");
        bumenid = "" + shijian_page3.mubiaobumenid;
        Log.d("2312312asdsad", bumenid);

      } else {
        bumenid = Loginpage.yhjo.getString("organizationId");
      }
    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


//		Log.d("2312312asdsad", bumenid);
    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(benbumenzongheshijianguanli.this, Roleengineer.class);
          ////����
          startActivity(intent);

        }
      }
    });


    btn_daishenheshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        flagshijian = "0";
        new Thread(new benbumenzonghehijianweichaxunThread()).start();
      }
    });


    btn_yitongguoshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        flagshijian = "1";
        new Thread(new benbumenzonghehijianweichaxunThread()).start();
      }
    });


    btn_chulizhongshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        flagshijian = "2";
        new Thread(new benbumenlishishijianchaxunThread()).start();
      }
    });


    btn_yishentuihuishijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        flagshijian = "3";
        new Thread(new benbumenlishishijianchaxunThread()).start();

      }
    });

    btn_ershentuihishijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        flagshijian = "4";
        new Thread(new benbumenlishishijianchaxunThread()).start();

      }
    });

  }


  class benbumenzonghehijianweichaxunThread extends Thread {
    public void run() {
      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchEvent2");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("organizationId", bumenid);

        params.put("organizationIdTwo", fasomgbumenID);

        params.put("checkStatus", flagshijian);

        String resStr = appapi.getWebServiceRes(params, url);


        Intent intent = new Intent(benbumenzongheshijianguanli.this, benbumenweichulishijianlist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("zongheshijianlist", resStr);
        intent.putExtras(bundle);

        ////����
        startActivity(intent);
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }
  }


  class benbumenlishishijianchaxunThread extends Thread {
    public void run() {
      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchEvent2");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put("organizationId", bumenid);

        params.put("organizationIdTwo", fasomgbumenID);

        params.put("checkStatus", flagshijian);

        String resStr = appapi.getWebServiceRes(params, url);


        Intent intent = new Intent(benbumenzongheshijianguanli.this, shijianlist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("shijianlist", resStr);
        intent.putExtras(bundle);

        ////����
        startActivity(intent);
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }


  private class ProvOnItemSelectedListener_mubiaobumen implements OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
      //��ȡѡ������ֵ
      mubiaobumenname = adapter.getItemAtPosition(position).toString();
      if(mubiaobumenname.equals("��ҵ���̲�")) {
        fasomgbumenID = "4";
      } else if(mubiaobumenname.equals("������")) {
        fasomgbumenID = "3";
      } else if(mubiaobumenname.equals("�߻���")) {
        fasomgbumenID = "5";
      } else if(mubiaobumenname.equals("�г���")) {
        fasomgbumenID = "2";
      } else if(mubiaobumenname.equals("������")) {
        fasomgbumenID = "6";
      } else if(mubiaobumenname.equals("�ͷ���")) {
        fasomgbumenID = "7";
      } else if(mubiaobumenname.equals("���в���")) {
        fasomgbumenID = "";
      }
      Toast.makeText(getApplicationContext(), "��ѡ��Ŀ�겿��" + mubiaobumenname, Toast.LENGTH_LONG).show();
//	            Log.d("2312312asdsad", mubiaobumenid);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
      shijianleixing = "ʲôҲûѡ��";
      Toast.makeText(getApplicationContext(), shijianleixing, Toast.LENGTH_LONG).show();

    }

  }


}

