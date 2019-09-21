package com.ipn.mx.gasolimetro.medicion;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.Medicion;
import com.ipn.mx.gasolimetro.datos.modelos.MedicionFeed;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;

import java.net.HttpURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformacionAdicionalMedicion extends AppCompatActivity {
    Spinner bombas;
    Button btnEnviarDatos;
    Bundle bundle;
    private RestClientServer restClientServer=null;
    TextInputEditText textInputEditTextPrecio;
    ResponseGeneric resultServer;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_adicional_medicion);
        showToolbar(getResources().getString(R.string.toolbar_tittle_informacion_adicional_medicion),true);
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);
        activity=this;

        bombas = findViewById(R.id.spinnerBombas);
        textInputEditTextPrecio = findViewById(R.id.PrecionRegistrado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        bombas.setAdapter(adapter);
        btnEnviarDatos=findViewById(R.id.buttonEnviar);
        btnEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = getIntent().getExtras();
                Medicion medicion = bundle.getParcelable("medicion");
                String precio = textInputEditTextPrecio.getText().toString();
                int bombaSeleccionada = bombas.getSelectedItemPosition() +1 ;
                medicion.setBomba(bombaSeleccionada);
                medicion.setPrecioSolicitado(Double.parseDouble(precio));
                medicion.setPrecioPagado(Double.parseDouble(precio)*medicion.getLitrosSolicitados());
                MedicionFeed medicionFeed = new MedicionFeed(bundle.getLong("idGasolinera"),bundle.getLong("idSensor"),bundle.getLong("idAutomovil"),medicion);
                //Llamado retrofit
                Call<ResponseGeneric> call = restClientServer.registrarMedicion(medicionFeed);
                call.enqueue(new Callback<ResponseGeneric>() {
                    @Override
                    public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                        Log.d("ConfirmarMedicion", "request: "+new Gson().toJson(call.request()));
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_CREATED:
                                resultServer=response.body();
                                Log.d("ConfirmarMedicion", "Exito al consultar server");
                                ResponseGeneric data = response.body();
                                Log.d("ConfirmarMedicion", "response: "+new Gson().toJson(response));
//                                new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText("Exito")
//                                        .setContentText(resultServer.getMsg())
//                                        .show();
                                break;
                            default:
                                Log.e("ConfirmarMedicion", "error: No pude conectarme al server: code "+response.code());
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error en la conexión",Toast.LENGTH_LONG).show();
                    }
                });
                new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Exito")
                        .setContentText(resultServer.getMsg())
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                activity.finish();
                            }
                        })
                        .show();

            }
        });
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bundle = getIntent().getExtras();
        Medicion medicion = bundle.getParcelable("medicion");
        MedicionFeed medicionFeed = new MedicionFeed(bundle.getLong("idGasolinera"),bundle.getLong("idSensor"),bundle.getLong("idAutomovil"),medicion);
        //Llamado retrofit
        Call<ResponseGeneric> call = restClientServer.registrarMedicion(medicionFeed);
        call.enqueue(new Callback<ResponseGeneric>() {
            @Override
            public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                Log.d("ConfirmarMedicion", "request: "+new Gson().toJson(call.request()));
                switch (response.code()) {
                    case HttpURLConnection.HTTP_CREATED:
                        Log.d("ConfirmarMedicion", "Exito al consultar server");
                        ResponseGeneric data = response.body();

                        Log.d("ConfirmarMedicion", "response: "+new Gson().toJson(response));
//                        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText("Exito")
//                                .setContentText(data.getMsg())
//                                .show();
                        break;
                    default:
                        Log.e("ConfirmarMedicion", "error: No pude conectarme al server: code "+response.code());
                        break;
                }
            }
            @Override
            public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });
        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Exito")
                .setContentText(resultServer.getMsg())
                .show();
    }
}
