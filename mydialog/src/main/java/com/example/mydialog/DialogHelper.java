package com.example.mydialog;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Create by Hastur
 * on 2022/8/7
 */
class DialogHelper {
    private MyDialogControl controller;
    private SparseArray<WeakReference<View>> weakReferenceSparseArray;

    public DialogHelper(MyDialogControl myController) {
        this.controller = myController;
        weakReferenceSparseArray = new SparseArray<>();
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
    }

    private View getView(int viewId) {
        WeakReference<View> reference = weakReferenceSparseArray.get(viewId);
        View view;
        if (reference == null) {
            view = controller.mDialog.findViewById(viewId);
            if (view != null)
                weakReferenceSparseArray.put(viewId, new WeakReference<>(view));
        } else {
            view = reference.get();
        }
        return view;
    }

    /**
     * 只有textview才能设置
     * @param viewId
     * @param text
     */
    public void setText(int viewId, String text) {
        View view = getView(viewId);
        if (view instanceof TextView){
            ((TextView) view).setText(text);
        } else {
            throw new IllegalArgumentException("this view id does not has setText()");
        }
    }
}
