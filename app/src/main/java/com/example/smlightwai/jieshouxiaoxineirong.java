package com.example.smlightwai;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import jiekou.Utils;

public class jieshouxiaoxineirong extends Activity {


  TextView jieshouxiaoxizhuti;
  TextView jieshouxiaoxifasongren;
  TextView jieshouxiaoxifasongshijian;
  TextView jieshouxiaoxineirong;
  String xiaoxijiemian;
  String jieshouxiaoxichakanlist;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            jieshouxiaoxizhuti.setText(jsonObject.getString("title"));
            jieshouxiaoxifasongren.setText(jsonObject.getString("senderName"));
            jieshouxiaoxifasongshijian.setText(jsonObject.getString("submissionDate"));
            jieshouxiaoxineirong.setText(jsonObject.getString("content"));

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          break;
      }
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.jieshouxiaoxineirongchakan);

    jieshouxiaoxizhuti = (TextView) this.findViewById(R.id.jieshouxiaoxizhuti);
    jieshouxiaoxifasongren = (TextView) this.findViewById(R.id.jieshouxiaoxifasongren);
    jieshouxiaoxifasongshijian = (TextView) this.findViewById(R.id.jieshouxiaoxifasongshijian);
    jieshouxiaoxineirong = (TextView) this.findViewById(R.id.jieshouxiaoxineirong);


    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.jieshouxiaoxifanhui);


    btn_fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(jieshouxiaoxineirong.this, jieshouxiaoxilist.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp

          bundle.putString("jieshouxiaoxichakanlist", jieshouxiaoxichakanlist);
          bundle.putString("xiaoxijiemian", xiaoxijiemian);

          intent.putExtras(bundle);

          startActivity(intent);

        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String jsxx = bundle.getString("jieshouxiaoxineirong");
    xiaoxijiemian = bundle.getString("xiaoxijiemian");
    jieshouxiaoxichakanlist = bundle.getString("jieshouxiaoxichakanlist");


    Message message = new Message();
    message.what = 0;
    message.obj = jsxx.toString();
    handler.sendMessage(message);


  }


}