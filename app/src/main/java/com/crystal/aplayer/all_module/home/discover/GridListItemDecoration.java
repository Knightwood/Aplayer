package com.crystal.aplayer.all_module.home.discover;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/21 14:20
 * packageName：com.crystal.aplayer.all_module.home.discover
 * 描述：
 */
class GridListItemDecoration extends RecyclerView.ItemDecoration {
    public GridListItemDecoration(int i) {
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // item position
        RecyclerView.Adapter adapter=parent.getAdapter();
        int count = adapter==null?0:adapter.getItemCount(); //item count
        int spanIndex = ((GridLayoutManager.LayoutParams)view.getLayoutParams()).getSpanIndex();
      /*  int lastRowFirstItemPostion = count?.minus(spanCount)   //最后一行,第一个item索引
        val space = dp2px(space)

        when {
            position < spanCount -> {
                outRect.bottom = space
            }
            position < lastRowFirstItemPostion!! -> {
                outRect.top = space
                outRect.bottom = space
            }
            else -> {
                outRect.top = space
            }
        }
        when (spanIndex) {
            0 -> {
                outRect.right = space
            }
            spanCount - 1 -> {
                outRect.left = space
            }
            else -> {
                outRect.left = space
                outRect.right = space
            }
        }*/
    }
}
