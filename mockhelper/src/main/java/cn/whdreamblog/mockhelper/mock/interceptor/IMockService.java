package cn.whdreamblog.mockhelper.mock.interceptor;

import java.util.List;

import okhttp3.Interceptor;

/**
 * @author 28476 wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/06/14 16:46
 * desc :
 */
public interface IMockService {
    /**
     * @return 获取mock拦截器
     */
    List<Interceptor> getMockInterceptors();
}
