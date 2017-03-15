package com.example.vinayg.tmdb.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

<<<<<<< HEAD
import com.example.manasaa.imdb.favoritesAdapter.FavoritesAdapter;
import com.example.vinayg.tmdb.R;

=======
import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.adapters.FavoritesAdapter;
>>>>>>> 9ecd4a27b0a55bc721839bbb10bef64bdce3c9b0

/**
 *Reference:
 * https://android--code.blogspot.in/2015/12/android-recyclerview-grid-layout-example.html
 * https://guides.codepath.com/android/using-the-recyclerview
 */
public class FavoriteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorite, container, false); // Inflate the layout for this fragment

        Context mContext =  getActivity().getApplicationContext();

        // Get the widgets reference from XML layout
       RelativeLayout mRelativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
       RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewFavorites);
        // Initialize a new String array
        String[] animals = {
                "Aardvark",
                "Albatross",
                "Alligator",
                "Alpaca",
                "Ant",
                "Anteater",
                "Antelope",
                "Ape",
                "Armadillo",
                "Donkey",
                "Baboon",
                "Badger",
                "Barracuda",
                "Bear",
                "Beaver",
                "Bee", "Armadillo",
                "Donkey",
                "Baboon",
                "Badger",
                "Barracuda",
                "Bear",
                "Beaver",
                "Bee"
        };

        RecyclerView.LayoutManager mLayoutManager =new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(mLayoutManager);


         RecyclerView.Adapter mAdapter = new FavoritesAdapter(mContext,animals);  // Initialize a new instance of RecyclerView Adapter instance

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


}
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public FavoriteFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FavoriteFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FavoriteFragment newInstance(String param1, String param2) {
//        FavoriteFragment fragment = new FavoriteFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
// TODO: Rename method, update argument and hook method into UI event
//public void onButtonPressed(Uri uri) {
//    if (mListener != null) {
//        mListener.onFragmentInteraction(uri);
//    }
//}
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
///**
// * This interface must be implemented by activities that contain this
// * fragment to allow an interaction in this fragment to be communicated
// * to the activity and potentially other fragments contained in that
// * activity.
// * <p>
// * See the Android Training lesson <a href=
// * "http://developer.android.com/training/basics/fragments/communicating.html"
// * >Communicating with Other Fragments</a> for more information.
// */
//public interface OnFragmentInteractionListener {
//    // TODO: Update argument type and name
//    void onFragmentInteraction(Uri uri);
//}

