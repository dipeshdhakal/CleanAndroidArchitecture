package com.dipesh.auctionapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by braindigit on 11/26/15.
 */

/**
 * recycler view to handle item click event
 *
 * @param <T> ViewHolder required for recycler view
 * @param <S> To be returned model when item is clicked
 */
public abstract class SingleViewSelectableRecyclerViewAdapter<T extends RecyclerView.ViewHolder, S> extends SingleViewRecyleViewAdapter<T> {
    protected final List<S> dataList;
    protected int selectedPosition;
    private int previousSelectedPosition;
    private RecyclerViewItemClickListener<S> recyclerViewItemClickListener;

    public SingleViewSelectableRecyclerViewAdapter(@NonNull List<S> dataList, int selectedPosition) {
        this.dataList = dataList;
        this.selectedPosition = selectedPosition;
        this.previousSelectedPosition = selectedPosition;
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }


    @Override
    public void onBindViewHolder(T holder, int pos) {
        final int position = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                previousSelectedPosition = position;
                if (recyclerViewItemClickListener != null) {
                    recyclerViewItemClickListener.onRecyclerViewItemClick(dataList.get(position), position);
                }
            }
        });

        onBindCustomViewHolder(holder, position);


    }

    public abstract void onBindCustomViewHolder(T holder, int position);

    /**
     * @param recyclerViewItemClickListener setter to handel item click event
     */
    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener<S> recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public interface RecyclerViewItemClickListener<S> {
        void onRecyclerViewItemClick(S item, int position);
    }


    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyItemChanged(selectedPosition);
        if (previousSelectedPosition != -1)
            notifyItemChanged(previousSelectedPosition);
        previousSelectedPosition = selectedPosition;
    }
}
