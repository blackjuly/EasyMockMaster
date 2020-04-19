package cn.whdreamblog.mockhelper.devdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/10 13:30
 * desc :
 */
public class EasyMockResponseList {

    /**
     * code : 200
     * success : true
     * message : success
     * data : {"project":{"user":{"_id":"5caafcf0ad170a13bc0f137a","name":"hao.wang@1hai.cn","nick_name":"1554709744476","head_img":"//img.souche.com/20161230/png/fd9f8aecab317e177655049a49b64d02.png","create_at":"2019-04-10T05:28:41.277Z"},"_id":"5caafcf0ad170a13bc0f137b","name":"演示项目","url":"/example","description":"已创建多种 Mock 类型，只需点击预览便可查看效果。亦可编辑，也可删除。","swagger_url":"","members":[],"extend":{"_id":"5caafcf0ad170a13bc0f137c","is_workbench":false}},"mocks":[{"_id":"5caafcf0ad170a13bc0f1381","url":"/mock","method":"get","description":"带随机数据的 mock","mode":"{\"success\":true,\"data\":{\"projects|3-10\":[{\"name\":\"演示用\",\"url\":\"@url\",\"email\":\"@email\",\"address\":\"@county(true)\",\"string|1-10\":\"★\",\"number|1-100\":100,\"boolean|1-2\":true,\"object|2\":{\"310000\":\"上海市\",\"320000\":\"江苏省\",\"330000\":\"浙江省\"}}]}}"},{"_id":"5caafcf0ad170a13bc0f1382","url":"/","method":"get","description":"自定义响应的 mock","mode":"{success: true, data: {name: \"hh\"}, _res: {status: 400, data: {success: false}, cookies: {test: \"true\"}, headers: {Power: \"easy-mock\"}}}"},{"_id":"5caafcf0ad170a13bc0f137e","url":"/query","method":"get","description":"根据请求参数返回指定数据，试试在 url 上加 ?name={任意值}","mode":"{ success :true, data: { default: \"hah\", _req: function({ _req }) { return _req }, name: function({ _req }) { return _req.query.name || this.default }}}"},{"_id":"5caafcf0ad170a13bc0f137f","url":"/restful/:id/list","method":"get","description":"支持 restful 的 mock，替换 id 试试","mode":"{\"success\":true,\"data\":[{\"user\":{\"name\":\"演示用\"}}]}"},{"_id":"5caafcf0ad170a13bc0f1380","url":"/proxy","method":"get","description":"支持接口代理的 mock，试试在 url 上加 ?s={数字}","mode":"https://api.m.sohu.com/autonews/pool/?n=%E6%96%B0%E9%97%BB&s=1"},{"_id":"5caafcf0ad170a13bc0f137d","url":"/upload","method":"post","description":"这只是一个响应 post 接口返回随机数据的例子","mode":"{ data: { img: function({ _req, Mock }) { return _req.body.fileName + \"_\" + Mock.mock(\"@image\") }}}"}]}
     */

