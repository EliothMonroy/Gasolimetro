package com.ipn.mx.gasolimetro.datos.api;

import com.ipn.mx.gasolimetro.datos.modelos.AgregarAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.AgregarSensor;
import com.ipn.mx.gasolimetro.datos.modelos.Automovil;
import com.ipn.mx.gasolimetro.datos.modelos.GasolineraConsultaModel;
import com.ipn.mx.gasolimetro.datos.modelos.GasolinerasFeed;
import com.ipn.mx.gasolimetro.datos.modelos.InfoGasolineraServer;
import com.ipn.mx.gasolimetro.datos.modelos.Login;
import com.ipn.mx.gasolimetro.datos.modelos.MedicionFeed;
import com.ipn.mx.gasolimetro.datos.modelos.RegistrarUsuarioPeticion;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.Sensor;
import com.ipn.mx.gasolimetro.datos.modelos.SensorAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestClientServer {

    @POST("medicion-service/mediciones")
    Call<ResponseGeneric> registrarMedicion(@Body MedicionFeed medicion);

    @POST("usuario-service/login")
    Call<ResponseGeneric<UsuarioLogin>> getLogin(@Body Login login);

    @POST("usuario-service/automoviles")
    Call<ResponseGeneric> agregarAutomoviles(@Body AgregarAutomovil agregarAutomovil);

    @GET("medicion-service/mediciones/sensor/{idUsuarios}")
    Call<ResponseGeneric<List<SensorAutomovil>>> getSensorAutomovil(@Path("idUsuarios") Long idUsuarios);

    @GET("usuario-service/automoviles/usuario/{idUsuarios}")
    Call<List<Automovil>> getAutomoviles(@Path("idUsuarios") Long idUsuarios);

    @GET("usuario-service/automovil/{idAutomovil}")
    Call<Automovil> getAutomovilById(@Path("idAutomovil") Long idAutomoviles);

    @DELETE("usuario-service/automoviles/{idAutomovil}")
    Call<ResponseGeneric> deleteAutoMovil(@Path("idAutomovil") Long idAutomoviles);


    @POST("gasolinera-service/gasolineras/mapa")
    Call<List<GasolineraConsultaModel>> getGasolineras(@Body GasolinerasFeed gasolinerasFeed);

    @GET("usuario-service/password/{correo}")
    Call<ResponseGeneric> recuperarPassword(@Path("correo") String correo);

    @POST("usuario-service/usuarios")
    Call<ResponseGeneric> registrarUsuario(@Body RegistrarUsuarioPeticion agregarUsuario);

    @GET("gasolinera-service/gasolineras/{idGasolinera}")
    Call<ResponseGeneric<InfoGasolineraServer>> obtenerInfoGasolinera(@Path("idGasolinera") Long idGasolinera);

    @GET("medicion-service/sensores/usuario/{idUsuario}")
    Call<List<Sensor>> getSensores(@Path("idUsuario") Long idUsuario);


    @POST("medicion-service/sensores")
    Call<ResponseGeneric> registrarSensor(@Body AgregarSensor agregarSensor);

}

