package com.crystal.aplayer.module_base.common.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.scwang.smart.refresh.layout.util.SmartUtil.dp2px;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/23 22:46
 * packageName：com.crystal.aplayer.module_base.common.view
 * 描述：
 */
public class SimpleGridItemDecoration extends RecyclerView.ItemDecoration {
    // 记录上次偏移位置 防止一行多个数据的时候视图偏移
    private List<Integer> offsetPositions = new ArrayList<>();
    private final int space;//留出的空间
    private final int spanSize;    // 总的SpanSize
    private final LayoutOrientation layoutOrientation;//布局的方向
    private int itemCount;//元素的个数

    /**
     * @param spanSize 列数
     * @param halfSpace 空出来的空间的一半
     * @param layoutOrientation
     */
    public SimpleGridItemDecoration(int spanSize, float halfSpace, LayoutOrientation layoutOrientation) {
        this.spanSize = spanSize == 0 ? 2 : spanSize;
        this.space = halfSpace == 0.0f ? dp2px(2f) : dp2px(halfSpace);
        this.layoutOrientation = layoutOrientation == null ? LayoutOrientation.VERTICAL : layoutOrientation;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.Adapter adapter = parent.getAdapter();
        this.itemCount = adapter == null ? 0 : adapter.getItemCount(); //item count
        int rowCount = itemCount / spanSize;//完整满的行数
        int pos = parent.getChildAdapterPosition(view); // item position

        if (itemCount <= spanSize) {
            if (pos>0 && pos<spanSize) {
                //非最左和非最右
                //outRect.set(spanSize,0,spanSize,0);
                outRect.left = spanSize;
                outRect.right = spanSize;
            }else if (pos==0){
                outRect.right = spanSize;
            }else if (pos==spanSize-1){
                outRect.left = spanSize;
            }
            return;
        }

        if (!isFirstRow(pos) && !isEndRow(pos)) {
            //非第一行和非最后一行
            if (!isLeftColumn(pos) && !isRightColumn(pos)) {
                //非最左和非最右
                //outRect.set(spanSize,spanSize,spanSize,spanSize);
                outRect.left = spanSize;
                outRect.right = spanSize;
                outRect.top = spanSize;
                outRect.bottom = spanSize;
            } else {
                //非第一行和非最后一行 ,但是最左和最右
                //outRect.set(0,spanSize,0,spanSize);
                outRect.top = spanSize;
                outRect.bottom = spanSize;
            }
        } else {
            //第一行和最后一行
            if (!isLeftColumn(pos) && !isRightColumn(pos)) {
                //非最左和非最右
                //outRect.set(spanSize,0,spanSize,0);
                outRect.left = spanSize;
                outRect.right = spanSize;
            } else {
                if (pos == 0) {
                    outRect.right = spanSize;
                    outRect.bottom = spanSize;
                    return;
                }
                if (pos == spanSize-1) {
                    outRect.left = spanSize;
                    outRect.bottom = spanSize;
                    return;
                }
                if (pos==itemCount-1){
                    outRect.left = spanSize;
                    outRect.top = spanSize;
                    return;
                }
                if (isLastRowFirst(pos)){
                    outRect.right = spanSize;
                    outRect.top = spanSize;
                    return;
                }
            }
        }
    }

    /**
     * @param pos item的位置，下标从0开始
     * @return
     */
    private boolean isFirstRow(int pos) {
        return pos > -1 && pos < spanSize;
    }

    /**
     * @param pos item的位置，下标从0开始
     * @return
     */
    boolean isEndRow(int pos) {
        if (itemCount < spanSize) {//如果元素的个数比recyclerview的列数（spansize）少，也就是只有一行
            return isFirstRow(pos);
        }
        return pos >= getLastRowFirst();
    }

    /**
     * @param pos item的位置，下标从0开始
     * @return
     */
    private boolean isLeftColumn(int pos) {
        return (pos + 1) % spanSize == 1;
    }

    /**
     * @param pos item的位置，下标从0开始
     * @return
     */
    private boolean isRightColumn(int pos) {
        return (pos + 1) % spanSize == 0;
    }

    /**
     * @return 返回最后一行的开头元素, 从0开始
     */
    private int getLastRowFirst() {
        if (itemCount % spanSize==0){

        }int count = itemCount / spanSize;
        return count*spanSize;

    }
    private boolean isLastRowFirst(int pos){
        return getLastRowFirst()==pos;//没有多出来导致不满一行的元素
    }

    public enum LayoutOrientation {
        HORIZONTAL, VERTICAL
    }
}