    @SerializedName("code")
    private int code;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataResponse data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }

    public static class DataResponse {
        /**
         * project : {"user":{"_id":"5caafcf0ad170a13bc0f137a","name":"hao.wang@1hai.cn","nick_name":"1554709744476","head_img":"//img.souche.com/20161230/png/fd9f8aecab317e177655049a49b64d02.png","create_at":"2019-04-10T05:28:41.277Z"},"_id":"5caafcf0ad170a13bc0f137b","name":"演示项目","url":"/example","description":"已创建多种 Mock 类型，只需点击预览便可查看效果。亦可编辑，也可删除。","swagger_url":"","members":[],"extend":{"_id":"5caafcf0ad170a13bc0f137c","is_workbench":false}}
         * mocks : [{"_id":"5caafcf0ad170a13bc0f1381","url":"/mock","method":"get","description":"带随机数据的 mock","mode":"{\"success\":true,\"data\":{\"projects|3-10\":[{\"name\":\"演示用\",\"url\":\"@url\",\"email\":\"@email\",\"address\":\"@county(true)\",\"string|1-10\":\"★\",\"number|1-100\":100,\"boolean|1-2\":true,\"object|2\":{\"310000\":\"上海市\",\"320000\":\"江苏省\",\"330000\":\"浙江省\"}}]}}"},{"_id":"5caafcf0ad170a13bc0f1382","url":"/","method":"get","description":"自定义响应的 mock","mode":"{success: true, data: {name: \"hh\"}, _res: {status: 400, data: {success: false}, cookies: {test: \"true\"}, headers: {Power: \"easy-mock\"}}}"},{"_id":"5caafcf0ad170a13bc0f137e","url":"/query","method":"get","description":"根据请求参数返回指定数据，试试在 url 上加 ?name={任意值}","mode":"{ success :true, data: { default: \"hah\", _req: function({ _req }) { return _req }, name: function({ _req }) { return _req.query.name || this.default }}}"},{"_id":"5caafcf0ad170a13bc0f137f","url":"/restful/:id/list","method":"get","description":"支持 restful 的 mock，替换 id 试试","mode":"{\"success\":true,\"data\":[{\"user\":{\"name\":\"演示用\"}}]}"},{"_id":"5caafcf0ad170a13bc0f1380","url":"/proxy","method":"get","description":"支持接口代理的 mock，试试在 url 上加 ?s={数字}","mode":"https://api.m.sohu.com/autonews/pool/?n=%E6%96%B0%E9%97%BB&s=1"},{"_id":"5caafcf0ad170a13bc0f137d","url":"/upload","method":"post","description":"这只是一个响应 post 接口返回随机数据的例子","mode":"{ data: { img: function({ _req, Mock }) { return _req.body.fileName + \"_\" + Mock.mock(\"@image\") }}}"}]
         */

        @SerializedName("project")
        private ProjectResponse project;
        @SerializedName("mocks")
        private List<MocksResponse> mocks;

        public ProjectResponse getProject() {
            return project;
        }

        public void setProject(ProjectResponse project) {
            this.project = project;
        }

        public List<MocksResponse> getMocks() {
            return mocks;
        }

        public void setMocks(List<MocksResponse> mocks) {
            this.mocks = mocks;
        }

        public static class ProjectResponse {
            /**
             * user : {"_id":"5caafcf0ad170a13bc0f137a","name":"hao.wang@1hai.cn","nick_name":"1554709744476","head_img":"//img.souche.com/20161230/png/fd9f8aecab317e177655049a49b64d02.png","create_at":"2019-04-10T05:28:41.277Z"}
             * _id : 5caafcf0ad170a13bc0f137b
             * name : 演示项目
             * url : /example
             * description : 已创建多种 Mock 类型，只需点击预览便可查看效果。亦可编辑，也可删除。
             * swagger_url :
             * members : []
             * extend : {"_id":"5caafcf0ad170a13bc0f137c","is_workbench":false}
             */

            @SerializedName("user")
            private UserResponse user;
            @SerializedName("_id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("url")
            private String url;
            @SerializedName("description")
            private String description;
            @SerializedName("swagger_url")
            private String swaggerUrl;
            @SerializedName("extend")
            private ExtendResponse extend;
            @SerializedName("members")
            private List<?> members;

            public UserResponse getUser() {
                return user;
            }

            public void setUser(UserResponse user) {
                this.user = user;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getSwaggerUrl() {
                return swaggerUrl;
            }

            public void setSwaggerUrl(String swaggerUrl) {
                this.swaggerUrl = swaggerUrl;
            }

            public ExtendResponse getExtend() {
                return extend;
            }

            public void setExtend(ExtendResponse extend) {
                this.extend = extend;
            }

            public List<?> getMembers() {
                return members;
            }

            public void setMembers(List<?> members) {
                this.members = members;
            }

            public static class UserResponse {
                /**
                 * _id : 5caafcf0ad170a13bc0f137a
                 * name : hao.wang@1hai.cn
                 * nick_name : 1554709744476
                 * head_img : //img.souche.com/20161230/png/fd9f8aecab317e177655049a49b64d02.png
                 * create_at : 2019-04-10T05:28:41.277Z
                 */

                @SerializedName("_id")
                private String id;
                @SerializedName("name")
                private String name;
                @SerializedName("nick_name")
                private String nickName;
                @SerializedName("head_img")
                private String headImg;
                @SerializedName("create_at")
                private String createAt;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getHeadImg() {
                    return headImg;
                }

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
                }

                public String getCreateAt() {
                    return createAt;
                }

                public void setCreateAt(String createAt) {
                    this.createAt = createAt;
                }
            }

            public static class ExtendResponse {
                /**
                 * _id : 5caafcf0ad170a13bc0f137c
                 * is_workbench : false
                 */

                @SerializedName("_id")
                private String id;
                @SerializedName("is_workbench")
                private boolean isWorkbench;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIsWorkbench() {
                    return isWorkbench;
                }

                public void setIsWorkbench(boolean isWorkbench) {
                    this.isWorkbench = isWorkbench;
                }
            }
        }

    }
}
