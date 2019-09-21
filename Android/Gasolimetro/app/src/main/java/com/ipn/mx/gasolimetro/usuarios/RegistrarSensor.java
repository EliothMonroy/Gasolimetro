package com.ipn.mx.gasolimetro.usuarios;

import android.app.Activity;
import android.content.Intent;
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
import com.ipn.mx.gasolimetro.ConsultarMapaMenu;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.AgregarSensor;
import com.ipn.mx.gasolimetro.datos.modelos.EstadoSensor;
import com.ipn.mx.gasolimetro.datos.modelos.Login;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.net.HttpURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarSensor extends AppCompatActivity {

    TextInputEditText nombreView;
    TextInputEditText codigoView;
    TextView errorView;
    String nombre;
    String codigo;
    RestClientServer restClientServer;
    ResponseGeneric resultsServer;
    UsuarioLogin usuarioLogin;
    ProgressBar progressBar;
    Button buttonRegistrar;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sensor);
        showToolbar(getResources().getString(R.string.toolbar_tittle_registrar_sensor),true);
        activity=this;

        nombreView=findViewById(R.id.nombre_sensor);
        codigoView=findViewById(R.id.codigo_activacion);
        errorView=findViewById(R.id.mensaje_error);
        progressBar=findViewById(R.id.progress_bar);
        buttonRegistrar=findViewById(R.id.registrarSensor_boton);

        usuarioLogin=getIntent().getParcelableExtra("UsuarioLogin");

        //Aquí deberiamos de inicializar cliente para conectarnos server batiz
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);


    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }


    public void registrarSensor(View view){
        buttonRegistrar.setEnabled(false);
        errorView.setText("");

        //Validaciones

        nombre=nombreView.getText().toString();
        codigo=codigoView.getText().toString();

        if(nombre.equals("") || codigo.equals("")){
            errorView.setText(getText(R.string.login_error_campos_vacios));
            buttonRegistrar.setEnabled(true);
        }else{

            buttonRegistrar.setText("");
            progressBar.setVisibility(View.VISIBLE);

            AgregarSensor agregarSensor=new AgregarSensor(nombre,usuarioLogin.getIdUsuario(),new EstadoSensor(1l,"Sin descripción","1"));

            Call<ResponseGeneric> call = restClientServer.registrarSensor(agregarSensor);

            call.enqueue(new Callback<ResponseGeneric>() {
                @Override
                public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                    //Log.d("obtenerGasolinerasSer", "response batiz: "+new Gson().toJson(call.request()));
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_CREATED:
                            Log.d("registrarSensor", "exito batiz");
                            Log.d("registrarSensor", "response batiz: "+new Gson().toJson(response));
                            resultsServer = response.body();
                            buttonRegistrar.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonRegistrar.setText(getString(R.string.text_button_registrar_sensor));
                            //Falta meter la info del usuario al bundle o algún lugar
                            new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(activity.getResources().getString(R.string.titulo_exito_registrar_sensor))
                                    .setContentText(activity.getResources().getString(R.string.exito_registrar_sensor))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            Intent intent=new Intent(activity, ConsultarSensores.class);
                                            intent.putExtra("UsuarioLogin",usuarioLogin);
                                            //bundle.putInt("tipoUsuario",1);
                                            //intent.putExtras(bundle);
                                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                            break;
                        case HttpURLConnection.HTTP_OK:
                            Log.e("registrarSensor", "error batiz: No pude conectarme a server"+new Gson().toJson(response));
                            errorView.setText(getText(R.string.codigo_sensor_ya_registrado));
                            buttonRegistrar.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonRegistrar.setText(getString(R.string.text_button_registrar_sensor));
                            break;
                        default:
                            Log.e("registrarSensor", "error batiz: No pude conectarme, estoy en el default"+new Gson().toJson(response));
                            errorView.setText(getText(R.string.error_server_no_disponible));
                            buttonRegistrar.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonRegistrar.setText(getString(R.string.text_button_registrar_sensor));
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                    Log.e("registrarSensor", "error: "+t.toString());
                    Toast.makeText(getApplicationContext(), getText(R.string.error_server_no_disponible),Toast.LENGTH_SHORT).show();
                    errorView.setText(getText(R.string.error_server_no_disponible));
                    buttonRegistrar.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    buttonRegistrar.setText(getString(R.string.text_button_registrar_sensor));
                }
            });

        }
    }

}
