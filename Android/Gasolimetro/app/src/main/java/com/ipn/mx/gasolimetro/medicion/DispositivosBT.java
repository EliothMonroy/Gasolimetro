package com.ipn.mx.gasolimetro.medicion;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ipn.mx.gasolimetro.ConsultarMapaMenu;
import com.ipn.mx.gasolimetro.R;

import java.util.Set;

public class DispositivosBT extends AppCompatActivity {

    ListView IdLista;

    private static final String TAG = "DispositivosBT";
    // String que se enviara a la actividad principal, mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bt);
        verificarBluetooth();
    }

    @Override
    public void onResume() {
        super.onResume();
        deviceAdapter = new ArrayAdapter(this, R.layout.nombre_dispositivos);

        IdLista = (ListView) findViewById(R.id.IdLista);
        IdLista.setAdapter(deviceAdapter);
        IdLista.setOnItemClickListener(onItemClickListener);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {

            // Obtener la dirección MAC del dispositivo, que son los últimos 17 caracteres en la vista
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            Intent i = new Intent(DispositivosBT.this, ConsultarMapaMenu.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
            setResult(Activity.RESULT_OK,i);
            finish();
        }
    };

    private void verificarBluetooth() {
        // Comprueba que el dispositivo tiene Bluetooth y que está encendido.
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getBaseContext(), "El dipositivo no soporta Bluetooth Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "---->  Bluetooth Activado");
            } else {
                //Solicita al usuario que active Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBtIntent);
            }
        }
    }
}