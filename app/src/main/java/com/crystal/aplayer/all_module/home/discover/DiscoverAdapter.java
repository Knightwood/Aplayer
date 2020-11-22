package com.crystal.aplayer.all_module.home.discover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.home.daily.DailyAdapter;
import com.crystal.aplayer.all_module.newdetail.NewDetailActivity;
import com.crystal.aplayer.all_module.main.LoginActivity;
import com.crystal.aplayer.all_module.newdetail.VideoInfo;
import com.crystal.aplayer.all_module.util.ActionUrlUtil;
import com.crystal.module_base.common.http.bean2.Discovery;
import com.crystal.module_base.common.http.bean2.FollowCard;
import com.crystal.module_base.common.http.bean2.Label;
import com.crystal.module_base.common.util.Process;
import com.crystal.module_base.common.util.viewholder.AllViewHolder;
import com.crystal.module_base.common.util.viewholder.ViewHolderTypes;
import com.crystal.module_base.tools.LogUtil;
import com.crystal.module_base.tools.SomeTools;
import com.crystal.module_base.tools.baseadapter2.BaseAdapter3;
import com.crystal.module_base.tools.baseadapter2.BaseHolder2;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/11 22:23
 * packageName：com.crystal.aplayer.all_module.home.discover
 * 描述：
 */
public class DiscoverAdapter extends BaseAdapter3<Discovery.Item,BaseHolder2> {
    private static final String tag = "DiscoveryAdapter";
    private WeakReference<Context> context;

    public DiscoverAdapter(List<Discovery.Item> list, Context context) {
        super(list);
        this.context = new WeakReference<>(context);
    }

    @Override
    public int getItemViewType(int position) {
        Discovery.Item data = list.get(position);
        return AllViewHolder.parseViewHolderType(data.getType(), data.getData().getDataType());
    }

