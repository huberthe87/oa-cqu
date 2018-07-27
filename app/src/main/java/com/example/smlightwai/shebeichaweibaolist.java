package com.example.smlightwai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jiekou.Utils;

public class shebeichaweibaolist extends Activity {

  private ListView lv;
  private JSONArray ja;
  private String flag;

  JSONObject cxwb1;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shebeichaweibaolist);


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(Utils.isFastClick()) {

          Intent intent = new Intent(shebeichaweibaolist.this, shebeichaweibaoneirong.class);
          JSONObject xiangxishebei = ja.optJSONObject(position);
          Bundle bundle = new Bundle();

          bundle.putString("xiangxide", xiangxishebei.toString());

          intent.putExtras(bundle);
          startActivity(intent);
        }

      }

    });


    Bundle bundle = this.getIntent().getExtras();
    String shijianlist = "{\"xxsslist\":" + bundle.getString("shebeichaweibaolist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("xxsslist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = "                " + ob.getString("startDate");
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


