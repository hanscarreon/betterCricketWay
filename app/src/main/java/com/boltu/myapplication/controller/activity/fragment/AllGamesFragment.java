package com.boltu.myapplication.controller.activity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boltu.myapplication.adapter.AllGamesAdapter;
import com.boltu.myapplication.adapter.GamesAdapter;
import com.boltu.myapplication.controller.GlobalController;
import com.boltu.myapplication.model.games.MatchListModel;
import com.sports.bettercricketway.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllGamesFragment extends Fragment {
    GlobalController globalController;
    RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllGamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllGamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllGamesFragment newInstance(String param1, String param2) {
        AllGamesFragment fragment = new AllGamesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_games, container, false);
            globalController = new GlobalController(getContext());
            recyclerView = view.findViewById(R.id.games_all_recycler);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(llm);

            List<MatchListModel> matchList = globalController.retrieveAllGames();
            AllGamesAdapter adapter = new AllGamesAdapter(getContext(), matchList);
            recyclerView.setAdapter(adapter);
        return view;
    }
}