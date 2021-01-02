package com.crystal.aplayer.all_module.home.daily;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.login.LoginActivity;
import com.crystal.aplayer.all_module.newdetail.NewDetailActivity;
import com.crystal.aplayer.all_module.newdetail.VideoInfo;
import com.crystal.aplayer.all_module.util.CommonActionUrlUtil;
import com.crystal.aplayer.all_module.util.ToolsKt;
import com.crystal.module_base.common.http.bean2.Daily;
import com.crystal.module_base.common.http.bean2.FollowCard;
import com.crystal.module_base.common.http.bean2.Label;
import com.crystal.module_base.common.util.GlobalUtil;
import com.crystal.module_base.common.util.viewholder.AllViewHolder;
import com.crystal.module_base.common.util.viewholder.ViewHolderTypes;
import com.crystal.module_base.tools.LogUtil;
import com.crystal.module_base.tools.SomeTools;
import com.crystal.module_base.tools.baseadapter2.BaseAdapter3;
import com.crystal.module_base.tools.baseadapter2.BaseHolder2;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/21 11:37
 * packageName：com.crystal.aplayer.all_module.home.daily
 * 描述：
 */
public class DailyAdapter extends BaseAdapter3<Daily.Item, BaseHolder2> {
    public static final String DAILY_LIBRARY_TYPE = "DAILY";
    public static final String tag = "DailyAdapter";
    public static final String DEFAULT_LIBRARY_TYPE = "DEFAULT";
    public static final String NONE_LIBRARY_TYPE = "NONE";

    public DailyAdapter(List<Daily.Item> list, Context context) {
        super(list,context);
    }

    @Override
    public int getItemViewType(int position) {
        Daily.Item data = list.get(position);
        return AllViewHolder.parseViewHolderType(data.getType(), data.getData().getDataType(), data.getData().getType());
    }

