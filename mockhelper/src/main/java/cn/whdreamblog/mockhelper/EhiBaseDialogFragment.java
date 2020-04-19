package cn.whdreamblog.mockhelper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import cn.whdreamblog.mockhelper.util.MyLogger;


/**
 * A template class
 * <p>
 * U will create DialogFragment more easier when use this template class.
 *
 * @author Sharry <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2/28/2019 5:57 PM
 */
public class EhiBaseDialogFragment extends DialogFragment {

    private static final String TAG = EhiBaseDialogFragment.class.getSimpleName();

    /*   ================================= DialogFragment Lifecycle =======================================  */

    /**
     * Display the dialog, this is a convenience for explicitly create Tag.
     *
     * @see #show(FragmentManager, String)
     */
    public void show(@NonNull FragmentManager manager) {
        String tag = String.valueOf(System.currentTimeMillis());
        show(manager, tag);
    }

    @Override
    public void show(@NonNull final FragmentManager manager, @NonNull final String tag) {
        if (verifyShowCondition(manager, tag)) {
            try {
                super.showNow(manager, tag);
            } catch (Throwable e) {
                MyLogger.getLogger().e(e, e.getMessage());
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (null != arguments) {
            parseArguments(arguments);
        }
    }

    /**
     * This is called before {@link #onCreateView}
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        Dialog dialog = createDialog(savedInstanceState, getActivity());
        // User inflater view when build the dialog.
        Window window = dialog.getWindow();
        if (window != null && window.getDecorView() != null) {
            contextConvert(getContext());
            configDialog(dialog);
            initViews((ViewGroup) window.getDecorView());
        } else {
            MyLogger.getLogger().i("The dialog less invoke setContentView(), " +
                    "{#configDialog} and {#setupView} will called at onActivityCreate.");
        }
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        if (window != null && window.getDecorView() != null) {
            contextConvert(getContext());
            configDialog(getDialog());
            initViews((ViewGroup) window.getDecorView());
        } else {
            MyLogger.getLogger().i("Cannot find root view for this dialog.");
        }
    }

    @Override
    public void dismiss() {
        if (verifyDismissCondition()) {
            super.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onDetach() {
        dismiss();
        super.onDetach();
    }

    /**
     * U can parse args that transfer from {@link #setArguments}
     */
    protected void parseArguments(@NonNull Bundle args) {
    }

    /**
     * Override to build U custom Dialog container.
     */
    @NonNull
    protected Dialog createDialog(Bundle savedInstanceState, @NonNull Context context) {
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * U can do context convert from here.
     */
    protected void contextConvert(@NonNull Context context) {
    }

    /**
     * Override to config the Dialog associated with the Fragment.
     * <p>
     * It`s nice time that config dialog window params.
     */
    protected void configDialog(@NonNull Dialog dialog) {

    }

    /**
     * Override to setup views associated with the Fragment.
     */
    protected void initViews(@NonNull ViewGroup container) {

    }

    /*   ================================= DialogFragment Lifecycle =======================================  */

    /**
     * Verify conditions when perform dialog show.
     */
    private boolean verifyShowCondition(FragmentManager manager, String tag) {
        if (null == manager) {
            Log.e(TAG, "Dialog show failed, parameter manager requires non null.");
            return false;
        }
        if (manager.isStateSaved()) {
            Log.e(TAG, "Dialog show failed, can not perform this show action after onSaveInstanceState");
            return false;
        }
        if (manager.isDestroyed()) {
            Log.e(TAG, "Dialog show failed, can not perform this show action after Activity destroyed");
            return false;
        }
        if (TextUtils.isEmpty(tag)) {
            Log.e(TAG, "Dialog show failed, parameter tag requires non null.");
            return false;
        }
        if (manager.findFragmentByTag(tag) != null) {
            Log.e(TAG, "Dialog show failed, Fragment already added:" + this);
            return false;
        }
        if (this.isAdded()) {
            Log.e(TAG, "Dialog show failed, Fragment already added:" + this);
            return false;
        }
        return true;
    }

    /**
     * Verify conditions when perform dialog dismiss.
     */
    private boolean verifyDismissCondition() {
        if (getFragmentManager() == null) {
            return false;
        }
        if (getFragmentManager().isStateSaved()) {
            return false;
        }
        if (getFragmentManager().isDestroyed()) {
            return false;
        }
        return true;
    }

}
