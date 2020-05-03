package cn.whdreamblog.mockhelper.mock;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.whdreamblog.mockhelper.BaseActivity;
import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
import cn.whdreamblog.mockhelper.util.MockUtils;
import cn.whdreamblog.mockhelper.widget.AbsCommonCheckBoxDevAdapter;
import cn.whdreamblog.mockhelper.widget.CommonFragmentViewPagerAdapter;
import cn.whdreamblog.mockhelper.widget.CommonViewHolder;
import cn.whdreamblog.mockhelper.widget.MockTitleBar;


/**
 * @author wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/4/5
 * desc : mock数据管理界面
 */
@MockUtils.DisableDevToolsActivity
public class MockDataActivity extends BaseActivity<MockDataContract.Presenter> implements MockDataContract.View {



    MockTitleBar mockTitleBar2;

    ViewPager viewPager;

    CheckBox cbAllAdd;

    TabLayout tabMockTab;
    private MockDataViewModel viewModel;
    private MockDataFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_develop_activity_mock_data);
        mockTitleBar2 = findViewById(R.id.ehiTitleBar2);
        viewPager = findViewById(R.id.view_pager);
        cbAllAdd = findViewById(R.id.cb_all_add);
        tabMockTab = findViewById(R.id.tab_mock_tab);
        initViews();
        viewModel = ViewModelProviders.of(this).get(MockDataViewModel.class);
        viewModel.setSelectDataSet(MockRemote.SelectDataSet);
        new MockDataPresenter(this);
        presenter.start();

    }


    private void initViews() {
        cbAllAdd.setChecked(MockRemote.isMockAll());
        cbAllAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MockRemote.setMockAll(isChecked);
                if (isChecked) {
                    viewModel.addAll();
                } else {
                    viewModel.clearAll();
                }
            }
        });
        fragment = new MockDataFragment();
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), new ArrayList<Fragment>(Arrays.asList(
                fragment, new MockDataSelectListFragment()
        ))));
        TabLayout.Tab mockTab = tabMockTab.newTab();
        TabLayout.Tab mockSelectListTab = tabMockTab.newTab();
        tabMockTab.addTab(mockTab);
        tabMockTab.addTab(mockSelectListTab);
        tabMockTab.setupWithViewPager(viewPager);
    }

    public static void startActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activity.startActivity(new Intent(activity, MockDataActivity.class));
    }

    @Override
    public void showMockList(List<MocksResponse> mockResponseList) {
        viewModel.setMutableLiveDataList(mockResponseList);
    }

    @Override
    public void initSearchBar(List<String> searchList) {
        viewModel.setSearchList(searchList);
        mockTitleBar2.setRightBtnClickListener(new MockTitleBar.TitleBarRightBtnOnClickListener() {
            @Override
            public void onTitleBarRightBtnClick(View v) {
                new MockDataSearchFragment().show(getSupportFragmentManager());
            }
        });
    }


    static class PageAdapter extends CommonFragmentViewPagerAdapter {

        public PageAdapter(FragmentManager fragmentManager, List<Fragment> list) {
            super(fragmentManager, list);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "mock列表";
            }
            if (position == 1) {
                return "mock选中列表";
            }
            return super.getPageTitle(position);
        }
    }

    static class DevAdapter extends AbsCommonCheckBoxDevAdapter {
        private final Set<MocksResponse> selectList = new HashSet<>();
        private CompoundButton.OnCheckedChangeListener listener;

        public DevAdapter(Context context, List<MocksResponse> dataSet, CompoundButton.OnCheckedChangeListener listener) {
            super(context, dataSet);
            this.listener = listener;

        }

        public void setSelectList(Set<MocksResponse> list) {
            Set<MocksResponse> optional = list == null?new HashSet<MocksResponse>():list;
            if (getSelectList() != null
                    && getSelectList().equals(list)) {
                notifyDataSetChanged();
                return;
            } else {
                //不是相同的对象，所以直接clear掉本地数据，覆盖新的
                selectList.clear();
            }
            this.selectList.addAll(list);
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
                    if (isChecked) {
                        selectList.add(response);
                    } else {
                        selectList.remove(response);
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
