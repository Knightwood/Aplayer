package com.crystal.aplayer.all_module.community.commend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.home.daily.DailyAdapter;
import com.crystal.aplayer.all_module.ugcdetail.UgcDetailActivity;
import com.crystal.aplayer.all_module.util.CommonActionUrlUtil;
import com.crystal.module_base.common.http.bean2.CommunityRecommend;
import com.crystal.module_base.common.http.bean2.Label;
import com.crystal.module_base.common.util.GlobalUtil;
import com.crystal.module_base.common.util.TextViewKt;
import com.crystal.module_base.common.util.ToolsKt;
import com.crystal.module_base.common.util.viewholder.ViewHolderHelper;
import com.crystal.module_base.common.util.viewholder.CommunityViewHolderTypes;
import com.crystal.module_base.tools.LogUtil;
import com.crystal.module_base.tools.SomeTools;
import com.crystal.module_base.tools.baseadapter2.BaseAdapter3;
import com.crystal.module_base.tools.baseadapter2.BaseHolder2;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.crystal.module_base.common.util.ToolsKt.calculateImageHeight;
import static com.zhpan.bannerview.utils.BannerUtils.dp2px;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/25 21:44
 * packageName：com.crystal.aplayer.all_module.community
 * 描述：社区-推荐
 */
public class CommendAdapter extends BaseAdapter3<CommunityRecommend.Item, BaseHolder2> {
    private static final String tag = "CommendAdapter";
    private static final String STR_VIDEO_TYPE = "video";
    private static final String STR_UGC_PICTURE_TYPE = "ugcPicture";

    public CommendAdapter(List<CommunityRecommend.Item> list, Context context) {
        super(list, context);
    }

    @Override
    public int getItemViewType(int position) {
        CommunityRecommend.Item data = dataList.get(position);
        return ViewHolderHelper.parseCommunityViewHolderType(data.getData().getDataType());
    }

