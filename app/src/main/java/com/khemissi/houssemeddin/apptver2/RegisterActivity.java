package com.khemissi.houssemeddin.apptver2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText name,email,pass,connfpass,datenaissance;
    Button register;
    AlertDialog.Builder builder ;
    ApiInterface mAPIService ;
    Calendar ca = Calendar.getInstance();

    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.en1);
        email = (EditText) findViewById(R.id.en2);
        pass = (EditText) findViewById(R.id.en3);
        connfpass = (EditText) findViewById(R.id.en4);
        datenaissance = (EditText) findViewById(R.id.en5);


        datenaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this,DP,ca.get(Calendar.YEAR),ca.get(Calendar.MONTH),ca.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        register = (Button)findViewById(R.id.br);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("")||email.getText().toString().equals("")||pass.getText().toString().equals("")){

                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("somthing is wrong");
                    builder.setMessage("please fill all the fields");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if (!(pass.getText().toString().equals(connfpass.getText().toString()))){

                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("somthing is wrong");
                    builder.setMessage("Password is not Matching");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            pass.setText("");
                            connfpass.setText("");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    mAPIService = ApiUtils.getAPIService();
                    sendPost(name.getText().toString().trim(),email.getText().toString().trim(),datenaissance.getText().toString().trim(),pass.getText().toString().trim());


                }

            }
        });


    }
    final  DatePickerDialog.OnDateSetListener DP= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ca.set(Calendar.YEAR,year);
            ca.set(Calendar.MONTH,month);
            ca.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            String format = "yyyy/MM/dd";
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.FRANCE);
            datenaissance.setText(df.format(ca.getTime()));

        }
    };
    public void sendPost(String username, String email,String date,String password) {
        mAPIService.register(username, email,date, password).enqueue(new Callback<ServerResponce>() {
            @Override
            public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {

                if(response.isSuccessful()) {

                    String su = "message "+response.body().getMessage();
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("yes");
                    builder.setMessage(su);
                    builder.setPositiveButton("Return to login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent i12 = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(i12);
                        }
                    });

                    currentUser = response.body().getUser();


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponce> call, Throwable t) {

                builder = new AlertDialog.Builder(RegisterActivity.this);
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


