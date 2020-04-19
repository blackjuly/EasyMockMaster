package cn.whdreamblog.mockhelper.widget;

import android.content.Context;

import java.util.List;

import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;


/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/10 16:19
 * desc : 用于MocksResponse列表的增删改查
 */
public abstract class AbsCommonCheckBoxDevAdapter extends CommonRecyclerAdapter<MocksResponse> {

    public AbsCommonCheckBoxDevAdapter(Context context, List<MocksResponse> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected int getLayoutResId(MocksResponse data, int position) {
        return R.layout.module_develop_item_mock;
    }


}
