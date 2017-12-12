package com.khemissi.houssemeddin.apptver2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView v ;
    EditText e1,e2;
    Button b1 ;
    ApiInterface mAPIService ;
    AlertDialog.Builder builder ;
    SharedPreferences mPrefs ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        v = (TextView) findViewById(R.id.TS);
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        b1= (Button) findViewById(R.id.b1);


        mPrefs = getPreferences(MODE_PRIVATE);
        mAPIService = ApiUtils.getAPIService();

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= ""+e1.getText().toString()+""+e2.getText().toString();
                Toast.makeText(LoginActivity.this,s1 , Toast.LENGTH_SHORT).show();
                mAPIService = ApiUtils.getAPIService();
                login(e1.getText().toString().trim(),e2.getText().toString().trim());



            }
        });
    }
    public void login(final String email, final String password) {
        mAPIService.login( email, password).enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {

                if(response.isSuccessful()) {


                    Toast.makeText(LoginActivity.this, ""+email+""+password, Toast.LENGTH_SHORT).show();
                    String su = "yes";
                    builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("yes");
                    builder.setMessage(su);
                    builder.setPositiveButton("aaa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();


                            SharedPreferences.Editor prefsEditor = getSharedPreferences("apptv", MODE_PRIVATE).edit();

                            prefsEditor.putString("email", email);

                            Log.d("emaiiiiiiiiiiil", "onResponse: email"+email);

                            prefsEditor.apply();
                            Intent i60 = new Intent(LoginActivity.this,ProfilActivity.class);

                            startActivity(i60);

                        }
                    });




                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {

                builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("no");
                builder.setMessage("Problem de connection ésayer plus tard");
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
