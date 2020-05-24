package cn.whdreamblog.mockhelper.main;


import java.util.List;

import cn.whdreamblog.mockhelper.BasePresenterImp;
import cn.whdreamblog.mockhelper.data.MockRemote;
import cn.whdreamblog.mockhelper.data.model.EasyMockCommonResponse;
import cn.whdreamblog.mockhelper.data.model.LoginRequest;
import cn.whdreamblog.mockhelper.data.model.LoginResponse;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/05/03 15:56
 * desc : 开发者工具获取验证方式
 */
public class DevelopMainPresenter extends BasePresenterImp<DevelopMainContract.View> implements DevelopMainContract.Presenter {
    public DevelopMainPresenter(DevelopMainContract.View view) {
        super(view);
    }

    @Override
    public void start() {

        //利用mock任意接口测试mock令牌是否过期
        subscribe(mockService.testConnection().onErrorResumeNext(new Function<Throwable, SingleSource<? extends EasyMockCommonResponse<List>>>() {
                    @Override
                    public SingleSource<? extends EasyMockCommonResponse<List>> apply(final Throwable throwable) throws Exception {
                        //当mock令牌过期，则使用mock登陆密码和用户名重新获取令牌
                        return mockService.loginMock(new LoginRequest(MockRemote.userId, MockRemote.password)).map(
                                new Function<EasyMockCommonResponse<LoginResponse>, EasyMockCommonResponse<List>>() {
                                    @Override
                                    public EasyMockCommonResponse<List> apply(EasyMockCommonResponse<LoginResponse> loginResponseEasyMockCommonResponse) throws Exception {
                                        myLogger.eByAndroidLog("mock auth need to update.....");
                                        myLogger.e(throwable);
                                        //更新验证令牌
                                        MockRemote.setAuth(loginResponseEasyMockCommonResponse.getData().getAuth());
                                        return new EasyMockCommonResponse<>();
                                    }
                                }
                        );
                    }
                }).toObservable()
                , new Consumer<EasyMockCommonResponse<List>>() {
                    @Override
                    public void accept(EasyMockCommonResponse<List> stringEasyMockCommonResponse) throws Exception {
                        myLogger.d("mock auth update success!!");
                    }
                }
        );
    }
}
