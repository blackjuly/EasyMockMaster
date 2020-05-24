package cn.whdreamblog.mockhelper.widget;

import android.content.Context;

import java.util.List;

import cn.whdreamblog.mockhelper.R;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;


/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
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
        return R.layout.mock_item_mock;
    }


}
