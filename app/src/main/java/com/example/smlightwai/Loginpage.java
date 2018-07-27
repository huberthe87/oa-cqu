package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import jiekou.Apppeizhiwenjian;
import jiekou.Utils;
import jiekou.appapi;

public class Loginpage extends Activity implements OnClickListener {

  private Button btn_login;
  private EditText edit_password;
  private EditText edit_account;
  private String zhiwei;
  public static JSONObject yhjo;
  public static String guanliyuan;
  public static String gerenshijiaguanlifrom;
  String rid;

  private String resStr;
  private CheckBox check_jizhumima;

  SharedPreferences sp = null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_loginpage);

    sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    init();
  }


  public void init() {
    btn_login = (Button) findViewById(R.id.denglu);
    check_jizhumima = (CheckBox) findViewById(R.id.jizhumima);
    edit_password = (EditText) findViewById(R.id.mima);
    edit_account = (EditText) findViewById(R.id.yonghuming);


    rid = JPushInterface.getRegistrationID(getApplicationContext());
    Toast.makeText(getApplicationContext(), rid, Toast.LENGTH_LONG).show();
    Log.d("1099", "run:--------->registrationId�� " + rid);

    Log.d("1099", "3 " + rid);

    if(sp.getBoolean("checkboxBoolean", false)) {
      edit_account.setText(sp.getString("uname", null));
      edit_password.setText(sp.getString("upswd", null));
      check_jizhumima.setChecked(true);

    }

    btn_login.setOnClickListener(this);


  }

  @Override
  public void onClick(View v) {
    if(Utils.isFastClick()) {
      if(v == btn_login) {
        String name = edit_account.getText().toString();
        String pswd = edit_password.getText().toString();
        if(name.trim().equals("")) {
          Toast.makeText(this,
              "���������û�����", Toast.LENGTH_SHORT).show();
          return;
        }
        if(pswd.trim().equals("")) {
          Toast.makeText(this,
              "�����������룡", Toast.LENGTH_SHORT).show();
          return;
        }
        boolean CheckBoxLogin = check_jizhumima.isChecked();
        if(CheckBoxLogin) {
          Editor editor = sp.edit();
          editor.putString("uname", name);
          editor.putString("upswd", pswd);
          editor.putBoolean("checkboxBoolean", true);
          editor.commit();
        } else {
          Editor editor = sp.edit();
          editor.putString("uname", null);
          editor.putString("upswd", null);
          editor.putBoolean("checkboxBoolean", false);
          editor.commit();
        }
        //Intent��ת
        new Thread(new LoginThread()).start();

      }
    }
  }


  class LoginThread extends Thread {
    public void run() {


      String ip = Apppeizhiwenjian.getProperties(getApplicationContext(), "serviceUrl");
      String duankou = Apppeizhiwenjian.getProperties(getApplicationContext(), "duankounum");
      String api = Apppeizhiwenjian.getProperties(getApplicationContext(), "login_apiname");

      Log.d("kankan", "http://" + ip + ":" + duankou + api);
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/user/login");
        //post����
        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("loginName", edit_account.getText().toString().trim());
        params.put("password", edit_password.getText().toString().trim());
        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("true", resStr);

        if(resStr.length() >= 20) {

          Log.d("MainActivity", "����������ֵ��" + resStr);

          yhjo = new JSONObject(resStr);

          URL urltuisong = new URL("http://222.178.109.129:9082/ibs/api/user/{userId,registrationId},");
          //post����
          Map<String, Object> paramstuisong = new LinkedHashMap<String, Object>();


          paramstuisong.put("userId", yhjo.getString("id"));
          paramstuisong.put("registrationId", rid);
          String resStrtuisong = appapi.getWebServiceRes(paramstuisong, urltuisong);


          //��¼����ݽ�ɫ��ת
          Intent intent;

          if(yhjo.getString("position").equals("�������ֳ�")) {
            intent = new Intent(Loginpage.this, nfcdaka.class);
            startActivity(intent);
          } else if(yhjo.getString("organizationId").equals("2")) {
            intent = new Intent(Loginpage.this, Roleshichang.class);
            startActivity(intent);
          } else if(yhjo.getString("organizationId").equals("3")) {
            intent = new Intent(Loginpage.this, Roleanbao.class);
            startActivity(intent);

          } else if(yhjo.getString("organizationId").equals("4")) {
            intent = new Intent(Loginpage.this, Roleengineer.class);
            startActivity(intent);

          } else if(yhjo.getString("organizationId").equals("5")) {
            intent = new Intent(Loginpage.this, Rolecehua.class);
            startActivity(intent);

          } else if(yhjo.getString("organizationId").equals("6")) {
            intent = new Intent(Loginpage.this, Rolexingzheng.class);
            startActivity(intent);

          } else if(yhjo.getString("organizationId").equals("7")) {
            intent = new Intent(Loginpage.this, Rolekefu.class);
            startActivity(intent);

          } else if(yhjo.getString("organizationId").equals("8")) {
            intent = new Intent(Loginpage.this, Rolezongjingli.class);
            startActivity(intent);
          }

        } else {
          Looper.prepare();
          Toast.makeText(Loginpage.this, "�˻����������������ȷ��", Toast.LENGTH_LONG).show();
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
