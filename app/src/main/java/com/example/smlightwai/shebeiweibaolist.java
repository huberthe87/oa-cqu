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
import android.widget.ListView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class shebeiweibaolist extends Activity {
  private ListView lv;
  private JSONArray ja;
  private String flag;

  JSONObject cxwb1;
  JSONObject cxwb2;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shebeiweibaonianjianlist);


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(Utils.isFastClick()) {

          Intent intent = new Intent(shebeiweibaolist.this, shebeiweibaoneirong.class);
          JSONObject xiangxishebei = ja.optJSONObject(position);
          Bundle bundle = new Bundle();
          bundle.putString("yiduide", cxwb1.toString());
          bundle.putString("xiangxide", xiangxishebei.toString());
          Log.d("1111111111111", bundle.getString("yiduide"));
          Log.d("2222222222222", bundle.getString("xiangxide"));
          intent.putExtras(bundle);
          startActivity(intent);
        }
      }
    });


    Bundle bundle = this.getIntent().getExtras();

    try {
      //����nameֵ
      cxwb1 = new JSONObject(bundle.getString("fatherjo"));
      cxwb2 = new JSONObject(bundle.getString("childjo"));


      ja = cxwb2.getJSONArray("houyigelist");
      Log.d("dadasda", ja.toString());
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = ob.getString("maintainDate") + "                          " + ob.getString("maintainPerson");
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


	

