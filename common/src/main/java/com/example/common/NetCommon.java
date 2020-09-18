package com.example.common;

public class NetCommon {

    public static Boolean isBackHome = false;

    public static final String JSON_ERROR_CODE = "10000";
    public static final String JSON_ERROR_MESSAGE = "服务端范湖数据解析错误";

    public static final String HTTP_ERROR_CODE = "20000";
    public static final String HTTP_ERROR_MESSAGE = "网络错误";

    public static final String SECURITY_ERROR_CODE = "30000";
    public static final String SECURITY_ERROR_MESSAGE = "权限错误";

    public static final String USER_NOT_REGISTER_ERROR = "1001";

    public static final String SOCKET_TIMEOUT_ERROR_CODE = "40000";
    public static final String SOCKET_TIMEOUT_ERROR_MESSAGE = "连接超时错误";

    public static final String PLAYER_VIDEO_URL = "videoUrl";
    public static final String PLAYER_VIDEO_LIST = "videoList";
    public static final String PLAYER_VIDEO_POSITION = "position";
    public static final String BASE_URL = "http://is.snssdk.com/";
    public static final String GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752";

}
