package com.example.theodistonline_shoppingapp;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Custom RecyclerView that expands its height to fit all items when placed inside a ScrollView,
 * preventing grid clipping and ensuring all products display correctly.
 */
public class WrapContentRecyclerView extends RecyclerView {
    public WrapContentRecyclerView(@NonNull Context context) {
        super(context);
    }

    public WrapContentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
