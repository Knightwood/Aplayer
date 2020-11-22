package com.crystal.module_base.common.util.typefaces;

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
    public TypefaceKind[] typefaceKinds;//5种字体enum
    private SparseArray<Typeface> array;//存储字体

    public static TextKinds getInstance() {
        return Singleton.INSTANCE.getInstance();
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

    private TextKinds() {
        array = new SparseArray<>(5);
        typefaceKinds = new TypefaceKind[]{
                TypefaceKind.FZLL_TYPEFACE,
                TypefaceKind.FZDB1_TYPEFACE,
                TypefaceKind.FUTURA_TYPEFACE,
                TypefaceKind.DIN_TYPEFACE,
                TypefaceKind.LOBSTER_TYPEFACE };
    }

    /**
     * @param typefaceCode 5种字体
     * @return 根据typefaceCode返回不同的字体
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



}
