package com.dipesh.auctionapp.addbid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Bid;
import com.dipesh.auctionapp.data.RepositoryImpl;


/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class AddBidActivity extends AppCompatActivity implements View.OnClickListener, AddBidContract.View {

    private static final String AUCTION_ITEM = "auction_item";
    private AuctionItem auctionItem;
    private Button btnBid;
    private TextView tvAuctionItemName;
    private TextView tvAuctionItemDesc;
    private EditText etBidAmount;
    private AddBidContract.InteractionListener interactionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);
        interactionListener = new AddBidPresenter(this, RepositoryImpl.getInstance());
        auctionItem = getIntent().getParcelableExtra(AUCTION_ITEM);
        initUI();
    }

    private void initUI() {
        tvAuctionItemName = (TextView) findViewById(R.id.tvAuctionItemName);
        tvAuctionItemName.setText(auctionItem.getName());
        tvAuctionItemDesc = (TextView) findViewById(R.id.tvAuctionItemDesc);
        tvAuctionItemDesc.setText(auctionItem.getDescription());
        etBidAmount = (EditText) findViewById(R.id.etBidAmount);
        btnBid = (Button) findViewById(R.id.btnBid);
        btnBid.setOnClickListener(this);
    }

    public static void start(Context context, AuctionItem item) {

        Intent intent = new Intent(context, AddBidActivity.class);
        intent.putExtra(AUCTION_ITEM, item);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBid:
                interactionListener.bid(auctionItem, etBidAmount.getText().toString().trim());
                break;
        }
    }

    @Override
    public void showNotAValidNumberView() {
        etBidAmount.setError(getString(R.string.text_not_a_valid_amount));
    }

    @Override
    public void showBidAddError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBidSuccessfullyAddedView(Bid bid) {
        Toast.makeText(this, getString(R.string.text_bid_successfully_added), Toast.LENGTH_LONG).show();
        finish();
    }
}
