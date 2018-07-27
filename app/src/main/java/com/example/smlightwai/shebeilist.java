package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;
import jiekou.shebeitaizhang;

public class shebeilist extends Activity {

  private ListView lv;
  private JSONArray ja;
  private String flag;
  private int wbposition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shebeilist);

    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          if(flag.equals("sbcx")) {
            Intent intent = new Intent(shebeilist.this, shebeichaxunneirong.class);  //?????????
            ////����

            Bundle bundle = new Bundle();
            //����name����Ϊtinyphp
            bundle.putString("chaxunxiangxixinxi", ja.optJSONObject(position).toString());
            intent.putExtras(bundle);

            startActivity(intent);
          } else if(flag.equals("sbweibao") || flag.equals("sbnianjian")) {

            wbposition = position;


            new Thread(new ShebeiweibaoneirongThread()).start();


          }

        }

      }
    });


    //��ҳ���������
    Bundle bundle = this.getIntent().getExtras();
    flag = bundle.getString("flag");

    if(flag.equals("sbcx")) {
      //����nameֵ
      try {
        String shebeilist = "{\"sblist\":" + shebeitaizhang.getInstance().getData() + "}";
        JSONObject jo = new JSONObject(shebeilist);
        ja = jo.getJSONArray("sblist");
        int n = ja.length();
        String strs[] = new String[n];
        for(int i = 0; i < n; i++) {
          JSONObject ob = ja.getJSONObject(i);
          strs[i] = "             " + ob.getString("buy_date") + "                  " + new JSONObject(ob.getString("ibsItemDTO")).getString("name");
        }


        /*ΪListView����Adapter��������*/
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, strs));
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else if(flag.equals("sbweibao")) {
      try {
        String weibaolist = "{\"wblist\":" + bundle.getString("shebeiweibaolist") + "}";
        JSONObject jo = new JSONObject(weibaolist);
        ja = jo.getJSONArray("wblist");
        int n = ja.length();
        String strs[] = new String[n];
        for(int i = 0; i < n; i++) {
          JSONObject ob = ja.getJSONObject(i);
          strs[i] = ob.getString("itemNames");
        }


        /*ΪListView����Adapter��������*/
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, strs));
        Log.d("shebeilist", "########" + weibaolist);

      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else if(flag.equals("sbnianjian")) {
      try {
        String nianjianlist = "{\"njlist\":" + bundle.getString("shebeinianjianlist") + "}";
        JSONObject jo = new JSONObject(nianjianlist);
        ja = jo.getJSONArray("njlist");
        int n = ja.length();
        String strs[] = new String[n];
        for(int i = 0; i < n; i++) {
          JSONObject ob = ja.getJSONObject(i);
          strs[i] = ob.getString("itemNames");
        }


        /*ΪListView����Adapter��������*/
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, strs));
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }


  }


  class ShebeiweibaoneirongThread extends Thread {
    public void run() {

      try {
        //post����
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintain/getMaintainContentListByPlanId/{planId}");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        JSONObject fjo = ja.optJSONObject(wbposition);

        params.put("planId", fjo.getString("id"));
        Log.d("MainActivity", "���յķ��ؽ��������������������������������" + fjo.getString("id"));

        String resStr = appapi.getWebServiceRes(params, url).replace("[", "");
        if(resStr.equals("]")) {
          Looper.prepare();
          Toast.makeText(shebeilist.this, "���豸��ά�������Ϣ", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        }

        Log.d("MainActivity", "���յķ��ؽ��������������������������������" + resStr);

        JSONObject chjo = new JSONObject(resStr.replace("]", ""));


        Intent intent = new Intent(shebeilist.this, shebeiweibaoneirong.class);
        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("fatherjo", fjo.toString());
        bundle.putString("childjo", chjo.toString());

        intent.putExtras(bundle);
        startActivity(intent);
        Log.d("MainActivity", ">>>>>>>>>>>>>��" + resStr);


      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }
  }


}