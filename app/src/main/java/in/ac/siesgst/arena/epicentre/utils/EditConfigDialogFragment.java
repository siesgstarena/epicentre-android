package in.ac.siesgst.arena.epicentre.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import in.ac.siesgst.arena.epicentre.R;

/**
 * Created by SIESGSTarena on 06/12/18.
 */
public class EditConfigDialogFragment extends DialogFragment {

    private EditText mEditText;

    public EditConfigDialogFragment() {
    }

    public static EditConfigDialogFragment newInstance(String title) {
        EditConfigDialogFragment fragment = new EditConfigDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.config_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.heroku_edit_config);
        String title = getArguments().getString("title", "Enter Value");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


}

