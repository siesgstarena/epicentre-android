package in.ac.siesgst.arena.epicentre.ui.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.ac.siesgst.arena.epicentre.R;

/**
 * Created by SIESGSTarena on 05/12/18.
 */
public class HerokuAdapter extends ArrayAdapter<Pair<String, String>> {

    private ArrayList<Pair<String, String>> mArrayList;

    public HerokuAdapter(Context context, ArrayList<Pair<String, String>> arrayList) {
        super(context, 0, arrayList);
        mArrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<String, String> item = mArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Set Text on Views
        TextView heading = (TextView) convertView.findViewById(R.id.list_item_heading);
        TextView subheading = (TextView) convertView.findViewById(R.id.list_item_subheading);
        heading.setText(item.first.toUpperCase());
        subheading.setText(item.second);
        return convertView;
    }


}
