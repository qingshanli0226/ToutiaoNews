package com.example.net.activity_bean;

public class UpdateBean {
    /**
     * code : 200
     * msg : 请求成功
     * result : {"version":"1.2","apkUrl":"http://49.233.93.155:8080/atguigu/apk/P2PInvest/app_new.apk","desc":"解决一些bug, 优化网络请求!"}
     */

    private int code;
    private String msg;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * version : 1.2
         * apkUrl : http://49.233.93.155:8080/atguigu/apk/P2PInvest/app_new.apk
         * desc : 解决一些bug, 优化网络请求!
         */

        private String version;
        private String apkUrl;
        private String desc;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
