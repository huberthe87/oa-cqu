package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class shijianlist extends Activity {


  private ListView lv;
  private JSONArray ja;
  private JSONObject jasonzhuti;//�豸����list
  String shijianlistyuanshi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shijianlist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhuishijianlist);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          try {
            if(Loginpage.yhjo.getString("roleId").equals("8")) {
              Intent intent = new Intent(shijianlist.this, shijian_page1.class);
              startActivity(intent);
            } else {
              Intent intent = new Intent(shijianlist.this, shijian_page2.class);
              startActivity(intent);
            }
          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

      }
    });


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        if(Utils.isFastClick()) {

          jasonzhuti = ja.optJSONObject(position);


          new Thread(new kuakuakuakua()).start();
        }

      }
    });

    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    shijianlistyuanshi = bundle.getString("shijianlist");
    String shijianlist = "{\"sjlist\":" + bundle.getString("shijianlist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("sjlist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = ob.getString("submissionDate") + "                " + ob.getString("title");
      }


      /*ΪListView����Adapter��������*/
      lv.setAdapter(new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, strs));
    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }


  class kuakuakuakua extends Thread {
    public void run() {


      //post����

      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/eventDeal/searchOne");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        Log.d("jasonzhuti.getString(\"id\")", jasonzhuti.getString("id"));

        params.put("eventId", jasonzhuti.getString("id"));
        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("resStr", resStr);

        Intent intent = new Intent(shijianlist.this, shijianchaxunneirong.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("shijianchaxunneirong", jasonzhuti.toString());
        bundle.putString("ewaideneirongaaaa", resStr);
        bundle.putString("shijianlistyuanshi", shijianlistyuanshi);
        Log.d("shijianlistyuanshi", shijianlistyuanshi);
        intent.putExtras(bundle);

        ////����
        startActivity(intent);


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

