package com.crystal.module_base.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.crystal.module_base.R;
import com.crystal.module_base.common.util.TextKinds;
import com.crystal.module_base.common.util.TypefaceKind;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.scwang.smart.refresh.layout.api.RefreshFooter;

import static com.scwang.smart.refresh.layout.util.SmartUtil.dp2px;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/20 13:07
 * packageName：com.crystal.module_base.common.ui
 * 描述：
 */
public class JustTextFooter extends SimpleComponent implements RefreshFooter {
    private static final String TAG = "JustTextFooter";
    public static String REFRESH_FOOTER_NOTHING = null;//没有更多数据了

    private String mTextNothing = ""; //没有更多数据了

    private TextView mTitleText;

    private int mFooterHeight = 0;

    private int mBackgroundColor = 0;

    private boolean mNoMoreData = false;

    private RefreshKernel mRefreshKernel = null;

    public JustTextFooter(Context context) {
        this(context, null);
    }

    public JustTextFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JustTextFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.just_text_footer, this);

        mTitleText = findViewById(R.id.jst_footer_title);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JustTextFooter, 0, 0);
        if (typedArray.hasValue(R.styleable.JustTextFooter_srlPrimaryColor)) {
            setPrimaryColor(typedArray.getColor(R.styleable.JustTextFooter_srlPrimaryColor, 0));
        }
        if (typedArray.hasValue(R.styleable.JustTextFooter_srlAccentColor)) {
            setAccentColor(typedArray.getColor(R.styleable.JustTextFooter_srlAccentColor, 0));
        }
        if (typedArray.hasValue(R.styleable.JustTextFooter_srlTextSizeTitle)) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.JustTextFooter_srlTextSizeTitle, dp2px(16f)));
        }

        //无数据的标题信息
        if (typedArray.hasValue(R.styleable.JustTextFooter_srlTextNothing)) {
            String tmp = typedArray.getString(R.styleable.JustTextFooter_srlTextNothing);
            mTextNothing = tmp == null ? context.getString(R.string.jst_footer_nothing) : tmp;
        } else if (REFRESH_FOOTER_NOTHING != null) {
            mTextNothing = REFRESH_FOOTER_NOTHING;
        } else {
            mTextNothing = context.getString(R.string.jst_footer_nothing);
        }

        //字体
        int typeFaceKind = typedArray.getInt(R.styleable.JustTextFooter_typeface1, TypefaceKind.LOBSTER_TYPEFACE.getKindCode());
        mTitleText.setTypeface(TextKinds.getInstance().parseTypeface(typeFaceKind));

        typedArray.recycle();
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        super.onInitialized(kernel, height, maxDragHeight);
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
        if (mFooterHeight == 0) {
            mFooterHeight = height;
        }//获取SmartRefreshLayout全局设置的Footer高度。
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return super.onFinish(refreshLayout, success);
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        Log.d(TAG, "setNoMoreData: ");
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            refreshFooterHeight();
            if (noMoreData) {
                mTitleText.setText(mTextNothing);
            }
        }
        return true;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        super.onStateChanged(refreshLayout, oldState, newState);
        if (!mNoMoreData) {
            switch (newState) {
                case PullUpToLoad:
                    refreshFooterHeight();
                    break;
            }
        }
    }

    private void refreshFooterHeight() {
        Log.d(TAG, "refreshFooterHeight: ");
        if (mRefreshKernel != null) {
            if (mNoMoreData) {//有数据要加载
                mRefreshKernel.getRefreshLayout().setFooterHeightPx(mFooterHeight);
            } else {//无数据要加载
                mRefreshKernel.getRefreshLayout().setFooterHeight(0f);
            }
            mRefreshKernel.requestRemeasureHeightFor(this);
        }

    }


    private JustTextFooter setAccentColor(@ColorInt int color) {
        mTitleText.setTextColor(color);
        return this;
    }

    private void setPrimaryColor(@ColorInt int color) {
        mBackgroundColor = color;
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgroundFor(this, color);
        }
    }


    public JustTextFooter setAccentColorId(@ColorRes int colorTextPrimary) {
        setAccentColor(ContextCompat.getColor(this.getContext(),colorTextPrimary));
        return this;
    }

    public JustTextFooter setTextTitleSize(float v) {
        mTitleText.setTextSize(v);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }
    public JustTextFooter setTextTitleTypeface(Typeface tf ) {
        mTitleText.setTypeface(tf);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public static void setRefreshFooterNothing(String refreshFooterNothing) {
        REFRESH_FOOTER_NOTHING = refreshFooterNothing;
    }
}
