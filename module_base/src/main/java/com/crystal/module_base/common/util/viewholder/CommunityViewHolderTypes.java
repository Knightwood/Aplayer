package com.crystal.module_base.common.util.viewholder;

import com.crystal.module_base.R;
import com.crystal.module_base.tools.baseadapter2.HolderType;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/26 10:33
 * packageName：com.crystal.module_base.common.util.viewholder
 * 描述：
 */
public enum CommunityViewHolderTypes implements HolderType {
    UNKNOWN(-1, R.layout.item_unknown,"unKnown", "unKnown"),
    HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE(1, R.layout.item_community_horizontal_scrollcard_item_collection_type,"ItemCollection","horizontalScrollCard"), //type:horizontalScrollCard -> dataType:ItemCollection
    HORIZONTAL_SCROLLCARD_TYPE(2, R.layout.item_community_horizontal_scrollcard_type,"HorizontalScrollCard", "horizontalScrollCard"),     //type:horizontalScrollCard -> dataType:HorizontalScrollCard
    FOLLOW_CARD_TYPE(3, R.layout.item_community_columns_card_follow_card_type, "FollowCard","communityColumnsCard "),//type:communityColumnsCard -> dataType:FollowCard
    //社区关注
    AUTOPLAYFOLLOWCARD(4,R.layout.item_community_auto_play_follow_card_follow_card_type,"FollowCard","autoPlayFollowCard"),//type:autoPlayFollowCard -> dataType:FollowCard
    LOGINHEADER(5,R.layout.item_community_follow_header_type,"LOGIN","LOGIN");//提示登录
    private final String type;
    private int typeInt, layoutId;
    private String dataType;

    CommunityViewHolderTypes(int typeInt, int layoutId, String dataType,String type ) {
        this.typeInt = typeInt;
        this.layoutId = layoutId;
        this.dataType = dataType;
        this.type=type;
    }

    private static final CommunityViewHolderTypes[] enums = CommunityViewHolderTypes.values();

    /**
     * @param dataType data.getType()
     * @param type     data.getData().getDataType()
     * @return 除了textcard之外，其余的没有进行进一步的判断，目前不需要。
     */
    static CommunityViewHolderTypes getTypeEnum(String type, String dataType) {
        for (CommunityViewHolderTypes t : enums) {
            if (t.dataType.equals(dataType)&&t.type.equals(type))
                return t;
        }
        return UNKNOWN;
    }
    /**
     * @param type     data.getData().getDataType()
     * @return 除了textcard之外，其余的没有进行进一步的判断，目前不需要。
     */
    static CommunityViewHolderTypes getTypeEnum( String dataType) {
        for (CommunityViewHolderTypes t : enums) {
            if (t.dataType.equals(dataType))
                return t;
        }
        return UNKNOWN;
    }

    static CommunityViewHolderTypes getTypeEnum(int typeInt) {
        for (CommunityViewHolderTypes t : enums) {
            if (t.typeInt == typeInt)
                return t;
        }
        return UNKNOWN;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public String getTypeKind() {
        return dataType;
    }

    /**
     * 描述viewholder的type和datatype
     */
    public enum dataType {
        //type:horizontalScrollCard -> dataType:ItemCollection
        //type:horizontalScrollCard -> dataType:HorizontalScrollCard
       //type:communityColumnsCard -> dataType:FollowCard

        FollowCard("communityColumnsCard"),
        HorizontalScrollCard("horizontalScrollCard"),
        ItemCollection("horizontalScrollCard");

        private final String type;

        dataType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
