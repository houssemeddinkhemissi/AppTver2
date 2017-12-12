package com.khemissi.houssemeddin.apptver2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfficherEmissionActivity extends AppCompatActivity {


    AlertDialog.Builder builder ;
    ApiInterface mAPIService ;
    ListView listView;
    List<String> ChannelsList = new ArrayList<String>();
    HashSet<String> Channelset = new HashSet<String>();
    public static User currentUser;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afficher_emission);

                    mAPIService = ApiUtils.getAPITVService();
                    GetEmission();



    }

    public void GetEmission() {


        Calendar calander = Calendar.getInstance();
        SimpleDateFormat DateForm = new SimpleDateFormat("yyyy-MM-dd");
        String TodayDate= DateForm.format(calander.getTime());



        mAPIService.getSingleEmission("US",TodayDate).enqueue(new Callback<List<Emission>>(){
            @Override
            public void onResponse(Call<List<Emission>> call, Response<List<Emission>> response) {

                if(response.isSuccessful()) {

                   // String su = "message "+response.body().getMessage();
                    final List<Emission> ems = response.body();

                    String[] chaines = new String[ems.size()];
                    for (int i = 0; i < ems.size(); i++) {
                     //   chaines[i] = ChainesList.get(i).getNom();

                        String Emission_chaineid = ems.get(i).getNom();

                        Bundle extras = getIntent().getExtras();

                       String idch = extras.getString("idEms");
                        //System.out.println("idch"+idch);
                          ;
                            if(Emission_chaineid.equals(idch)) {
                                Channelset.add(ems.get(i).getNom());

                                TextView txtNom = (TextView) findViewById(R.id.txtNom);
                                TextView txtCategory = (TextView) findViewById(R.id.txtCategory);
                                TextView txtRating = (TextView) findViewById(R.id.txtRating);

                                txtNom.setText("Nom : " + ems.get(i).getNom());
                                txtCategory.setText("Categorie : " + ems.get(i).getShow().getType());
                                txtRating.setText("Rating : " + ems.get(i).getShow().getRating().getAverage());
                             //   System.out.println("imgggggg"+ems.get(i).getShow().getImage().getUrl());
                                img = (ImageView) findViewById(R.id.imgView);


                                if (ems.get(i).getShow().getImage().getUrl() == null) {
                                }

                                if (ems.get(i).getShow().getImage().getUrl() != null){
                                    String url = ems.get(i).getShow().getImage().getUrl();
                                Picasso.with(AfficherEmissionActivity.this).load(url).into(img);
                            }

                            }


                    }
                    ChannelsList.addAll(Channelset);

                }
            }

            @Override
            public void onFailure(Call<List<Emission>> call, Throwable t) {

                builder = new AlertDialog.Builder(AfficherEmissionActivity.this);
                builder.setTitle("no");
                builder.setMessage(t.getMessage());
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }
}


