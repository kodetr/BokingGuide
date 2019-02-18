package com.ari.bokingguide.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.network.models.Login;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLogin extends Fragment {

    private DataService nService;
    private List<Login> loginList;
    private Login mLogin;
    private EditText username;
    private EditText password;
    private ProgressDialog prgDialog;

    public FragmentLogin() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
        prgDialog = new ProgressDialog(getContext());
        prgDialog.setMessage("Tunggu sebentar...!!!");

        username = v.findViewById(R.id.etUseranme);
        password = v.findViewById(R.id.etPassword);

        Button login = v.findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionLogin();
            }
        });

        return v;
    }

    private void actionLogin() {
        final String user = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            if (TextUtils.isEmpty(user)) {
                username.setError(getString(R.string.pesan_username));
            }
            if (TextUtils.isEmpty(pass)) {
                password.setError(getString(R.string.pesan_password));
            }
        } else {
            prgDialog.show();
            Call<List<Login>> call = nService.login(user, pass);
            call.enqueue(new Callback<List<Login>>() {
                             @Override
                             public void onResponse(@NotNull Call<List<Login>> call, @NotNull Response<List<Login>> response) {
                                 if (response.isSuccessful()) {
                                     prgDialog.dismiss();
                                     loginList = response.body();
                                     for (int i = 0; i < loginList.size(); i++) {
                                         mLogin = loginList.get(i);
                                         if (mLogin.getPesan().equalsIgnoreCase(getString(R.string.login_berhasil))) {
//                                           startActivity(new Intent(getContext(), MenuTernak.class));
                                             Toast.makeText(getContext(), mLogin.getPesan(), Toast.LENGTH_SHORT).show();
                                         } else {
                                             Toast.makeText(getContext(), mLogin.getPesan(), Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 } else {
                                     prgDialog.dismiss();
                                 }
                             }

                             @Override
                             public void onFailure(@NotNull Call<List<Login>> call, @NotNull Throwable t) {
                                 prgDialog.dismiss();
                             }
                         }
            );
        }
    }
}
