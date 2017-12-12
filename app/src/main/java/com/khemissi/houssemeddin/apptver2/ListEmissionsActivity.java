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
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 20/11/2017.
 */

public class ListEmissionsActivity extends AppCompatActivity {




   // Button btn;
    AlertDialog.Builder builder ;
    ApiInterface mAPIService ;
    ListView listView;
    List<String> ChannelsList = new ArrayList<String>();
    HashSet<String> Channelset = new HashSet<String>();
    public static User currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_emissions);
        listView = (ListView) findViewById(R.id.LstView);


                mAPIService = ApiUtils.getAPITVService();
                GetEmissions();

    }

    public void GetEmissions() {

        Calendar calander = Calendar.getInstance();
        SimpleDateFormat DateForm = new SimpleDateFormat("yyyy-MM-dd");
        String TodayDate= DateForm.format(calander.getTime());



        mAPIService.getEmissions("US",TodayDate).enqueue(new Callback<List<Emission>>(){
            @Override
            public void onResponse(Call<List<Emission>> call, Response<List<Emission>> response) {

                if(response.isSuccessful()) {

                    // String su = "message "+response.body().getMessage();
                    final List<Emission> ems = response.body();




               //     String[] chaines = new String[ChainesList.size()];
                    for (int i = 0; i < ems.size(); i++) {
                        //   chaines[i] = ChainesList.get(i).getNom();
                        String Emission_chaineid = ems.get(i).getShow().getNetwrk().getName();

                        Bundle extras = getIntent().getExtras();

                            String idch = extras.getString("idch");
                           /// System.out.println("idch"+idch);


                        if(Emission_chaineid.equals(idch)){
                        Channelset.add(ems.get(i).getNom());}
                        //  System.out.println(chaines[i]);


                    }
                    ChannelsList.addAll(Channelset);


                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListEmissionsActivity.this,
                            android.R.layout.simple_list_item_1, ChannelsList);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //int idEms =ems.get(position).getId();
                            String idEms=ChannelsList.get(position).toString();

                           // System.out.println(idEms);
                            Intent i = new Intent(ListEmissionsActivity.this, AfficherEmissionActivity.class);
                            i.putExtra("idEms",idEms);
                            startActivity(i);
                        }
                    });

                    builder = new AlertDialog.Builder(ListEmissionsActivity.this);
                    builder.setTitle("Emissions disponibles");
                    //builder.setMessage(chaines[1]);
                    builder.setPositiveButton("voici la liste des chaines disponibles", new DialogInterface.OnClickListener() {
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
            public void onFailure(Call<List<Emission>> call, Throwable t) {

                builder = new AlertDialog.Builder(ListEmissionsActivity.this);
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
