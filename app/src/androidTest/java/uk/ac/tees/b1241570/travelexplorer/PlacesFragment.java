package uk.ac.tees.b1241570.travelexplorer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import studio.knowhere.travelappg.Database.PlacesGenerator;
import studio.knowhere.travelappg.ui.Class.Place;
import studio.knowhere.travelappg.ui.Class.PlacesRvAdapter;


public class PlacesFragment extends Fragment {
    private static final String ARG_TOPIC = "ARG_TOPIC";

    public static PlacesFragment newInstance(String topic) {
        Bundle args = new Bundle();
        args.putString(ARG_TOPIC, topic);
        PlacesFragment placesFragment = new PlacesFragment();
        placesFragment.setArguments(args);
        return placesFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            throw new IllegalStateException("No arguments was sent here");
        }

        String topic = getArguments().getString(ARG_TOPIC);
        if (topic == null) {
            throw new IllegalStateException("Topic was not sent here via arguments");
        }

        // Get the list of places for the selected topic using the PlacesGenerator getPlaces() method.
        List<Place> places = PlacesGenerator.getPlaces(requireActivity(), topic);

        // Find a reference of the RecyclerView and set it up with the PlacesRvAdapter.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new PlacesRvAdapter(places));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }
}



