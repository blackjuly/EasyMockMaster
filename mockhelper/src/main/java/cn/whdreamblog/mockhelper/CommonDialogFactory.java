package cn.whdreamblog.mockhelper;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import cn.whdreamblog.mockhelper.dialog.ProgressDialog;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/12 ${Time}
 * desc : The class is used for .....
 */
public class CommonDialogFactory implements IDialogFactory{
    private static final String PROGRESS_BAR_TAG = "progressBarTag";
    private FragmentActivity context;
    private ProgressDialog progressDialog;

    public CommonDialogFactory(FragmentActivity context) {
        this.context = context;
    }

    @Override
    public void showProgressBarDialog(boolean cancelable) {
        if (context == null || context.isDestroyed() || context.isFinishing()){
            return;
        }
        progressDialog = (ProgressDialog) context.getSupportFragmentManager().findFragmentByTag(PROGRESS_BAR_TAG);
        if (progressDialog == null){
            progressDialog = ProgressDialog.newInstance();
        }
        progressDialog.setCancelable(cancelable);
        progressDialog.showNow(context.getSupportFragmentManager(), PROGRESS_BAR_TAG);
    }

    @Override
    public void hideProgressBarDialog() {
        if (context == null || context.isDestroyed() || context.isFinishing()){
            return;
        }
        progressDialog = (ProgressDialog) context.getSupportFragmentManager().findFragmentByTag(PROGRESS_BAR_TAG);
        if (progressDialog == null){
            return;
        }
        progressDialog.dismiss();
        context.getSupportFragmentManager().beginTransaction().remove(progressDialog).commit();
    }

    @Override
    public void setOnKeyListenerForProgressBarDialog(DialogInterface.OnKeyListener onKeyListener) {

    }

    @Override
    public void showNetErrorDialog(@Nullable String text) {
        if (TextUtils.isEmpty(text)|| context == null){
            return;
        }
        View decorView = context.getWindow().getDecorView();
        Snackbar.make(decorView,text, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
