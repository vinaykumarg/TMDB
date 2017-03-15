package com.example.vinayg.tmdb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vinayg.tmdb.R;

/**
 * Created by vinay.g.
 */
public class PopularScreenFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TextView tv = (TextView) view.findViewById(R.id.section_label);
        tv.setText("popularscreen");
        return view;
    }
}
