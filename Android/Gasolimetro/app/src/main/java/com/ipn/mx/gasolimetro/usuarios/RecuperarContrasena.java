package com.ipn.mx.gasolimetro.usuarios;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.Login;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;

import java.net.HttpURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarContrasena extends AppCompatActivity {

    RestClientServer restClientServer;
    ResponseGeneric resultsServer;
    TextView errorView;
    TextInputEditText correo;
    Button botonRecuperar;
    String email;
    ProgressBar progressBar;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);
        activity=this;

        showToolbar(getResources().getString(R.string.toolbar_tittle_recuperar_contrasena),true);

        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);

        errorView=findViewById(R.id.mensaje_error);
        correo=findViewById(R.id.recuperar_correo);
        botonRecuperar=findViewById(R.id.recuperarContra_boton);
        progressBar=findViewById(R.id.progress_bar);
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void recuperarContrasena(View view){

        email=correo.getText().toString();
        botonRecuperar.setEnabled(false);
        errorView.setText("");

        if(email.equals("")){
            errorView.setText(getText(R.string.recuperar_contra_campo_vacio));
            botonRecuperar.setEnabled(true);

        }else{
            botonRecuperar.setText("");
            progressBar.setVisibility(View.VISIBLE);
            validarDatos();
        }

    }

    public void validarDatos(){

        Call<ResponseGeneric> call = restClientServer.recuperarPassword(email);

        call.enqueue(new Callback<ResponseGeneric>() {
            @Override
            public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                //Log.d("obtenerGasolinerasSer", "response batiz: "+new Gson().toJson(call.request()));
                switch (response.code()) {
                    case HttpURLConnection.HTTP_ACCEPTED:
                        Log.d("recuperarContra", "exito batiz");
                        Log.d("recuperarContra", "response batiz: "+new Gson().toJson(response));
                        resultsServer = response.body();
                        botonRecuperar.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        botonRecuperar.setText(getString(R.string.text_button_recuperar_contra));

                        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(activity.getResources().getString(R.string.titulo_exito_recuperar_contra))
                                .setContentText(resultsServer.getMsg())
                                .show();

                        break;
                    default:
                        Log.e("recuperarContra", "error batiz: No pude conectarme, estoy en el default"+new Gson().toJson(response));
                        errorView.setText(getText(R.string.error_server_no_disponible));
                        botonRecuperar.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        botonRecuperar.setText(getString(R.string.text_button_recuperar_contra));
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                Log.e("recuperarContra", "error: "+t.toString());
                Toast.makeText(getApplicationContext(), getText(R.string.error_server_no_disponible),Toast.LENGTH_SHORT).show();
                errorView.setText(getText(R.string.error_server_no_disponible));
                botonRecuperar.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                botonRecuperar.setText(getString(R.string.text_button_recuperar_contra));
            }
        });
    }

}
