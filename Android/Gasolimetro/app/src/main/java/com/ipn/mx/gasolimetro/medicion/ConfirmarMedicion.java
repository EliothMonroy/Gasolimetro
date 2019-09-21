package com.ipn.mx.gasolimetro.medicion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.AgregarAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.Automovil;
import com.ipn.mx.gasolimetro.datos.modelos.GasolinerasFeed;
import com.ipn.mx.gasolimetro.datos.modelos.ListaMediciones;
import com.ipn.mx.gasolimetro.datos.modelos.Login;
import com.ipn.mx.gasolimetro.datos.modelos.Medicion;
import com.ipn.mx.gasolimetro.datos.modelos.MedicionFeed;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.SensorAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConfirmarMedicion extends DialogFragment {

    private static String cantidadLitros = "0";
    private TextView litrosCargados;
    private RestClientServer restClientServer=null;
    Bundle bundle;
    List<String> datos = new ArrayList<>();
    List<Long> valorDatos = new ArrayList<>();
    List<SensorAutomovil> sensorAutomovil;
    Activity activity;
    public ConfirmarMedicion(){

    }

    public static ConfirmarMedicion newInstance(String title) {
        ConfirmarMedicion frag = new ConfirmarMedicion();
        Bundle args = new Bundle();
        args.putString("Litros", title);
        frag.setArguments(args);
        cantidadLitros = title;
        Log.d("valor","--->Title "+ title);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);

        activity=getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmar_medicion, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        UsuarioLogin usuarioLogin = bundle.getParcelable("UsuarioLogin");
        Log.d("tipoUsuario",""+bundle.getInt("tipoUsuario"));
        if(usuarioLogin.getTipoUsuario() == 1){
            ImageView img = (ImageView)view.findViewById(R.id.imageViewInformacionAdicional);
            img.setVisibility(View.VISIBLE);
        }
        //Checar los automoviles
        Call<ResponseGeneric<List<SensorAutomovil>>> callSpinnerAutos = restClientServer.getSensorAutomovil(usuarioLogin.getIdUsuario());
        callSpinnerAutos.enqueue(new Callback<ResponseGeneric<List<SensorAutomovil>>>(){
            @Override
            public void onResponse(Call<ResponseGeneric<List<SensorAutomovil>>> call, Response<ResponseGeneric<List<SensorAutomovil>>> response) {
                //Log.d("ConfirmarMedicion", "request: "+new Gson().toJson(call.request()));
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        Log.d("LlenarSpinnerAutos", "Exito al consultar server");
                        ResponseGeneric data = response.body();
                        Log.d("LlenarSpinnerAutos", "response: "+new Gson().toJson(response.body().getObject()));
                        sensorAutomovil=(List<SensorAutomovil>)data.getObject();

                        for(int i =0; i<sensorAutomovil.size();i++){
                            datos.add(sensorAutomovil.get(i).getAutomovil().toString());
                            valorDatos.add(sensorAutomovil.get(i).getIdSensor());
                            Log.e("LlenarSpinnerAutomovil","---->"+sensorAutomovil.get(i).getAutomovil().getNombre());
                        }
                        Spinner  spinnerAutomoviles = view.findViewById(R.id.spinnerAutomovil);
                        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.opciones,android.R.layout.simple_spinner_item);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity ,android.R.layout.simple_spinner_item,datos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter.notifyDataSetChanged();
                        spinnerAutomoviles.setAdapter(adapter);
                        spinnerAutomoviles.setSelection(0);

                        break;

                    default:
                        Log.e("LlenarSpinnerAutos", "error: No pude conectarme al server: code "+response.code());
                        break;
                }
            }
            @Override
            public void onFailure(Call<ResponseGeneric<List<SensorAutomovil>>> call, Throwable t) {
                Log.e("LlenarSpinnerAutos", "error: "+t.toString());
            }
        });

        // Get field from view
        Log.d("valor","----->CantidadMedicion: "+cantidadLitros);
        litrosCargados = view.findViewById(R.id.txtMedicionCalculada);
        litrosCargados.setText(cantidadLitros);
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        view.findViewById(R.id.buttonAceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //datos bundle
                bundle = getArguments();
                //Enviar medicion
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                Date date = new Date();
                String litrosSolicitados=bundle.getString("litrosSolicitados");
                Log.d("ConfirmarMedicion", "---> Cantidad solicitada: "+litrosSolicitados);
                Spinner spinnerAutomoviles = view.findViewById(R.id.spinnerAutomovil);
                int posSpinner = spinnerAutomoviles.getSelectedItemPosition();
                bundle.putLong("idGasolinera",1);
                bundle.putLong("idSensor",valorDatos.get(posSpinner));
                bundle.putLong("idAutomovil",(long)sensorAutomovil.get(posSpinner).getAutomovil().getIdAutomovil());
                Medicion medicion=new Medicion(dateFormat.format(date),Double.parseDouble(litrosSolicitados),Double.parseDouble(cantidadLitros),null,null,null);
                bundle.putParcelable("medicion",medicion);
                final MedicionFeed medicionFeed=new MedicionFeed((long)1,valorDatos.get(posSpinner),(long)sensorAutomovil.get(posSpinner).getAutomovil().getIdAutomovil(),medicion);
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
                                //Toast.makeText(activity,data.getMsg(),Toast.LENGTH_LONG).show();
//                                new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText("Exito")
//                                        .setContentText(data.getMsg())
//                                        .show();
                                break;
                            default:
                                Log.e("ConfirmarMedicion", "error: No pude conectarme al server: code "+response.code());
                                Toast.makeText(activity,activity.getResources().getText(R.string.error_server_no_disponible),Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                        Toast.makeText(activity,"Error en la conexión",Toast.LENGTH_LONG).show();
                    }
                });

                dismissAllowingStateLoss();
            }
        });

        // Informacion Extra
        view.findViewById(R.id.imageViewInformacionAdicional).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();


                Intent intent=new Intent(activity, InformacionAdicionalMedicion.class);
                bundle = getArguments();

                //Enviar medicion
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                Date date = new Date();
                String litrosSolicitados=bundle.getString("litrosSolicitados");
                Log.d("ConfirmarMedicion", "---> Cantidad solicitada: "+litrosSolicitados);
                Spinner spinnerAutomoviles = view.findViewById(R.id.spinnerAutomovil);
                int posSpinner = spinnerAutomoviles.getSelectedItemPosition();
                bundle.putLong("idGasolinera",1);
                bundle.putLong("idSensor",valorDatos.get(posSpinner));
                bundle.putLong("idAutomovil",(long)sensorAutomovil.get(posSpinner).getAutomovil().getIdAutomovil());
                Medicion medicion=new Medicion(dateFormat.format(date),Double.parseDouble(litrosSolicitados),Double.parseDouble(cantidadLitros),null,null,null);
                bundle.putParcelable("medicion",medicion);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
