package com.example.common.constant;

/**
 * @author ChayChan
 * @description: 记录常量的类
 * @date 2017/6/16  20:22
 */

public class Constant {
    /**已选中频道的json*/
    public static final String SELECTED_CHANNEL_JSON = "selectedChannelJson";
    /**w未选频道的json*/
    public static final String UNSELECTED_CHANNEL_JSON = "unselectChannelJson";

    /**频道对应的请求参数*/
    public static final String CHANNEL_CODE = "channelCode";
    public static final String IS_VIDEO_LIST = "isVideoList";

    public static final String ARTICLE_GENRE_VIDEO = "video";
    public static final String  ARTICLE_GENRE_AD = "ad";

    public static final String TAG_MOVIE = "video_movie";

    public static final String URL_VIDEO = "/video/urls/v/1/toutiao/mp4/%s?r=%s";

    /**获取评论列表每页的数目*/
    public static final int COMMENT_PAGE_SIZE = 20;

    public static final String DATA_SELECTED = "dataSelected";
    public static final String DATA_UNSELECTED = "dataUnselected";


    public static final String JSCON_ERROR_CODE = "10000";
    public static final String JSON_ERROR_MESSAGE = "服务端范湖数据解析错误";

    public static final String HTTP_ERROR_CODE = "20000";
    public static final String HTTP_ERROR_MESSAGE = "网络错误";

    public static final String SECURITY_ERROR_CODE = "30000";
    public static final String SECURITY_ERROR_MESSAGE = "权限错误";

    public static final String USER_NOT_REGISTER_ERROR = "1001";

    public static final String SOCKET_TIMEOUT_ERROR_CODE = "40000";
    public static final String SOCKET_TIMEOUT_ERROR_MESSAGE = "连接超时错误";

}
