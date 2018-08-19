package com.dipesh.auctionapp.bot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Bid;
import com.dipesh.auctionapp.data.RepositoryImpl;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.exception.AuctionItemAddException;
import com.dipesh.auctionapp.exception.AuctionItemsFetchFailException;
import com.dipesh.auctionapp.exception.BidAddException;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class BotService extends Service {

    //Inter is set to 1 hour
    public static final long INTERVAL_TO_ADD_BID_ITEM = 1000 * 60 * 60;
    private static User bot;

    @Override
    public void onCreate() {
        super.onCreate();
        if(bot == null){
           createNewBot();
        }
    }

    private void createNewBot() {
        bot = RepositoryImpl.getInstance().getBot();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startBidReceiverAlarm();
        return START_STICKY;
    }

    private void startBidReceiverAlarm() {
        AlarmManager am =( AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, BidReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL_TO_ADD_BID_ITEM, pi);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class BidReceiver extends BroadcastReceiver{
        private Random randomGenerator = new Random();

        @Override
        public void onReceive(final Context context, Intent intent) {
            bidOnRandomAuctionItem(context);
            createRandomAuctionItem(context);
        }

        private void createRandomAuctionItem(final Context context) {
            AuctionItem auctionItem = new AuctionItem();
            auctionItem.setName("Bot item " + UUID.randomUUID().toString());
            auctionItem.setDescription("Bot item desc " + UUID.randomUUID().toString());
            auctionItem.setPostedUser(bot);
            Date expiryDate = new Date(new Date().getTime() + INTERVAL_TO_ADD_BID_ITEM); // Expiry date is also set to 1 hour from current date
            auctionItem.setExpiryDateTime(expiryDate);
            RepositoryImpl.getInstance().addAuctionItem(auctionItem, new Repository.AddAuctionItemCallback() {
                @Override
                public void onAuctionItemSuccessfullyAdded(AuctionItem item) {
                    Toast.makeText(context, "New auction item successfully added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuctionItemAddError(AuctionItemAddException e) {

                }
            });
        }

        private void bidOnRandomAuctionItem(final Context context) {
            RepositoryImpl.getInstance().fetchAuctionItemList(bot, new Repository.FetchAuctionItemsCallback() {
                @Override
                public void onAuctionItemSuccessfullyFetched(List<AuctionItem> auctionItemList) {
                    if(auctionItemList != null && auctionItemList.size() > 0){
                        int index = randomGenerator.nextInt(auctionItemList.size());
                        float amount = randomGenerator.nextInt(10) * 1000;
                        RepositoryImpl.getInstance().bid(auctionItemList.get(index), amount, bot, new Repository.AddBidCallback() {
                            @Override
                            public void onBidSuccessfullyAdded(Bid bid) {
                                Toast.makeText(context, "bot added a new bid", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onBidAddError(BidAddException e) {

                            }
                        });
                    }
                }

                @Override
                public void onAuctionItemFetchFail(AuctionItemsFetchFailException e) {

                }
            });
        }


    }


}
