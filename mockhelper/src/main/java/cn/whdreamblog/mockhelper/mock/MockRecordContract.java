package cn.whdreamblog.mockhelper.mock;



import java.util.List;
import java.util.Set;

import cn.whdreamblog.mockhelper.BasePresenter;
import cn.whdreamblog.mockhelper.BaseViewSupport;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/12 10:56
 * desc :
 */
interface MockRecordContract {
    interface View extends BaseViewSupport<Presenter> {

        void showUpdateMockList(List<MocksResponse> mocksResponses);

        void showDevPage();
    }

    interface Presenter extends BasePresenter {
        /**
         * 加载初始数据
         */
        void start();

        /**
         * 更新json到服务器
         */
        void updateRecords(Set<MocksResponse> selectList);
    }
}