package com.dipesh.auctionapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by braindigit on 4/7/16.
 */
public abstract class SingleViewRecyleViewAdapter<T extends RecyclerView.ViewHolder> extends BaseRecyclerViewAdapter<T> {
    Context context;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(getItemLayoutResource(), parent, false);

        return getViewHolder(view);
    }
    protected abstract T getViewHolder(View view);

    protected abstract int getItemLayoutResource();
}
