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

public class gerenfasongxiaoxineirong extends Activity {

  TextView gerenxiaoxizhuti;
  TextView gerenxiaoxishijian;
  TextView gerenxiaoxineirong;
  TextView gerenxiaoxishenheren;
  TextView gerenxiaoxishenheyijian;
  TextView gerenxiaoxishenheshijian;
  TextView gerenxiaoxishenhezhuangtai;
  String xiaoxijiemian;
  String gerenfabuxiaoxichakanlist;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            gerenxiaoxizhuti.setText(jsonObject.getString("title"));
            gerenxiaoxishijian.setText(jsonObject.getString("submissionDate"));
            gerenxiaoxineirong.setText(jsonObject.getString("content"));
            gerenxiaoxishenheren.setText(jsonObject.getString("adminName"));
            gerenxiaoxishenheyijian.setText(jsonObject.getString("returnReason"));
            gerenxiaoxishenheshijian.setText(jsonObject.getString("checkDate"));
            if(jsonObject.getString("checkStatus").equals("0")) {
              gerenxiaoxishenhezhuangtai.setText("δ����");
            } else if(jsonObject.getString("checkStatus").equals("1")) {
              gerenxiaoxishenhezhuangtai.setText("��ͨ��");
            } else {
              gerenxiaoxishenhezhuangtai.setText("�˻�");
            }


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
    setContentView(R.layout.gerenfasongxiaoxineirong);

    gerenxiaoxizhuti = (TextView) this.findViewById(R.id.gerenxiaoxizhuti);
    gerenxiaoxishijian = (TextView) this.findViewById(R.id.gerenxiaoxishijian);
    gerenxiaoxineirong = (TextView) this.findViewById(R.id.gerenxiaoxineirong);
    gerenxiaoxishenheren = (TextView) this.findViewById(R.id.gerenxiaoxishenheren);
    gerenxiaoxishenheyijian = (TextView) this.findViewById(R.id.gerenxiaoxishenheyijian);
    gerenxiaoxishenheshijian = (TextView) this.findViewById(R.id.gerenxiaoxishenheshijian);
    gerenxiaoxishenhezhuangtai = (TextView) this.findViewById(R.id.gerenxiaoxishenhezhuangtai);

    ImageButton btn_back = (ImageButton) findViewById(R.id.jieshouxiaoxifanhui);

    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(gerenfasongxiaoxineirong.this, Xiaoxi_gerenxiaoxizhuangtailist.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp

          bundle.putString("gerenfabuxiaoxichakanlist", gerenfabuxiaoxichakanlist);
          bundle.putString("xiaoxijiemian", xiaoxijiemian);

          intent.putExtras(bundle);

          startActivity(intent);

        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ

    String grxx = bundle.getString("gerenfabuxiaoxineirong");

    gerenfabuxiaoxichakanlist = bundle.getString("gerenfabuxiaoxichakanlist");


    xiaoxijiemian = bundle.getString("xiaoxijiemian");

    Message message = new Message();
    message.what = 0;
    message.obj = grxx.toString();
    handler.sendMessage(message);


  }


}