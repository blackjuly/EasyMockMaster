package cn.whdreamblog.mockhelper.data.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.whdreamblog.mockhelper.EasyMockHelperApplication;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/10 13:33
 * desc : easyMock列表响应数据
 */
public class MocksResponse implements Serializable {
    /**
     * _id : 5caafcf0ad170a13bc0f1381 更新接口，和获取接口，使用的id值是是一样的，但是字段使用的不同
     * url : /mock
     * method : get
     * description : 带随机数据的 mock
     * mode : {"success":true,"data":{"projects|3-10":[{"name":"演示用","url":"@url","email":"@email","address":"@county(true)","string|1-10":"★","number|1-100":100,"boolean|1-2":true,"object|2":{"310000":"上海市","320000":"江苏省","330000":"浙江省"}}]}}
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("id")
    private String requestId;
    @SerializedName("project_id")
    private String projectId;
    @SerializedName("url")
    private String url;
    @SerializedName("method")
    private String method;
    @SerializedName("description")
    private String description;
    @SerializedName("mode")
    private String mode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.requestId = id;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method.toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MocksResponse that = (MocksResponse) o;
        if (!getUrlWithoutSplitter().equalsIgnoreCase(that.getUrlWithoutSplitter())) {
            return false;
        }
        return method.equalsIgnoreCase(that.method);
    }


    @Override
    public int hashCode() {
        return url.hashCode()+ method.toUpperCase().hashCode();
    }
    public String getUrlWithoutSplitter(){
        String s = EasyMockHelperApplication.get().getUrlMatcher().splitter();
        if (TextUtils.isEmpty(s) || TextUtils.isEmpty(url)){
            return url;
        }
        if (!url.contains(s)){
            return url;
        }
        if (url.endsWith(s)){
            throw new RuntimeException("url is invalid! :"+url);
        }
        final String placeHolder = "holder";
        return placeHolder.concat(url).split(s)[1];
    }
    @Override
    public String toString() {
        return "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
