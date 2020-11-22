package com.crystal.module_base.base;

import android.app.Application;
import android.content.Context;

import com.crystal.module_base.R;
import com.crystal.module_base.common.view.JustTextFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;


/**
 * 创建者 kiylx
 * 创建时间 2020/9/17 16:20
 * packageName：com.crystal.aplayer.base
 * 描述：
 */
public class BaseApplication extends Application {
    protected static Context mContext;

    static {
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            layout.setEnableLoadMore(true);
            layout.setEnableLoadMoreWhenContentNotFull(true);
        });

        SmartRefreshLayout.setDefaultRefreshHeaderCreator  ((context, layout) -> {
            layout.setEnableHeaderTranslationContent(true);
            return new MaterialHeader(context)
                    .setColorSchemeColors(context.getResources().getColor(R.color.blue),context.getResources().getColor(R.color.blue), context.getResources().getColor(R.color.blue));
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator ((context, layout)->{
            layout.setEnableFooterFollowWhenNoMoreData(true);
            layout.setEnableFooterTranslationContent(true);
            layout.setFooterHeight(153f);
            layout.setFooterTriggerRate(0.6f);


            JustTextFooter.REFRESH_FOOTER_NOTHING = "- The End -";
            return new JustTextFooter(context)
                    .setAccentColorId(R.color.colorTextPrimary)
                    .setTextTitleSize(16f);
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return mContext;
    }
}
