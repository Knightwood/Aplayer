package com.crystal.aplayer.all_module.community.commend;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.crystal.aplayer.module_base.common.util.ToolsKt;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/27 20:12
 * packageName：com.crystal.aplayer.all_module.community.commend
 * 描述：社区推荐列表的分割线
 */
class CommendItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int spanIndex =((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();

        outRect.top = ToolsKt.getBothSideSpace();
        if (spanIndex == 0) {
            outRect.left = ToolsKt.getBothSideSpace();
            outRect.right = ToolsKt.getMiddleSpace();
        }  else {
            outRect.left = ToolsKt.getMiddleSpace();
            outRect.right = ToolsKt.getBothSideSpace();
        }
    }
}
