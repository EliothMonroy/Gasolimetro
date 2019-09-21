package com.ipn.mx.gasolimetro.usuarios;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.Automovil;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarAutomovil extends AppCompatActivity {

    private RestClientServer restClientServer=null;
    Bundle bundle;
    Activity activity;
    Long idAutomovil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultar_automovil);
        activity = this;
        bundle = getIntent().getExtras();
        idAutomovil = bundle.getLong("idAutomovil");
        restClientServer = RetrofitUtilsServer.getInstance().create(RestClientServer.class);

        //Checar los automoviles
        Call<Automovil> callCardViewAutomovil = restClientServer.getAutomovilById(idAutomovil);
        callCardViewAutomovil.enqueue(new Callback<Automovil>() {
            @Override
            public void onResponse(Call<Automovil> call, Response<Automovil> response) {
                Log.d("ConsultarAutomoviles", "request: " + new Gson().toJson(call.request()));
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        Log.d("ConsultarAutomoviles", "Exito al consultar server");
                        final Automovil automovil = (Automovil) response.body();
                        // Aqui se crean los cardview
                        ((TextView) findViewById(R.id.ConsultarMarca)).setText("Marca: " + automovil.getMarca());
                        ((TextView) findViewById(R.id.ConsutarlModelo)).setText("Modelo: " + automovil.getModelo());
                        showToolbar(automovil.getNombre(), true);
                        break;
                    default:
                        Log.e("LlenarSpinnerAutos", "error: No pude conectarme al server: code " + response.code());
                        break;
                }
            }

            @Override
            public void onFailure(Call<Automovil> call, Throwable t) {
                Log.e("ConsultarAutomovil", "error: " + t.toString());
            }
        });


        ((Button) findViewById(R.id.ConsultarEliminar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checar los automoviles
                Call<ResponseGeneric> callCardViewAutomovil = restClientServer.deleteAutoMovil(idAutomovil);
                callCardViewAutomovil.enqueue(new Callback<ResponseGeneric>() {
                    @Override
                    public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                        Log.d("ConsultarAutomoviles", "request: " + new Gson().toJson(call.request()));
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_OK:
                                mostrarAutomoviles();
                                break;
                            default:
                                Log.e("LlenarSpinnerAutos", "error: No pude conectarme al server: code " + response.code());
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                        Log.e("ConsultarAutomovil", "error: " + t.toString());
                    }
                });
            }
        });
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
    public void mostrarAutomoviles(){
        Intent intent=new Intent(this, ConsultarAutomoviles.class);
        UsuarioLogin usuarioLogin1 = getIntent().getParcelableExtra("UsuarioLogin");
        intent.putExtra("UsuarioLogin",usuarioLogin1);
        startActivity(intent);
    }
}
