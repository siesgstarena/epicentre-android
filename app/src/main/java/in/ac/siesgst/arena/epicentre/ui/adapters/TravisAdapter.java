package in.ac.siesgst.arena.epicentre.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.database.entity.Travis;

/**
 * Created by SIESGSTarena on 05/12/18.
 */
public class TravisAdapter extends
        RecyclerView.Adapter<TravisAdapter.ViewHolder> {

    private List<Travis> travisBuilds;
    private Context mContext;

    public TravisAdapter(List<Travis> travisBuilds) {
        this.travisBuilds = travisBuilds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View buildView = inflater.inflate(R.layout.item_travis_build, viewGroup, false);
        return new ViewHolder(buildView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Travis travisBuild = travisBuilds.get(position);
        viewHolder.branch.setText(travisBuild.getBranch());
        viewHolder.message.setText(travisBuild.getMessage());
        viewHolder.state.setText("#" + travisBuild.getNumber() + " " + travisBuild.getState());
        viewHolder.startedAt.setText(travisBuild.getStartedAt());
        // Setting colors based on Build Status
        if (travisBuild.getState().equals("passed")) {
            viewHolder.state.setTextColor(mContext.getResources().getColor(R.color.buildPassed));
        } else if (travisBuild.getState().equals("failed") || travisBuild.getState()
                .equals("errored")) {
            viewHolder.state.setTextColor(mContext.getResources().getColor(R.color.buildFailed));
        }
    }

    @Override
    public int getItemCount() {
        return travisBuilds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView branch, message, state, startedAt;

        ViewHolder(View itemView) {
            super(itemView);

            branch = (TextView) itemView.findViewById(R.id.item_travis_build_branch);
            message = (TextView) itemView.findViewById(R.id.item_travis_build_message);
            state = (TextView) itemView.findViewById(R.id.item_travis_build_state);
            startedAt = (TextView) itemView.findViewById(R.id.item_travis_build_startedAt);
        }

    }
}
