package com.crystal.aplayer.all_module.home.recommend.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.home.daily.DailyAdapter;
import com.crystal.aplayer.all_module.home.daily.InformationCardFollowCardAdapter;
import com.crystal.aplayer.all_module.login.LoginActivity;
import com.crystal.aplayer.all_module.video_detail.VideoDetailActivity;
import com.crystal.module_base.common.http.bean2.VideoInfo;
import com.crystal.aplayer.all_module.util.CommonActionUrlUtil;
import com.crystal.module_base.common.http.bean2.Discovery;
import com.crystal.module_base.common.http.bean2.FollowCard;
import com.crystal.module_base.common.http.bean2.HomePageRecommend;
import com.crystal.module_base.common.http.bean2.Label;
import com.crystal.module_base.common.util.GlobalUtil;
import com.crystal.module_base.common.util.ToolsKt;
import com.crystal.module_base.common.util.viewholder.ViewHolderHelper;
import com.crystal.module_base.common.util.viewholder.ViewHolderTypes;
import com.crystal.module_base.common.view.videoplayer.VideoPlayerToolsKt;
import com.crystal.module_base.tools.LogUtil;
import com.crystal.module_base.tools.SomeTools;
import com.crystal.module_base.tools.baseadapter2.BaseAdapter3;
import com.crystal.module_base.tools.baseadapter2.BaseHolder2;
import com.shuyu.gsyvideoplayer.GSYVideoADManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/9 14:58
 * packageName：com.crystal.aplayer.all_module.home.recommend.adapter
 * 描述：
 */
public class RecommendAdapter extends BaseAdapter3<HomePageRecommend.Item, BaseHolder2> {
    private static final String tag = "RecommendAdapter";

    public RecommendAdapter(List<HomePageRecommend.Item> list, Context context) {
        super(list, context);
    }

    @Override
    public int getItemViewType(int position) {
        HomePageRecommend.Item data = dataList.get(position);
        return ViewHolderHelper.parseViewHolderType(data.getType(), data.getData().getDataType(), data.getData().getType());
    }

