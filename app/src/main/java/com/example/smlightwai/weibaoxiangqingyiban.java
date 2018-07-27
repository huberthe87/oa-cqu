package com.example.smlightwai;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.appapi;

public class weibaoxiangqingyiban extends Activity {


  private String shijianstr;
  JSONObject cxwb1;

  TextView caozuoneirong;
  TextView caozuoleixing;
  TextView caozuoshijian;
  TextView caozuoren;
  TextView weibaozhuangtai;


  private File fileName = null;

  private ImageView imageView;


  EditText edt_weibaoneirong;
  //	EditText edt_weibaoneirong = new EditText(this);
  private String weibaoneirong;
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {

            //���������UI�������������ʾ��������


            caozuoneirong.setText(cxwb1.getString("maintainContent"));

            if(cxwb1.getString("maintainStatus").equals("0")) {
              caozuoleixing.setText("�ƻ��ƶ�");
            } else if(cxwb1.getString("maintainStatus").equals("1")) {
              caozuoleixing.setText("�ֳ�ά��");
            } else if(cxwb1.getString("maintainStatus").equals("2")) {
              caozuoleixing.setText("ά��ȷ��");
            }
            caozuoshijian.setText(cxwb1.getString("maintainDate"));
            caozuoren.setText(cxwb1.getString("maintainPerson"));

            weibaozhuangtai.setText(cxwb1.getString("status"));
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
    setContentView(R.layout.weibaoxiangqingyiban);

    edt_weibaoneirong = new EditText(this);

    caozuoneirong = (TextView) this.findViewById(R.id.caozuoneirong);
    caozuoleixing = (TextView) this.findViewById(R.id.caozuoleixing);
    caozuoshijian = (TextView) this.findViewById(R.id.caozuoshijian);
    caozuoren = (TextView) this.findViewById(R.id.caozuoren);
    weibaozhuangtai = (TextView) this.findViewById(R.id.weibaozhuangtai);

    Button btn_weibaowanchen = (Button) this.findViewById(R.id.weibaowanchen);
    imageView = (ImageView) this.findViewById(R.id.imageView1);


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);

    Button btn_paizhao = (Button) this.findViewById(R.id.weibaopaizhao);


    btn_paizhao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        File filePath = new File(Environment.getExternalStorageDirectory(), "myCamera");
        if(!filePath.exists()) {
          filePath.mkdirs();
        }
        fileName = new File(filePath, System.currentTimeMillis() + ".jpg");
        try {
          if(!fileName.exists()) {
            fileName.createNewFile();
          }
        } catch(Exception e) {
          e.printStackTrace();
        }
        // intent��������ϵͳ�Դ���Camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ��ϵͳCamera��������д�뵽�ļ�
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileName));
        // ����intent��Ӧ��Activity������Ĭ����Ϣ
        startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
      }
    });


    btn_weibaowanchen.setOnClickListener(chuliClick);


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    try {

      cxwb1 = new JSONObject(bundle.getString("weibaoxiangqing"));

    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Message message = new Message();
    message.what = 0;
    message.obj = "";


    handler.sendMessage(message);


  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == Activity.DEFAULT_KEYS_DIALER) {
      // MainActivity����Camera���ص���Ϣ��Ȼ���Ѿ�д���ͼƬ��ʾ��ImageView��
      Bitmap cameraImageBitmap = getBitmapFromBigFile(fileName.getPath(), 480, 720);


      try {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
        cameraImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
      } catch(FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      imageView.setImageBitmap(cameraImageBitmap);
//    		imageView.setImageURI(Uri.fromFile(fileName));  
    }
  }


  private View.OnClickListener chuliClick = new View.OnClickListener() {

    @Override
    public void onClick(View v) {
      // TODO Auto-generated method stub
      new AlertDialog.Builder(weibaoxiangqingyiban.this)
          .setTitle("�ֳ�ά������")
          .setIcon(android.R.drawable.ic_dialog_info)
          .setView(edt_weibaoneirong)
          .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              Toast.makeText(getApplicationContext(), "ddddkaishi�ɹ�",
                  Toast.LENGTH_SHORT).show();
              new Thread(new querenweibaoThread()).start();

            }
          })
          .setNegativeButton("ȡ��", null)
          .show();

    }
  };


  class querenweibaoThread extends Thread {
    public void run() {


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintain/saveOrUpdate");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", cxwb1.getString("id"));
        params.put("maintainContent", edt_weibaoneirong.getText().toString() + "(�ֳ�ά������)");
        params.put("maintainPerson", Loginpage.yhjo.getString("name"));

        params.put("maintainDate", shijianstr);

        params.put("planId", cxwb1.getString("planId"));
        params.put("maintainStatus", "1");


//								String resStr=appapi.getWebServiceRes(params,url);
//								
//								Log.d("resStrresStr", resStr);


        Map<String, Object> imgMap = new HashMap<String, Object>();


        imgMap.put("1", "file1");
        imgMap.put("2", fileName);

        String resStr = appapi.getWebServiceRestupianwenben(url, params, imgMap);


        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "ȷ�ϳɹ�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "ȷ��ʧ�ܣ�������ȷ��",
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
      } catch(Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }

  }


  private static Bitmap getBitmapFromBigFile(String path, int width, int height) {
    //У����ת�Ƕ�
    Matrix matrix = new Matrix();
    matrix.postRotate(getBitmapDegree(path));
    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    // BitmapFactory.decodeResource(res, resId, options);
    BitmapFactory.decodeFile(path, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, width, height);
    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    Bitmap decodeFile = BitmapFactory.decodeFile(path, options);

    decodeFile = Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);

    return decodeFile;
  }


  private static int getBitmapDegree(String path) {
    int degree = 0;
    try {
      // ��ָ��·���¶�ȡͼƬ������ȡ��EXIF��Ϣ
      ExifInterface exifInterface = new ExifInterface(path);
      // ��ȡͼƬ����ת��Ϣ
      int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
          ExifInterface.ORIENTATION_NORMAL);
      switch(orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          degree = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          degree = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          degree = 270;
          break;
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    return degree;
  }

  /**
   * ����������inSampleSize
   *
   * @param options
   * @param reqWidth
   * @param reqHeight
   * @return
   */
  private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if(height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and
      // keeps both
      // height and width larger than the requested height and width.
      while((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
      }
    }

    return inSampleSize;
  }


}
		
	
  	


