package cn.whdreamblog.mockhelper.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.whdreamblog.mockhelper.BaseActivity;
import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.mock.MockDataActivity;
import cn.whdreamblog.mockhelper.mock.MockRecordActivity;
import cn.whdreamblog.mockhelper.util.MockUtils;
import cn.whdreamblog.mockhelper.widget.CommonRecyclerAdapter;
import cn.whdreamblog.mockhelper.widget.CommonViewHolder;


/**
 * @author wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/4/5
 * desc : 开发工具主界面
 */
@MockUtils.DisableDevToolsActivity
public class DevelopMainActivity extends BaseActivity<DevelopMainContract.Presenter>
implements DevelopMainContract.View{

    public static final String ITEM_MOCK_DATA = "Mock数据";
    public static final String ITEM_MOCK_RECORD = "MOCK数据录制";
    RecyclerView recycleDev;

    public static void startActivity(Activity context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, DevelopMainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_develop_activity_main);
        initViews();
        new DevelopMainPresenter(this);
        presenter.start();
    }

    private void initViews() {
        recycleDev = findViewById(R.id.recycle_dev);
        DevAdapter adapter = new DevAdapter(this, new ArrayList<>(Arrays.asList(
                ITEM_MOCK_DATA
                , ITEM_MOCK_RECORD
        ))
                , new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = (String) v.getTag(DevAdapter.TV_NAME);
                start(s);
            }
        });
        recycleDev.setAdapter(adapter);
        LinearLayoutManager managerPlugin = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycleDev.setLayoutManager(managerPlugin);
    }

    private void start(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        switch (s) {
            case ITEM_MOCK_DATA:
            default:
                MockDataActivity.startActivity(this);
                break;
            case ITEM_MOCK_RECORD:
                MockRecordActivity.start(this);
                break;
        }
    }

    private static class DevAdapter extends CommonRecyclerAdapter<String> {

        public static final int TV_NAME = R.id.tv_name;
        private ImageView iv;
        private TextView tvName;
        private View.OnClickListener v;

        public DevAdapter(Context context, List<String> dataSet, View.OnClickListener v) {
            super(context, dataSet);
            this.v = v;
        }

        @Override
        protected int getLayoutResId(String data, int position) {
            return R.layout.module_develop_item_main;
        }

        @Override
        protected void convert(CommonViewHolder holder, String data, int position) {
            iv = holder.itemView.findViewById(R.id.iv);
            tvName = holder.itemView.findViewById(R.id.tv_name);
            tvName.setText(data);
            holder.itemView.setTag(TV_NAME, data);
            holder.itemView.setOnClickListener(v);
        }


    }


}