    @NonNull
    @Override
    public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolderHelper.getViewHolder(viewType, LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder2 holder2, int position) {
        HomePageRecommend.Item item = dataList.get(position);
        LogUtil.d(tag, "item的样式" + item.getType() + "|==|" + item.getData().getDataType());
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
                        SomeTools.INSTANCES.showToast(contextWeakReference.get(), GlobalUtil.INSTANCE.getString(R.string.currently_not_supported) + ((TextView) holder2.getView(R.id.tvRefresh)).getText());
                    } else if (v.getId() == R.id.tvFooterRightText3 || v.getId() == R.id.ivTooterInto3) {
                        CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getText());
                    }
                }, R.id.tvRefresh, R.id.tvFooterRightText3, R.id.ivTooterInto3);
                break;
            case BANNER:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getImage(), 4f, null);
                holder2.itemView.setOnClickListener(v -> {
                    CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getTitle());
                });
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
                    CommonActionUrlUtil.processShare((Activity) contextWeakReference.get(), item.getData().getContent().getData().getTitle() + "：" + item.getData().getContent().getData().getWebUrl().getRaw());
                }, R.id.ivShare);
                holder2.itemView.setOnClickListener(v -> {
                    FollowCard tmp = item.getData().getContent().getData();
                    if (tmp.getAd() || tmp.getAuthor() == null) {
                        VideoDetailActivity.Companion.start(contextWeakReference.get(), tmp.getId());
                    } else {
                        VideoDetailActivity.Companion.start(contextWeakReference.get(), new VideoInfo(tmp.getId(), tmp.getPlayUrl(), tmp.getTitle(), tmp.getDescription(), tmp.getCategory(), tmp.getLibrary(), tmp.getConsumption(), tmp.getCover(), tmp.getAuthor(), tmp.getWebUrl()));
                    }
                });
                break;
            case TAG_BRIEFCARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getIcon(), 4f, null);
                holder2.setText(R.id.tvDescription, item.getData().getDescription());
                holder2.setText(R.id.tvTitle, item.getData().getTitle());
                if (item.getData().getFollow() != null)
                    holder2.setViewVisibility(R.id.tvFollow, View.VISIBLE);
                else
                    holder2.setViewVisibility(R.id.tvFollow, View.GONE);
                holder2.setOnItemClickListener(v -> {
                    holder2.startActivity(contextWeakReference.get(), LoginActivity.class);
                }, R.id.tvFollow);
                holder2.itemView.setOnClickListener(v -> {
                    SomeTools.INSTANCES.showToast(contextWeakReference.get(), item.getData().getTitle() + "：" + GlobalUtil.INSTANCE.getString(R.string.currently_not_supported));
                });
                break;
            case TOPIC_BRIEFCARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getIcon(), 4f, null);
                holder2.setText(R.id.tvDescription, item.getData().getDescription());
                holder2.setText(R.id.tvTitle, item.getData().getTitle());
                holder2.itemView.setOnClickListener(v -> {
                    SomeTools.INSTANCES.showToast(contextWeakReference.get(), item.getData().getTitle() + "：" + GlobalUtil.INSTANCE.getString(R.string.currently_not_supported));
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

            case VIDEO_SMALL_CARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getCover().getFeed(), 4f, null);
                String string = item.getData().getLibrary().equals(DailyAdapter.DAILY_LIBRARY_TYPE) ? "#" + item.getData().getCategory() + " / 开眼精选" : "#" + item.getData().getCategory();
                holder2.setText(R.id.tvDescription, string);
                holder2.setText(R.id.tvTitle, item.getData().getTitle());
                holder2.setText(R.id.tvVideoDuration, ToolsKt.transformToVideoDuration(item.getData().getDuration()));
                holder2.setOnItemClickListener(v -> {
                    CommonActionUrlUtil.processShare((Activity) contextWeakReference.get(), item.getData().getContent().getData().getTitle() + "：" + item.getData().getContent().getData().getWebUrl().getRaw());

                }, R.id.ivShare);
                holder2.itemView.setOnClickListener(v -> {
                    HomePageRecommend.Data tmp = item.getData();
                    VideoDetailActivity.Companion.start(contextWeakReference.get(), new VideoInfo(tmp.getId(), tmp.getPlayUrl(), tmp.getTitle(), tmp.getDescription(), tmp.getCategory(), tmp.getLibrary(), tmp.getConsumption(), tmp.getCover(), tmp.getAuthor(), tmp.getWebUrl()));

                });
                break;
            case AUTO_PLAY_VIDEO_AD:
                Discovery.AutoPlayVideoAdDetail detail = item.getData().getDetail();
                if (detail != null) {
                    SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivAvatar), item.getData().getDetail().getIcon(), null);
                    holder2.setText(R.id.tvTitle, item.getData().getDetail().getTitle());
                    holder2.setText(R.id.tvDescription, item.getData().getDetail().getDescription());

                }
                Discovery.AutoPlayVideoAdDetail detail2 = item.getData().getDetail();
                if (detail2 != null) {
                    VideoPlayerToolsKt.startAutoPlay((Activity) contextWeakReference.get(), holder2.getView(R.id.videoPlayer), position, detail2.getUrl(), detail2.getImageUrl(), tag, new GSYSampleCallBack() {
                        @Override
                        public void onPrepared(String url, Object... objects) {
                            super.onPrepared(url, objects);
                            GSYVideoADManager.instance().setNeedMute(true);
                        }

                        @Override
                        public void onClickBlank(String url, Object... objects) {
                            super.onClickBlank(url, objects);
                            CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getDetail().getActionUrl());
                        }
                    });
                    holder2.setOnItemClickListener(v -> {
                        CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getDetail().getActionUrl());
                    }, holder2.itemView, ((GSYVideoPlayer) holder2.getView(R.id.videoPlayer)).getThumbImageView());
                }
                break;

            case UGC_SELECTED_CARD_COLLECTION:
                holder2.setText(R.id.tvTitle, item.getData().getHeader().getTitle());
                holder2.setText(R.id.tvRightText, item.getData().getHeader().getRightText());
                holder2.getView(R.id.tvRightText).setOnClickListener(v -> {
                    //EventBus.getDefault().post(SwitchPagesEvent(com.eyepetizer.android.ui.community.commend.CommendFragment::class.java))
                    //EventBus.getDefault().post(RefreshEvent(CommunityFragment::class.java))
                });
                List<HomePageRecommend.ItemX> itemList = item.getData().getItemList();
                for (int i = 0; i < itemList.size(); i++) {
                    HomePageRecommend.ItemX it = itemList.get(i);
                    @LayoutRes
                    int iv2 = -1;
                    int tv2=-1;
                    switch (i) {
                        case 0:
                            iv2 = R.id.ivCoverLeft;
                            tv2=R.id.tvNicknameLeft;
                            break;
                        case 1:
                            iv2 = R.id.ivCoverRightTop;
                            tv2=R.id.tvNicknameRightTop;
                            break;
                        case 2:
                            iv2 = R.id.ivCoverRightBottom;
                            tv2=R.id.tvNicknameRightBottom;
                            break;
                    }
                    if (iv2 ==-1||tv2==-1)
                        return;
                    ToolsKt.load(holder2.getView(iv2), it.getData().getUrl(), 4f, RoundedCornersTransformation.CornerType.TOP_RIGHT);
                    if (it.getData().getUrls() != null && it.getData().getUrls().size() > 1) {
                        holder2.setViewVisibility(iv2, View.VISIBLE);
                        ToolsKt.load(holder2.getView(iv2), it.getData().getUserCover(), 0f,  RoundedCornersTransformation.CornerType.ALL);
                        holder2.setText(tv2, it.getData().getNickname());
                    }
                }
                holder2.itemView.setOnClickListener(v -> {
                    SomeTools.INSTANCES.showToast(contextWeakReference.get(), GlobalUtil.INSTANCE.getString(R.string.currently_not_supported));
                });
                break;
        }
    }
}
