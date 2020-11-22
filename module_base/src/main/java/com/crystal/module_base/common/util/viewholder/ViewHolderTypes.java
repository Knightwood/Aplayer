package com.crystal.module_base.common.util.viewholder;

import com.crystal.module_base.R;
import com.crystal.module_base.tools.baseadapter2.HolderType;

import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/6 16:06
 * packageName：com.crystal.module_base.common.util
 * 描述：描述所有viewholder类型
 */
public enum ViewHolderTypes implements HolderType {
    UNKNOWN(-1, R.layout.item_unknown, "unKnown"),              //未知类型，使用EmptyViewHolder容错处理。

    CUSTOM_HEADER(0, R.layout.item_unknown, "customHeader"),        //自定义头部类型。

    TEXT_CARD_HEADER1(1, R.layout.item_unknown, "textCard_"),

    TEXT_CARD_HEADER2(2, R.layout.item_unknown, "textCard_"),

    TEXT_CARD_HEADER3(3, R.layout.item_unknown, "textCard_"),

    TEXT_CARD_HEADER4(4, R.layout.item_text_card_type_header_four, "textCard"),    //type:textCard -> dataType:TextCard,type:header4

    TEXT_CARD_HEADER5(5, R.layout.item_text_card_type_header_five, "textCard"),   //type:textCard -> dataType:TextCard -> type:header5

    TEXT_CARD_HEADER6(6, R.layout.item_unknown, "textCard_"),

    TEXT_CARD_HEADER7(7, R.layout.item_text_card_type_header_seven, "textCard"),   //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header7

    TEXT_CARD_HEADER8(8, R.layout.item_text_card_type_header_eight, "textCard"),  //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header8

    TEXT_CARD_FOOTER1(9, R.layout.item_unknown, "textCard_"),

    TEXT_CARD_FOOTER2(10, R.layout.item_text_card_type_footer_two, "textCard"),    //type:textCard -> dataType:TextCard,type:footer2

    TEXT_CARD_FOOTER3(11, R.layout.item_text_card_type_footer_three, "textCard"),   //type:textCard -> dataType:TextCardWithTagId,type:footer3

    BANNER(12, R.layout.item_banner_type, "banner"),  //type:banner -> dataType:Banner

    BANNER3(13, R.layout.item_banner_three_type, "banner3"), //type:banner3-> dataType:Banner

    FOLLOW_CARD(14, R.layout.item_follow_card_type, "followCard"),//type:followCard -> dataType:FollowCard -> type:video -> dataType:VideoBeanForClient

    TAG_BRIEFCARD(15, R.layout.item_brief_card_tag_brief_card_type, "briefCard"),//type:briefCard -> dataType:TagBriefCard

    TOPIC_BRIEFCARD(16, R.layout.item_brief_card_topic_brief_card_type, "briefCard"),//type:briefCard -> dataType:TopicBriefCard

    COLUMN_CARD_LIST(17, R.layout.item_column_card_list_type, "columnCardList"),//type:columnCardList -> dataType:ItemCollection

    VIDEO_SMALL_CARD(18, R.layout.item_video_small_card_type, "videoSmallCard"),//type:videoSmallCard -> dataType:VideoBeanForClient

    INFORMATION_CARD(19, R.layout.item_information_card_type, "informationCard"),//type:informationCard -> dataType:InformationCard

    AUTO_PLAY_VIDEO_AD(20, R.layout.item_auto_play_video_ad, "autoPlayVideoAd"),//type:autoPlayVideoAd -> dataType:AutoPlayVideoAdDetail

    HORIZONTAL_SCROLL_CARD(21, R.layout.item_horizontal_scroll_card_type, "horizontalScrollCard"), //type:horizontalScrollCard -> dataType:HorizontalScrollCard

    SPECIAL_SQUARE_CARD_COLLECTION(22, R.layout.item_special_square_card_collection_type, "specialSquareCardCollection"),   //type:specialSquareCardCollection -> dataType:ItemCollection

    UGC_SELECTED_CARD_COLLECTION(23, R.layout.item_ugc_selected_card_collection_type, "ugcSelectedCardCollection");//type:ugcSelectedCardCollection -> dataType:ItemCollection

    private int typeInt, layoutId;
    private String firstTypeKind;

    ViewHolderTypes(int typeInt, int layoutId, String firstTypeKind) {
        this.typeInt = typeInt;
        this.layoutId = layoutId;
        this.firstTypeKind = firstTypeKind;
    }

    private static final ViewHolderTypes[] enums = ViewHolderTypes.values();

    static ViewHolderTypes getTypeEnum(int typeInt) {
        for (ViewHolderTypes t : enums) {
            if (t.typeInt == typeInt)
                return t;
        }
        return UNKNOWN;
    }

    /**
     * @param type
     * @param dataType
     * @return
     * 除了textcard之外，其余的没有进行进一步的判断，目前不需要。
     */
   static ViewHolderTypes getTypeEnum(String type, String dataType) {
        if (type.equals("textCard")) {
            return getTextCard(dataType);
        }
        if (type.equals("briefCard")) {
            switch (dataType){
                case "TagBriefCard" :
                    return TAG_BRIEFCARD;
                case "TopicBriefCard" :
                   return TOPIC_BRIEFCARD;
            }
           return UNKNOWN;
       } else {
            for (ViewHolderTypes t : enums) {
                if (t.firstTypeKind.equals(type))
                    return t;
            }
        }
        return UNKNOWN;
    }

    static ViewHolderTypes getTextCard(String type) {
        switch (type) {
            case "header4":
                return TEXT_CARD_HEADER4;
            case "header5":
                return TEXT_CARD_HEADER7;
            case "header8":
                return TEXT_CARD_HEADER8;
            case "footer2":
                return TEXT_CARD_FOOTER2;
            case "footer3":
                return TEXT_CARD_FOOTER3;
            default:
                return UNKNOWN;
        }
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public String getFirstTypeKind() {
        return firstTypeKind;
    }

}
