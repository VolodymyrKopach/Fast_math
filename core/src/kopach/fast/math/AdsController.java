package kopach.fast.math;

/**
 * Created by vova on 09.06.17.
 */

public interface AdsController {

    public void showBannerAd();
    public void hideBannerAd();

    public void showInterstitial(Runnable then);
}
