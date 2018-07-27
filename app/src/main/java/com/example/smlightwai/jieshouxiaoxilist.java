package com.example.smlightwai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import jiekou.Utils;

public class jieshouxiaoxilist extends Activity {

  private ListView lv;
  private JSONArray ja;//�豸����list
  String xiaoxijiemian;
  String jieshouxiaoxichakanlist;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.jieshouxiaoxilist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.jieshouxiaoxifanghui);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent;
          if(xiaoxijiemian.equals("xiaoxi_page1")) {
            intent = new Intent(jieshouxiaoxilist.this, xiaoxi_page1.class);
            startActivity(intent);
          } else if(xiaoxijiemian.equals("xiaoxi_page2")) {
            intent = new Intent(jieshouxiaoxilist.this, xiaoxi_page2.class);
            startActivity(intent);
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
          Intent intent = new Intent(jieshouxiaoxilist.this, jieshouxiaoxineirong.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("jieshouxiaoxineirong", ja.optJSONObject(position).toString());
          bundle.putString("xiaoxijiemian", xiaoxijiemian);
          bundle.putString("jieshouxiaoxichakanlist", jieshouxiaoxichakanlist);

          intent.putExtras(bundle);

          startActivity(intent);
        }
      }
    });


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    xiaoxijiemian = bundle.getString("xiaoxijiemian");
    jieshouxiaoxichakanlist = bundle.getString("jieshouxiaoxichakanlist");
    String shijianlist = "{\"jsxxlist\":" + bundle.getString("jieshouxiaoxichakanlist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("jsxxlist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = " " + ob.getString("submissionDate") + "           " + ob.getString("senderName");
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





