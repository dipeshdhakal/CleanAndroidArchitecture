package com.dipesh.auctionapp.auctionitems;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dipesh.auctionapp.SingleViewSelectableRecyclerViewAdapter;
import com.dipesh.auctionapp.addbid.AddBidActivity;
import com.dipesh.auctionapp.AuctionWonItemRecyclerAdapter;
import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.RepositoryImpl;

import java.util.List;



/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class AuctionItemsFragment extends Fragment implements AuctionItemsContract.View, SingleViewSelectableRecyclerViewAdapter.RecyclerViewItemClickListener<AuctionItem> {

    private RecyclerView rvAuctionItems;
    private AuctionItemsContract.InteractionListener interactionListener;
    private AuctionWonItemRecyclerAdapter auctionWonItemRecyclerAdapter;

    public static AuctionItemsFragment newInstance() {

        Bundle args = new Bundle();

        AuctionItemsFragment fragment = new AuctionItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction_items, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactionListener = new AuctionItemsPresenter(this, RepositoryImpl.getInstance());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);

    }


    @Override
    public void onResume() {
        super.onResume();
        interactionListener.fetchAuctionItemsList();
    }

    private void initUI(View view) {
        rvAuctionItems = (RecyclerView) view.findViewById(R.id.rvAuctionItems);
    }

    @Override
    public void onAuctionItemsFetched(List<AuctionItem> auctionItemList) {
        auctionWonItemRecyclerAdapter = new AuctionWonItemRecyclerAdapter(auctionItemList, -1);
        auctionWonItemRecyclerAdapter.setRecyclerViewItemClickListener(this);
        rvAuctionItems.setAdapter(auctionWonItemRecyclerAdapter);
    }

    @Override
    public void onAuctionItemsFetchFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecyclerViewItemClick(AuctionItem item, int position) {
        AddBidActivity.start(getContext(), item);
    }
}
