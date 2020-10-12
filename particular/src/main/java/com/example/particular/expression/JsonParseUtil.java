package com.example.particular.expression;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParseUtil {
    public static List<EmojiEntity> parseEmojiList(String json) {
        List<EmojiEntity> emojiEntityList = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = jsonObject.optJSONArray("emoji_list");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                if (jsonObject1 != null) {
                    EmojiEntity mEmojiEntity = new EmojiEntity();
                    mEmojiEntity.setName(jsonObject1.optString("name", ""));
                    mEmojiEntity.setUnicode(jsonObject1.optInt("unicode", 0));
                    emojiEntityList.add(mEmojiEntity);
                }
            }
        }
        return emojiEntityList;
    }
}
