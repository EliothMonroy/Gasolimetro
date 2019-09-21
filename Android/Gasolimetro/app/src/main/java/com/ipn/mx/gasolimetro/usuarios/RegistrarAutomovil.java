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
import com.ipn.mx.gasolimetro.datos.modelos.AgregarAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.Automovil;
import com.ipn.mx.gasolimetro.datos.modelos.EstadoUsuarioRegistrarUsuario;
import com.ipn.mx.gasolimetro.datos.modelos.Login;
import com.ipn.mx.gasolimetro.datos.modelos.RegistrarUsuarioPeticion;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.net.HttpURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarAutomovil extends AppCompatActivity {

    RestClientServer restClientServer;
    ResponseGeneric resultsServer;
    TextInputEditText nombreView, marcaView,modeloView,capacidadLitrosView;
    String nombre,marca,modelo,capacidadLitros;
    ProgressBar progressBar;
    TextView errorView;
    Button buttonRegistrar;
    int tipo_usuario;
    Long estado_usuario;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_automovil);
        showToolbar("Registrar",true);
        activity=this;
        nombreView=findViewById(R.id.nombre_registrar_automovil);
        modeloView=findViewById(R.id.modelo_registrar_automovil);
        marcaView=findViewById(R.id.marca_registrar_automovil);
        capacidadLitrosView=findViewById(R.id.capacidad_registrar_automovil);
        errorView=findViewById(R.id.mensaje_error);
        buttonRegistrar=findViewById(R.id.boton_registrarAutomovil);
        progressBar=findViewById(R.id.progress_bar);
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);

        ((Button)findViewById(R.id.boton_registrarAutomovil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarAutomovil(v);
            }
        });
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void registrarAutomovil(View view){
        //Deshabilitamos el botón y limpiamos campo de error
        buttonRegistrar.setEnabled(false);
        errorView.setText("");

        //Obtenemos valores campos
        nombre=nombreView.getText().toString();
        marca=marcaView.getText().toString();
        modelo=modeloView.getText().toString();
        capacidadLitros=capacidadLitrosView.getText().toString();

        //Primer validación
        if(nombre.equals("") || marca.equals("") || modelo.equals("") || capacidadLitros.equals("")){
            errorView.setText(getText(R.string.registrar_usuario_campos_vacios));
            buttonRegistrar.setEnabled(true);
        }else{
            //Mostramos progressBar
            buttonRegistrar.setText("");
            progressBar.setVisibility(View.VISIBLE);

            //Creamos objeto para mandar las cosas al server
            UsuarioLogin usuarioLogin = getIntent().getParcelableExtra("UsuarioLogin");
            AgregarAutomovil registrarAutomovil = new AgregarAutomovil();
            registrarAutomovil.setNombre(nombre);
            registrarAutomovil.setMarca(marca);
            registrarAutomovil.setModelo(modelo);
            registrarAutomovil.setCapacidadLitros(Double.parseDouble(capacidadLitros));
            registrarAutomovil.setIdUsuario(usuarioLogin.getIdUsuario());

            //Hacemos la petición al servidor
            Call<ResponseGeneric> call = restClientServer.agregarAutomoviles(registrarAutomovil);

            call.enqueue(new Callback<ResponseGeneric>() {
                @Override
                public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                    Log.d("registrarAutomovil", "response ENVIADA a batiz: "+new Gson().toJson(call.request()));
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_CREATED:
                            Log.d("registrarAutomovil", "exito batiz");
                            Log.d("registrarAutomovil", "response batiz: "+new Gson().toJson(response));
                            resultsServer = response.body();
                            //respuesta=resultsServer.getObject();
                            buttonRegistrar.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonRegistrar.setText(getString(R.string.texto_boton_registrar_usuario));
                            new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(activity.getResources().getString(R.string.exito_crear_automovil))
                                    .setContentText(resultsServer.getMsg())
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            mostrarAutomoviles();
                                        }
                                    })
                                    .show();
                            break;
                        default:
                            Log.e("registrarUsuario", "error batiz: No pude conectarme, estoy en el default"+new Gson().toJson(response));
                            errorView.setText(getText(R.string.error_server_no_disponible));
                            buttonRegistrar.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonRegistrar.setText(getString(R.string.texto_boton_registrar_usuario));
                            Log.d("registrarUsuario","Código: "+response.code());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                    Log.e("registrarUsuario", "error: "+t.toString());
                    Toast.makeText(getApplicationContext(), getText(R.string.error_server_no_disponible),Toast.LENGTH_SHORT).show();
                    errorView.setText(getText(R.string.error_server_no_disponible));
                    buttonRegistrar.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    buttonRegistrar.setText(getString(R.string.text_button_login));
                }
            });
        }
    }

    public void mostrarAutomoviles(){
        Intent intent=new Intent(this, ConsultarAutomoviles.class);
        UsuarioLogin usuarioLogin1 = getIntent().getParcelableExtra("UsuarioLogin");
        intent.putExtra("UsuarioLogin",usuarioLogin1);
        startActivity(intent);
    }

}
