package com.ari.bokingguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadVidioDestinasiActivity extends AppCompatActivity {

    private Button btnVidio;
    private DataService nService;
    private ProgressDialog prgDialog;
    private static final int SELECT_VIDEO = 3;
    private String selectedPath;
    private Uri selectedImageUri;
    private TextView tv_pathvidio;

    public UploadVidioDestinasiActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vidio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Vidio Destinasi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        tv_pathvidio = findViewById(R.id.tv_pathvidio);
        btnVidio = findViewById(R.id.btnVidio);
        btnVidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
            }
        });
        Button btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSimpan();
            }
        });
    }

    private void btnSimpan() {
        if (selectedImageUri != null) {
            uploadVidio();
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadVidio() {
        prgDialog = ProgressDialog.show(UploadVidioDestinasiActivity.this, "Proses Data", "Tunggu sebentar..!", false, false);
        prgDialog.show();

//        File file = FileUtils.getFile(this, selectedImageUri);
        File file = new File(selectedPath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part photo = MultipartBody.Part.createFormData("upload_file", file.getName(), requestFile);

        Call<ResponseBody> call = nService.upload_vidio_destinasi(getIntent().getIntExtra("id", 0), photo);
        // finally, execute the request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                prgDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(UploadVidioDestinasiActivity.this, "Berhasil diupload", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadVidioDestinasiActivity.this, "Gagal diupload", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                prgDialog.dismiss();
                Log.e("Error", t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                selectedImageUri = data.getData();

                selectedPath = getPath(selectedImageUri);
//                selectedImageUri = Uri.fromFile(new File(selectedPath));
                tv_pathvidio.setText(selectedPath);
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
