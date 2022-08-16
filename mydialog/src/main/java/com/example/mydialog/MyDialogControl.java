package com.example.mydialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.ObjectOutputStream;

/**
 * Create by Hastur
 * on 2022/8/7
 */
class MyDialogControl {

    Context mContext;
    MyDialog mDialog;
    Window mWindow;
    DialogHelper helper;

    public MyDialogControl(Context context, MyDialog dialog, Window window) {
        mContext = context;
        mDialog = dialog;
        mWindow = window;
        helper = new DialogHelper(this);
    }

    public static class AlertParams {

        public boolean mCancelable = true; //是否可以取消
        public DialogInterface.OnCancelListener mOnCancelListener; //取消监听
        public DialogInterface.OnDismissListener mOnDismissListener; //消失监听
        public View mView;
        public int mResId;
        public int mWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        public int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public SparseArray<View.OnClickListener> array = new SparseArray<>();
        public SparseArray<String> textArray = new SparseArray<>();
        public Context mContext;
        public int mStyleId = -1;
        public int mResStyleId;

        public void apply(MyDialogControl mControl) {
            //mResId的优先级高于mView
            if (mResId != 0) {
                mControl.mDialog.setContentView(mResId);
            } else if (mView != null) {
                mControl.mDialog.setContentView(mView);
            } else {
                throw new NullPointerException();
            }
            WindowManager.LayoutParams params = mControl.mWindow.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            params.gravity = mGravity;
            mControl.mWindow.setAttributes(params);

            mControl.mDialog.setCancelable(mCancelable);
            mControl.mDialog.setOnCancelListener(mOnCancelListener);
            mControl.mDialog.setOnDismissListener(mOnDismissListener);

            if (mStyleId != 1) {
                mControl.mWindow.setWindowAnimations(mStyleId);
            }

            for (int i = 0; i < array.size(); i++) {
                mControl.helper.setOnClickListener(array.keyAt(i),array.valueAt(i));
            }

            for (int i = 0; i < textArray.size(); i++) {
                mControl.helper.setText(textArray.keyAt(i),textArray.valueAt(i));
            }
        }
    }
}
