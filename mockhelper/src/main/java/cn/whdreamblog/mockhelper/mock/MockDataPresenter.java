package cn.whdreamblog.mockhelper.mock;


import java.util.ArrayList;
import java.util.List;

import cn.whdreamblog.mockhelper.devdata.model.EasyMockResponseList;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/12 10:29
 * desc :
 */
public class MockDataPresenter extends BaseMockPresenter<MockDataContract.View> implements MockDataContract.Presenter {
    public MockDataPresenter(MockDataContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        subscribe(
                getMockListCompletable()
                        .doOnNext(new Consumer<EasyMockResponseList>() {
                            @Override
                            public void accept(EasyMockResponseList easyMockResponseList) throws Exception {
                                List<MocksResponse> responses = easyMockResponseList.getData().getMocks();
                                if (responses == null || responses.isEmpty()) {
                                    easyMockResponseList.getData().setMocks(new ArrayList<MocksResponse>());
                                    return;
                                }
                                String url = easyMockResponseList.getData().getMocks().get(0).getUrl();
                                MockRemote.updatePrefix(url);
                            }
                        }).map(new Function<EasyMockResponseList, List<MocksResponse>>() {
                    @Override
                    public List<MocksResponse> apply(EasyMockResponseList easyMockResponseList) throws Exception {
                        List<MocksResponse> responses = easyMockResponseList.getData().getMocks();
                        if ( responses == null||responses.isEmpty()){
                            return new ArrayList<MocksResponse>();
                        }else {
                            return responses;
                        }
                    }
                }).doOnNext(new Consumer<List<MocksResponse>>() {
                    @Override
                    public void accept(List<MocksResponse> mocksResponses) throws Exception {
                        List<String> searchList = new ArrayList<>(mocksResponses.size());
                        for (MocksResponse mocksResponse : mocksResponses){
                            String temp = mocksResponse.getUrl().split(MockRemote.API)[1];
                            searchList.add(temp+"method:"+mocksResponse.getMethod());
                        }
                        view.initSearchBar(searchList);
                    }
                })
                , new Consumer<List<MocksResponse>>() {
                    @Override
                    public void accept(List<MocksResponse> mockResponseList) throws Exception {
                        view.showMockList(mockResponseList);

                    }
                }
        );
    }
}
