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

public class RegistrarUsuario extends AppCompatActivity {

    RestClientServer restClientServer;
    ResponseGeneric resultsServer;
    TextInputEditText nombreView, apellidopView,apellidomView,correoView,contraView;
    String nombre,apellidop,apellidom,correo,contra;
    ProgressBar progressBar;
    TextView errorView;
    Button buttonRegistrar;
    int tipo_usuario;
    Long estado_usuario;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        showToolbar(getResources().getString(R.string.toolbar_tittle_registrar_usuario),true);
        activity=this;
        nombreView=findViewById(R.id.nombre_registrar_usuario);
        apellidopView=findViewById(R.id.apellidop_registrar_usuario);
        apellidomView=findViewById(R.id.apellidom_registrar_usuario);
        correoView=findViewById(R.id.email_registrar_usuario);
        contraView=findViewById(R.id.password_registrar_usuario);
        errorView=findViewById(R.id.mensaje_error);
        buttonRegistrar=findViewById(R.id.boton_registrarUsuario);
        progressBar=findViewById(R.id.progress_bar);
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);

    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void registrarUsuario(View view){
        //Deshabilitamos el botón y limpiamos campo de error
        buttonRegistrar.setEnabled(false);
        errorView.setText("");

        //Obtenemos valores campos
        nombre=nombreView.getText().toString();
        apellidop=apellidopView.getText().toString();
        apellidom=apellidomView.getText().toString();
        correo=correoView.getText().toString();
        contra=contraView.getText().toString();

        //Primer validación
        if(nombre.equals("") || apellidop.equals("") || apellidom.equals("") || correo.equals("") || contra.equals("")){
            errorView.setText(getText(R.string.registrar_usuario_campos_vacios));
            buttonRegistrar.setEnabled(true);
        }else{
            //Mostramos progressBar
            buttonRegistrar.setText("");
            progressBar.setVisibility(View.VISIBLE);

            //Creamos objeto para mandar las cosas al server
            tipo_usuario=1;
            estado_usuario=1l;
            Login login=new Login(correo,contra);
            RegistrarUsuarioPeticion peticion=new RegistrarUsuarioPeticion(nombre,apellidop,apellidom,true,tipo_usuario,new EstadoUsuarioRegistrarUsuario(estado_usuario),login);

            //Hacemos la petición al servidor

            Call<ResponseGeneric> call = restClientServer.registrarUsuario(peticion);

            call.enqueue(new Callback<ResponseGeneric>() {
                @Override
                public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                    Log.d("registrarUsuario", "response ENVIADA a batiz: "+new Gson().toJson(call.request()));
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_CREATED:
                            Log.d("registrarUsuario", "exito batiz");
                            Log.d("registrarUsuario", "response batiz: "+new Gson().toJson(response));
                            resultsServer = response.body();
                            //respuesta=resultsServer.getObject();
                            buttonRegistrar.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonRegistrar.setText(getString(R.string.texto_boton_registrar_usuario));
                            new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(activity.getResources().getString(R.string.exito_crear_cuenta))
                                    .setContentText(resultsServer.getMsg())
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            mostrarMapa();
                                        }
                                    })
                                    .show();
                            break;
                        case HttpURLConnection.HTTP_OK:
                            Log.e("registrarUsuario", "error batiz: Correo electrónico en uso. "+new Gson().toJson(response));
                            errorView.setText(getText(R.string.registrar_usuario_correo_en_uso));
                            buttonRegistrar.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonRegistrar.setText(getString(R.string.texto_boton_registrar_usuario));
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

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;

        String origen=getIntent().getExtras().getString("origen");

        // Here you need to do some logic to determine from which Activity you came.
        // example: you could pass a variable through your Intent extras and check that.
        if (origen.equals("Login")) {
            i = new Intent(this, AutenticarUsuario.class);
            // set any flags or extras that you need.
            // If you are reusing the previous Activity (i.e. bringing it to the top
            // without re-creating a new instance) set these flags:
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // if you are re-using the parent Activity you may not need to set any extras

        } else {
            i = new Intent(this, ConsultarMapaMenu.class);
            // same comments as above
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        }

        return i;
    }

    public void mostrarMapa(){
        Intent intent=new Intent(this, ConsultarMapaMenu.class);
        //bundle.putInt("tipoUsuario",1);
        //intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