    @NonNull
    @Override
    public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AllViewHolder.getViewHolder(viewType, LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder2 holder2, int position) {
        Discovery.Item item = list.get(position);
        LogUtil.d(tag, item.getType() + "/" + item.getData().getDataType());
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
                holder2.getView(R.id.tvFollow).setOnClickListener(v -> holder2.startActivity(context.get(), LoginActivity.class));
                holder2.setOnItemClickListener(v -> ActionUrlUtil.process(context.get(), item.getData().getActionUrl(), item.getData().getText()), R.id.tvTitle5, R.id.ivInto5);
                break;
            case TEXT_CARD_HEADER7:
                holder2.setText(R.id.tvTitle7, item.getData().getText());
                holder2.setText(R.id.tvRightText7, item.getData().getRightText());
                holder2.setOnItemClickListener(v -> ActionUrlUtil.process(context.get(), item.getData().getActionUrl(), item.getData().getText() + "," + item.getData().getRightText()), R.id.tvRightText7, R.id.ivInto7);
                break;
            case TEXT_CARD_HEADER8:
                holder2.setText(R.id.tvTitle8, item.getData().getText());
                holder2.setText(R.id.tvRightText8, item.getData().getRightText());
                holder2.setOnItemClickListener(v -> ActionUrlUtil.process(context.get(), item.getData().getActionUrl(), item.getData().getText()), R.id.tvRightText8, R.id.ivInto8);
                break;
            case TEXT_CARD_FOOTER2:
                holder2.setText(R.id.tvFooterRightText2, item.getData().getText());
                holder2.setOnItemClickListener(v -> ActionUrlUtil.process(context.get(), item.getData().getActionUrl(), item.getData().getText()), R.id.tvFooterRightText2, R.id.ivTooterInto2);
                break;
            case TEXT_CARD_FOOTER3:
                holder2.setText(R.id.tvFooterRightText3, item.getData().getText());
                holder2.setOnItemClickListener(v -> {
                    if (v.getId() == R.id.tvRefresh) {
                        // "${holder.tvRefresh.text},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                    } else if (v.getId() == R.id.tvFooterRightText3 || v.getId() == R.id.ivTooterInto3) {
                        ActionUrlUtil.process(context.get(), item.getData().getActionUrl(), item.getData().getText());
                    }
                }, R.id.tvRefresh, R.id.tvFooterRightText3, R.id.ivTooterInto3);
                break;
            case BANNER:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getImage(), 4f, null);
                holder2.itemView.setOnClickListener(v -> {
                    ActionUrlUtil.process(context.get(), item.getData().getActionUrl(), item.getData().getTitle());
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
                    ActionUrlUtil.process(context.get(), item.getData().getActionUrl(), item.getData().getHeader().getTitle());
                });
                break;
            case FOLLOW_CARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivVideo), item.getData().getContent().getData().getCover().getFeed(), 4f, null);
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivAvatar), item.getData().getHeader().getIcon(), null);
                holder2.setText(R.id.tvVideoDuration, Process.getTimeFromInt(item.getData().getContent().getData().getDuration()));
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
                    /*showDialogShare(fragment.activity, "${item.data.content.data.title}：${item.data.content.data.webUrl.raw}")*/
                }, R.id.ivShare);
                holder2.itemView.setOnClickListener(v -> {
                    FollowCard tmp = item.getData().getContent().getData();
                    if (tmp.getAd() || tmp.getAuthor() == null) {
                        NewDetailActivity.start(context.get(), tmp.getId());
                    } else {
                        NewDetailActivity.start(context.get(), new VideoInfo(tmp.getId(), tmp.getPlayUrl(), tmp.getTitle(), tmp.getDescription(), tmp.getCategory(), tmp.getLibrary(), tmp.getConsumption(), tmp.getCover(), tmp.getAuthor(), tmp.getWebUrl()));
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
                    holder2.startActivity(context.get(), LoginActivity.class);
                }, R.id.tvFollow);
                holder2.itemView.setOnClickListener(v -> {
                    //"${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                });
                break;
            case TOPIC_BRIEFCARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getIcon(), 4f, null);
                holder2.setText(R.id.tvDescription, item.getData().getDescription());
                holder2.setText(R.id.tvTitle, item.getData().getTitle());
                holder2.itemView.setOnClickListener(v -> {
                    //"${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                });
                break;
            case COLUMN_CARD_LIST:
                holder2.setText(R.id.tvTitle, item.getData().getHeader().getTitle());
                holder2.setText(R.id.tvRightText, item.getData().getHeader().getRightText());
                holder2.setOnItemClickListener(v -> {
                    //"${item.data.header.rightText},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                }, R.id.tvRightText, R.id.ivInto);
                RecyclerView rv = holder2.getView(R.id.recyclerView);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new GridLayoutManager(context.get(), 2));
                if (rv.getItemDecorationCount() == 0)
                    rv.addItemDecoration(new GridListItemDecoration(2));
                List<Discovery.ItemX> list = new ArrayList<>();
                for (Discovery.ItemX ite : item.getData().getItemList()) {
                    if (ite.getType().equals("squareCardOfColumn") && ite.getData().getDataType().equals("SquareCard")) {
                        list.add(ite);
                    }
                }
                rv.setAdapter(new ColumnCardListAdapter(list));
                break;
            case VIDEO_SMALL_CARD:
                SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivPicture), item.getData().getCover().getFeed(), 4f, null);
                String string = item.getData().getLibrary().equals(DailyAdapter.DAILY_LIBRARY_TYPE) ? "#" + item.getData().getCategory() + " / 开眼精选" : "#" + item.getData().getCategory();
                holder2.setText(R.id.tvDescription, string);
                holder2.setText(R.id.tvTitle, item.getData().getTitle());
                holder2.setText(R.id.tvVideoDuration, Process.getTimeFromInt(item.getData().getDuration()));
                holder2.setOnItemClickListener(v -> {
                    //showDialogShare(fragment.activity, "${item.data.title}：${item.data.webUrl.raw}";
                }, R.id.ivShare);
                holder2.itemView.setOnClickListener(v -> {
                    FollowCard tmp = item.getData().getContent().getData();
                    NewDetailActivity.start(context.get(), new VideoInfo(tmp.getId(), tmp.getPlayUrl(), tmp.getTitle(), tmp.getDescription(), tmp.getCategory(), tmp.getLibrary(), tmp.getConsumption(), tmp.getCover(), tmp.getAuthor(), tmp.getWebUrl()));

                });
                break;
            case AUTO_PLAY_VIDEO_AD:
                Discovery.AutoPlayVideoAdDetail detail = item.getData().getDetail();
                if (detail != null) {
                    SomeTools.INSTANCES.loadImg(holder2.getView(R.id.ivAvatar), item.getData().getDetail().getIcon(), null);
                    holder2.setText(R.id.tvTitle, item.getData().getDetail().getDescription());
                }
                /*item.data.detail ?.run {
                CommendAdapter.startAutoPlay(fragment.activity, holder.videoPlayer, position, url, imageUrl, CommendAdapter.TAG, object :
                GSYSampleCallBack() {
                    override fun onPrepared(url:String ?, vararg objects:
                    Any ?){
                        super.onPrepared(url, * objects)
                        GSYVideoManager.instance().isNeedMute = true
                    }

                    override fun onClickBlank(url:String ?, vararg objects:
                    Any ?){
                        super.onClickBlank(url, * objects)
                        ActionUrlUtil.process(fragment, item.data.detail.actionUrl)
                    }
                })
                setOnClickListener(holder.videoPlayer.thumbImageView, holder.itemView) {
                    ActionUrlUtil.process(fragment, item.data.detail.actionUrl)
                }
            }*/
                break;
            case HORIZONTAL_SCROLL_CARD:
                holder2.getView(R.id.bannerViewPager);
                /* holder.bannerViewPager.run {
                    setCanLoop(false)
                    setRoundCorner(dp2px(4f))
                    setRevealWidth(GlobalUtil.getDimension(R.dimen.listSpaceSize))
                    if (item.data.itemList.size == 1) setPageMargin(0) else setPageMargin(dp2px(4f))
                    setIndicatorVisibility(View.GONE)
                    setAdapter(HorizontalScrollCardAdapter())
                    removeDefaultPageTransformer()
                    setOnPageClickListener { position ->
                        ActionUrlUtil.process(fragment, item.data.itemList[position].data.actionUrl, item.data.itemList[position].data.title)
                    }
                    create(item.data.itemList)
                }*/
                break;
            case SPECIAL_SQUARE_CARD_COLLECTION:
                holder2.setText(R.id.tvTitle, item.getData().getHeader().getTitle());
                holder2.setText(R.id.tvRightText, item.getData().getHeader().getRightText());
                holder2.setOnItemClickListener(v -> {
                    //"${item.data.header.rightText},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                }, R.id.tvRightText, R.id.ivInto);
                RecyclerView rv2 = holder2.getView(R.id.recyclerView);
                rv2.setHasFixedSize(true);
                rv2.setNestedScrollingEnabled(false);
                rv2.setLayoutManager(new GridLayoutManager(context.get(), 2, GridLayoutManager.HORIZONTAL, false));
                if (rv2.getItemDecorationCount() == 0)
                    rv2.addItemDecoration(new SpecialSquareCardCollectionItemDecoration());
                List<Discovery.ItemX> list1 = new ArrayList<>();
                for (Discovery.ItemX ite : item.getData().getItemList()) {
                    if (ite.getType().equals("squareCardOfCategory") && ite.getData().getDataType().equals("SquareCard")) {
                        list1.add(ite);
                    }
                }
                rv2.setAdapter(new specialSquareCardCollectionAdapter(list1));
                break;
            case UGC_SELECTED_CARD_COLLECTION:
                break;
        }
    }
}
