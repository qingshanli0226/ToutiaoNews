package com.example.common.mode;

/**
 * @author ChayChan
 * @description: 用户实体类
 * @date 2017/7/9  10:43
 */

public class UserEntity {
    /**
     * verified_content :
     * avatar_url : http://p3.pstatp.com/thumb/216b000e0abb3ee9cb91
     * user_id : 59834611934
     * name : 电竞手游君
     * follower_count : 0
     * follow : false
     * user_auth_info :
     * user_verified : false
     * description : 游戏 资讯 游戏攻略 你要的这里都有，来这里就对了。
     */

    public String verified_content;
    public String avatar_url;
    public long user_id;
    public String name;
    public int follower_count;
    public boolean follow;
    public String user_auth_info;
    public boolean user_verified;
    public String description;

    public String getVerified_content() {
        return verified_content;
    }

    public void setVerified_content(String verified_content) {
        this.verified_content = verified_content;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public String getUser_auth_info() {
        return user_auth_info;
    }

    public void setUser_auth_info(String user_auth_info) {
        this.user_auth_info = user_auth_info;
    }

    public boolean isUser_verified() {
        return user_verified;
    }

    public void setUser_verified(boolean user_verified) {
        this.user_verified = user_verified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
