package com.ipn.mx.gasolimetro;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.constantes.Constants;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.SensorAutomovil;
import com.ipn.mx.gasolimetro.datos.modelos.UsuarioLogin;
import com.ipn.mx.gasolimetro.medicion.BluetoothService;
import com.ipn.mx.gasolimetro.medicion.CalcularCombustible;
import com.ipn.mx.gasolimetro.medicion.DispositivosBT;
import com.ipn.mx.gasolimetro.pruebaPath.TaskLoadedCallback;
import com.ipn.mx.gasolimetro.usuarios.AutenticarUsuario;
import com.ipn.mx.gasolimetro.usuarios.ConsultarAutomovil;
import com.ipn.mx.gasolimetro.usuarios.ConsultarAutomoviles;
import com.ipn.mx.gasolimetro.usuarios.ConsultarPanelControl;
import com.ipn.mx.gasolimetro.usuarios.ConsultarSensores;
import com.ipn.mx.gasolimetro.usuarios.EditarUsuario;
import com.ipn.mx.gasolimetro.usuarios.GenerarReporte;
import com.ipn.mx.gasolimetro.usuarios.RegistrarUsuario;
import com.ipn.mx.gasolimetro.usuarios.SeleccionCompartir;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConsultarMapaMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CalcularCombustible.EditNameDialogListener, TaskLoadedCallback {

    ConsultarMapa mapa;
    public MenuItem changeItem;
    private BluetoothAdapter bluetoothAdapter = null;
    int tipoUsuario=0;
    private RestClientServer restClientServer=null;
    NavigationView navigationView;
    UsuarioLogin usuarioLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_mapa_menu);
        //Toolbar
        showToolbar(getResources().getString(R.string.toolbar_tittle_consultar_mapa), true);

        //Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(getIntent().getParcelableExtra("UsuarioLogin")!=null){
            usuarioLogin=getIntent().getParcelableExtra("UsuarioLogin");
            tipoUsuario=usuarioLogin.getTipoUsuario();
            Log.e("ConsultarMapaMenu","NombreUsuario: "+usuarioLogin.getNombre());
            Log.e("ConsultarMapaMenu","Correo: "+usuarioLogin.getLogin().getEmail());
        }else{
            Log.e("ConsultarMapaMenu","No vengo de un intent");
            tipoUsuario=0;
        }
        //tipoUsuario = 1;
        //Nav view
        Log.e("ConsultarMapaMenu",""+tipoUsuario);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout header=(LinearLayout) navigationView.getHeaderView(0);

        if(tipoUsuario==0){//Usuario no registrado
            ((ImageView)header.findViewById(R.id.imagenUsuario)).setImageResource(R.drawable.ic_person_black_24dp);
            ((TextView)header.findViewById(R.id.nombre_usuario)).setText(getText(R.string.texto_nombre_usuario_no_registrado));
            ((TextView)header.findViewById(R.id.textViewPerfil)).setText(getText(R.string.texto_subtitulo_usuario_no_registrado_nav));

            //Menu
            navigationView.inflateMenu(R.menu.menu_usuario_no_registrado);

            //Para iniciar sesión
            TextView perfil=header.findViewById(R.id.textViewPerfil);
            perfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AutenticarUsuario.class);
                    startActivity(intent);

                }
            });

        }else if(tipoUsuario==1){//Usuario registrado
            mostrarInfoUsuario(header);
            //Menu
            navigationView.inflateMenu(R.menu.menu_usuario_registrado);

            //Para acceder al perfil
            TextView perfil=header.findViewById(R.id.textViewPerfil);
            perfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), EditarUsuario.class);
                    startActivity(intent);
                }
            });

        }else if(tipoUsuario==2){//Administrador
            mostrarInfoAdministrador(header);
            //Menu
            navigationView.inflateMenu(R.menu.menu_administrador);

            //Para acceder al perfil
            TextView perfil=header.findViewById(R.id.textViewPerfil);
            perfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), EditarUsuario.class);
                    startActivity(intent);
                }
            });

        }

        //Mapa
        mapa=new ConsultarMapa();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map,mapa);
        fragmentTransaction.commit();

        //Bluetooth
        LocalBroadcastManager.getInstance(this).registerReceiver(exitReceiver, new IntentFilter(Constants.ACTION_BLUETOOTH_EXIT));
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);

    }

    private void mostrarInfoUsuario(LinearLayout header) {
        ((ImageView)header.findViewById(R.id.imagenUsuario)).setImageResource(R.drawable.img_usuario_elioth);
        ((TextView)header.findViewById(R.id.nombre_usuario)).setText(usuarioLogin.getNombre()+" "+usuarioLogin.getApellidoPaterno());
        ((TextView)header.findViewById(R.id.textViewPerfil)).setText(getText(R.string.nav_header_subtitle));
    }

    private void mostrarInfoAdministrador(LinearLayout header) {
        ((ImageView)header.findViewById(R.id.imagenUsuario)).setImageResource(R.drawable.img_usuario_elioth);
        ((TextView)header.findViewById(R.id.nombre_usuario)).setText(usuarioLogin.getNombre()+" "+usuarioLogin.getApellidoPaterno());
        ((TextView)header.findViewById(R.id.textViewPerfil)).setText(getText(R.string.nav_header_subtitle_admi));
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_fragment) { // Medición
            changeItem = item;
            if(item.getTitle().equals("Conectar sensor")) {
                Call<ResponseGeneric<List<SensorAutomovil>>> callSpinnerAutos = restClientServer.getSensorAutomovil(usuarioLogin.getIdUsuario());
                callSpinnerAutos.enqueue(new Callback<ResponseGeneric<List<SensorAutomovil>>>(){
                    @Override
                    public void onResponse(Call<ResponseGeneric<List<SensorAutomovil>>> call, Response<ResponseGeneric<List<SensorAutomovil>>> response) {
                        //Log.d("ConfirmarMedicion", "request: "+new Gson().toJson(call.request()));
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_OK:
                                //Encendido de Bluetooth
                                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                verificarSoporteBluetooth();
                                Intent intent = new Intent(getApplicationContext(), DispositivosBT.class);
                                //En teoría aquí te paso el objeto del login
                                startActivityForResult(intent, 1);
                                break;
                            default:
                                Log.e("LlenarSpinnerAutos", "error: No pude conectarme al server: code "+response.code());
                                break;
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseGeneric<List<SensorAutomovil>>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error en la conexión",Toast.LENGTH_LONG).show();
                    }
                });

            }else{
                item.setTitle("Conectar sensor");
                Intent intentService = new Intent(this,BluetoothService.class);
                stopService(intentService);

            }

        } else if (id == R.id.nav_auto) {
            Intent intent=new Intent(this, ConsultarAutomoviles.class);
            intent.putExtra("UsuarioLogin",usuarioLogin);
            startActivity(intent);

        } else if (id == R.id.nav_sensor) {
            Intent intent=new Intent(this, ConsultarSensores.class);
            intent.putExtra("UsuarioLogin",usuarioLogin);
            startActivity(intent);

        } else if (id == R.id.nav_compartir) {

            Intent intent = new Intent(this, SeleccionCompartir.class);
            intent.putExtra("UsuarioLogin",usuarioLogin);
            startActivity(intent);

        }
        /*else if (id == R.id.nav_reportar) {
            //Generar reporte
            Intent intent=new Intent(this, GenerarReporte.class);
            intent.putExtra("UsuarioLogin",usuarioLogin);
            startActivity(intent);

        } */
        else if (id == R.id.nav_cerrar_usuario || id == R.id.nav_cerrar_admi) {
            this.finish();
            Intent intent=new Intent(this,ConsultarMapaMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }else if (id == R.id.nav_registrate){
            Intent intent=new Intent(this,RegistrarUsuario.class);
            Bundle bundle=new Bundle();
            bundle.putString("origen","Menu");
            intent.putExtras(bundle);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        //Log.d("Aquí llego: ",inputText);
        Toast.makeText(this, "Cantidad: " + inputText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskDone(Object... values) {
        mapa.onTaskDone(values);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Log.d("valor",data.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS));
                String address=data.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);
                Toast.makeText(this,address,Toast.LENGTH_LONG).show();

                //((MenuItem)navigationView.findViewById(R.id.nav_fragment)).setTitle("Desconectar");
                changeItem.setTitle("Desconectar");
                Intent intent = new Intent(this, BluetoothService.class);
                intent.putExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS, address);
                startService(intent); // Iniciamos el servicio
            }
        }
    }//onActivityResult

    private BroadcastReceiver exitReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Double litroCargados = intent.getDoubleExtra("exitBluetooth",0.0);
            Log.d("valor","----------> Litros  recibidos: "+litroCargados);
            //changeItem.setTitle("Conectar sensor");
            Bundle bundle = new Bundle();
            bundle.putString("Gasolinera","1");
            bundle.putParcelable("UsuarioLogin",usuarioLogin);

            //CalcularCombustible fragmentCombustible = CalcularCombustible.newInstance(""+litroCargados);
            //fragmentCombustible.setArguments(bundle);
            //fragmentCombustible.show(fragmentManager, "activity_calcular_combustible");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            CalcularCombustible fragmentCombustible = CalcularCombustible.newInstance(""+litroCargados);
            fragmentCombustible.setArguments(bundle);
            ft.add(fragmentCombustible,"");
            ft.commitAllowingStateLoss();

        }
    };

    private void verificarSoporteBluetooth() {
        if(bluetoothAdapter ==null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBtIntent);
            }
        }
    }


}
