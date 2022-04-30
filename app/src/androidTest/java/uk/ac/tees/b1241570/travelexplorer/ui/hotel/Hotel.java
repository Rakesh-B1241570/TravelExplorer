package uk.ac.tees.b1241570.travelexplorer.ui.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import studio.knowhere.travelappg.Adapter.HomelistViewAdapter;
import studio.knowhere.travelappg.R;
import studio.knowhere.travelappg.data.Data;
import studio.knowhere.travelappg.data.DataManger;
import studio.knowhere.travelappg.ui.detail.DetailActivity;

public class Hotel extends Fragment {

    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        mListView = (ListView) view.findViewById(R.id.hotellistview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataManger.getInstance().init(getActivity());
        DataManger.getInstance().addSampleData();


        // get Hash Map Data
        final HashMap<String, Data> dataHashMap = DataManger.getInstance().getSampleData();

        final ArrayList<Data> sampleDataListValue = new ArrayList<>();

        // get all keys
        for (String key : dataHashMap.keySet()) {
            sampleDataListValue.add(dataHashMap.get(key));
        }

        // Set Adapter
        HomelistViewAdapter homelistViewAdapter = new HomelistViewAdapter(
                getActivity(), sampleDataListValue
        );
        mListView.setAdapter(homelistViewAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(getString(R.string.share_intent),
                        sampleDataListValue.get(i).getCity()
                );
                startActivity(intent);
            }
        });
    }
}
