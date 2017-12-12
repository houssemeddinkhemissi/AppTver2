package com.khemissi.houssemeddin.apptver2;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 25/11/2017.
 */

public class AfficherXmlEmissActivity extends AppCompatActivity {

    protected XmlPullParserFactory xmlPullParserFactory;
    protected XmlPullParser parser;
    // TextView textView;
    ListView lstViewXml;

    AlertDialog.Builder builder ;
    ApiInterface mAPIService ;




    Calendar calander = Calendar.getInstance();
    SimpleDateFormat DateForm = new SimpleDateFormat("dd-MM-yyyy");
    String TodayDate= DateForm.format(calander.getTime());

    private final String xmlPath = "https://webnext.fr/epg_cache/programme-tv-rss_"+TodayDate+".xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_emissions_xml);

        lstViewXml= (ListView) findViewById(R.id.LstViewXml);
        // textView= (TextView)findViewById(R.id.text);


        try {




            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);
            parser = xmlPullParserFactory.newPullParser();






        } catch (XmlPullParserException e) {

            e.printStackTrace();
        }
        System.out.println("xxxxx"+xmlPath);
        BackgroundAsyncTask backgroundAsyncTask = new BackgroundAsyncTask();
        backgroundAsyncTask.execute(xmlPath);


    }



    private class BackgroundAsyncTask extends AsyncTask<String, Object, ArrayList<XmlEmission>> {
        @Override
        protected ArrayList<XmlEmission> doInBackground(String ...params) {
            URL url = null;
            ArrayList<XmlEmission> returnedResult =new ArrayList();;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
        conn = (HttpURLConnection)url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(20000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();
        parser.setInput(is, null);
        returnedResult = getLoadedXmlValues(parser);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (XmlPullParserException e) {
        e.printStackTrace();
    }
            return returnedResult;
}
    @Override
    protected void onPostExecute(ArrayList<XmlEmission> s) {
        super.onPostExecute(s);
        ArrayList<String> emisss= new ArrayList<String>();
        if(!s.equals("")){
            // textView.setText(s.get(1).getTitle());

            for (XmlEmission em : s) {

                emisss.add(em.getTitle());

            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AfficherXmlEmissActivity    .this,
                    android.R.layout.simple_list_item_1, emisss);


            lstViewXml.setAdapter(adapter);



        }
    }
}


private ArrayList<XmlEmission> getLoadedXmlValues(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String name = null;
        ArrayList<XmlEmission> emissions =new ArrayList();
    XmlEmission mEntity = new XmlEmission();
        while (eventType != XmlPullParser.END_DOCUMENT){

        switch (eventType){
        case XmlPullParser.START_DOCUMENT:
        emissions = new ArrayList();
        break;
        case XmlPullParser.START_TAG:
        name = parser.getName();



        if (name.equals("title")){
        mEntity = new XmlEmission();
        //mEntity.title=parser.nextText();

            String CurrentString = parser.nextText();
            String[] separated = CurrentString.split("\\|");
            System.out.println(separated[0]);
            mEntity.title=CurrentString;
            mEntity.Chaine=separated[0];

            //mEntity.title=separated[0];
            //System.out.println(mEntity.getTitle());
        } else if ( mEntity != null){
        if (name.equals("category")){
        mEntity.category = parser.nextText();
        } else if (name.equals("description")){
        mEntity.description = parser.nextText();
        } else if (name.equals("comments")){
        mEntity.comment = parser.nextText();
        }
        }
        break;
        case XmlPullParser.END_TAG:
        name = parser.getName();

            Bundle extras = getIntent().getExtras();
            String NomCh = extras.getString("NomCh");

            if (name.equalsIgnoreCase("item") &&  mEntity != null ){
//        if (name.equalsIgnoreCase("item") &&  mEntity != null && mEntity.Chaine.equals(NomCh)){
           // System.out.println(NomCh);
            System.out.println("here");
            mAPIService = ApiUtils.getAPIService();
            System.out.println("thiss "+mEntity.getDescription());
            sendPost(2,2,mEntity.getTitle().toString().trim(),"non",mEntity.getDescription(),mEntity.getCategory().toString().trim());
        emissions.add( mEntity);





        }
        }
        eventType = parser.next();
        }





        return emissions;
        }



    public void sendPost(int chaine,int idEmission,String nom,String image,String description,String categorie) {
        mAPIService.addEmission(chaine,idEmission,nom,image,description,categorie).enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {

                if(response.isSuccessful()) {

                    String su = "message "+response.body().getMessage();
                    builder = new AlertDialog.Builder(AfficherXmlEmissActivity.this);
                    builder.setTitle("yes");
                    builder.setMessage(su);
                    builder.setPositiveButton("aaa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });




                    AlertDialog alertDialog = builder.create();
                    //alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {

                builder = new AlertDialog.Builder(AfficherXmlEmissActivity.this);
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

