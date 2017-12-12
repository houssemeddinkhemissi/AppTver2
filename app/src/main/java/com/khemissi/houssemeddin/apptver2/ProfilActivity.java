package com.khemissi.houssemeddin.apptver2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {
    ImageView v;
    Button chose;
    ApiInterface mAPIService;
    Bitmap bitmap;
    TextView tu1;
    String s;
    SharedPreferences mPrefs;
    String email;
    String downloadimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil);
        mAPIService = ApiUtils.getAPIService();
        chose = (Button) findViewById(R.id.bu1);
        v = (ImageView) findViewById(R.id.ima25);
        tu1 = (TextView) findViewById(R.id.tu1);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);


        System.out.println(s);
        SharedPreferences prefs = getSharedPreferences("apptv", MODE_PRIVATE);
        email = prefs.getString("email", null);


        Log.d("emaiiiiiiiiiiil2", "onCreate: email" + email);
        getimage();





        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                // bitmap.setWidth(150);
                // bitmap.setHeight(150);
                //displaying selected image to imageview
                v.setImageBitmap(bitmap);
                updateimage();

                //calling the method uploadBitmap to upload image

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateimage() {
        String ima = upload();


        mAPIService.upimage(ima, email).enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                downloadimage = response.body().getMessage();

            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {

            }
        });
    }

    public void getimage() {
        mAPIService.getimage(email).enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {

                tu1.setText(response.body().getMessage());
              
                downloadimage = response.body().getMessage();
                Picasso.with(ProfilActivity.this).load("http://10.0.2.2/AppTvApi/web/"+downloadimage).into(v);

            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {

            }
        });


    }

    private String upload() {


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imagebyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebyte, Base64.DEFAULT);

    }

}