    @NonNull
    @Override
    public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolderHelper.getCommunityViewHolder(viewType, LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder2 holder, int position) {
        CommunityRecommend.Item beanItem = dataList.get(position);
        LogUtil.d(tag, "item的样式" + beanItem.getType() + "|==|" + beanItem.getData().getDataType());
        switch ((CommunityViewHolderTypes) holder.getHolderTypeEnum()) {
            case HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE:
                ((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
                RecyclerView recyclerView = holder.getView(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(contextWeakReference.get(), RecyclerView.HORIZONTAL, false));
                if (recyclerView.getItemDecorationCount() == 0) {
                    recyclerView.addItemDecoration(new SquareCardOfCommunityContentItemDecoration());
                }
                recyclerView.setAdapter(new SquareCardOfCommunityContentAdapter(beanItem.getData().getItemList(), contextWeakReference.get()));
                break;
            case HORIZONTAL_SCROLLCARD_TYPE:
                ((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
                BannerViewPager<CommunityRecommend.ItemX, BannerAdapter.ComViewHolder> bannerViewPager = holder.getView(R.id.bannerViewPager);
                bannerViewPager.setCanLoop(false).setRoundCorner(dp2px(4f))
                        .setRevealWidth(0, GlobalUtil.INSTANCE.getDimension(R.dimen.listSpaceSize));
                if (beanItem.getData().getItemList().size() == 1)
                    bannerViewPager.setPageMargin(0);
                else
                    bannerViewPager.setPageMargin(dp2px(4f));

                bannerViewPager.setIndicatorVisibility(View.GONE)
                        .setAdapter(new BannerAdapter())
                        .setOnPageClickListener(position1 -> {
                            CommonActionUrlUtil.process(contextWeakReference.get(), beanItem.getData().getItemList().get(position1).getData().getActionUrl(), beanItem.getData().getItemList().get(position1).getData().getTitle());
                        }).removeDefaultPageTransformer();
                bannerViewPager.create(beanItem.getData().getItemList());
                break;
            case FOLLOW_CARD_TYPE:
                holder.setViewVisibility(R.id.tvChoiceness, View.GONE);
                holder.setViewVisibility(R.id.ivPlay, View.GONE);
                holder.setViewVisibility(R.id.ivLayers, View.GONE);
                if (beanItem.getData().getContent().getData().getLibrary().equals(DailyAdapter.DAILY_LIBRARY_TYPE))
                    holder.setViewVisibility(R.id.tvChoiceness, View.VISIBLE);

                CommunityRecommend.Header header = beanItem.getData().getHeader();
                if (header != null && header.getIconType().equals("round")) {
                    holder.setViewVisibility(R.id.ivAvatar, View.INVISIBLE);
                    holder.setViewVisibility(R.id.ivRoundAvatar, View.VISIBLE);
                    SomeTools.INSTANCES.loadImg(holder.getView(R.id.ivRoundAvatar), beanItem.getData().getContent().getData().getOwner().getAvatar(), 0f, null);
                } else {
                    holder.setViewVisibility(R.id.ivRoundAvatar, View.GONE);
                    holder.setViewVisibility(R.id.ivAvatar, View.VISIBLE);
                    SomeTools.INSTANCES.loadImg(holder.getView(R.id.ivAvatar), beanItem.getData().getContent().getData().getOwner().getAvatar(), 0f, null);

                }
                ImageView imageView = holder.getView(R.id.ivBgPicture);
                int imageHeight = calculateImageHeight(beanItem.getData().getContent().getData().getWidth(), beanItem.getData().getContent().getData().getHeight());
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(ToolsKt.getMaxImageWidth(), imageHeight));
                SomeTools.INSTANCES.loadImg(imageView, beanItem.getData().getContent().getData().getCover().getFeed(), 4f, null);

                holder.setText(R.id.tvCollectionCount, String.valueOf(beanItem.getData().getContent().getData().getConsumption().getCollectionCount()));
                Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_favorite_border_black_20dp);
                TextViewKt.setDrawable(holder.getView(R.id.tvCollectionCount), drawable, 17f, 17f, 2);
                holder.getView(R.id.tvCollectionCount).setOnClickListener(v->{
                    //喜欢
                    CommonActionUrlUtil.processCollection(contextWeakReference.get(),null);
                });
                holder.setText(R.id.tvDescription, beanItem.getData().getContent().getData().getDescription());
                holder.setText(R.id.tvNickName, beanItem.getData().getContent().getData().getOwner().getNickname());

                switch (beanItem.getData().getContent().getType()) {
                    case STR_VIDEO_TYPE:
                        holder.setViewVisibility(R.id.ivPlay, View.VISIBLE);
                        holder.itemView.setOnClickListener(v -> openUgcDetail(beanItem));
                        break;
                    case STR_UGC_PICTURE_TYPE:
                        if (beanItem.getData().getContent().getData().getUrls() != null && beanItem.getData().getContent().getData().getUrls().size() > 1)
                            holder.setViewVisibility(R.id.ivLayers, View.VISIBLE);
                        holder.itemView.setOnClickListener(v -> openUgcDetail(beanItem));
                        break;
                }
                break;
            case UNKNOWN:
                holder.itemView.setVisibility(View.GONE);
        }
    }

    private void openUgcDetail(CommunityRecommend.Item item) {
        List<CommunityRecommend.Item> resultList = new ArrayList<>();
        for (CommunityRecommend.Item it : dataList) {
            if (it.getType().equals(CommunityViewHolderTypes.dataType.FollowCard.getType()) && it.getData().getDataType().equals(CommunityViewHolderTypes.dataType.FollowCard.name()))
                resultList.add(it);
        }
        UgcDetailActivity.Companion.start((Activity) contextWeakReference.get(), resultList, item);
    }

    public static class BannerAdapter extends BaseBannerAdapter<CommunityRecommend.ItemX, BannerAdapter.ComViewHolder> {
        @Override
        protected void onBind(ComViewHolder holder, CommunityRecommend.ItemX data, int position, int pageSize) {
            holder.bindData(data, position, pageSize);
        }

        @Override
        public ComViewHolder createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType) {
            return new ComViewHolder(itemView);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_banner_item_type;
        }

        public static class ComViewHolder extends BaseViewHolder<CommunityRecommend.ItemX> {
            public ComViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void bindData(CommunityRecommend.ItemX item, int position, int pageSize) {
                ImageView ivPicture = findView(R.id.ivPicture);
                TextView tvLabel = findView(R.id.tvLabel);
                Label label = item.getData().getLabel();
                if (label == null || label.getText() == null || label.getText().isEmpty()) {
                    tvLabel.setVisibility(View.INVISIBLE);
                    tvLabel.setText("");
                } else {
                    tvLabel.setVisibility(View.VISIBLE);
                    tvLabel.setText(label.getText());
                }
                SomeTools.INSTANCES.loadImg(ivPicture, item.getData().getImage(), 4f, null);
            }
        }
    }

    static class SquareCardOfCommunityContentAdapter extends BaseAdapter3<CommunityRecommend.ItemX, BaseHolder2> {

        SquareCardOfCommunityContentAdapter(List<CommunityRecommend.ItemX> list, Context context) {
            super(list, context);
        }

        @NonNull
        @Override
        public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BaseHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_community_horizontal_scroll_card_itemcollection_item_type, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BaseHolder2 holder, int position) {
            CommunityRecommend.ItemX item = dataList.get(position);
            ImageView imageView = holder.getView(R.id.ivBgPicture);
            imageView.getLayoutParams().width = ToolsKt.getMaxImageWidth();
            SomeTools.INSTANCES.loadImg(imageView, item.getData().getBgPicture(), 4f, null);
            holder.setText(R.id.tvTitle, item.getData().getTitle());
            holder.setText(R.id.tvSubTitle, item.getData().getSubTitle());
            holder.itemView.setOnClickListener(v -> CommonActionUrlUtil.process(contextWeakReference.get(), item.getData().getActionUrl(), item.getData().getTitle()));
        }
    }

    static class SquareCardOfCommunityContentItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildAdapterPosition(view); // item position
            RecyclerView.Adapter adapter = parent.getAdapter();
            int count = adapter == null ? 0 : adapter.getItemCount(); //item count
            if (position == 0) {
                /*outRect.left = fragment.bothSideSpace*/
                outRect.right = ToolsKt.getMiddleSpace();
            } else if (position == count) {
                outRect.left = ToolsKt.getMiddleSpace();
                /*outRect.right = fragment.bothSideSpace*/
            } else {
                outRect.left = ToolsKt.getMiddleSpace();
                outRect.right = ToolsKt.getMiddleSpace();
            }

        }
    }

}
