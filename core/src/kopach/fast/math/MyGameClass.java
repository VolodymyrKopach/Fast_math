package kopach.fast.math;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGameClass extends Game {

    private AdsController adsController;

    public SpriteBatch spriteBatch;
    public BitmapFont score_textlevel, score_t_b_s, score_b_s;

    public MyGameClass(AdsController adsController) {
        this.adsController = adsController;


       /* if (adsController != null) {
            this.adsController = adsController;
        } else{
            this.adsController = adsController;  // new DummyAdsController();
        }    */

    }

    public void create() {
        spriteBatch = new SpriteBatch();

       // bitmapFont();

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
}
