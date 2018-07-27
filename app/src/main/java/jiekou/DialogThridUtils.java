package jiekou;

import com.example.smlightwai.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 锛氱▼搴忓憳灏忓啺
 * @鏂版氮寰崥 锛歨ttp://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN鍗氬: http://blog.csdn.net/qq_21376985
 */
public class DialogThridUtils {
    /**
     * 鏄剧ずDialog
     * @param context  涓婁笅鏂�
     * @param msg  鏄剧ず鍐呭
     * @param isTransBg 鏄惁閫忔槑
     * @param isCancelable 鏄惁鍙互鐐瑰嚮鍙栨秷
     * @return
     */
    public static Dialog showWaitDialog(Context context, String msg, boolean isTransBg, boolean isCancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.thridlogin_dialog_loading, null);             // 寰楀埌鍔犺浇view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);// 鍔犺浇甯冨眬

        // main.xml涓殑ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);   // 鎻愮ず鏂囧瓧
        // 鍔犺浇鍔ㄧ敾
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
        // 浣跨敤ImageView鏄剧ず鍔ㄧ敾
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 璁剧疆鍔犺浇淇℃伅

        Dialog loadingDialog = new Dialog(context, isTransBg ? R.style.TransDialogStyle : R.style.WhiteDialogStyle);    // 鍒涘缓鑷畾涔夋牱寮廳ialog
        loadingDialog.setContentView(layout);
        loadingDialog.setCancelable(isCancelable);
        loadingDialog.setCanceledOnTouchOutside(false);

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
