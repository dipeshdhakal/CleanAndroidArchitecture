package com.dipesh.auctionapp.addauctionitem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.RepositoryImpl;
import com.dipesh.auctionapp.util.DateUtil;


/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class AddAuctionItemActivity extends AppCompatActivity implements View.OnClickListener, AddAuctionItemContract.View {

    private EditText etAuctionItemName;
    private EditText etAuctionItemDesc;
    private EditText etAuctionItemExpiryDate;
    private Button btnAddAuctionItem;
    private AddAuctionItemContract.InteractionListener interactionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction_item);
        interactionListener = new AddAuctionItemPresenter(this, RepositoryImpl.getInstance());
        initUI();
    }

    private void initUI() {
        etAuctionItemName = (EditText) findViewById(R.id.etAuctionItemName);
        etAuctionItemDesc = (EditText) findViewById(R.id.etAuctionItemDesc);
        etAuctionItemExpiryDate = (EditText) findViewById(R.id.etAuctionItemExpiryDate);
        btnAddAuctionItem = (Button) findViewById(R.id.btnAddAuctionItem);
        btnAddAuctionItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddAuctionItem:
                interactionListener.add(createAuctionItemFromForm());
                break;
        }
    }

    private AuctionItem createAuctionItemFromForm() {

        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setName(etAuctionItemName.getText().toString().trim());
        auctionItem.setDescription(etAuctionItemDesc.getText().toString().trim());
        auctionItem.setExpiryDateTime(DateUtil.convertDateFromString(etAuctionItemExpiryDate.getText().toString()));
        return auctionItem;
    }

    @Override
    public void showInvalidNameError() {
        etAuctionItemName.setError(getString(R.string.text_invalid_name));
    }

    @Override
    public void showAddAuctionItemSuccessView(AuctionItem auctionItem) {
        finish();
        Toast.makeText(AddAuctionItemActivity.this, getString(R.string.text_auction_item_successfully_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddAuctionItemError(String message) {
        Toast.makeText(AddAuctionItemActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, AddAuctionItemActivity.class));
    }

    @Override
    public void showNotAValidDateTimeError() {
        etAuctionItemExpiryDate.setError(getString(R.string.text_not_a_valid_date_time));
    }
}
