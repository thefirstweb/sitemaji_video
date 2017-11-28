Change Log
--

2017-11-27
---

* Upgrade third-party library

```
mobvista_alphab.aar
mobvista_common.aar
mobvista_mvjscommon.aar
mobvista_mvnative.aar
mobvista_nativeex.aar
mobvista_playercommon.aar
mobvista_reward.aar
mobvista_videocommon.aar
mobvista_videofeeds.aar

```

* MvNativeHandler AdListener add new interface

```
//native广告展示的上报的回调    
@Override
public void onLoggingImpression(int i) {
    
}

```

* MVRewardVideoHandler RewardVideoListener Adjust interface paramters

```
...

public void onVideoLoadSuccess(String unitId) {
    Log.e(TAG, "onVideoLoadSuccess");
}

...

public void onVideoLoadFail(String unitId) {
    Log.e(TAG, "onVideoLoadFail");
}

...

```
