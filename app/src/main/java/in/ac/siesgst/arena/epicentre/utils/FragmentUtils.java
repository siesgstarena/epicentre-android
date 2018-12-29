package in.ac.siesgst.arena.epicentre.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import in.ac.siesgst.arena.epicentre.R;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class FragmentUtils {

    /**
     * Helps with fragment transition from any fragment to a new fragment
     *
     * @param context  Application Context
     * @param fragment New Fragment Object
     */
    public static void replaceFragment(Context context, Fragment fragment) {
        FragmentTransaction ft = ((AppCompatActivity) context)
                .getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_home_layout, fragment);
        ft.commit();
    }

    /**
     * Used to update the toolbar title from every fragment in Nav Drawer Activity
     *
     * @param context Application Context
     * @param title   String containing title to update toolbar title
     */
    public static void updateTitle(Context context, String title) {
        ((AppCompatActivity) context).getSupportActionBar().setTitle(title);
    }

}
