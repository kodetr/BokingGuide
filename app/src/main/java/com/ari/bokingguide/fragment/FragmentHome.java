package com.ari.bokingguide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.ViewGuideActivity;
import com.ari.bokingguide.utils.CircleImageView;
import com.ari.bokingguide.utils.EffectButton;

import org.jetbrains.annotations.NotNull;

public class FragmentHome extends Fragment {

    private Animation anim;
    private CircleImageView btnPengguna;
    private CircleImageView btnLokasi;

    public FragmentHome() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        double animationDuration = 0.1 * 2000;
        EffectButton effect = new EffectButton(1, 3);
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        anim.setDuration((long) animationDuration);
        anim.setInterpolator(effect);

        btnPengguna = v.findViewById(R.id.btnPengguna);
        btnLokasi = v.findViewById(R.id.btnLokasi);
        btnPengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPengguna.startAnimation(anim);
                startActivity(new Intent(getContext(), ViewGuideActivity.class));
            }
        });
        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLokasi.startAnimation(anim);
                Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
