package cn.whdreamblog.mockhelper.devdata.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/05 18:40
 * desc : 用于封装需要mock的request的特征数据
 */
public class InterfacesBean {
    /**
     * id : 901585
     * name : 违章列表
     * url : /ticket/list
     * method : POST
     */

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("method")
    private String method;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterfacesBean that = (InterfacesBean) o;

        if (!url.equals(that.url)) return false;
        return method.equals(that.method);
    }

    @Override
    public int hashCode() {
        return url.hashCode()+ method.toUpperCase().hashCode();
    }
}
