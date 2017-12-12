package com.khemissi.houssemeddin.apptver2;

/**
 * Created by Lenovo on 25/11/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class AfficherPaysActivity extends Activity{

    ListView listePays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_pays);
        listePays = (ListView) findViewById(R.id.listePays);


        String[] arr = {"Tunisie","France","USA","Canada"};
        ArrayAdapter<String> ad = new ArrayAdapter<String>(AfficherPaysActivity.this,R.layout.element_item,arr);
        listePays.setAdapter(ad);



        listePays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    System.out.println("tunisie");
                    Intent i = new Intent(AfficherPaysActivity.this, AffChaineActivity.class);
                    i.putExtra("countryId",position);
                    startActivity(i);

                }
                else if(position==1){
                    System.out.println("france");
                    Intent i = new Intent(AfficherPaysActivity.this, AffChaineActivity.class);
                    i.putExtra("countryId",position);
                    startActivity(i);
                }
                else if(position==2){
                    System.out.println("usa");
                    Intent i = new Intent(AfficherPaysActivity.this, AjoutChaineActivity.class);
                    i.putExtra("countryId",2);
                    startActivity(i);
                }
                else {
                    System.out.println("canada");
                }

            }
        });






    }



}
