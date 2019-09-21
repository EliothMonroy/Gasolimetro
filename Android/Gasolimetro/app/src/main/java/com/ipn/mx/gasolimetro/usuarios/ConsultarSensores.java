package com.ipn.mx.gasolimetro.usuarios;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.Automovil;
import com.ipn.mx.gasolimetro.datos.modelos.Sensor;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarSensores extends AppCompatActivity {

    CardView cardViewSensor;
    FloatingActionButton floatingActionButtonAgregar, floatingActionButtonAsociar;
    UsuarioLogin usuarioLogin;
    LinearLayout linearLayout;
    RestClientServer restClientServer;
    Long idSensor;
    Activity activity;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_sensores);
        showToolbar(getResources().getString(R.string.title_activity_consultar_sensores),true);
        activity=this;

        usuarioLogin=getIntent().getParcelableExtra("UsuarioLogin");

        inflater= LayoutInflater.from(this);

        floatingActionButtonAgregar=findViewById(R.id.boton_agregar_sensor);
        floatingActionButtonAsociar=findViewById(R.id.boton_asociar_sensor);

        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);
        linearLayout=findViewById(R.id.layout_sensores);

        obtenerSensores();

        //cardViewSensor = (CardView) inflater.inflate(R.layout.cardview_sensor, null);

    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void obtenerSensores(){

        Call<List<Sensor>> call = restClientServer.getSensores(usuarioLogin.getIdUsuario());
        call.enqueue(new Callback<List<Sensor>>(){
            @Override
            public void onResponse(Call<List<Sensor>> call, Response<List<Sensor>> response) {
                Log.d("obtenerSensores", "request: "+new Gson().toJson(call.request()));
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        Log.d("obtenerSensores", "Exito al consultar server");
                        Log.d("obtenerSensores", "response: "+new Gson().toJson(response.body()));
                        final List<Sensor> sensorList = response.body();;
                        Log.d("obtenerSensores", "size list: "+sensorList.size());
                        // Aqui se crean los cardview
                        for(int i=0;i<sensorList.size();i++){
                            inflater = LayoutInflater.from(getApplicationContext());
                            CardView newCardView=(CardView)inflater.inflate(R.layout.cardview_sensor, null);
                            //newCardView.setId(sensorList.get(i).getIdSensor());
                            CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,(int)activity.getResources().getDimension(R.dimen.height_cardview));
                            layoutParams.setMargins(16,16,16,16);
                            newCardView.setLayoutParams(layoutParams);
                            ((TextView)newCardView.findViewById(R.id.nombre_sensor)).setText(""+sensorList.get(i).getTxDevice());
                            idSensor =sensorList.get(i).getIdSensor();
                            /*newCardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity,ConsultarSensor.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putLong("idSensor",idSensor);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });*/
                            linearLayout.addView(newCardView);
                        }
                        break;
                    default:
                        Log.e("obtenerSensores", "error: No pude conectarme al server: code "+response.code());
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Log.e("obtenerSensores", "error: "+t.toString());
            }
        });

    }


    public void registrarSensor(View view){
        Intent intent=new Intent(this, RegistrarSensor.class);
        intent.putExtra("UsuarioLogin",usuarioLogin);
        startActivity(intent);

    }

    public void asociarSensor(View view){
        Intent intent=new Intent(this, AsociarSensor.class);
        intent.putExtra("UsuarioLogin",usuarioLogin);
        startActivity(intent);

    }

}
