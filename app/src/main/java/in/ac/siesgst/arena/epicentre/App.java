package in.ac.siesgst.arena.epicentre;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * Initialization of Fast Android Networking
         * Reference: https://github.com/amitshekhariitbhu/Fast-Android-Networking
         */
        AndroidNetworking.initialize(getApplicationContext());

        /**
         * Custom Fonts using Calligraphy
         * Reference: https://github.com/chrisjenx/Calligraphy
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Rubik-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
