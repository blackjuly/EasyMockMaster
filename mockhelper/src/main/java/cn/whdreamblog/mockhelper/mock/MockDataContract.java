package cn.whdreamblog.mockhelper.mock;


import java.util.List;

import cn.whdreamblog.mockhelper.BasePresenter;
import cn.whdreamblog.mockhelper.BaseView;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/12 10:30
 * desc :
 */
public interface MockDataContract {
    interface View extends BaseView<Presenter> {

        void showMockList(List<MocksResponse> MockResponseList);

        void initSearchBar(List<String> searchList);
    }

    interface Presenter extends BasePresenter {
        /**
         * 加载初始数据
         */
        void start();
    }
}