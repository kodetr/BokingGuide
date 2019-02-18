package com.ari.bokingguide.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ari.bokingguide.R;

import org.jetbrains.annotations.NotNull;

public class FragmentHome extends Fragment {

    public FragmentHome() {
    }

//    public static FragmentHome newInstance() {
//        FragmentHome fragment = new FragmentHome();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
