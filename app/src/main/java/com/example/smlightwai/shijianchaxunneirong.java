package com.example.smlightwai;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import jiekou.Utils;

public class shijianchaxunneirong extends Activity {


  TextView shenqingren;
  TextView shenqingshijian;
  TextView shenqingneirong;
  TextView shijianzhuangtai;
  TextView chuliyijian;
  String cxnr;
  String tupianone;
  String tupiantwo;
  String tupianthree;
  String cxnr2;
  String cxnr3;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:
          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(cxnr);
            //���������UI�������������ʾ��������
            tupianone = jsonObject.getString("imageOne");
            tupiantwo = jsonObject.getString("imageOne");
            tupianthree = jsonObject.getString("imageOne");

            shenqingren.setText(jsonObject.getString("senderName"));
            shenqingshijian.setText(jsonObject.getString("submissionDate"));
            shenqingneirong.setText(jsonObject.getString("content"));
            chuliyijian.setText(jsonObject.getString("returnReason") + ("(����Ա�˻���ʾ)"));
            JSONObject jsonObject2 = new JSONObject(cxnr2);

            if(jsonObject2.getString("dealStatus").equals("0")) {
              shijianzhuangtai.setText("��δ����");
            } else if(jsonObject2.getString("dealStatus").equals("1")) {
              shijianzhuangtai.setText("���ڴ���");
            } else if(jsonObject2.getString("dealStatus").equals("2")) {
              shijianzhuangtai.setText("�������");
            } else if(jsonObject2.getString("dealStatus").equals("3")) {
              shijianzhuangtai.setText("���˻�");
            }


//					chuliyijian.setText(jsonObject.getString("returnReason")+("(����Ա�˻���ʾ)"));
            if(jsonObject.getString("returnReason").equals("null") || jsonObject.getString("returnReason").length() == 0) {

              chuliyijian.setText(jsonObject2.getString("returnReason") + ("(�ֳ������¼)"));

            } else {

              chuliyijian.setText(jsonObject.getString("returnReason") + ("(����Ա�˻���ʾ)"));

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
    setContentView(R.layout.shijianchaxunneirong);


    shenqingren = (TextView) this.findViewById(R.id.shenqingneirong19);
    shenqingshijian = (TextView) this.findViewById(R.id.shenqingneirong20);
    shenqingneirong = (TextView) this.findViewById(R.id.shenqingshijian4333530);
    shijianzhuangtai = (TextView) this.findViewById(R.id.shijianzhuangtai);
    chuliyijian = (TextView) this.findViewById(R.id.chuliyijian);
    ImageButton fanghui = (ImageButton) this.findViewById(R.id.fanghui);
    Button chakantupian = (Button) this.findViewById(R.id.chakantupian);


    fanghui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shijianchaxunneirong.this, shijianlist.class);
          Bundle bundle = new Bundle();

          bundle.putString("shijianlist", cxnr3);
          Log.d("cxnr3", cxnr3);
          intent.putExtras(bundle);

          startActivity(intent);
        }


      }
    });


    chakantupian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shijianchaxunneirong.this, shijian_chakantupian.class);
          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tupiandizhi1", tupianone);
          bundle.putString("tupiandizhi2", tupiantwo);
          bundle.putString("tupiandizhi3", tupianthree);
          intent.putExtras(bundle);
          ////����
          startActivity(intent);
        }


      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    cxnr = bundle.getString("shijianchaxunneirong");
    cxnr2 = bundle.getString("ewaideneirongaaaa");
    cxnr3 = bundle.getString("shijianlistyuanshi");
    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();

    handler.sendMessage(message);


  }


}
