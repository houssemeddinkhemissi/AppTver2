package com.khemissi.houssemeddin.apptver2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class AjoutChaineActivity extends AppCompatActivity {


    AlertDialog.Builder builder ;
    ApiInterface mAPIService ;
    ListView listView;
    List<String> ChannelsList = new ArrayList<String>();
    HashSet<String> Channelset = new HashSet<String>();
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_chaine);
        listView = (ListView) findViewById(R.id.LstView);


        Bundle extras = getIntent().getExtras();
        int countryId = extras.getInt("countryId");



        if (countryId == 0){
            List<String> TunisianChannels = new ArrayList<String>();
            TunisianChannels.add("Wataniya 1");
            TunisianChannels.add("Wataniya 2");

            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
           final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    TunisianChannels );

            listView.setAdapter(arrayAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // System.out.println(position);
                    long idChaine = listView.getItemIdAtPosition(position);
                    System.out.println(idChaine);
                    //System.out.println(idChaine);

                    if(idChaine==0) {
                        Intent i = new Intent(AjoutChaineActivity.this, AfficherHtmlEmissActivity.class);
                        i.putExtra("idch", idChaine);
                        startActivity(i);
                    }
                }
            });


        }


        if (countryId == 2){
            mAPIService = ApiUtils.getAPITVService();
        GetChannels();
          }


    }

    public void GetChannels() {


        Calendar calander = Calendar.getInstance();
        SimpleDateFormat DateForm = new SimpleDateFormat("yyyy-MM-dd");
        String TodayDate= DateForm.format(calander.getTime());


        mAPIService.getChaines("US",TodayDate).enqueue(new Callback<List<Chaine>>(){
            @Override
            public void onResponse(Call<List<Chaine>> call, Response<List<Chaine>> response) {

                if(response.isSuccessful()) {

                   // String su = "message "+response.body().getMessage();
                    final List<Chaine> ChainesList = response.body();

                    String[] chaines = new String[ChainesList.size()];
                    for (int i = 0; i < ChainesList.size(); i++) {
                     //   chaines[i] = ChainesList.get(i).getNom();

                        //Channelset.add(ChainesList.get(i).getShow().getNetwrk().getName());

                        if (ChannelsList.contains(ChainesList.get(i).getShow().getNetwrk().getName()))
                        {

                        }
                        else{

                            ChannelsList.add(ChainesList.get(i).getShow().getNetwrk().getName());
                        }

                      //  System.out.println(chaines[i]);


                    }
                 //   ChannelsList.addAll(Channelset);


                   final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AjoutChaineActivity.this,
                            android.R.layout.simple_list_item_1, ChannelsList);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           // System.out.println(position);
                            String idChaine = ChannelsList.get(position);
                            //System.out.println(idChaine);


                            Intent i = new Intent(AjoutChaineActivity.this, ListEmissionsActivity.class);
                            i.putExtra("idch",idChaine);
                            startActivity(i);
                        }
                    });

                    builder = new AlertDialog.Builder(AjoutChaineActivity.this);
                    builder.setTitle("Chaines Disponibles");
                    builder.setMessage("voici la liste des chaines disponibles");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    //currentUser = response.body().getUser();


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<List<Chaine>> call, Throwable t) {

                builder = new AlertDialog.Builder(AjoutChaineActivity.this);
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


