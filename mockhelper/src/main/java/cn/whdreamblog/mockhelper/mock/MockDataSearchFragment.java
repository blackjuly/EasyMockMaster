package cn.whdreamblog.mockhelper.mock;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import cn.whdreamblog.mockhelper.EhiBaseDialogFragment;
import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.widget.CommonDialog;
import cn.whdreamblog.mockhelper.widget.MockSearchView;


/**
 * 搜索mock列表数据
 *
 * @author wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/18 13:35
 */
public class MockDataSearchFragment extends EhiBaseDialogFragment {

    private MockSearchView mocksSearchBar;
    private android.widget.RelativeLayout rlEmpty;
    private android.widget.TextView tvEmptyMessage;
    private MockDataViewModel viewModel;

    public MockDataSearchFragment() {
        // Required long_charter_icon_upload_record_chart_child_checked public constructor
    }

    @Override
    protected void parseArguments(Bundle arguments) {
        super.parseArguments(arguments);
        if (getArguments() != null) {

        }
    }

    @NonNull
    @Override
    protected Dialog createDialog(Bundle savedInstanceState, @NonNull Context context) {
        return new CommonDialog.Builder(context)
                .setLayoutRes(R.layout.module_develop_fragment_search_mock_data)
                .setGravity(Gravity.TOP)
                .setFillScreenWidth()
                .create();
    }

    @Override
    protected void initViews(@NonNull ViewGroup container) {
        super.initViews(container);
        viewModel = ViewModelProviders.of(getActivity()).get(MockDataViewModel.class);
        mocksSearchBar =  container.findViewById(R.id.mocks_search_bar);
        rlEmpty =  container.findViewById(R.id.rl_empty);
        tvEmptyMessage =  container.findViewById(R.id.tv_empty_message);
        mocksSearchBar.getAcTvSearchBox().setAdapter(new ArrayAdapter<String>(
                getContext()
                , android.R.layout.simple_list_item_1
                , android.R.id.text1
                , viewModel.getSearchList()
        ) {
        });
        mocksSearchBar.setCancelBtnListener(new MockSearchView.OnClickCancelBtnListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mocksSearchBar.setEmptyView(rlEmpty);
        mocksSearchBar.setSearchBoxItemListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mocksSearchBar.getAcTvSearchBox().setFocusable(false);
                mocksSearchBar.getAcTvSearchBox().post(new Runnable() {
                    @Override
                    public void run() {
                        hideSoftKeyboard(getActivity(),mocksSearchBar.getAcTvSearchBox());
                    }
                });
                viewModel.setSelectItem(position);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });
    }

    public static void hideSoftKeyboard(@NonNull Context context,
                                        @NonNull EditText mEditText)
    {
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}