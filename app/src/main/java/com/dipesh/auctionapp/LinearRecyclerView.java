package com.dipesh.auctionapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class LinearRecyclerView extends RecyclerView {
    public LinearRecyclerView(Context context) {
        super(context);
        init(null);
    }

    public LinearRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LinearRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
    }
}
