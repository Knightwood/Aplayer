package com.crystal.aplayer.module_base.common.util.viewholder;

import com.crystal.aplayer.R;
import com.crystal.aplayer.module_base.tools.baseadapter2.HolderType;

public enum UgcHolderTypes implements HolderType {

    UNKNOWN(-1, R.layout.item_unknown, "unKnown", "unKnown"),
    FOLLOW_CARD_TYPE(1, R.layout.item_ugc_detail, "FollowCard", "communityColumnsCard");//item.data.dataType->"FollowCard",type:communityColumnsCard

    private static final UgcHolderTypes[] enums = values();
    private String dataType;
    private int layoutId;
    private final String type;
    private int typeInt;

    private UgcHolderTypes(int typeInt, int layoutId, String dataType, String type) {
        this.typeInt = typeInt;
        this.layoutId = layoutId;
        this.dataType = dataType;
        this.type = type;
    }

    static UgcHolderTypes getTypeEnum(String type, String dataType) {
        UgcHolderTypes[] ugcHolderTypesArr = enums;
        for (UgcHolderTypes t : ugcHolderTypesArr) {
            if (t.dataType.equals(dataType) && t.type.equals(type)) {
                return t;
            }
        }
        return UNKNOWN;
    }

    static UgcHolderTypes getTypeEnum(String dataType) {
        UgcHolderTypes[] ugcHolderTypesArr = enums;
        for (UgcHolderTypes t : ugcHolderTypesArr) {
            if (t.dataType.equals(dataType)) {
                return t;
            }
        }
        return UNKNOWN;
    }

    static UgcHolderTypes getTypeEnum(int typeInt) {
        UgcHolderTypes[] ugcHolderTypesArr = enums;
        for (UgcHolderTypes t : ugcHolderTypesArr) {
            if (t.typeInt == typeInt) {
                return t;
            }
        }
        return UNKNOWN;
    }

    public int getLayoutId() {
        return this.layoutId;
    }

    public int getTypeInt() {
        return this.typeInt;
    }

    public String getTypeKind() {
        return this.dataType;
    }

}
