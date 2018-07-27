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

public class tongxing_tarenneirong extends Activity {
  TextView huowuleixing;
  TextView huowumiaoshu;
  TextView shijian;

  TextView Texttiandanrenyuan;

  JSONObject jsonObject1;

  String tupianone;
  String tupiantwo;
  String tupianthree;
  String tongxinganbaolist;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            jsonObject1 = new JSONObject(str);
            //���������UI�������������ʾ��������
            Texttiandanrenyuan.setText(jsonObject1.getString("applicant_id"));
            huowuleixing.setText(jsonObject1.getString("goodsType"));
            huowumiaoshu.setText(jsonObject1.getString("goodsDescription"));
            shijian.setText(jsonObject1.getString("submissionDate"));

            tupianone = jsonObject1.getString("imageUrl1");
            tupiantwo = jsonObject1.getString("imageUrl2");
            tupianthree = jsonObject1.getString("imageUrl3");


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
    setContentView(R.layout.tongxing_tarenneirong);


    Texttiandanrenyuan = (TextView) this.findViewById(R.id.Texttiandanrenyuan);
    huowuleixing = (TextView) this.findViewById(R.id.texthuowuleixing);
    huowumiaoshu = (TextView) this.findViewById(R.id.texthuowumiaoshu);

    Button tupian = (Button) this.findViewById(R.id.tupian);

    shijian = (TextView) this.findViewById(R.id.textshijian);
//		huowutupian= (TextView) this.findViewById(R.id.huowutupian);
    ImageButton fanghui = (ImageButton) this.findViewById(R.id.fanhui);

    fanghui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_tarenneirong.this, tongxing_anbaolist.class);
          Bundle bundle = new Bundle();

          bundle.putString("tongxinganbaolist", tongxinganbaolist);

          intent.putExtras(bundle);

          startActivity(intent);
        }
      }
    });


    tupian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_tarenneirong.this, tongxing_tupianchakan.class);
          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tupiandizhi1", tupianone);
          Log.d("tupiandizhi1", tupianone);
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
    String cxnr = bundle.getString("tongxingshenhe_neirong");
    tongxinganbaolist = bundle.getString("tongxinganbaolist");


    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();
    handler.sendMessage(message);

  }

}
