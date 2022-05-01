package uk.ac.tees.b1241570.travelexplorer.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import studio.knowhere.travelappg.R;

public class ShareFragment extends Fragment {

    private studio.knowhere.travelappg.ui.share.ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(studio.knowhere.travelappg.ui.share.ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        // final TextView textView = root.findViewById(R.id.text_share);
       /* shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}