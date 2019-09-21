package com.ipn.mx.gasolimetro.usuarios;

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
import com.ipn.mx.gasolimetro.datos.modelos.Login;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.net.HttpURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
    tipoUsuario   Significado       estadoUsuario   Significado
    1             Cliente           1               Registrado
    2             Administrador     2               Cuenta básica (cuenta verificada)
                                    3               Cuenta premium (pueden dar insignias)
                                    4               Usuario bloqueado

*/

public class AutenticarUsuario extends AppCompatActivity {

    TextInputEditText emailView;
    TextInputEditText passwordView;
    TextView errorView;
    String email;
    String password;
    RestClientServer restClientServer;
    ResponseGeneric<UsuarioLogin> resultsServer;
    UsuarioLogin usuarioLogin;
    ProgressBar progressBar;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticar_usuario);

        showToolbar(getResources().getString(R.string.toolbar_tittle_autenticar_usuario),true);

        emailView=findViewById(R.id.login_correo);
        passwordView=findViewById(R.id.login_password);
        errorView=findViewById(R.id.mensaje_error);
        progressBar=findViewById(R.id.progress_bar);
        buttonLogin=findViewById(R.id.login);

        //Aquí deberiamos de inicializar cliente para conectarnos server batiz
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);

    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void crearUsuario(View view){
        Intent intent=new Intent(this,RegistrarUsuario.class);
        Bundle bundle=new Bundle();
        bundle.putString("origen","Login");
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void recuperarContrasena(View view) {
        Intent intent=new Intent(this,RecuperarContrasena.class);
        startActivity(intent);

    }

    public void iniciarSesion(View view) {

        buttonLogin.setEnabled(false);
        errorView.setText("");

        //Validaciones

        email=emailView.getText().toString();
        password=passwordView.getText().toString();

        if(email.equals("") || password.equals("")){
            errorView.setText(getText(R.string.login_error_campos_vacios));
            buttonLogin.setEnabled(true);
        }else{

            buttonLogin.setText("");
            progressBar.setVisibility(View.VISIBLE);

            Login login=new Login(email,password);

            Call<ResponseGeneric<UsuarioLogin>> call = restClientServer.getLogin(login);

            call.enqueue(new Callback<ResponseGeneric<UsuarioLogin>>() {
                @Override
                public void onResponse(Call<ResponseGeneric<UsuarioLogin>> call, Response<ResponseGeneric<UsuarioLogin>> response) {
                    //Log.d("obtenerGasolinerasSer", "response batiz: "+new Gson().toJson(call.request()));
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_ACCEPTED:
                            Log.d("iniciarSesion", "exito batiz");
                            Log.d("iniciarSesion", "response batiz: "+new Gson().toJson(response));
                            resultsServer = response.body();
                            usuarioLogin=resultsServer.getObject();
                            buttonLogin.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonLogin.setText(getString(R.string.text_button_login));
                            //Falta meter la info del usuario al bundle o algún lugar

                            //Verificar usuario
                            verificarEstadoUsuario();
                            break;
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            Log.e("iniciarSesion", "error batiz: No pude conectarme a server"+new Gson().toJson(response));
                            errorView.setText(getText(R.string.login_datos_incorrectos));
                            buttonLogin.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonLogin.setText(getString(R.string.text_button_login));
                            break;
                        default:
                            Log.e("iniciarSesion", "error batiz: No pude conectarme, estoy en el default"+new Gson().toJson(response));
                            errorView.setText(getText(R.string.error_server_no_disponible));
                            buttonLogin.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            buttonLogin.setText(getString(R.string.text_button_login));
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ResponseGeneric<UsuarioLogin>> call, Throwable t) {
                    Log.e("iniciarSesion", "error: "+t.toString());
                    Toast.makeText(getApplicationContext(), getText(R.string.error_server_no_disponible),Toast.LENGTH_SHORT).show();
                    errorView.setText(getText(R.string.error_server_no_disponible));
                    buttonLogin.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    buttonLogin.setText(getString(R.string.text_button_login));
                }
            });

        }

    }

    public void mostrarMapa(){
        Intent intent=new Intent(this, ConsultarMapaMenu.class);
        Bundle bundle=new Bundle();
        intent.putExtra("UsuarioLogin",usuarioLogin);
        //bundle.putInt("tipoUsuario",1);
        //intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void verificarEstadoUsuario(){

        //No ha activado su cuenta
        if(usuarioLogin.getEstadoUsuario().getIdEstado()==1){
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.titulo_cuenta_no_verificada))
                    .setContentText(getResources().getString(R.string.cuenta_no_verificada))
                    .show();
        //El usuario está bloqueado
        }else if(usuarioLogin.getEstadoUsuario().getIdEstado()==4){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getResources().getString(R.string.titulo_cuenta_bloqueada))
                    .setContentText(this.getResources().getString(R.string.cuenta_bloqueada))
                    .show();
        //El usuario puede iniciar sesión correctamente
        }else{
            mostrarMapa();
        }
    }

}
