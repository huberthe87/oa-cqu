package com.example.smlightwai;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class lishiguanggaoneirong extends Activity {


  TextView shangpuname;
  TextView shenqingshijian;
  TextView shenqingneirong;
  TextView chulizhuangtai;
  TextView chuliyijian;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            shangpuname.setText(jsonObject.getString("storeName"));
            shenqingshijian.setText(jsonObject.getString("submissionDate"));
            shenqingneirong.setText(jsonObject.getString("description"));
            chulizhuangtai.setText(jsonObject.getString("checkStatus"));
            chuliyijian.setText(jsonObject.getString("returnReason"));

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
    setContentView(R.layout.guanggaoneirong);

    shangpuname = (TextView) this.findViewById(R.id.shangpuname);
    shenqingshijian = (TextView) this.findViewById(R.id.shenqingshijian);
    shenqingneirong = (TextView) this.findViewById(R.id.shenqingneirong);
    chulizhuangtai = (TextView) this.findViewById(R.id.chulizhuangtai);
    chuliyijian = (TextView) this.findViewById(R.id.chuliyijian);


    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.ggnrfanghui);


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String jsxx = bundle.getString("lishiguanggaoneirong");

    Message message = new Message();
    message.what = 0;
    message.obj = jsxx.toString();
    handler.sendMessage(message);


  }


}