    @NonNull
    @Override
    public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AllViewHolder.getViewHolder(viewType, LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder2 holder2, int position) {
        Daily.Item item = list.get(position);
        LogUtil.d(tag, "item的样式" + item.getType() + "|==|" + item.getData().getDataType() + "|==|" + item.getData().getType());
        switch ((ViewHolderTypes) holder2.getHolderTypeEnum()) {
            case TEXT_CARD_HEADER5:
                holder2.setText(R.id.tvTitle5, item.getData().getText());
                if (item.getData().getActionUrl() != null)
                    holder2.setViewVisibility(R.id.ivInto5, View.VISIBLE);
                else
                    holder2.setViewVisibility(R.id.ivInto5, View.GONE);
                if (item.getData().getFollow() != null)
                    holder2.setViewVisibility(R.id.tvFollow, View.VISIBLE);
                else
                    holder2.setViewVisibility(R.id.tvFollow, View.GONE);
                holder2.getView(R.id.tvFollow).setOnClickListener(v -> holder2.startActivity(contextWeakReference.get(), LoginActivity.class));
                holder2.setOnItemClickListener(v -> CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getText()), R.id.tvTitle5, R.id.ivInto5);
                break;
            case TEXT_CARD_HEADER7:
                holder2.setText(R.id.tvTitle7, item.getData().getText());
                holder2.setText(R.id.tvRightText7, item.getData().getRightText());
                holder2.setOnItemClickListener(v -> CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getText() + "," + item.getData().getRightText()), R.id.tvRightText7, R.id.ivInto7);
                break;
            case TEXT_CARD_HEADER8:
                holder2.setText(R.id.tvTitle8, item.getData().getText());
                holder2.setText(R.id.tvRightText8, item.getData().getRightText());
                holder2.setOnItemClickListener(v -> CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getText()), R.id.tvRightText8, R.id.ivInto8);
                break;
            case TEXT_CARD_FOOTER2:
                holder2.setText(R.id.tvFooterRightText2, item.getData().getText());
                holder2.setOnItemClickListener(v -> CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getText()), R.id.tvFooterRightText2, R.id.ivTooterInto2);
                break;
            case TEXT_CARD_FOOTER3:
                holder2.setText(R.id.tvFooterRightText3, item.getData().getText());
                holder2.setOnItemClickListener(v -> {
                    if (v.getId() == R.id.tvRefresh) {
                        SomeTools.INSTANCES.showToast(contextWeakReference.get(), GlobalUtil.INSTANCE.getString(R.string.currently_not_supported)+((TextView)holder2.getView(R.id.tvRefresh)).getText());
                    } else if (v.getId() == R.id.tvFooterRightText3 || v.getId() == R.id.ivTooterInto3) {
                        CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getText());
                    }
                }, R.id.tvRefresh, R.id.tvFooterRightText3, R.id.ivTooterInto3);
                break;

            case BANNER3:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getImage(), 4f, null);
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivAvatar), item.getData().getHeader().getIcon(), null);
                holder2.setText(R.id.tvTitle, item.getData().getHeader().getTitle());
                holder2.setText(R.id.tvDescription, item.getData().getHeader().getDescription());
                Label label = item.getData().getLabel();
                if (label != null && label.getText() != null) {
                    holder2.setViewVisibility(R.id.tvLabel, View.VISIBLE);
                    holder2.setText(R.id.tvLabel, label.getText());
                } else {
                    holder2.setViewVisibility(R.id.tvLabel, View.INVISIBLE);
                    holder2.setText(R.id.tvLabel, "");
                }
                holder2.itemView.setOnClickListener(v -> {
                    CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getHeader().getTitle());
                });
                break;
            case FOLLOW_CARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivVideo), item.getData().getContent().getData().getCover().getFeed(), 4f, null);
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivAvatar), item.getData().getHeader().getIcon(), null);
                holder2.setText(R.id.tvVideoDuration, ToolsKt.transformToVideoDuration(item.getData().getContent().getData().getDuration()));
                holder2.setText(R.id.tvDescription, item.getData().getHeader().getDescription());
                holder2.setText(R.id.tvTitle, item.getData().getHeader().getTitle());
                if (item.getData().getContent().getData().getAd())
                    holder2.setViewVisibility(R.id.tvLabel, View.VISIBLE);
                else
                    holder2.setViewVisibility(R.id.tvLabel, View.GONE);

                if (item.getData().getContent().getData().getLibrary().equals(DailyAdapter.DAILY_LIBRARY_TYPE))
                    holder2.setViewVisibility(R.id.ivChoiceness, View.VISIBLE);
                else
                    holder2.setViewVisibility(R.id.ivChoiceness, View.GONE);
                holder2.setOnItemClickListener(v -> {
                    CommonActionUrlUtil.processShare((Activity) contextWeakReference.get(),item.getData().getContent().getData().getTitle()+"："+item.getData().getContent().getData().getWebUrl().getRaw());
                }, R.id.ivShare);
                holder2.itemView.setOnClickListener(v -> {
                    FollowCard tmp = item.getData().getContent().getData();
                    if (tmp.getAd() || tmp.getAuthor() == null) {
                        NewDetailActivity.start(contextWeakReference.get(), tmp.getId());
                    } else {
                        NewDetailActivity.start(contextWeakReference.get(), new VideoInfo(tmp.getId(), tmp.getPlayUrl(), tmp.getTitle(), tmp.getDescription(), tmp.getCategory(), tmp.getLibrary(), tmp.getConsumption(), tmp.getCover(), tmp.getAuthor(), tmp.getWebUrl()));
                    }
                });
                break;
            case INFORMATION_CARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivCover), item.getData().getBackgroundImage(), 4f, RoundedCornersTransformation.CornerType.TOP);
                RecyclerView recyclerView = holder2.getView(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);

                if (recyclerView.getItemDecorationCount() == 0) {
                    recyclerView.addItemDecoration(new InformationCardFollowCardAdapter.InformationCardFollowCardItemDecoration());
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(holder2.itemView.getContext()));
                recyclerView.setAdapter(new InformationCardFollowCardAdapter(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getTitleList()));
                holder2.itemView.setOnClickListener(v -> {
                     CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl());
                });
                break;
            default:
                holder2.itemView.setVisibility(View.GONE);
        }
    }
}
