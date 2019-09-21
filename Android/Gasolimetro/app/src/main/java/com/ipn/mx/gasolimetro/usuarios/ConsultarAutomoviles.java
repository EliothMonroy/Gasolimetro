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
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.AgregarAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.Automovil;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.SensorAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarAutomoviles extends AppCompatActivity {

    private RestClientServer restClientServer=null;
    Bundle bundle;
    LinearLayout linearLayout;
    Activity activity;
    Long idAutomovil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_automoviles);
        activity = this;
        bundle = new Bundle();
        showToolbar("Automoviles",true);
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);
        linearLayout=findViewById(R.id.layout_automoviles);
        final UsuarioLogin usuarioLogin = getIntent().getParcelableExtra("UsuarioLogin");
        //Checar los automoviles
        Call<List<Automovil>> callCardViewAutomoviles = restClientServer.getAutomoviles(usuarioLogin.getIdUsuario());
        callCardViewAutomoviles.enqueue(new Callback<List<Automovil>>(){
            @Override
            public void onResponse(Call<List<Automovil>> call, Response<List<Automovil>> response) {
                Log.d("ConsultarAutomoviles", "request: "+new Gson().toJson(call.request()));
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        Log.d("ConsultarAutomoviles", "Exito al consultar server");
                        final List<Automovil> lstAutomovil = (List<Automovil>)response.body();;
                        // Aqui se crean los cardview
                        for(int i=0;i<lstAutomovil.size();i++){
                            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                            CardView newCardView=(CardView)inflater.inflate(R.layout.cardview_automovil, null);
                            newCardView.setId(lstAutomovil.get(i).getIdAutomovil());
                            CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,(int)activity.getResources().getDimension(R.dimen.height_cardview));
                            layoutParams.setMargins(16,16,16,16);
                            newCardView.setLayoutParams(layoutParams);
                            ((TextView)newCardView.findViewById(R.id.NombreAutomovil)).setText(lstAutomovil.get(i).getNombre());
                            ((TextView)newCardView.findViewById(R.id.Marca)).setText(lstAutomovil.get(i).getMarca());
                            ((TextView)newCardView.findViewById(R.id.Modelo)).setText(lstAutomovil.get(i).getModelo());
                            idAutomovil =(long)lstAutomovil.get(i).getIdAutomovil();
                            newCardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity,ConsultarAutomovil.class);
                                    bundle.putLong("idAutomovil",idAutomovil);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            linearLayout.addView(newCardView);
                        }
                        break;
                    default:
                        Log.e("LlenarSpinnerAutos", "error: No pude conectarme al server: code "+response.code());
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Automovil>> call, Throwable t) {
                Log.e("ConsultarAutomovil", "error: "+t.toString());
            }
        });



        ((FloatingActionButton)findViewById(R.id.boton_agregar_automovil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrarAutomovil.class);
                UsuarioLogin usuarioLogin1 = getIntent().getParcelableExtra("UsuarioLogin");
                intent.putExtra("UsuarioLogin",usuarioLogin1);
                startActivity(intent);
            }
        });
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
