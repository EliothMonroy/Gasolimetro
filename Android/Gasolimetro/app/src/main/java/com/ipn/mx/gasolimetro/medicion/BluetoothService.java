package com.ipn.mx.gasolimetro.medicion;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ipn.mx.gasolimetro.ConsultarMapa;
import com.ipn.mx.gasolimetro.ConsultarMapaMenu;
import com.ipn.mx.gasolimetro.R;
import com.ipn.mx.gasolimetro.constantes.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService extends Service {

    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    Handler bluetoothHandler;
    final int handlerState = 0;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private StringBuilder stringBuilderBluetooth = new StringBuilder();

    private double litrosTotales=0;
    private boolean flagStop = false;
    private int contadorCeros = 0;

    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC Address
    private static String address = null;

    public BluetoothService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand (Intent intent,int flags, int startId){
        String address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        Log.d("valor", "---->  Device Address : "+device.getAddress()+ " Nombre: "+device.getName());
        Toast.makeText(getBaseContext(), "Socket conectado:" +address, Toast.LENGTH_LONG).show();
        connectThread = new ConnectThread(device);
        connectThread.start();


        bluetoothHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    byte [] aux = new byte[0];
                    try {
                        aux = serializeObject(msg.obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int pulsosSegundos = 0;
                    for(int i = 27; i < aux.length; i++){
                        if(aux[i] > 0 ){
                            stringBuilderBluetooth.append(aux[i]+" ");
                            pulsosSegundos += aux[i];
                            flagStop = true;
                        }
                    }
                    if(flagStop && (pulsosSegundos == 0))
                        contadorCeros++;
                    if(contadorCeros >= 10)
                        stopSelf();
                    Log.d("valor",stringBuilderBluetooth.toString());
                    double calculoSegundo = (((double)(pulsosSegundos))/(4.8*60));
                    litrosTotales += calculoSegundo;
                    Log.d("valor", "---->Total:  "+litrosTotales);
                    stringBuilderBluetooth.delete(0, stringBuilderBluetooth.length());
                }
            }
        };

        return START_STICKY;
    }

    public void onDestroy(){
        super.onDestroy();
        connectThread.cancel();
        try {
            connectThread.cancel();
            }
        catch (Exception e)
        { Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();}
        Log.d("valor","Servicio desconectado");

        Intent intentExit =new Intent(Constants.ACTION_BLUETOOTH_EXIT);
        intentExit.putExtra("exitBluetooth",litrosTotales);
        LocalBroadcastManager.getInstance(BluetoothService.this).sendBroadcast(intentExit);
        litrosTotales =0;
    }


    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                //tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("HC-05", BTMODULEUUID);
                tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("G Pro Lite",BTMODULEUUID);
            } catch (Exception e) {
                Log.d("Valor", "----> Bluetooth error server: "+e.getMessage());
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();

                } catch (Exception e) {
                    Log.d("valor", "----> Server socket accept: "+e.toString());
                    Log.d("valor", "----> Cause: "+e.getCause());

                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    //manageConnectedSocket(socket);
                    Log.d("valor", "----> Socket creado");
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }else{
                    Log.d("valor", "----> Socket no creado");
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID);
            } catch (IOException e) {
                Log.d("valor", "---->  RFComm error: " + e.toString());
            }
            mmSocket = tmp;
            bluetoothSocket = mmSocket;

        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                Log.d("valor", "---->  Socket status: " + mmSocket.isConnected());

                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                Log.d("valor", "---->  Unable to connect: " + connectException.toString());
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.d("valor", "---->  Close exception: " + closeException.toString());
                }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            //manageConnectedSocket(mmSocket);

            final InputStream mmInStream;
            final OutputStream mmOutStream;

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    bluetoothHandler.obtainMessage(handlerState, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private static byte[] serializeObject(Object obj) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
        oos.writeObject(obj);
        oos.flush();
        byte[] bytes = bytesOut.toByteArray();
        bytesOut.close();
        oos.close();
        return bytes;
    }
}
