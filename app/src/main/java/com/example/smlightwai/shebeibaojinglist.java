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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import jiekou.Utils;
import jiekou.appapi;

public class shebeibaojinglist extends Activity {


  private ListView lv;
  private JSONArray ja;//�豸����list

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shebeibaojinglist);

    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhuishebeilist);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shebeibaojinglist.this, baojingboth.class);
          startActivity(intent);
        }

      }
    });


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shebeibaojinglist.this, guzhangneirong.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shebeibaojingxaingxixinxi", ja.optJSONObject(position).toString());
          intent.putExtras(bundle);

          startActivity(intent);
        }

      }
    });

    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    String shebeibaojinglist = "{\"sblist\":" + bundle.getString("shebeibaojing") + "}";
    try {
      JSONObject jo = new JSONObject(shebeibaojinglist);
      //����豸����list
      ja = jo.getJSONArray("sblist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = ob.getString("alarmTime") + "                " + ob.getString("floor");
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

