package com.ari.bokingguide.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ari.bokingguide.R;

import org.jetbrains.annotations.NotNull;

public class FragmentLogin  extends Fragment {

    public FragmentLogin() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText username = v.findViewById(R.id.etUseranme);
        final EditText password = v.findViewById(R.id.etPassword);

        Button login = v.findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

}
