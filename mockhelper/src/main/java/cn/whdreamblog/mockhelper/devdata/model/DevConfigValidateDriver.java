package cn.whdreamblog.mockhelper.devdata.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/22 15:57
 * desc : 司机激活
 */
public class DevConfigValidateDriver {

    /**
     * operatorID : 11968
     * drivers : ["15533","11052","11071"]
     * default :
     * url :
     */
    public static final String DEMO = "demo";
    public static final String DEV = "dev";
    public static final String RELEASE = "release";
    @SerializedName("operatorID")
    private String operatorID;
    @SerializedName("url")
    private String url;
    @SerializedName("drivers")
    private List<String> drivers;

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<String> drivers) {
        this.drivers = drivers;
    }

    /**
     *
     * @return 判断 url是不是空的
     */
    public boolean isUrlAvailable(){
        return !TextUtils.isEmpty(url);
    }
}
