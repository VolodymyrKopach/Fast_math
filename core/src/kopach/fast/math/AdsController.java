package kopach.fast.math;

/**
 * Created by vova on 09.06.17.
 */

public interface AdsController {

    void showBannerAd();

    void hideBannerAd();

    void writeEmailToFirebase(String email);

    void showInterstitial(Runnable then);

    void loadRewardedVideoAd();

    void showRewardedVideoAd();

    boolean hasVideoReward();

    void rewardedVideoAd_pause();

    void rewardedVideoAd_resume();

    void rewardedVideoAd_destroy();
}
