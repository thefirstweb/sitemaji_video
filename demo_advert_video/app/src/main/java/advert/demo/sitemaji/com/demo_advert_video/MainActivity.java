package advert.demo.sitemaji.com.demo_advert_video;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.MIntegralSDK;
import com.mintegral.msdk.nativex.view.MTGMediaView;
import com.mintegral.msdk.out.Campaign;
import com.mintegral.msdk.out.Frame;
import com.mintegral.msdk.out.MIntegralSDKFactory;
import com.mintegral.msdk.out.MTGRewardVideoHandler;
import com.mintegral.msdk.out.MtgNativeHandler;
import com.mintegral.msdk.out.NativeListener;
import com.mintegral.msdk.out.OnMTGMediaViewListener;
import com.mintegral.msdk.out.RewardVideoListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private final static String TAG = "MainActivity";
    private Context mContext;
    private Map<String, Object> mNativeVideoMapConfig;
    private MTGMediaView mMVMediaView;
    private MTGRewardVideoHandler mMVRewardVideoHandler;

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initView();

        mMVRewardVideoHandler = preloadRewardVideo(this, "unit_id");
        mNativeVideoMapConfig = preloadNative("unit_id");
    }

    private void initView() {
        mLinearLayout = findViewById(R.id.linearLayout);

        findViewById(R.id.button_video_reward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardVideo(mMVRewardVideoHandler);
            }
        });
        findViewById(R.id.button_native_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMVMediaView = generateMediaView(mContext);
                showNative(mContext, mMVMediaView, mNativeVideoMapConfig);
            }
        });
    }

    private Map<String, Object> preloadNative(String unitId) {
        MIntegralSDK sdk = MIntegralSDKFactory.getMIntegralSDK();
        Map<String, Object> preloadMap = new HashMap<>();
        preloadMap.put(MIntegralConstans.PROPERTIES_LAYOUT_TYPE, MIntegralConstans.LAYOUT_NATIVE);
        preloadMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, unitId);
        preloadMap.put(MIntegralConstans.NATIVE_VIDEO_WIDTH, 720);//the width of video ,defalt 1024
        preloadMap.put(MIntegralConstans.NATIVE_VIDEO_HEIGHT, 480);//the heigh of video ,defalt 720
        preloadMap.put(MIntegralConstans.NATIVE_VIDEO_SUPPORT, true);//support video
        preloadMap.put(MIntegralConstans.PROPERTIES_AD_NUM, 1);
        sdk.preload(preloadMap);
        return preloadMap;
    }

    private MTGRewardVideoHandler preloadRewardVideo(Activity activity, String unitId) {
        MTGRewardVideoHandler mMVRewardVideoHandler = new MTGRewardVideoHandler(activity, unitId);
        mMVRewardVideoHandler.setRewardVideoListener(new RewardVideoListener() {
            @Override
            public void onVideoLoadSuccess(String unitId) {
                Log.e(TAG, "onVideoLoadSuccess");
            }
            @Override
            public void onVideoLoadFail(String unitId) {
                Log.e(TAG, "onVideoLoadFail");
            }
            @Override
            public void onShowFail(String errorMsg) {
                Log.e(TAG, "onShowFail=" + errorMsg);
            }
            @Override
            public void onAdShow() {
                Log.e(TAG, "onAdShow");
            }
            @Override
            public void onAdClose(boolean isCompleteView, String RewardName, float RewardAmout) {
                Log.e(TAG, "reward info :" + "RewardName:" + RewardName + "RewardAmout:" + RewardAmout);
            }
            @Override
            public void onVideoAdClicked(String unitId) {
                Log.e(TAG, "onVideoAdClicked");
            }
        });
        mMVRewardVideoHandler.load();
        return mMVRewardVideoHandler;
    }

    private MTGMediaView generateMediaView(Context context) {
        MTGMediaView mediaView = new MTGMediaView(context);
        mediaView.setIsAllowFullScreen(true);
        mediaView.setOnMediaViewListener(new OnMTGMediaViewListener() {
            @Override
            public void onEnterFullscreen() {
                Log.e(TAG, "onEnterFullscreen");
            }

            @Override
            public void onExitFullscreen() {
                Log.e(TAG, "onExitFullscreen");
            }

            @Override
            public void onStartRedirection(Campaign campaign, String url) {
                Log.e(TAG, "onStartRedirection");
            }

            @Override
            public void onFinishRedirection(Campaign campaign, String url) {
                Log.e(TAG, "onFinishRedirection");
            }

            @Override
            public void onRedirectionFailed(Campaign campaign, String url) {
                Log.e(TAG, "onRedirectionFailed");
            }

            @Override
            public void onVideoAdClicked(Campaign campaign) {
                Log.e(TAG, "onVideoAdClicked id:" + campaign.getId());
            }
        });
        return mediaView;
    }

    private void showRewardVideo(MTGRewardVideoHandler mMVRewardVideoHandler) {
        if (mMVRewardVideoHandler == null) {
            return;
        }
        if (mMVRewardVideoHandler.isReady()) {
            mMVRewardVideoHandler.show("rewardid");
        }else{
            mMVRewardVideoHandler.load();
        }
    }

    private void showNative(Context context, final MTGMediaView mediaView, Map<String, Object> propertiesMap) {
        if (mediaView == null) {
            return;
        }
        if (propertiesMap == null) {
            propertiesMap = Collections.emptyMap();
        }
        MtgNativeHandler nativeHandle = new MtgNativeHandler(propertiesMap, context);
        nativeHandle.setAdListener(new NativeListener.NativeAdListener() {
            @Override
            public void onAdLoaded(List<Campaign> campaigns, int template) {
                mediaView.setNativeAd(campaigns.get(0));

                //mLinearLayout(mMVMediaView);
                mLinearLayout.addView(mediaView);
            }

            @Override
            public void onAdLoadError(String message) {
            }

            @Override
            public void onAdClick(Campaign campaign) {
            }

            @Override
            public void onAdFramesLoaded(List<Frame> list) {
            }

            @Override
            public void onLoggingImpression(int i) {
            }
        });
        nativeHandle.load();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
