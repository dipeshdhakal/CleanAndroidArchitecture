package com.dipesh.auctionapp.wonitems;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dipesh.auctionapp.data.RepositoryImpl;
import com.dipesh.auctionapp.AuctionWonItemRecyclerAdapter;
import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.data.AuctionItem;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class WonItemsFragment extends Fragment implements WonItemsContract.View {

    private WonItemsPresenter interactionListener;
    private RecyclerView rvWonItems;
    private AuctionWonItemRecyclerAdapter auctionWonItemRecyclerAdapter;

    public static WonItemsFragment newInstance() {

        Bundle args = new Bundle();

        WonItemsFragment fragment = new WonItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_won_items, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactionListener = new WonItemsPresenter(this, RepositoryImpl.getInstance());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        interactionListener.fetchWonItemList();
    }

    private void initUI(View view) {
        rvWonItems = (RecyclerView) view.findViewById(R.id.rvWonItems);
    }

    @Override
    public void onWonItemListFetched(List<AuctionItem> wonItemList) {
        auctionWonItemRecyclerAdapter = new AuctionWonItemRecyclerAdapter(wonItemList, -1);
        rvWonItems.setAdapter(auctionWonItemRecyclerAdapter);
    }

    @Override
    public void onWonItemsFetchFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
