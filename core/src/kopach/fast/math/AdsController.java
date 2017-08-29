package kopach.fast.math;

/**
 * Created by vova on 09.06.17.
 */

public interface AdsController{

    public void showBannerAd();
    public void hideBannerAd();

    public void writeEmailToFirebase(String email);

    public void showInterstitial(Runnable then);

    public void loadRewardedVideoAd();
    public void showRewardedVideoAd();
    public boolean hasVideoReward();

    public void rewardedVideoAd_pause();
    public void rewardedVideoAd_resume();
    public void rewardedVideoAd_destroy();
}
