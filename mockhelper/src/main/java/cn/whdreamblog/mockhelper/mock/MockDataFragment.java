package cn.whdreamblog.mockhelper.mock;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;

import java.util.List;
import java.util.Set;

import cn.whdreamblog.mockhelper.BaseFragment;
import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;


/**
 * Associated Presenter {@link MockDataPresenter}
 * Associated Activity {@link MockDataActivity}
 *
 * @author wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/13 19:21
 */
public class MockDataFragment extends BaseFragment {

    private android.support.v7.widget.RecyclerView recycleMock;
    private MockDataActivity.DevAdapter adapter;
    private MockDataViewModel viewModel;

    public MockDataFragment() {
        // Required long_charter_icon_upload_record_chart_child_checked public constructor
    }

    @Override
    protected void parseArguments(@NonNull Bundle arguments) {
        super.parseArguments(arguments);
        if (getArguments() != null) {

        }
    }

    @Override
    protected void performLazyWork() {
        super.performLazyWork();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.module_develop_fragment_mock_data;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        if (getActivity() == null) {
            return;
        }
        recycleMock = view.findViewById(R.id.recycle_mock);
        viewModel = ViewModelProviders.of(getActivity()).get(MockDataViewModel.class);
        viewModel.getMutableLiveData().observe(
                this, new Observer<List<MocksResponse>>() {
                    @Override
                    public void onChanged(@Nullable List<MocksResponse> mocksResponses) {
                        showMockList(mocksResponses);
                    }
                }
        );
        viewModel.getSelectDataSet().observe(this, new Observer<Set<MocksResponse>>() {
            @Override
            public void onChanged(@Nullable Set<MocksResponse> mocksResponses) {
                if (adapter == null) {
                    return;
                }
                adapter.setSelectList(mocksResponses);
            }
        });
        viewModel.getSelectItem().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                MocksResponse response = viewModel.getMutableLiveData().getValue().get(integer);
                viewModel.addSelectData(response);
                adapter.getSelectList().add(response);
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void showMockList(List<MocksResponse> mocksResponses) {
        adapter = new MockDataActivity.DevAdapter(
                getContext()
                , mocksResponses
                , new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //处理包含全选的情况，用直接设置值的方式，简单粗暴却没有bug
                MockRemote.setSelectDataSet(adapter.getSelectList());

            }
        }
        );
        adapter.setSelectList(MockRemote.getSelectDataSet());

        recycleMock.setAdapter(adapter);
        LinearLayoutManager managerPlugin = new LinearLayoutManager(getContext());
        managerPlugin.setOrientation(GridLayoutManager.VERTICAL);
        recycleMock.setLayoutManager(managerPlugin);
    }
}