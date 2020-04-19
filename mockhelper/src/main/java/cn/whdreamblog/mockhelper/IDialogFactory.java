package cn.whdreamblog.mockhelper;

import android.content.DialogInterface;
import android.support.annotation.Nullable;

/**
 * Created by Frank on 2018/7/9.
 * Email: xiaoyu.zhu@1hai.cn
 * Version: 1.0
 * Description:
 */
public interface IDialogFactory {

    void showProgressBarDialog(boolean cancelable);

    void hideProgressBarDialog();

    void setOnKeyListenerForProgressBarDialog(DialogInterface.OnKeyListener onKeyListener);

    void showNetErrorDialog(@Nullable String content);





}
