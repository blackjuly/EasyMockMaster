package cn.whdreamblog.mockhelper.mock;



import cn.whdreamblog.mockhelper.BasePresenterImp;
import cn.whdreamblog.mockhelper.BaseView;
import cn.whdreamblog.mockhelper.devdata.model.EasyMockResponseList;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
import io.reactivex.Observable;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/12 17:24
 * desc :
 */
public class BaseMockPresenter<T extends BaseView> extends BasePresenterImp<T> {
    public BaseMockPresenter(T view) {
        super(view);
    }
    public Observable<EasyMockResponseList> getMockListCompletable(){
        return MockRemote.mockService.getMockList(
                MockRemote.mockDataProjectId
                , MockRemote.page_size
                , MockRemote.page_index
                ,""
        ).toObservable();
    }
}
