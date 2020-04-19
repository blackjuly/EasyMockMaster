package cn.whdreamblog.mockhelper.devdata.source.devremote;


import cn.whdreamblog.mockhelper.devdata.model.DevConfigValidateDriver;
import cn.whdreamblog.mockhelper.devdata.model.DevConfigValidateMock;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/22 16:09
 * desc : 用于管理 司机相关管理配置的接口
 */
public interface DevConfigApi {
    /**
     * 配置激活司机 接口
     * @param env 环境
     * @return 配置类
     */
    @GET("device/validate/drivers")
    Single<DevConfigValidateDriver> getValidateConfig(@Query("env") String env);
    @GET("mock/validate")
    Single<DevConfigValidateMock> getValidateMockAuth();
}
