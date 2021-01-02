package com.crystal.aplayer.all_module.home.discover;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.scwang.smart.refresh.layout.util.SmartUtil.dp2px;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/21 16:35
 * packageName：com.crystal.aplayer.all_module.home.discover
 * 描述：
 */
public class SpecialSquareCardCollectionItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    private final int spanSize;

    public SpecialSquareCardCollectionItemDecoration(int spanSize, float space) {
        this.spanSize = spanSize == 0 ? 2 : spanSize;
        this.space = space == 0.0f ? dp2px(2f) : dp2px(space);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view); // item position
        RecyclerView.Adapter adapter = parent.getAdapter();
        int count = adapter == null ? 0 : adapter.getItemCount(); //item count
        int spanIndex = ((androidx.recyclerview.widget.GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();


        int lastRowColumnFirstItem = count == 0 ? 0 : lastRowColumnFirst(count);//最后一行,第一个item索引
        int rightCountSpace = dp2px(14f);
        if (position < spanSize) {
            outRect.right = space;
        } else if (position < lastRowColumnFirstItem) {
            outRect.left = space;
            outRect.right = space;
        } else {
            outRect.left = space;
            outRect.right = rightCountSpace;
        }
        if (spanIndex == 0) {
            outRect.bottom = space;
        } else if (spanIndex == spanSize - 1) {
            outRect.top = space;
        } else {
            outRect.top = space;
            outRect.bottom = space;
        }

    }
    private int lastRowColumnFirst(int count) {
        int result = (count / spanSize) * spanSize;
        return result + 1;
    }
}
