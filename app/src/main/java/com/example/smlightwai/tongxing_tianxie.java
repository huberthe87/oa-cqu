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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.example.smlightwai.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import jiekou.Utils;
import jiekou.WeiboDialogUtils;
import jiekou.appapi;

public class tongxing_tianxie extends Activity {

  private File fileName = null;

  private ImageView imageView;
  private Spinner spinner_huowuleixing = null;
  private EditText edit_huowumiaoshu;
  private String huowuleixing;
  private String shijianstr;
  private Dialog mWeiboDialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.tongxingxinxitianxie);

    edit_huowumiaoshu = (EditText) findViewById(R.id.huowumiaoshu);
    spinner_huowuleixing = (Spinner) findViewById(R.id.huowuleix);
    spinner_huowuleixing.setOnItemSelectedListener(new ProvOnItemSelectedListener_huowuleixing());
    Button btn_paizhao = (Button) findViewById(R.id.tongxingtianxiepaizhao);
    Button btn_shenqing = (Button) findViewById(R.id.tongxingtianxieshenqing);
    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhui);


    imageView = (ImageView) findViewById(R.id.imageView1);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_tianxie.this, tongxing_bothyuangong.class);
          startActivity(intent);
        }

      }
    });


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


    btn_shenqing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          mWeiboDialog = WeiboDialogUtils.createLoadingDialog(tongxing_tianxie.this, "������...");
          new Thread(new tongxingshenqingThread()).start();
        }
      }
    });

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


  private class ProvOnItemSelectedListener_huowuleixing implements OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
      //��ȡѡ������ֵ
      huowuleixing = adapter.getItemAtPosition(position).toString();
      Toast.makeText(getApplicationContext(), huowuleixing, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
      huowuleixing = "ʲôҲûѡ��";
      Toast.makeText(getApplicationContext(), huowuleixing, Toast.LENGTH_LONG).show();

    }

  }


  class tongxingshenqingThread extends Thread {
    public void run() {

      //post����


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/saveOrUpdatePassRecordForApp");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("goodsType", huowuleixing);
        params.put("goodsDescription", edit_huowumiaoshu.getText().toString());
        params.put("submissionDate", shijianstr);
        params.put("applicantId", Long.parseLong(Loginpage.yhjo.getString("id")));
        params.put("administratorId", 2);
        Map<String, Object> imgMap = new HashMap<String, Object>();
        imgMap.put("1", "file1");
        imgMap.put("2", fileName);
        String resStr = appapi.getWebServiceRestupianwenben(url, params, imgMap);
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(resStr);

        mWeiboDialog.dismiss();

        if(isNum.matches()) {

          Looper.prepare();
          Toast.makeText(tongxing_tianxie.this, "���ͳɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tongxing_tianxie.this, "����ʧ�ܣ������·���", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        }
      } catch(NumberFormatException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
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
