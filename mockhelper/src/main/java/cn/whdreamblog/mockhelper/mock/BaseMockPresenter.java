package cn.whdreamblog.mockhelper.mock;


import cn.whdreamblog.mockhelper.BasePresenterImp;
import cn.whdreamblog.mockhelper.BaseView;
import cn.whdreamblog.mockhelper.data.MockRemote;
import cn.whdreamblog.mockhelper.data.model.EasyMockResponseList;
import io.reactivex.Observable;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/12 17:24
 * desc :
 */
public class BaseMockPresenter<T extends BaseView> extends BasePresenterImp<T> {
    public BaseMockPresenter(T view) {
        super(view);
    }
    public Observable<EasyMockResponseList> getMockListCompletable(){
        return mockService.getMockList(
                MockRemote.mockDataProjectId
                , MockRemote.page_size
                , MockRemote.page_index
                ,""
        ).toObservable();
    }
}
