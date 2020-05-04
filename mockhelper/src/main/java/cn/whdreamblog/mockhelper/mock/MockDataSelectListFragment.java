package cn.whdreamblog.mockhelper.mock;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.whdreamblog.mockhelper.BaseFragment;
import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
import cn.whdreamblog.mockhelper.widget.AbsCommonCheckBoxDevAdapter;
import cn.whdreamblog.mockhelper.widget.CommonViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MockDataSelectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MockDataSelectListFragment extends BaseFragment {


    private MockDataViewModel viewModel;
    private android.support.v7.widget.RecyclerView recycleMock;
    private DevSelectListAdapter adapter;
    public MockDataSelectListFragment() {
        // Required long_charter_icon_upload_record_chart_child_checked public constructor
    }


    public static MockDataSelectListFragment newInstance() {
        MockDataSelectListFragment fragment = new MockDataSelectListFragment();
        return fragment;
    }

    @Override
    protected void performLazyWork() {
        super.performLazyWork();
        dataChange();
    }

    private void dataChange() {
        if (adapter != null){
            adapter.getDataSet().clear();
            adapter.getDataSet().addAll(MockRemote.getSelectDataSet());
            adapter.setSelectList(MockRemote.getSelectDataSet());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initViews(View container) {
        super.initViews(container);
        viewModel = ViewModelProviders.of(getActivity()).get(MockDataViewModel.class);
        viewModel.getSelectDataSet().observe(this, new Observer<Set<MocksResponse>>() {
            @Override
            public void onChanged(@Nullable Set<MocksResponse> mocksResponses) {
                dataChange();
            }
        });
        adapter = new DevSelectListAdapter(
                getContext()
                , new ArrayList<>(MockRemote.getSelectDataSet())
                , new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    viewModel.removeSelectData((MocksResponse) buttonView.getTag());
                }
            }
        }
        );
        adapter.setSelectList(MockRemote.getSelectDataSet());
        recycleMock = container.findViewById(R.id.recycle_mock);
        recycleMock.setAdapter(adapter);
        LinearLayoutManager managerPlugin = new LinearLayoutManager(getContext());
        managerPlugin.setOrientation(LinearLayoutManager.VERTICAL);
        recycleMock.setLayoutManager(managerPlugin);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.mock_fragment_mock_data_select_list;
    }

    private   static class DevSelectListAdapter extends AbsCommonCheckBoxDevAdapter {
        private final Set<MocksResponse> selectList = new HashSet<>();
        private CompoundButton.OnCheckedChangeListener listener;

        public DevSelectListAdapter(Context context, List<MocksResponse> dataSet, CompoundButton.OnCheckedChangeListener listener) {
            super(context, dataSet);
            this.listener = listener;

        }

        public void setSelectList(Set<MocksResponse> list) {
          Set<MocksResponse> set =list == null?new HashSet<MocksResponse>():list;
            if (getSelectList() != null
                    && getSelectList().equals(list)){
                return;
            }else {
                //不是相同的对象，所以直接clear掉本地数据，覆盖新的
                selectList.clear();
            }
            this.selectList.addAll(set);
            notifyDataSetChanged();
        }

        @Override
        protected void convert(final CommonViewHolder holder, MocksResponse data, int position) {
            CheckBox cb = holder.itemView.findViewById(R.id.cb_mock);
            if (selectList.contains(data)) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setText(data.getDescription() + "Method:" + data.getMethod() + "\n url:" + data.getUrl());
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MocksResponse response = getDataSet().get(holder.getAdapterPosition());
                    buttonView.setTag(response);
                    if (!isChecked) {
                        selectList.remove(response);
                        getDataSet().remove(response);
                        notifyDataSetChanged();
                    }
                    if (listener != null) {
                        listener.onCheckedChanged(buttonView, isChecked);
                    }
                }
            });
        }

        public Set<MocksResponse> getSelectList() {
            return selectList;
        }
    }

}
