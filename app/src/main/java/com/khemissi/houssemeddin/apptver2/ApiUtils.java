package com.khemissi.houssemeddin.apptver2;

/**
 * Created by Houssem on 20/11/2017.
 */

public class ApiUtils {

   // BASE_URL= "http://10.0.2.2:8080/";

        private ApiUtils() {}

    public static final String BASE_URL= "http://10.0.2.2/";



        public static ApiInterface getAPIService() {

            return ApiClient.getApiClient(BASE_URL).create(ApiInterface.class);
        }

    public static final String BASE_URL_TV= "http://api.tvmaze.com/";

    public static ApiInterface getAPITVService() {

        return ApiClient.getApiClient(BASE_URL_TV).create(ApiInterface.class);
    }


}
