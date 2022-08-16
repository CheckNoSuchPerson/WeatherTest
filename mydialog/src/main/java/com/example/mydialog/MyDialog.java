package com.example.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Create by Hastur
 * on 2022/8/7
 */
public class MyDialog extends Dialog {
    private MyDialogControl mDialogControl;

    private MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mDialogControl = new MyDialogControl(getContext(), this, getWindow());
    }

    public static class Builder {
        private final MyDialogControl.AlertParams P;

        //使用默认的布局
        public Builder(Context context) {
           this(context,R.style.dialog_style);
        }

        /**
         * 使用自定义布局
         * @param context
         * @param resId 布局id
         */
        public Builder(Context context, int resId){
            P = new MyDialogControl.AlertParams();
            P.mContext = context;
            P.mResStyleId = resId;
        }

        // 是否能够点击取消
        public Builder setCancelable(boolean cancelable){
            P.mCancelable = cancelable;
            return this;
        }

        //取消监听
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        // dismiss监听
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * 采用自定义布局
         * @param layoutResId 布局id
         * @return
         */
        public Builder setView(int layoutResId) {
            P.mView = null;
            P.mResId = layoutResId;
            return this;
        }

        /**
         * 利用view作为布局
         * @param view 布局的view
         * @return
         */
        public Builder setView(View view) {
            P.mView = view;
            P.mResId = 0;
            return this;
        }

        /**
         * 设计弹框的大小
         * @param w 弹框宽
         * @param h 弹框高
         * @return
         */
        public Builder setWidthAndHeight(int w, int h) {
            P.mWidth =w;
            P.mHeight = h;
            return this;
        }

        /**
         * 设计弹框布局位置
         * @param gravity Gravity
         * @return
         */
        public Builder setGravity(int gravity){
            P.mGravity = gravity;
            return this;
        }

        /**
         * 给特定的view设置特定的点击监听
         * @param viewId view id
         * @param listener OnClickListener
         * @return
         */
        public Builder setViewClickListener(int viewId,View.OnClickListener listener){
            P.array.put(viewId,listener);
            return this;
        }

        /**
         * 给特定的view设置文本
         * @param viewId
         * @param text
         * @return
         */
        public Builder setViewText(int viewId,String text){
            P.textArray.put(viewId,text);
            return this;
        }

        /**
         * 设置窗口动画
         * @param styleId 动画资源id
         * @return
         */
        public Builder setWindowAnimator(int styleId){
            P.mStyleId = styleId;
            return this;
        }

        /**
         * 设置默认窗口动画
         * @return
         */
        public Builder setDefaultWindowAnimator(){
            P.mStyleId = R.style.dialog_from_bottom_anim;
            return this;
        }

        public MyDialog create() {
            final MyDialog dialog = new MyDialog(P.mContext, P.mResStyleId);
            P.apply(dialog.mDialogControl);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            return dialog;
        }

        public MyDialog show() {
            final MyDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
