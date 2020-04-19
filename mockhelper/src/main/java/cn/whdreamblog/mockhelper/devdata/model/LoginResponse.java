package cn.whdreamblog.mockhelper.devdata.model;
/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/2/29 17:04
 * desc : The class is used to receiving login`s response
 */
public class LoginResponse {
    private String _id;

    private String name;

    private String nick_name;

    private String head_img;

    private String token;

    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setNick_name(String nick_name){
        this.nick_name = nick_name;
    }
    public String getNick_name(){
        return this.nick_name;
    }
    public void setHead_img(String head_img){
        this.head_img = head_img;
    }
    public String getHead_img(){
        return this.head_img;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return this.token;
    }

    public String getAuth(){
        return "Bearer "+token;
    }

}
