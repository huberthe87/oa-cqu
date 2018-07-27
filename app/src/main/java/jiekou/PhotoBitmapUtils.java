package jiekou;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

public class PhotoBitmapUtils {

	 
	 /** 
	  * �������ͼƬ���ļ��� 
	  */
	 private static final String FILES_NAME = "/MyPhoto"; 
	 /** 
	  * ��ȡ��ʱ���ʽ 
	  */
	 public static final String TIME_STYLE = "yyyyMMddHHmmss"; 
	 /** 
	  * ͼƬ���� 
	  */
	 public static final String IMAGE_TYPE = ".png"; 
	  
	 // ��ֹʵ���� 
	 private PhotoBitmapUtils() { 
	 } 
	  
	 /** 
	  * ��ȡ�ֻ��ɴ洢·�� 
	  * 
	  * @param context ������ 
	  * @return �ֻ��ɴ洢·�� 
	  */
	 private static String getPhoneRootPath(Context context) { 
	  // �Ƿ���SD�� 
	  if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) 
	    || !Environment.isExternalStorageRemovable()) { 
	   // ��ȡSD����Ŀ¼ 
	   return context.getExternalCacheDir().getPath(); 
	  } else { 
	   // ��ȡapk���µĻ���·�� 
	   return context.getCacheDir().getPath(); 
	  } 
	 } 
	  
	 /** 
	  * ʹ�õ�ǰϵͳʱ����Ϊ�ϴ�ͼƬ������ 
	  * 
	  * @return �洢�ĸ�·��+ͼƬ���� 
	  */
	 public static String getPhotoFileName(Context context) { 
	  File file = new File(getPhoneRootPath(context) + FILES_NAME); 
	  // �ж��ļ��Ƿ��Ѿ����ڣ��������򴴽� 
	  if (!file.exists()) { 
	   file.mkdirs(); 
	  } 
	  // ����ͼƬ�ļ����� 
	  SimpleDateFormat format = new SimpleDateFormat(TIME_STYLE, Locale.getDefault()); 
	  Date date = new Date(System.currentTimeMillis()); 
	  String time = format.format(date); 
	  String photoName = "/" + time + IMAGE_TYPE; 
	  return file + photoName; 
	 } 
	  
	 /** 
	  * ����BitmapͼƬ��SD���� 
	  * ���û��SD��������ֻ��� 
	  * 
	  * @param mbitmap ��Ҫ�����BitmapͼƬ 
	  * @return ����ɹ�ʱ����ͼƬ��·����ʧ��ʱ����null 
	  */
	 public static String savePhotoToSD(Bitmap mbitmap, Context context) { 
	  FileOutputStream outStream = null; 
	  String fileName = getPhotoFileName(context); 
	  try { 
	   outStream = new FileOutputStream(fileName); 
	   // ������д���ļ���100��ʾ��ѹ�� 
	   mbitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
	   return fileName; 
	  } catch (Exception e) { 
	   e.printStackTrace(); 
	   return null; 
	  } finally { 
	   try { 
	    if (outStream != null) { 
	     // �ǵ�Ҫ�ر����� 
	     outStream.close(); 
	    } 
	    if (mbitmap != null) { 
	     mbitmap.recycle(); 
	    } 
	   } catch (Exception e) { 
	    e.printStackTrace(); 
	   } 
	  } 
	 } 
	  
	 /** 
	  * ��ԭͼ��1/10�ı���ѹ�� 
	  * 
	  * @param path ԭͼ��·�� 
	  * @return ѹ�����ͼƬ 
	  */
	 public static Bitmap getCompressPhoto(String path) { 
	  BitmapFactory.Options options = new BitmapFactory.Options(); 
	  options.inJustDecodeBounds = false; 
	  options.inSampleSize = 10; // ͼƬ�Ĵ�С����Ϊԭ����ʮ��֮һ 
	  Bitmap bmp = BitmapFactory.decodeFile(path, options); 
	  options = null; 
	  return bmp; 
	 } 
	  
	 /** 
	  * ������ת���ͼƬ 
	  * @param originpath ԭͼ·�� 
	  * @param context ������ 
	  * @return �����޸���Ϻ��ͼƬ·�� 
	  */
	 public static String amendRotatePhoto(String originpath, Context context) { 
	  
	  // ȡ��ͼƬ��ת�Ƕ� 
	  int angle = readPictureDegree(originpath); 
	  
	  // ��ԭͼѹ����õ�Bitmap���� 
	  Bitmap bmp = getCompressPhoto(originpath);; 
	  
	  // �޸�ͼƬ����ת�ĽǶ� 
	  Bitmap bitmap = rotaingImageView(angle, bmp); 
	  
	  // �����޸����ͼƬ�����ر�����ͼƬ·�� 
	  return savePhotoToSD(bitmap, context); 
	 } 
	  
	 /** 
	  * ��ȡ��Ƭ��ת�Ƕ� 
	  * 
	  * @param path ��Ƭ·�� 
	  * @return �Ƕ� 
	  */
	 public static int readPictureDegree(String path) { 
	  int degree = 0; 
	  try { 
	   ExifInterface exifInterface = new ExifInterface(path); 
	   int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL); 
	   switch (orientation) { 
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
	  } catch (IOException e) { 
	   e.printStackTrace(); 
	  } 
	  return degree; 
	 } 
	  
	 /** 
	  * ��תͼƬ 
	  * @param angle ����ת�Ƕ� 
	  * @param bitmap ͼƬ���� 
	  * @return ��ת���ͼƬ 
	  */
	 public static Bitmap rotaingImageView(int angle, Bitmap bitmap) { 
	  Bitmap returnBm = null; 
	  // ������ת�Ƕȣ�������ת���� 
	  Matrix matrix = new Matrix(); 
	  matrix.postRotate(angle); 
	  try { 
	   // ��ԭʼͼƬ������ת���������ת�����õ��µ�ͼƬ 
	   returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); 
	  } catch (OutOfMemoryError e) { 
	  } 
	  if (returnBm == null) { 
	   returnBm = bitmap; 
	  } 
	  if (bitmap != returnBm) { 
	   bitmap.recycle(); 
	  } 
	  return returnBm; 
	 } 
	}
