package com.crystal.module_base.common.util;

import android.graphics.Typeface;
import android.util.SparseArray;

import com.crystal.module_base.base.BaseApplication;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/24 22:34
 * packageName：com.crystal.module_base.common.util
 * 描述：
 */
public class TextKinds {
    /*public static final int FZLL_TYPEFACE = 1;
    public static final int FZDB1_TYPEFACE = 2;
    public static final int FUTURA_TYPEFACE = 3;
    public static final int DIN_TYPEFACE = 4;
    public static final int LOBSTER_TYPEFACE = 5;
     switch (typefaceKinds[kinds - 1]) {


            case FZLL_TYPEFACE:
                try {
                    typeface = Typeface.createFromAsset(BaseApplication.getContext().getAssets(), "fonts/FZLanTingHeiS-L-GB-Regular.TTF");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Typeface.DEFAULT;
                }
                break;
            case FZDB1_TYPEFACE:
                try {
                    Typeface.createFromAsset(BaseApplication.getContext().getAssets(), "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Typeface.DEFAULT;
                }
                break;
            case FUTURA_TYPEFACE:
                try {
                    Typeface.createFromAsset(BaseApplication.getContext().getAssets(), "fonts/Futura-CondensedMedium.ttf");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Typeface.DEFAULT;
                }

                break;
            case DIN_TYPEFACE:
                try {
                    Typeface.createFromAsset(BaseApplication.getContext().getAssets(), "fonts/DIN-Condensed-Bold.ttf");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Typeface.DEFAULT;
                }

                break;
            case LOBSTER_TYPEFACE:
                try {
                    Typeface.createFromAsset(BaseApplication.getContext().getAssets(), "fonts/Lobster-1.4.otf");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Typeface.DEFAULT;
                }
                break;
        }
        return null;*/
    public TypefaceKind[] typefaceKinds;//5种字体enum
    private SparseArray<Typeface> array;//存储字体

    public static TextKinds getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private TextKinds() {
        array = new SparseArray<>(5);
        typefaceKinds = new TypefaceKind[]{
                TypefaceKind.FZLL_TYPEFACE,
                TypefaceKind.FZDB1_TYPEFACE,
                TypefaceKind.FUTURA_TYPEFACE,
                TypefaceKind.DIN_TYPEFACE,
                TypefaceKind.LOBSTER_TYPEFACE};
    }

    /**
     * @param typefaceCode 5种字体
     * @return
     */
    public Typeface parseTypeface(int typefaceCode) {
        if (typefaceCode < 1 || typefaceCode > 5) {
            return Typeface.DEFAULT;
        }
        Typeface typeface =array.get(typefaceCode);
        if ( typeface== null) {
            try {
                typeface = Typeface.createFromAsset(BaseApplication.getContext().getAssets(), typefaceKinds[typefaceCode - 1].path);
                array.put(typefaceCode, typeface);
            } catch (RuntimeException e) {
                e.printStackTrace();
                return Typeface.DEFAULT;
            }
        }
        return typeface;
    }

    private static enum Singleton {
        INSTANCE;
        private TextKinds textKinds;

        Singleton() {
            textKinds = new TextKinds();
        }

        public TextKinds getInstance() {
            return textKinds;
        }
    }

}
