package com.example.net.mode;

import java.util.List;

public class VideoPathResponse {
    public String status;
    public List<VideoEntity> video;

    public class VideoEntity{
        public String url;
    }
}
