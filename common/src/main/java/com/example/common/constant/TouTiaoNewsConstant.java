package com.example.common.constant;

public class TouTiaoNewsConstant {

    //定义 多久 时间 进行刷新数据
    public static final long REFRESHTIME = 1000 * 60 * 10;

    //SharedPreferences管理的key值
    //用户名
    public static final String SP_USERNAME = "username";
    //用户密码
    public static final String SP_PASSWORD = "password";
    //token
    public static final String SP_TOKEN = "token";
    //是否登录
    public static final String SP_ISLOGIN = "isLogin";

    //储存上一次的时间戳
    public static final String LAST_TIME = "last_time";
    //储存这一次的时间戳
    public static final String CURRENT_TIME = "current_time";

    //fragment间传值的argument值
    public static final String ARGUMENT_CHANNEL = "argument_channel";
    //是否是视频页面
    public static final String ISVIDEO = "isVideo";

    //第一次进行网络请求的时间戳
    public static final String ONETIME = "oneTime";
    //用户可见时的时间戳
    public static final String USERLOOKTIME = "userLookTime";
    //用户是否是第一次可见此Fragment
    public static final String ISLOOK = "isLook";
    //隔了一段时间后请求网络数据的boolean
    public static final String ISTWODATA = "isTwoData";

    //webView的加载地址
    public static final String WEBVIEW_URL = "webViewUrl";
    //webView的作者标题
    public static final String WEBVIEW_TITLE = "webView_Title";
    //webView的作者头像
    public static final String WEBVIEW_AVATAR = "webView_Avatar";

    //是否是编辑状态
    public static final String ISCOMPILE = "isCompile";
    //是否是第一次在内存中储存选择的频道数据
    public static final String ISONESELECTDATA = "isOneSelectData";
    //是否是第一次启动app时的状态
    public static final String ISSTARTAPP = "isStartApp";
    //是否是第一次在内存中储存未选择的频道数据
    public static final String ISONEUNSELECTDATA = "isOneUnSelectData";
    //储存的选择的频道的数据源
    public static final String SELECTDATA = "selectData";
    //储存的未选择的频道的数据源
    public static final String UNSELECTDATA = "unSelectData";

    //编辑页面储存的未选择频道fragment的数据个数
    public static final String UNSELECTDATASIZE = "unSelectDataSize";
    //主页面完成操作后的未选择的fragment数据个数
    public static final String MAINUNSELECTDATASIZE = "mainUnSelectDataSize";

    //通过onNewIntent传参数回到主页面跳转的fragment下标
    public static final String MAININDEX = "mainIndex";

}
