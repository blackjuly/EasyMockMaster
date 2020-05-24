package cn.whdreamblog.mockhelper.mock;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.whdreamblog.mockhelper.BaseActivity;
import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.data.MockRemote;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;
import cn.whdreamblog.mockhelper.util.MockUtils;
import cn.whdreamblog.mockhelper.util.ToastUtil;
import cn.whdreamblog.mockhelper.widget.AbsCommonCheckBoxDevAdapter;
import cn.whdreamblog.mockhelper.widget.CommonViewHolder;
import cn.whdreamblog.mockhelper.widget.MockTitleBar;


/**
 * @author wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/4/10
 * desc : 控制mockResponse的添加，修改
 */
@MockUtils.DisableDevToolsActivity
public class MockRecordActivity extends BaseActivity<MockRecordContract.Presenter>
        implements MockRecordContract.View {


    MockTitleBar mockTitleBar2;

    CheckBox cbAllUpdate;

    RecyclerView recycleMock;

    Button btnUpdateMock;

    Switch switchRecord;
    View view;
    private DevMockRecordAdapter adapter;

    public static void start(Activity activity) {
        if (activity == null) {
            return;
        }
        activity.startActivity(new Intent(activity, MockRecordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_activity_mock_record);
        mockTitleBar2 = findViewById(R.id.ehiTitleBar2);
        cbAllUpdate = findViewById(R.id.cb_all_update);
        recycleMock = findViewById(R.id.recycle_mock);
        btnUpdateMock = findViewById(R.id.btn_update_mock);
        switchRecord = findViewById(R.id.switch_record);
        view = findViewById(R.id.content);

        initViews();
        new MockRecordPresenter(this);
        presenter.start();
    }

    private void initViews() {
        switchRecord.setChecked(MockRemote.isOpenMockRecord());
        switchRecord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MockRemote.setOpenMockRecord(isChecked);
            }
        });
        btnUpdateMock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter == null|| adapter.selectList.isEmpty()){
                    return;
                }
               new AlertDialog.Builder(getBaseContext())
                       .setCancelable(false)
                       .setMessage("确定更新选中的接口")
                       .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               presenter.updateRecords(adapter.selectList);
                           }
                       }).create();
//                dialogFactory.showConfirmDialog(
//                        "确定更新选中的接口"
//                        , "确定"
//                        , new EhiConfirmDialog.ConfirmBtnListener() {
//                            @Override
//                            public void onClickLeftBtn(View v) {
//                                // do nothing
//                            }
//
//                            @Override
//                            public void onClickRightBtn(View v) {
//
//                            }
//                        }
//                        ,"updateRecords"
//                );
            }
        });
    }

    @Override
    public void showUpdateMockList(List<MocksResponse> mocksResponses) {
        adapter = new DevMockRecordAdapter(this, mocksResponses);
        recycleMock.setAdapter(adapter);
        LinearLayoutManager managerPlugin = new LinearLayoutManager(this);
        managerPlugin.setOrientation(GridLayoutManager.VERTICAL);
        recycleMock.setLayoutManager(managerPlugin);
    }

    @Override
    public void showDevPage() {
        ToastUtil.makeText("更新完毕");
        finish();
    }

    static class DevMockRecordAdapter extends AbsCommonCheckBoxDevAdapter {
        private Set<MocksResponse> selectList = new HashSet<>();
        public DevMockRecordAdapter(Context context, List<MocksResponse> dataSet) {
            super(context, dataSet);
        }

        @Override
        protected void convert(final CommonViewHolder holder, MocksResponse data, int position) {
            CheckBox cb = holder.itemView.findViewById(R.id.cb_mock);
            if (MockRemote.SelectDataSet.contains(data)) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setText(data.getDescription() + "Method:" + data.getMethod() + "\n url:" + data.getUrl()
            +"\nmode:"+data.getMode());
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MocksResponse temp = getDataSet().get(holder.getAdapterPosition());
                    buttonView.setTag(temp);
                    if (isChecked) {
                        selectList.add(temp);
                    } else {
                        selectList.remove(temp);
                    }
                }
            });
        }

    }
}
