package com.example.smlightwai;

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

import jiekou.Utils;

public class Xiaoxi_gerenxiaoxizhuangtailist extends Activity {


  private ListView lv;
  private JSONArray ja;//�豸����list
  String a;
  String xiaoxijiemian;
  String gerenfabuxiaoxichakanlist;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.xiaoxi_gerenxiaoxizhuangtaichakanlist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.gerenchakanfanghui);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          Intent intent;
          if(xiaoxijiemian.equals("xiaoxi_page1")) {
            intent = new Intent(Xiaoxi_gerenxiaoxizhuangtailist.this, xiaoxi_page1.class);
            startActivity(intent);
          } else if(xiaoxijiemian.equals("xiaoxi_page2")) {
            intent = new Intent(Xiaoxi_gerenxiaoxizhuangtailist.this, xiaoxi_page2.class);
            startActivity(intent);
          }
        }
      }
    });


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������

          Intent intent = new Intent(Xiaoxi_gerenxiaoxizhuangtailist.this, gerenfasongxiaoxineirong.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("gerenfabuxiaoxineirong", ja.optJSONObject(position).toString());
          bundle.putString("gerenfabuxiaoxichakanlist", gerenfabuxiaoxichakanlist);
          bundle.putString("xiaoxijiemian", xiaoxijiemian);

          intent.putExtras(bundle);

          startActivity(intent);
        }
      }
    });


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();

    gerenfabuxiaoxichakanlist = bundle.getString("gerenfabuxiaoxichakanlist");
    xiaoxijiemian = bundle.getString("xiaoxijiemian");


    String shijianlist = "{\"ggerenxxlist\":" + bundle.getString("gerenfabuxiaoxichakanlist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("ggerenxxlist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);

        if(ob.getString("checkStatus").equals("0")) {
          strs[i] = "    " + "δ����" + "                               " + ob.getString("title");
        } else if(ob.getString("checkStatus").equals("1")) {
          strs[i] = "    " + "��ͨ��" + "                                 " + ob.getString("title");
        } else {
          strs[i] = "    " + "���˻�" + "                                " + ob.getString("title");
        }
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





