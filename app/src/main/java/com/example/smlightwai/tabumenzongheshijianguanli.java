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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import jiekou.Utils;
import jiekou.appapi;

public class tabumenzongheshijianguanli extends Activity {
  private String flagshijian;
  private String bumenid;
  private String mubiaobumenname;
  private String fasomgbumenID;
  private String shijianleixing;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.tabumenchaxun);

    Button btn_daizhipaishijian = (Button) findViewById(R.id.daizhipaishijian);
    Button btn_chulizhongshijian = (Button) findViewById(R.id.chulizhongshijian);
    Button btn_ershentuihuishijian = (Button) findViewById(R.id.ershentuihuishijian);
    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhui);
    Spinner spinner_mubiaobumen = (Spinner) findViewById(R.id.fasongbumen);

    spinner_mubiaobumen.setOnItemSelectedListener(new ProvOnItemSelectedListener_mubiaobumen());


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
          Intent intent = new Intent(tabumenzongheshijianguanli.this, Roleengineer.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_daizhipaishijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flagshijian = "1";
          new Thread(new tabumenzonghehijianweichaxunThread()).start();
        }
      }
    });


    btn_chulizhongshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flagshijian = "2";
          new Thread(new tabumenzongheshijianchaxunThread()).start();
        }
      }
    });


    btn_ershentuihuishijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flagshijian = "4";
          new Thread(new tabumenzongheshijianchaxunThread()).start();
        }

      }
    });


  }


  class tabumenzonghehijianweichaxunThread extends Thread {
    public void run() {
      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchEvent1");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("organizationId", bumenid);
        params.put("organizationIdTwo", fasomgbumenID);
        params.put("checkStatus", flagshijian);

        String resStr = appapi.getWebServiceRes(params, url);


        Intent intent = new Intent(tabumenzongheshijianguanli.this, tabumenweichulishijianlist.class);

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


  class tabumenzongheshijianchaxunThread extends Thread {
    public void run() {
      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchEvent1");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put("organizationId", bumenid);
        params.put("eventType", 1);//teshu�¼�
        params.put("checkStatus", flagshijian);
        params.put("organizationIdTwo", fasomgbumenID);

        String resStr = appapi.getWebServiceRes(params, url);


        Intent intent = new Intent(tabumenzongheshijianguanli.this, shijianlist.class);

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
