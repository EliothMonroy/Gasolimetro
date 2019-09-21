package com.ipn.mx.gasolimetro.datos.api;

import com.ipn.mx.gasolimetro.datos.modelos.GasolinerasFeed;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestClientTest {

    @POST("test")
    Call<GasolinerasFeed> getGasolineras(@Body GasolinerasFeed gasolinerasFeed);


}

