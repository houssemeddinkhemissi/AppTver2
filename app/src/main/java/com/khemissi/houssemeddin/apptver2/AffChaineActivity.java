package com.khemissi.houssemeddin.apptver2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 27/11/2017.
 */


public class AffChaineActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    AlertDialog.Builder builder ;
    ApiInterface mAPIService ;

   // public static final int image_ids=0x7f0b0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affich_chaines);

        gridView = (GridView) findViewById(R.id.gridView);

        Bundle extras = getIntent().getExtras();
        final int countryId = extras.getInt("countryId");

        if(countryId==0){


        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getDataTunisianChannels());}

       if(countryId==1){
            gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getDataFrenchChannels());}

        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                System.out.println(position);
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);


                if(countryId==0){
                    Intent i = new Intent(AffChaineActivity.this,AfficherHtmlEmissActivity.class);
                   i.putExtra("NomCh",item.getTitle());
                    startActivity(i);}


                if(countryId==1){
                Intent i = new Intent(AffChaineActivity.this,AfficherXmlEmissActivity.class);
                i.putExtra("NomCh",item.getTitle());
                startActivity(i);}



            }
        });
    }

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getDataFrenchChannels() {

        List<String> ChannelsNames = Arrays.asList("6ter ","Arte ","BFMTV ","C8 ","Canal+ ","Chérie 25 ",
                "CNews ","CStar ","France 2 ","France 3 ","France 4 ","France 5 ",
                "France Ô ","Gulli ","HD1 ","L'Équipe ","M6 ","NRJ 12 ","NT1 ","Numéro 23 ","Public Sénat - LCP AN ",
                "RMC Découverte ","TF1 ","TMC ","W9 "
        );

        for(int i = 0 ; i < ChannelsNames.size(); i++){
            mAPIService = ApiUtils.getAPIService();
            sendPost(ChannelsNames.get(i),"non");
        }




        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        System.out.println("first"+ChannelsNames.get(1));
        System.out.println("lengggg"+imgs.length());
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));

            imageItems.add(new ImageItem(bitmap, ChannelsNames.get(i) ));
        }
        return imageItems;
    }

    private ArrayList<ImageItem> getDataTunisianChannels() {

        List<String> ChannelsNames = Arrays.asList("Wataniya 1","Wataniya 2");

        for(int i = 0 ; i < ChannelsNames.size(); i++){
            mAPIService = ApiUtils.getAPIService();
            sendPost(ChannelsNames.get(i),"non");
        }

        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids_tunisian);


        System.out.println("first"+ChannelsNames.get(1));
        System.out.println("lengggg"+imgs.length());
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));

            imageItems.add(new ImageItem(bitmap, ChannelsNames.get(i) ));
        }
        return imageItems;
    }


    public void sendPost(String nom,String image) {
        mAPIService.addChaine(nom,image).enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {

                if(response.isSuccessful()) {

                    String su = "message "+response.body().getMessage();
                    builder = new AlertDialog.Builder(AffChaineActivity.this);
                    builder.setTitle("yes");
                    builder.setMessage(su);
                    builder.setPositiveButton("aaa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                }
            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {

                builder = new AlertDialog.Builder(AffChaineActivity.this);
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
