package kopach.fast.math;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

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
        this.setScreen(new GameScreen(this));
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
}
