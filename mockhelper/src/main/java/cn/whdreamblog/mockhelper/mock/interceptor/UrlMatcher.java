package cn.whdreamblog.mockhelper.mock.interceptor;

import cn.whdreamblog.mockhelper.data.model.MocksResponse;
import okhttp3.Request;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/19 20.51
 * desc : The class is used for match url ,and then when the url contains in mock url list,U can
 * get mock response (if you turn on Mock mode)
 * U can implement {@link UrlMatcher} replace url matcher rule
 */
public interface UrlMatcher {
    /**
     * 匹配 url示例 真实IP http://realip/mock/5e1845af11200024dc45886a/api/xxx-inventory
     * 匹配 url示例 mock http://192.168.99.101:7300/mock/5e1845af11200024dc45886a/api/xxx-inventory
     */
    UrlMatcher defaultMatcher = new SplitterUrlMatcher("/api/");

    /**
     * @param originalRequest 当前真实网络请求
     * @param bean            mock bean
     * @return true the request that u want replace by mock
     */
    boolean isUrlEquals(Request originalRequest, MocksResponse bean);

    /**
     *  返回分割 url的标识物
     * return the flag is used to split real url
     * @return return the flag is used to split real url
     */
    String splitter();
    /**
     * params u can see {@link #isUrlEquals(Request, MocksResponse) }
     *
     * @return true request method equals mockResponse
     */
    default boolean methodMatch(Request originalRequest, MocksResponse bean) {
        return originalRequest.method().equalsIgnoreCase(bean.getMethod());
    }

    /**
     * // TODO: 2020/5/23 后期支持匹配url模式
     * IP url 模式匹配
     */
    /*
    class IPUrlMatcher implements UrlMatcher {

        private String suffix = "";
        //ip 地址
        private final  String pattern = "(ht|f)tp(s?)\\\\:\\\\/\\\\/[0-9a-zA-Z]([-.\\\\w]*[0-9a-zA-Z])*(:\\\\d*)";
        private String realPattern = pattern + "/"+suffix;
        public IPUrlMatcher() {
        }

        public IPUrlMatcher(String suffix) {
            this.suffix = suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
        @Override
        public boolean isUrlEquals(Request originalRequest, MocksResponse bean) {
            if (!methodMatch(originalRequest, bean)) {
                return false;
            }

            String mockPattern = pattern +"?/mock/" + MockRemote.mockDataProjectId + "/"+suffix;
            String path = originalRequest.url().url().getPath().split(realPattern)[1];
            String beanUrl = bean.getUrl().split(mockPattern)[1];
            return path.equalsIgnoreCase(beanUrl);
        }

        @Override
        public String splitter() {
            return realPattern;
        }


    }
    */
    /**
     * 示例 ：
     *  mockurl www.mockip.com/api/login
     *  真实url www.xxxx.com/api/login
     *  调用 {@link SplitterUrlMatcher#SplitterUrlMatcher(String)} 传入 api/  该类可以获取到后面的
     *  login
     */
    class SplitterUrlMatcher implements UrlMatcher {
        private  String splitter;

        public SplitterUrlMatcher(String splitter) {
            this.splitter = splitter;
        }
        @Override
        public boolean isUrlEquals(Request originalRequest, MocksResponse bean) {
            if (!methodMatch(originalRequest, bean)) {
                return false;
            }
            String path = originalRequest.url().url().getPath().split(splitter)[1];
            String beanUrl = bean.getUrl().split(splitter)[1];
            return path.equalsIgnoreCase(beanUrl);
        }

        @Override
        public String splitter() {
            return this.splitter;
        }


    }

}
