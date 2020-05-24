package cn.whdreamblog.mockhelper.data.model;

/**
 * 定义登陆easymock的方式
 */
public class LoginRequest {
    public LoginRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private String name;

    private String password;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
}
