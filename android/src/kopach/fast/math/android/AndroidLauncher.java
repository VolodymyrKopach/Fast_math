package kopach.fast.math.android;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kopach.fast.math.AdsController;
import kopach.fast.math.MyGameClass;

public class AndroidLauncher extends AndroidApplication implements AdsController{
    public static final String BANNER_AD_UNIT_ID = "ca-app-pub-8320045635693885/1411226851";
    public static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-8320045635693885/7398884850";
    AdView bannerAd;
    InterstitialAd interstitialAd;
    RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useAccelerometer = false;
        config.useCompass = false;

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                loadRewardedVideoAd();

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }
        });

        loadRewardedVideoAd();

        View gameView = initializeForView(new MyGameClass(this), config);

        setupAds();

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(bannerAd, params);

        setContentView(relativeLayout);
    }

    @Override
    public void writeEmailToFirebase(String email) {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    public void setupAds() {
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff555555); //(gray) мабуть змінити на колір фону, який буде під рекламою
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        AdRequest adRequest = adRequestBuilder.build();
        interstitialAd.loadAd(adRequest);
    }


    @Override
    public void showBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.VISIBLE);
                AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                AdRequest adRequest = adRequestBuilder.build();
                bannerAd.loadAd(adRequest);
            }
        });
    }


    @Override
    public void hideBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void showInterstitial(final Runnable then) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (then != null) {
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Gdx.app.postRunnable(then);
                            AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                            AdRequest adRequest = adRequestBuilder.build();
                            interstitialAd.loadAd(adRequest);
                        }
                    });
                }
                interstitialAd.show();
            }
        });
    }

    public void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd("ca-app-pub-8320045635693885/1014478988", new AdRequest.Builder().build());
    }

    public void showRewardedVideoAd() {
        runOnUiThread(new Runnable() {
        public void run() {

            if (rewardedVideoAd.isLoaded()) {
                rewardedVideoAd.show();
            } else {
                loadRewardedVideoAd();
            }
        }
        });
    }


    public boolean hasVideoReward(){
        return rewardedVideoAd.isLoaded();
    }

    public void rewardedVideoAd_pause(){
        rewardedVideoAd.pause(this);
    }
    public void rewardedVideoAd_resume(){
        rewardedVideoAd.resume(this);
    }
    public void rewardedVideoAd_destroy(){
        rewardedVideoAd.destroy(this);
    }
}