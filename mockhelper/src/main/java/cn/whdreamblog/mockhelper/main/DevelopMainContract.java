package cn.whdreamblog.mockhelper.main;


import cn.whdreamblog.mockhelper.BasePresenter;
import cn.whdreamblog.mockhelper.BaseView;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/05/03 15:55
 * desc :
 */
public interface DevelopMainContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        /**
         * 加载初始数据
         */
        void start();
    }
}