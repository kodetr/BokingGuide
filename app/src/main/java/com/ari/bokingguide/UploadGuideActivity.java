package com.ari.bokingguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.utils.CircleImageView;
import com.ari.bokingguide.utils.FileUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadGuideActivity extends AppCompatActivity {

    private CircleImageView ivFoto;
    private DataService nService;
    private ProgressDialog prgDialog;
    private static final int GALLERY_REQUEST = 1;
    private Uri fotoURI;

    public UploadGuideActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_foto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ganti Foto Guide");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        ivFoto = findViewById(R.id.ivFoto);
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
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
        if (fotoURI != null) {
            uploadFoto();
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFoto() {
        prgDialog = ProgressDialog.show(UploadGuideActivity.this, "Proses Data", "Tunggu sebentar..!", false, false);
        prgDialog.show();

        File file = FileUtils.getFile(this, fotoURI);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part photo = MultipartBody.Part.createFormData("upload_file", file.getName(), requestFile);

        Call<ResponseBody> call = nService.upload_foto_guide(getIntent().getIntExtra("id", 0), photo);
        // finally, execute the request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                prgDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(UploadGuideActivity.this, "Berhasil diubah", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadGuideActivity.this, "Gagal diubah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                prgDialog.dismiss();
                Log.e("Error",t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        masuk ke layout crop
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
//        menampilkan photo sesudah di crop
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                fotoURI = result.getUri();
//              menampilkan photo
                ivFoto.setImageURI(fotoURI);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e("EEEE", "Error");
            }
        }
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
