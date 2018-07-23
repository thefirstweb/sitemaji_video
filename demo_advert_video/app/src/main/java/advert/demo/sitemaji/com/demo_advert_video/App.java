package advert.demo.sitemaji.com.demo_advert_video;

import android.app.Application;

import com.mintegral.msdk.MIntegralSDK;
import com.mintegral.msdk.out.MIntegralSDKFactory;

import java.util.Map;

/**
 * Created by showsky on 2018/7/23.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //  init SDK
        MIntegralSDK sdk = MIntegralSDKFactory.getMIntegralSDK();
        String appId = "92762";//test ID
        String appKey = "936dcbdd57fe235fd7cf61c2e93da3c4";//test key
        Map<String, String> map = sdk.getMTGConfigurationMap(appId, appKey);
        sdk.init(map, this);
    }
}
