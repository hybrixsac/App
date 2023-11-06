package com.cloud.app.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud.app.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.atomic.AtomicInteger;



public class UtilsMessage {


    public static void addMsgOK(Context activity, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getString(R.string.app_name));
        alert.setMessage(msg);
        alert.setNeutralButton("OK", null);
        alert.setIcon(R.drawable.ic_launcher_foreground);
        alert.show();
    }























}
