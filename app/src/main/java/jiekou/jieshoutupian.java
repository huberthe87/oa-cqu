package jiekou;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class jieshoutupian {
	
    public Bitmap loadRmoteImage(String imgUrl) {
        URL fileURL = null;
        try {
          fileURL = new URL(imgUrl);
        } catch (MalformedURLException err) {
          err.printStackTrace();
        }
        try {
          HttpURLConnection conn = (HttpURLConnection) fileURL
              .openConnection();
          conn.setDoInput(true);
          conn.connect();
          InputStream is = conn.getInputStream();
          int length = (int) conn.getContentLength();
          if (length != -1) {
            byte[] imgData = new byte[length];
            byte[] buffer = new byte[512];
            int readLen = 0;
            int destPos = 0;
            while ((readLen = is.read(buffer)) > 0) {
              System.arraycopy(buffer, 0, imgData, destPos, readLen);
              destPos += readLen;
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0,
                imgData.length);
            return bitmap;  
            //mHandler.sendEmptyMessage(1);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
		return null;
      }

}
