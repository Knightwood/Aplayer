package com.crystal.aplayer.module_base.common.util.typefaces;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/28 20:56
 * packageName：com.crystal.aplayer.module_base.common.util
 * 描述：
 */
public enum TypefaceKind {
    FZLL_TYPEFACE("fonts/FZLanTingHeiS-L-GB-Regular.TTF",1),
    FZDB1_TYPEFACE("fonts/FZLanTingHeiS-DB1-GB-Regular.TTF",2),
    FUTURA_TYPEFACE("fonts/Futura-CondensedMedium.ttf",3),
    DIN_TYPEFACE("fonts/DIN-Condensed-Bold.ttf",4),
    LOBSTER_TYPEFACE("fonts/Lobster-1.4.otf",5);
    int kinds;
    String path;

    TypefaceKind(String path, int i) {
        this.kinds = i;
        this.path=path;
    }

    public int getKindCode() {
        return kinds;
    }

    public String getPath() {
        return path;
    }
}
