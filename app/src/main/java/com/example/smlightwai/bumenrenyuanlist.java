package com.example.smlightwai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import jiekou.MyAdapter;
import jiekou.Utils;
import jiekou.MyAdapter.ViewHolder;


public class bumenrenyuanlist extends Activity {
  private ListView lv;
  private MyAdapter mAdapter;
  private ArrayList<String> list;
  private Button bt_selectall;
  private Button bt_cancel;
  private Button bt_deselectall;
  private int checkNum; // ��¼ѡ�е���Ŀ����
  private TextView tv_show;// ������ʾѡ�е���Ŀ����
  private JSONArray ja;
  public static StringBuffer renyuan;
  public static Map<String, String> map;
  public static String duixiangnum;
  int mystart = 0;
  int myend = 0;
  private String jiemian;

  /**
   * Called when the activity is first created.
   */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.xiaoxirenyuanlist);
    /* ʵ���������ؼ� */
    lv = (ListView) findViewById(R.id.lv);
    tv_show = (TextView) findViewById(R.id.tv);
    Button quedingrenyuan = (Button) findViewById(R.id.quedingrenyuan);
    list = new ArrayList<String>();
    // ΪAdapter׼������
    initDate();
    // ʵ�����Զ����MyAdapter
    mAdapter = new MyAdapter(list, this);
    // ��Adapter
    lv.setAdapter(mAdapter);


    renyuan = new StringBuffer();
    duixiangnum = new String();
    map = new HashMap<String, String>();

    quedingrenyuan.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        if(Utils.isFastClick()) {
          if(jiemian.equals("1")) {
            xiaoxi_page1.duixiang = duixiangnum;

            //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
            Intent intent = new Intent(bumenrenyuanlist.this, xiaoxi_page1.class);
            ////����
            startActivity(intent);
          } else if(jiemian.equals("2")) {
            xiaoxi_page2.duixiang = duixiangnum;
            Intent intent = new Intent(bumenrenyuanlist.this, xiaoxi_page2.class);
            ////����
            startActivity(intent);
          } else if(jiemian.equals("3")) {
            xiaoxi_page3.duixiang = duixiangnum;
            Intent intent = new Intent(bumenrenyuanlist.this, xiaoxi_page3.class);
            ////����
            startActivity(intent);
          }
        }
      }
    });


    // ��listView�ļ�����
    lv.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                              long arg3) {
        // ȡ��ViewHolder����������ʡȥ��ͨ������findViewByIdȥʵ����������Ҫ��cbʵ���Ĳ���
        ViewHolder holder = (ViewHolder) arg1.getTag();
        // �ı�CheckBox��״̬
        holder.cb.toggle();

        // ��CheckBox��ѡ��״����¼����

        try {
          MyAdapter.getIsSelected().put(arg2, holder.cb.isChecked());
          // ����ѡ����Ŀ
          if(holder.cb.isChecked() == true) {
            checkNum++;

            map.put("" + arg2, ja.optJSONObject(arg2).getString("id").toString());


            Log.d("renyuana00", renyuan.toString());
          } else {
            checkNum--;
            map.remove("" + arg2);

          }
        } catch(JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        // ��TextView��ʾ
        duixiangnum = "��ѡ��" + checkNum + "��";


      }
    });
  }


  // ��ʼ������
  private void initDate() {

    try {
      Bundle bundle = this.getIntent().getExtras();
      jiemian = bundle.getString("jiemiana");
      String weibaolist = "{\"wblist\":" + bundle.getString("bumenrenyuan") + "}";
      Log.d("resStrresStr", "111111111111111111");
      JSONObject jo = new JSONObject(weibaolist);
      Log.d("resStrresStr", weibaolist);
      ja = jo.getJSONArray("wblist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {

        JSONObject ob = ja.getJSONObject(i);
        strs[i] = "                   " + ob.getString("name");
        Log.d("resStrresStr", strs[i]);
        list.add(strs[i]);
      }
    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
  // ˢ��listview��TextView����ʾ


  private void dataChanged() {
    // ֪ͨlistViewˢ��
    mAdapter.notifyDataSetChanged();
    // TextView��ʾ���µ�ѡ����Ŀ
    tv_show.setText("��ѡ��" + checkNum + "��");
  }

  ;


//	      <CheckBox
//          android:id="@+id/quanxuan"
//          android:layout_width="wrap_content"
//          android:layout_height="wrap_content"
//          android:layout_alignParentRight="true"
//          android:layout_below="@+id/xiaoxizhuangtai"
//          android:clickable="false"
//          android:focusable="false"
//          android:focusableInTouchMode="false"
//          android:text="ȫ  ѡ    "
//          android:textColor="#FFFFFF"
//          android:textSize="16dp" />
}