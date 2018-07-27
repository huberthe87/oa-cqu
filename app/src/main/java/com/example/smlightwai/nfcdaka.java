package com.example.smlightwai;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;

import com.example.smlightwai.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.WeiboDialogUtils;
import jiekou.appapi;

import android.provider.Settings;

public class nfcdaka extends Activity {

  public static final String TAG = "NFCActivity";
  private NfcAdapter nfcAdapter;


  private TextView showText;
  private TextView dakaren;
  private String shijianstr;
  private Dialog mWeiboDialog;
  private TextView dakadizhi;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      try {
        switch(msg.what) {
          case 0:
            String res = (String) msg.obj;
            showText.setText(res);
            dakaren.setText(Loginpage.yhjo.getString("name"));
            if(res.equals("BDEA4D1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1���п����Դ���"));
            } else if(res.equals("10094E1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1���п���ͨ������"));
            } else if(res.equals("0819511E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1-LGR2�������·�"));
            } else if(res.equals("C729511E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1-R2-3��ͨ��"));
            } else if(res.equals("0753511E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1�㲷�䳬�г������Ϸ�"));
            } else if(res.equals("B421511E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1���������۾�����ͨ��"));
            } else if(res.equals("7C064E1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1��-R3-2Aͨ��"));
            } else if(res.equals("D3AB4D1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1��-R3-4Bͨ��"));
            } else if(res.equals("05CB4D1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1��-R3-4Aͨ��"));
            } else if(res.equals("6AC34D1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1��������"));
            } else if(res.equals("F4A34D1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1��-R3-5��ͨ��"));
            } else if(res.equals("30BE4D1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1��Ա��ͨ����"));
            } else if(res.equals("6F025C1E")) {
              dakadizhi.setText(Loginpage.yhjo.getString("L1���ϴ���"));
            }

            break;
        }
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.nfcdaka);


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);
    Log.d("timtimetime", shijianstr);

    dakaren = (TextView) findViewById(R.id.dakarenyuan);
    showText = (TextView) findViewById(R.id.nfcid);
    dakadizhi = (TextView) findViewById(R.id.dakadizhi);
    Button btn_dakafasong = (Button) findViewById(R.id.dakafasong);

    btn_dakafasong.setOnClickListener(new View.OnClickListener() {
      @Override

      public void onClick(View v) {
        if(Utils.isFastClick()) {
          if(dakaren.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "��ʧ�ܣ����¼�����",
                Toast.LENGTH_SHORT).show();
          } else {

            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(nfcdaka.this, "����...");
            new Thread(new SjclneirongThread()).start();
          }
        }
      }
    });


    // ��ȡĬ�ϵ�NFC������   
    nfcAdapter = NfcAdapter.getDefaultAdapter(this);

  }


  @Override
  protected void onResume() {
    super.onResume();
    Intent intent = this.getIntent();
    //�õ��Ƿ��⵽ACTION_TECH_DISCOVERED����
    if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
      //�����intent
      // processIntent(getIntent());
      //Intent intent=getIntent();
      try {
        String nfcId = readIdFromTag(intent);

        Message message = new Message();
        message.what = 0;
        message.obj = nfcId.toString();
        handler.sendMessage(message);

      } catch(UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  /**
   * ��nfcID
   */
  public static String readIdFromTag(Intent intent) throws UnsupportedEncodingException {
    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    String id = ByteArrayToHexString(tag.getId());
    return id;
  }

  /**
   * ���ֽ�����ת��Ϊ�ַ���
   */
  private static String ByteArrayToHexString(byte[] inarray) {
    int i, j, in;
    String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    String out = "";

    for(j = 0; j < inarray.length; ++j) {
      in = (int) inarray[j] & 0xff;
      i = (in >> 4) & 0x0f;
      out += hex[i];
      i = in & 0x0f;
      out += hex[i];
    }
    return out;
  }


  class SjclneirongThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/nfcManage/saveIbsNfcRecordForApp");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("cardNum", showText.getText().toString());
        params.put("checkTime", shijianstr);
        params.put("person", Loginpage.yhjo.getString("name"));


        String resStr = appapi.getWebServiceRes(params, url);

        mWeiboDialog.dismiss();

        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "�򿨳ɹ�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "��ʧ�ܣ������´�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        }
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }

}