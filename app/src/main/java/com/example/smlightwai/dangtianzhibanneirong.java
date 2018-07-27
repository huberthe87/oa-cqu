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

public class dangtianzhibanneirong extends Activity {

  TextView bancileixing;
  TextView gongzuomiaoshu;
  TextView shangbanshijian;
  TextView xiabanshijian;
  TextView zhibanrenyuan;
  ImageButton dangtianzhibanfanhui;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            bancileixing.setText(jsonObject.getString("workType"));
            gongzuomiaoshu.setText(jsonObject.getString("description"));
            shangbanshijian.setText(jsonObject.getString("workBeginTime"));
            xiabanshijian.setText(jsonObject.getString("workEndTime"));
            zhibanrenyuan.setText(jsonObject.getString("personChn"));
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
    setContentView(R.layout.dangtianzhibanneirong);


    bancileixing = (TextView) this.findViewById(R.id.bancileixing);
    gongzuomiaoshu = (TextView) this.findViewById(R.id.gongzuomiaoshu);
    shangbanshijian = (TextView) this.findViewById(R.id.shangbanshijian);
    xiabanshijian = (TextView) this.findViewById(R.id.xiabanshijian);
    zhibanrenyuan = (TextView) this.findViewById(R.id.zhibanrenyuan);
    dangtianzhibanfanhui = (ImageButton) this.findViewById(R.id.dangtianzhibanfanhui);

    dangtianzhibanfanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(dangtianzhibanneirong.this, dangtianzhibanlist.class);
          ////����
          startActivity(intent);
        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String cxnr = bundle.getString("dangtianzhibanrenyuanneirong");

    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();
    handler.sendMessage(message);


  }


}
