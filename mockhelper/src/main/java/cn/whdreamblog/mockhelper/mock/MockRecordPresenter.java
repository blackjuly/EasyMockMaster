package cn.whdreamblog.mockhelper.mock;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.whdreamblog.mockhelper.devdata.model.EasyMockCommonResponse;
import cn.whdreamblog.mockhelper.devdata.model.EasyMockResponseList;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
import cn.whdreamblog.mockhelper.util.ApiIOException;
import cn.whdreamblog.mockhelper.util.MyLogger;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/12 10:57
 * desc :
 */
public class MockRecordPresenter extends BaseMockPresenter<MockRecordContract.View> implements MockRecordContract.Presenter {
    private MocksResponse selectResponse;
    public MockRecordPresenter(MockRecordContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        subscribe(
                getMockListCompletable()
                        .map(new Function<EasyMockResponseList, List<MocksResponse>>() {
                            @Override
                            public List<MocksResponse> apply(EasyMockResponseList easyMockResponseList) throws Exception {
                                List<MocksResponse> mocks = easyMockResponseList.getData().getMocks();
                                if (mocks == null||mocks.isEmpty()){
                                    return new ArrayList<>();
                                }
                                MockRemote.updatePrefix(mocks.get(0).getUrl());
                                for (MocksResponse mocksResponse: MockRemote.getDEQUE()){
                                    if (mocks.contains(mocksResponse)){
                                        int index = mocks.indexOf(mocksResponse);
                                        mocksResponse.setId(mocks.get(index).getId());
                                        mocksResponse.setUrl(mocks.get(index).getUrl());
                                        mocksResponse.setDescription(mocks.get(index).getDescription());
                                        MyLogger.getLogger().dByAndroidLog("Dev"+mocksResponse);
                                        MyLogger.getLogger().dByAndroidLog(mocks.get(index).toString());
                                    }
                                }
                                return new ArrayList<>(MockRemote.getDEQUE());
                            }
                        }), new Consumer<List<MocksResponse>>() {
                    @Override
                    public void accept(List<MocksResponse> mocksResponses) throws Exception {
                        view.showUpdateMockList(mocksResponses);
                    }
                }
        );
    }

    @Override
    public void updateRecords(Set<MocksResponse> selectList) {
        if (!verifyDataSet(selectList)){
            view.tip("数据有不包含id,method,mode,url或者为空的");
            return;
        }
        subscribe(
                Observable.fromIterable(
                        selectList
                ).flatMap(new Function<MocksResponse, Observable<EasyMockCommonResponse<String>>>() {
                    @Override
                    public Observable<EasyMockCommonResponse<String>> apply(MocksResponse response) throws Exception {
                        selectResponse = response;
                        return MockRemote.mockService.updateRecord(response).toObservable();
                    }
                }).doOnNext(new Consumer<EasyMockCommonResponse<String>>() {
                    @Override
                    public void accept(EasyMockCommonResponse<String> easyMockResponseList) throws Exception {
                        if (!easyMockResponseList.isSuccess()){
                            throw new ApiIOException("message:"+easyMockResponseList.getMessage()+"\n data:"+easyMockResponseList.getData());
                        }
                    }
                })
                        .observeOn(schedulerProvider.ui())
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                view.showDevPage();
                            }
                        })
                , new Consumer<EasyMockCommonResponse<String>>() {
                    @Override
                    public void accept(EasyMockCommonResponse<String> easyMockResponseList) throws Exception {
                        if (selectResponse != null){
                            MockRemote.getDEQUE().remove(selectResponse);
                        }
                    }
                }
        );
    }

    private boolean verifyDataSet(Set<MocksResponse> selectList) {
        for (MocksResponse response:selectList){
            if (response == null){
                return false;
            }
            if (TextUtils.isEmpty(response.getId())){
                return false;
            }
            if (TextUtils.isEmpty(response.getMethod())){
                return false;
            }
            if (TextUtils.isEmpty(response.getMode())){
                return false;
            }
            if (TextUtils.isEmpty(response.getUrl())){
                return false;
            }
        }
        return true;
    }

}
