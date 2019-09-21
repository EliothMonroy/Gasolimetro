package com.ipn.mx.gasolimetro.datos.api;

import com.ipn.mx.gasolimetro.datos.modelos.DistanciaFeed;
import com.ipn.mx.gasolimetro.datos.modelos.PlaceFeed;
import com.ipn.mx.gasolimetro.datos.modelos.PlacesFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestClient {

    ///nearbysearch/json?location=19.504507,-99.146911&radius=500&types=gas_station&key=

    @GET("place/nearbysearch/json")
    Call<PlacesFeed> getPlaces(@Query("location") String loc, @Query("radius") String radius,@Query("types") String types,@Query("key") String key);


    //details/json?placeid=ChIJzz89naj50YURXUjX0-d_6hk&fields=formatted_address&key=

    @GET("place/details/json")
    Call<PlaceFeed> getPlaceInfo(@Query("placeid") String id, @Query("fields") String fields, @Query("key") String key);

    //json?units=imperial&language=es-419&origins=19.504507,%20-99.146911&destinations=19.50213,-99.14932&key=

    @GET("distancematrix/json")
    Call<DistanciaFeed> getDistance(@Query("units") String units, @Query("language") String language, @Query("origins") String origins, @Query("destinations") String destinations, @Query("key") String key);

}

