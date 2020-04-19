package cn.whdreamblog.mockhelper;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 18:30
 * desc : base class for presenter
 */
public interface BasePresenter {

    /**
     * ps:目前在用 rxLifeCycle,当如果发生问题，可能需要全部换回自己关闭rxJava
     * 取消注册
     */
    void unSubscribe();
}
