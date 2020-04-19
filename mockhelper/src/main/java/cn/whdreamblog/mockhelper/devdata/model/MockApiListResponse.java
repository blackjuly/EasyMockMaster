package cn.whdreamblog.mockhelper.devdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/05 18:06
 * desc :
 */
public class MockApiListResponse {

    /**
     * data : {"id":164479,"name":"DriverMock","modules":[{"id":242465,"name":"driverpad","description":"司机mock数据测试","interfaces":[{"id":901585,"name":"违章列表","url":"/ticket/list","method":"POST"}]}]}
     */

    @SerializedName("data")
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 164479
         * name : DriverMock
         * modules : [{"id":242465,"name":"driverpad","description":"司机mock数据测试","interfaces":[{"id":901585,"name":"违章列表","url":"/ticket/list","method":"POST"}]}]
         */

        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("modules")
        private List<ModulesBean> modules;

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

        public List<ModulesBean> getModules() {
            return modules;
        }

        public void setModules(List<ModulesBean> modules) {
            this.modules = modules;
        }

        public static class ModulesBean {
            /**
             * id : 242465
             * name : driverpad
             * description : 司机mock数据测试
             * interfaces : [{"id":901585,"name":"违章列表","url":"/ticket/list","method":"POST"}]
             */

            @SerializedName("id")
            private int id;
            @SerializedName("name")
            private String name;
            @SerializedName("description")
            private String description;
            @SerializedName("interfaces")
            private List<InterfacesBean> interfaces;

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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<InterfacesBean> getInterfaces() {
                return interfaces;
            }

            public void setInterfaces(List<InterfacesBean> interfaces) {
                this.interfaces = interfaces;
            }


        }
    }
}
