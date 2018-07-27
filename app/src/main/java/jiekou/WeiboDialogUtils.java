package jiekou;

import com.example.smlightwai.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 锛氱▼搴忓憳灏忓啺
 * @鏂版氮寰崥 锛歨ttp://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN鍗氬: http://blog.csdn.net/qq_21376985
 */

public class WeiboDialogUtils {

    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 寰楀埌鍔犺浇view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);// 鍔犺浇甯冨眬
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 鎻愮ず鏂囧瓧
        tipTextView.setText(msg);// 璁剧疆鍔犺浇淇℃伅

        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 鍒涘缓鑷畾涔夋牱寮廳ialog
        loadingDialog.setCancelable(true); // 鏄惁鍙互鎸夆�滆繑鍥為敭鈥濇秷澶�
        loadingDialog.setCanceledOnTouchOutside(false); // 鐐瑰嚮鍔犺浇妗嗕互澶栫殑鍖哄煙
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 璁剧疆甯冨眬
        /**
         *灏嗘樉绀篋ialog鐨勬柟娉曞皝瑁呭湪杩欓噷闈�
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();

        return loadingDialog;
    }

    /**
     * 鍏抽棴dialog
     *
     * http://blog.csdn.net/qq_21376985
     *
     * @param mDialogUtils
     */
    public static void closeDialog(Dialog mDialogUtils) {
    	
    	
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
        	
            mDialogUtils.dismiss();
        }
    }

}