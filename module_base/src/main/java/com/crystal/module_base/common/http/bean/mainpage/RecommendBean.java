package com.crystal.module_base.common.http.bean.mainpage;

import com.crystal.module_base.common.http.bean.common.Author;
import com.crystal.module_base.common.http.bean.common.Consumption;
import com.crystal.module_base.common.http.bean.common.Content;
import com.crystal.module_base.common.http.bean.common.Cover;
import com.crystal.module_base.common.http.bean.common.Discovery;
import com.crystal.module_base.common.http.bean.common.Label;
import com.crystal.module_base.common.http.bean.common.Provider;
import com.crystal.module_base.common.http.bean.common.Tag;
import com.crystal.module_base.common.http.bean.common.WebUrl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 9/29/2020 2:31 PM
 * packageName：com.crystal.module_base.common.http.bean
 * 描述：首页-推荐
 */

public class RecommendBean {
    public int count;
    public int total;
    public String nextPageUrl;
    public boolean adExist;
    public List<Item> itemList;

    public static class Item {
        public String type;
        public Data data;
        //public Object trackingData;
        public Object tag;
        public int id;
        public int adIndex;
    }

    public static class Data {
        @Nullable
        public String actionUrl;
        public boolean ad;
        @NotNull
        public Object adTrack;
        @NotNull
        public Author author;
        @NotNull
        public String backgroundImage;
        @NotNull
        public Object brandWebsiteInfo;
        @NotNull
        public Object campaign;
        @NotNull
        public String category;
        public boolean collected;
        @NotNull
        public Consumption consumption;
        @NotNull
        public Content content;
        public int count;
        @NotNull
        public Cover cover;
        @NotNull
        public String dataType;
        public long date;
        @NotNull
        public String description;
        @NotNull
        public String descriptionEditor;
        @NotNull
        public Object descriptionPgc;
        public int duration;
        public boolean expert;
        @NotNull
        public Object favoriteAdTrack;
        @Nullable
        public Author.Follow follow;
        @NotNull
        public Object footer;
        public boolean haveReward;
        @NotNull
        public Header header;
        @NotNull
        public String icon;
        @NotNull
        public String iconType;
        public long id;
        public int idx;
        public boolean ifLimitVideo;
        public boolean ifNewest;
        public boolean ifPgc;
        public boolean ifShowNotificationIcon;
        @NotNull
        public String image;
        @NotNull
        public List<ItemX> itemList;
        @Nullable
        public Label label;
        @NotNull
        public List<Label> labelList;
        @NotNull
        public Object lastViewTime;
        @NotNull
        public String library;
        public boolean medalIcon;
        @NotNull
        public Object newestEndTime;
        @NotNull
        public List playInfo;
        @NotNull
        public String playUrl;
        public boolean played;
        @NotNull
        public Object playlists;
        @NotNull
        public Object promotion;
        @NotNull
        public Provider provider;
        public boolean reallyCollected;
        @NotNull
        public String refreshUrl;
        public long releaseTime;
        @NotNull
        public Object remark;
        @NotNull
        public String resourceType;
        @NotNull
        public String rightText;
        public int searchWeight;
        @NotNull
        public Object shareAdTrack;
        public boolean showHotSign;
        public boolean showImmediately;
        @NotNull
        public Object slogan;
        public int src;
        @NotNull
        public Object subTitle;
        @NotNull
        public List<Subtitle> subtitles;
        public boolean switchStatus;
        @NotNull
        public List<Tag> tags;
        @NotNull
        public String text;
        @NotNull
        public Object thumbPlayUrl;
        @NotNull
        public String title;
        @NotNull
        public List<String> titleList;
        @NotNull
        public Object titlePgc;
        @NotNull
        public String topicTitle;
        @NotNull
        public String type;
        public int uid;
        @NotNull
        public Object waterMarks;
        @NotNull
        public Object webAdTrack;
        @NotNull
        public WebUrl webUrl;
        @Nullable
        public Discovery.AutoPlayVideoAdDetail detail;
    }

    public static class Header {
        @NotNull
        public String actionUrl;
        public Object cover;
        public String description;
        public Object font;
        public String icon;
        public String iconType;
        public int id;
        public Label label;
        public List<Label> labelList;
        public String rightText;
        public Boolean showHateVideo;
        public Object subTitle;
        public Object subTitleFont;
        public String textAlign;
        public Long time;
        public String title;
    }

    public static class ItemX {

        public String type;
        public DataXX data;
        //public Object trackingData;
        public Object tag;
        public int id;
        public int adIndex;
    }

    public static class Subtitle{
        public String type;
        public String url;
    }
    public static class DataXX {
        public Cover cover;
        public Boolean dailyResource;
        public String dataType;
        public int id;
        public String nickname;
        public  String resourceType;
        public String url;
        public List<String> urls;
        public String userCover;
    }
}
