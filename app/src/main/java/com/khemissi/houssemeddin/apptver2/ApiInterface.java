package com.khemissi.houssemeddin.apptver2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by Houssem on 20/11/2017.
 */

public interface ApiInterface {



    @FormUrlEncoded
    @POST("/AppTvApi/web/app_dev.php/registerUser")
    Call<ServerResponce> register(@Field("username") String username,
                                  @Field("email") String email,
                                  @Field("date") String date,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("/AppTvApi/web/app_dev.php/login")
    Call<ServerResponce> login(@Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("/AppTvApi/web/app_dev.php/updateimage")
    Call<ServerResponce> upimage( @Field("img") String img,
                                  @Field("email") String email);
    @FormUrlEncoded
    @POST("/AppTvApi/web/app_dev.php/getimage")
    Call<ServerResponce> getimage (@Field("email") String email);



    @GET("/schedule")
    Call<List<Chaine>> getChaines(
    @Query("country") String country,
    @Query("date") String TodayDate);



    @GET("/schedule")
    Call<List<Emission>> getEmissions(
            @Query("country") String country,
            @Query("date") String TodayDate);




    @GET("/schedule")
    Call<List<Emission>> getSingleEmission(
            @Query("country") String country,
            @Query("date") String TodayDate);

    @FormUrlEncoded
    @POST("/AppTvApi/web/app_dev.php/SelectEmission")
    Call<List<EmissionDB>> getDbEmission(
            @Field("id") int id);



    @FormUrlEncoded
    @POST("/AppTvApi/web/app_dev.php/addEmission")
    Call<ServerResponce> addEmission(@Field("chaine") int chaine,
                                  @Field("idEmission") int idEmission,
                                  @Field("nom") String nom,
                                  @Field("image") String image,
                                  @Field("description") String description,
                                  @Field("categorie") String categorie
    );

    @FormUrlEncoded
    @POST("/AppTvApi/web/app_dev.php/addChaine")
    Call<ServerResponce> addChaine(@Field("nom") String nom,
                                     @Field("image") String image
    );


}
