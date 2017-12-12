package com.khemissi.houssemeddin.apptver2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfficherEmissActivity extends AppCompatActivity {


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

        mAPIService = ApiUtils.getAPIService();
                    GetEmission();



    }

    public void GetEmission() {


        Calendar calander = Calendar.getInstance();
        SimpleDateFormat DateForm = new SimpleDateFormat("yyyy-MM-dd");
        String TodayDate= DateForm.format(calander.getTime());



        mAPIService.getDbEmission(648).enqueue(new Callback<List<EmissionDB>>(){
            @Override
            public void onResponse(Call<List<EmissionDB>> call, Response<List<EmissionDB>> response) {

                if(response.isSuccessful()) {

                   // String su = "message "+response.body().getMessage();



                    final List<EmissionDB> ems = response.body();
                    System.out.println("aaaaa"+ems);
                    String[] chaines = new String[ems.size()];
                    for (int i = 0; i < ems.size(); i++) {
                     //   chaines[i] = ChainesList.get(i).getNom();

                       // String Emission_chaineid = ems.get(i).getNom();

                      //  Bundle extras = getIntent().getExtras();

                    //   String idch = extras.getString("idEms");
                        //System.out.println("idch"+idch);
                          ;
                         //   if(Emission_chaineid.equals(idch)) {
                              //  Channelset.add(ems.get(i).getNom());

                                TextView txtNom = (TextView) findViewById(R.id.txtNom);
                                TextView txtCategory = (TextView) findViewById(R.id.txtCategory);
                                TextView txtRating = (TextView) findViewById(R.id.txtRating);

                                txtNom.setText("Nom : " + ems.get(i).getNom());
                                txtCategory.setText("Categorie : " + ems.get(i).getCategorie());
                                txtRating.setText("Rating : " + ems.get(i).getDescription());
                             //   System.out.println("imgggggg"+ems.get(i).getShow().getImage().getUrl());
                                img = (ImageView) findViewById(R.id.imgView);


                                if (ems.get(i).getImage() == null) {
                                }

                                if (ems.get(i).getImage() != null){
                                    String url = ems.get(i).getImage();
                                Picasso.with(AfficherEmissActivity.this).load(url).into(img);
                            }

                          //  }


                    }
                    ChannelsList.addAll(Channelset);

                }
            }

            @Override
            public void onFailure(Call<List<EmissionDB>> call, Throwable t) {

                builder = new AlertDialog.Builder(AfficherEmissActivity.this);
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


