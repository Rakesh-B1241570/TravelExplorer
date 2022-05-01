package uk.ac.tees.b1241570.travelexplorer.ui.Myschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import studio.knowhere.travelappg.Adapter.TripAdapter;
import studio.knowhere.travelappg.Database.SqliteHelper;
import studio.knowhere.travelappg.R;
import studio.knowhere.travelappg.ui.Class.TripPlacePoJO;

public class SlideshowFragment extends Fragment {

    private studio.knowhere.travelappg.ui.Myschedule.SlideshowViewModel slideshowViewModel;
    private TripAdapter mAdapter;
    private List<TripPlacePoJO> tripPlacePoJOS = new ArrayList<>();
    private RecyclerView recyclerView;
    SqliteHelper sqliteHelper;
    Button mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(studio.knowhere.travelappg.ui.Myschedule.SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        mMap = (Button) root.findViewById(R.id.map_btn);
        sqliteHelper = new SqliteHelper(getContext());

        tripPlacePoJOS.addAll(sqliteHelper.getAllTripPlans());

        //  Log.v("TAG","GET ALL TRIP VALUE"+String.valueOf(tripPlacePoJOS));
        mAdapter = new TripAdapter(getContext(), tripPlacePoJOS);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
      /*  RecyclerViewMargin decoration = new RecyclerViewMargin(itemMargin, numColumns);
        recyclerView.addItemDecoration(decoration);*/
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //  recyclerView.addItemDecoration(new DividerItemDecoration(, 16));
        recyclerView.setAdapter(mAdapter);

        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent intent = new Intent(getContext(), MapsActivity.class);
                //startActivity(intent);
            }
        });
        return root;
    }
}