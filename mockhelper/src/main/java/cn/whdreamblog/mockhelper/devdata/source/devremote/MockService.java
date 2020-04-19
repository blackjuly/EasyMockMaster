package cn.whdreamblog.mockhelper.devdata.source.devremote;

import java.util.List;

import cn.whdreamblog.mockhelper.devdata.model.EasyMockCommonResponse;
import cn.whdreamblog.mockhelper.devdata.model.EasyMockResponseList;
import cn.whdreamblog.mockhelper.devdata.model.LoginRequest;
import cn.whdreamblog.mockhelper.devdata.model.LoginResponse;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/11 17:53
 * desc :
 */
public interface MockService {


    @GET("api/mock")
    Single<EasyMockResponseList> getMockList(
            @Query("project_id") String project_id
            , @Query("page_size") String page_size
            , @Query("page_index") String page_index
            , @Query("keywords") String keywords);

    @POST("api/mock/update")
    Single<EasyMockCommonResponse<String>> updateRecord(@Body MocksResponse response);

    @POST("api/mock/create")
    Single<EasyMockCommonResponse<String>> addRecord(@Body MocksResponse response);

    @POST("api/u/login")
    Single<EasyMockCommonResponse<LoginResponse>> loginMock(@Body LoginRequest request);

    /**
     *
     * @return 测试连接是否畅通，是否可以直接连接mock
     */
    @GET("api/project?page_size=30&page_index=1&keywords=&type=&group=&filter_by_author=0")
    Single<EasyMockCommonResponse<List>> testConnection();
}
