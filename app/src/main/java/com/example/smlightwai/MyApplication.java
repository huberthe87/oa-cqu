package com.example.smlightwai;

import android.app.Application;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
  /**
   * ���Ӧ��һ�����ͻ����JPush����
   */
  @Override
  public void onCreate() {
    super.onCreate();
    System.out.println("Ӧ�ñ�������");
    JPushInterface.setDebugMode(true);
    JPushInterface.init(this);
  }
} 


