package com.example.net.activity_bean;

public class VideoBean {

    private String url = "http://ali.cdn.kaiyanapp.com/d76c7e6ba52fc71a360c9069d644a6c8_1280x720.mp4?auth_key=1600168897-0-0-70b2f249d389965c6ee1e44aef85c539";
    private String urlTiele;
    private String userPic;
    private String userName;
    private int commentCount;

    public VideoBean(String url, String urlTiele, String userPic, String userName, int commentCount) {
        this.url = url;
        this.urlTiele = urlTiele;
        this.userPic = userPic;
        this.userName = userName;
        this.commentCount = commentCount;
    }

    public String getUrlTiele() {
        return urlTiele;
    }

    public void setUrlTiele(String urlTiele) {
        this.urlTiele = urlTiele;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


}
