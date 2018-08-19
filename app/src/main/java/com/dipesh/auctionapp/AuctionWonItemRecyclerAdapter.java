package com.dipesh.auctionapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.util.DateUtil;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class AuctionWonItemRecyclerAdapter extends SingleViewSelectableRecyclerViewAdapter<AuctionWonItemRecyclerAdapter.AuctionWonItemViewHolder, AuctionItem> {


    public AuctionWonItemRecyclerAdapter(@NonNull List<AuctionItem> dataList, int selectedPosition) {
        super(dataList, selectedPosition);
    }

    @Override
    public void onBindCustomViewHolder(AuctionWonItemViewHolder holder, int position) {
        AuctionItem auctionItem = dataList.get(position);
        holder.tvAuctionWonItemName.setText(auctionItem.getName());
        holder.tvAuctionWonItemDesc.setText(auctionItem.getDescription());

        String expDateTime = context.getString(R.string.text_expiry_date_value,DateUtil.convertDateToString(auctionItem.getExpiryDateTime()));
        holder.tvAuctionWonItemExpiryDate.setText(expDateTime);
    }

    @Override
    protected AuctionWonItemViewHolder getViewHolder(View view) {
        return new AuctionWonItemViewHolder(view);
    }

    @Override
    protected int getItemLayoutResource() {
        return R.layout.rv_item_auction_won;
    }

    public static class AuctionWonItemViewHolder extends RecyclerView.ViewHolder{

        public final TextView tvAuctionWonItemName;
        public final TextView tvAuctionWonItemDesc;
        public final TextView tvAuctionWonItemExpiryDate;

        public AuctionWonItemViewHolder(View itemView) {
            super(itemView);
            tvAuctionWonItemName = (TextView) itemView.findViewById(R.id.tvAuctionWonItemName);
            tvAuctionWonItemDesc = (TextView) itemView.findViewById(R.id.tvAuctionWonItemDesc);
            tvAuctionWonItemExpiryDate = (TextView) itemView.findViewById(R.id.tvAuctionWonItemExpiryDate);
        }
    }
}
