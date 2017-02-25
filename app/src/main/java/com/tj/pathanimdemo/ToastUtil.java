package com.tj.pathanimdemo;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Don on 2016/11/29 13:32.
 * Describe：${TODO}
 * Modified：${TODO}
 */

public class ToastUtil {
    private static Toast sToast;

    public static void showToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.setGravity(Gravity.BOTTOM, 0, 200);
        sToast.show();
    }
}
