package com.positive.themeselector.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.positive.themeselector.R;
import com.positive.themeselector.adapter.ThemeAdapter;
import com.positive.themeselector.model.Theme;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemeFragment extends Fragment {

    private static final String ARG_PARAM1 = "cat_id";
    private static final String ARG_PARAM2 = "themes";
    private int mParam1;
    Context context;

    RecyclerView recyclerViewThemes;
    ThemeAdapter themeAdapter;

    public ArrayList<Theme> themes;
    public ThemeFragment() {
    }


    public static ThemeFragment newInstance(int param1, ArrayList<Theme> themes) {
        ThemeFragment fragment = new ThemeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, themes);
        Log.e(ARG_PARAM1, param1 + "");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        themes = (ArrayList<Theme>) getArguments().getSerializable(ARG_PARAM2);
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        binding(view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerViewThemes.setLayoutManager(gridLayoutManager);
        themeAdapter = new ThemeAdapter(context);
        recyclerViewThemes.setAdapter(themeAdapter);
        themeAdapter.setSoundArrayList(themes);
        return view;
    }

    public void binding(View view) {
        recyclerViewThemes = view.findViewById(R.id.recycler_view_themes);
    }
}