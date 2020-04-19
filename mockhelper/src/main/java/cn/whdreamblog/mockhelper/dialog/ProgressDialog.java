package cn.whdreamblog.mockhelper.dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.whdreamblog.mockhelper.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressDialog extends DialogFragment {

    public ProgressDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static ProgressDialog newInstance() {
        return new ProgressDialog();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.module_mock_frag_progress_dialog, container, false);
    }

}
