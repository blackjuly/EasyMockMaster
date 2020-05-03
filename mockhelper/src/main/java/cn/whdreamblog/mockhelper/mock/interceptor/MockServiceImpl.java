package cn.whdreamblog.mockhelper.mock.interceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * @author  wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/06/14 16:46
 * desc : 设定拦截器 mock拦截器的顺序
 */

public class MockServiceImpl implements IMockService {
    @Override
    public List<Interceptor> getMockInterceptors() {
        List<Interceptor> list = new ArrayList<>();
        list.add(new MockDataInterceptor());
        list.add(new MockRecordInterceptor());
        return list;
    }
}
