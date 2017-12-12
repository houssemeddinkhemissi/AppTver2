package com.khemissi.houssemeddin.apptver2;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 25/11/2017.
 */

public class AfficherHtmlEmissActivity extends AppCompatActivity {
   // private TextView result;
   List<String> Emissions= new ArrayList<String>();
    AlertDialog.Builder builder ;
    ApiInterface mAPIService ;
    ListView listViewHtml;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_emissions_html);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //result = (TextView) findViewById(R.id.result);
       listViewHtml= (ListView) findViewById(R.id.LstViewHtml);

        new JsoupListView().execute();

    }

    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
                final StringBuilder builder = new StringBuilder();

                try {
                    //     Document doc = Jsoup.connect("http://www.ssaurel.com/blog/").get();
                    Document doc = Jsoup.connect("http://www.watania1.tn/%D8%A7%D9%84%D8%A8%D8%B1%D9%86%D8%A7%D9%85%D8%AC-%D8%A7%D9%84%D8%A3%D8%B3%D8%A8%D9%88%D8%B9%D9%8A").get();
                    String title = doc.title();
                    //  Elements links = doc.select("a[href]");

                    //  Elements links = doc.getElementsByClass("elemProgTitre");

                    Calendar calander = Calendar.getInstance();

                    int TodayDate= calander.get(Calendar.DAY_OF_WEEK)-1;
                    System.out.println("daaaaate"+TodayDate);

                    String test="menu"+TodayDate;
                    System.out.println("aaaaaa"+test);
                    Elements links = doc.getElementById(test).getElementsByClass("elemProg");
                   // if(TodayDate==2){Elements links = doc.getElementById("menu2").getElementsByClass("elemProg");}

                    Elements divTag = doc.getElementsByClass("elemProgTitre");

                    System.out.println(divTag);


               //     builder.append(title).append("\n");

                 /*   for (Element link : links) {
                        builder.append("\n").append("link : ").append(link.attr("elemProgDate"))
                                .append("\n").append("text: ").append(link.text());
                    }
                    */




                    for (Element link : links) {


                        mAPIService = ApiUtils.getAPIService();
                        sendPost(1,2,link.getElementsByClass("elemProgTitre").text(),link.getElementsByClass("elemProgImg").select("img").attr("src").toString().trim(),link.getElementsByClass("elemProgDesc").text(),link.getElementsByClass("elemProgDate").text());


                        builder.append("\n").append("titre : ").append(link.getElementsByClass("elemProgTitre").text());
    Emissions.add(link.getElementsByClass("elemProgTitre").text()+"  "+link.getElementsByClass("elemProgDate").text());

                             //  .append("\n").append("date: ").append(link.getElementsByClass("elemProgDate").text())
                             //   .append("\n").append("image: ").append(link.getElementsByClass("elemProgImg").select("img").attr("src"))
                             //   .append("\n").append("description: ").append(link.getElementsByClass("elemProgDesc").text()) .append("------").append("\n");


                    }

                } catch (IOException e) {
                    builder.append("Error :").append(e.getMessage()).append("\n");

                }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AfficherHtmlEmissActivity.this,
                    android.R.layout.simple_list_item_1, Emissions);




                       listViewHtml.setAdapter(adapter);
                 progressBar.setVisibility(View.INVISIBLE);
                    }


    }


    public void sendPost(int chaine,int idEmission,String nom,String image,String description,String categorie) {
        mAPIService.addEmission(chaine,idEmission,nom,image,description,categorie).enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {

                if(response.isSuccessful()) {

                    String su = "message "+response.body().getMessage();
                    builder = new AlertDialog.Builder(AfficherHtmlEmissActivity.this);
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

                builder = new AlertDialog.Builder(AfficherHtmlEmissActivity.this);
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

