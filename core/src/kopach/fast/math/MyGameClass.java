package kopach.fast.math;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MyGameClass extends Game {

    private AdsController adsController;

    public MyGameClass(AdsController adsController) {
        this.adsController = adsController;


       /* if (adsController != null) {
            this.adsController = adsController;
        } else{
            this.adsController = adsController;  // new DummyAdsController();
        }    */

    }

    public void create() {
        this.setScreen(new MenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void bannerAdShow() {
        adsController.showBannerAd();
    }

    public void bannerAdHide() {
        adsController.hideBannerAd();
    }

    public void interstitialAdShow() {
        adsController.showInterstitial(new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });
    }

    public void loadRewardedVideoAd() {
        adsController.loadRewardedVideoAd();
    }

    public void showRewardedVideoAd() {
        adsController.showRewardedVideoAd();
    }

    public void rewardedVideoAd_pause() {
        adsController.rewardedVideoAd_pause();
    }

    public void rewardedVideoAd_resume() {
        adsController.rewardedVideoAd_resume();
    }

    public void rewardedVideoAd_destroy() {
        adsController.rewardedVideoAd_destroy();

    }
}
