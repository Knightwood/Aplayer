package com.crystal.aplayer.all_module.home.discover;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.scwang.smart.refresh.layout.util.SmartUtil.dp2px;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/21 14:20
 * packageName：com.crystal.aplayer.all_module.home.discover
 * 描述：
 */
public class GridListItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    private final int spanSize;

    public GridListItemDecoration(int spanSize, float space) {
        this.spanSize = spanSize == 0 ? 2 : spanSize;
        this.space = space == 0.0f ? dp2px(2f) : dp2px(space);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // item position
        RecyclerView.Adapter adapter = parent.getAdapter();
        int count = adapter == null ? 0 : adapter.getItemCount(); //item count
        int spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        int lastRowColumnFirstItem = count == 0 ? 0 : count-spanSize;//最后一行,第一个item索引
        int space = dp2px(this.space);
        if (position < spanSize) {
            outRect.bottom = space;
        } else if (position < lastRowColumnFirstItem) {
            outRect.top = space;
            outRect.bottom = space;
        } else {
            outRect.top = space;
        }
        if (spanIndex == 0) {
            outRect.right = space;
        } else if (spanIndex == spanSize - 1) {
            outRect.left = space;
        } else {
            outRect.left = space;
            outRect.right = space;
        }
    }

    private int lastRowColumnFirst(int count) {
        int result = (count / spanSize) * spanSize;
        return result + 1;
    }
}
