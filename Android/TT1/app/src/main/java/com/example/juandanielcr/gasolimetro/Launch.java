package com.example.juandanielcr.gasolimetro;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class Launch extends AppCompatActivity {

  private static final String TAG = "Launch Activity ";
  Button btnDesconectar;
  TextView textViewBufferIn;
  EditText textHost;

  Handler bluetoothHandler;
  final int handlerState = 0;
  private BluetoothAdapter bluetoothAdapter = null;
  private BluetoothSocket bluetoothSocket = null;
  private StringBuilder stringBuilderBluetooth = new StringBuilder();
  private AcceptThread acceptThread;
  private ConnectThread connectThread;
  private double totalPulsos;
  private String trama = "";
  //--- private ConnectedThread bluetoothConnectionThread;

  // Identificador unico de servicio - SPP UUID
  private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

  // MAC Address
  private static String address = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launch);

    btnDesconectar = (Button) findViewById(R.id.IdDesconectar);
    textViewBufferIn = (TextView) findViewById(R.id.IdBufferIn);
    textHost = (EditText) findViewById(R.id.txtHost);

    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    verificarSoporteBluetooth();

    btnDesconectar.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (bluetoothSocket !=null)
        {
          try {
            bluetoothSocket.close();}
          catch (IOException e)
          { Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();;}
        }
        double v2 = ((totalPulsos)/4.8)/60;
        Log.d(TAG, "----> Total pulsos: "+totalPulsos+" TOTAL v2: "+v2);
        Log.d(TAG, "----> Trama: "+trama);

        textViewBufferIn.setText(trama);

        String location = textHost.getText().toString();
        new ConexionServerGasolimetro().execute(location, trama);
        //finish();
      }
    });

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
          for(int i = 0; i < aux.length; i++){
            if(aux[i] > 0 ){
              stringBuilderBluetooth.append(aux[i]+" ");
              pulsosSegundos += aux[i];
            }
          }
          Log.d(TAG, "---->  Length: "+stringBuilderBluetooth.length()+" array_length: "+aux.length);
          int valorSinHeader = pulsosSegundos-754;
          double calculoSegundo = ((valorSinHeader)/60)/4.8;
          totalPulsos += valorSinHeader;
          trama += " "+valorSinHeader;

          Log.d(TAG, "----> valor sin header: "+valorSinHeader+"litros: "+calculoSegundo+" footprint: "+pulsosSegundos);
          //String auxStr = stringBuilderBluetooth.substring(0, stringBuilderBluetooth.length());
          String auxStr = stringBuilderBluetooth.substring(40, stringBuilderBluetooth.length());
          Log.d(TAG, "---->  "+auxStr);

          textViewBufferIn.setText("Datos: " + auxStr);
          stringBuilderBluetooth.delete(0, stringBuilderBluetooth.length());
        }
      }
    };
  } //--- onCreate

  private void verificarSoporteBluetooth() {
    if(bluetoothAdapter ==null) {
      Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
    } else {
      if (bluetoothAdapter.isEnabled()) {
      } else {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, 1);
      }
    }
  }

  @Override
  public void onResume()
  {
    super.onResume();
    totalPulsos = 0;
    Intent intent = getIntent();
    address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);
    BluetoothDevice device = bluetoothAdapter.getRemoteDevice("00:18:E4:34:E6:77");
    Log.d(TAG, "---->  Device Addres : "+device.getAddress()+ " Nombre: "+device.getName());

    Toast.makeText(getBaseContext(), "Socket conectado:" +address, Toast.LENGTH_LONG).show();
    connectThread = new ConnectThread(device);
    connectThread.start();
  }

  @Override
  public void onPause()
  {
    super.onPause();
    try
    {
      bluetoothSocket.close();
    } catch (IOException e2) {}
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
        Log.d(TAG, "----> Bluetooth error server: "+e.getMessage());
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
          Log.d(TAG, "----> Server socket accept: "+e.toString());
          Log.d(TAG, "----> Cause: "+e.getCause());

          break;
        }
        // If a connection was accepted
        if (socket != null) {
          // Do work to manage the connection (in a separate thread)
          //manageConnectedSocket(socket);
          Log.d(TAG, "----> Socket creado");
          try {
            mmServerSocket.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        }else{
          Log.d(TAG, "----> Socket no creado");
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
        Log.d(TAG, "---->  RFComm error: " + e.toString());
      }
      mmSocket = tmp;
    }

    public void run() {
      // Cancel discovery because it will slow down the connection
      bluetoothAdapter.cancelDiscovery();

      try {
        // Connect the device through the socket. This will block
        // until it succeeds or throws an exception
        Log.d(TAG, "---->  Socket status: " + mmSocket.isConnected());

        mmSocket.connect();
      } catch (IOException connectException) {
        // Unable to connect; close the socket and get out
        Log.d(TAG, "---->  Unable to connect: " + connectException.toString());
        try {
          mmSocket.close();
        } catch (IOException closeException) {
          Log.d(TAG, "---->  Close exception: " + closeException.toString());
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

  public class ConexionServerGasolimetro extends AsyncTask<String, Void, String[]> {

    @Override
    protected String[] doInBackground(String... params) {
      /* If there's no HOST, nothing to send. */
      if (params.length == 0) {
        return null;
      }
      String location = params[0];
      String trama = params[1];
      URL gasolimetroURL = buildUrl(location);

      try {
        String gasolimetroResponse = getResponseFromHttpUrl(gasolimetroURL, trama);
        Log.v(TAG, "---> Respuesta del servidor "+gasolimetroResponse);
      } catch (IOException e) {
        e.printStackTrace();
      }

      return new String[0];
    }
  }

  private static URL buildUrl(String locationQuery) {
    Uri builtUri = Uri.parse(locationQuery).buildUpon().build();
    URL url = null;
    try {
      url = new URL(builtUri.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    Log.v(TAG, "Built URI " + url);
    return url;
  }

  private static String getResponseFromHttpUrl(URL url, String trama) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {

      String json = "{\n" +
        "\t\"trama\":\""+trama+"\"\n" +
        "}";

      urlConnection.setConnectTimeout(5000);
      urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      urlConnection.setDoOutput(true);
      urlConnection.setDoInput(true);
      urlConnection.setRequestMethod("POST");

      OutputStream os = urlConnection.getOutputStream();
      os.write(json.getBytes("UTF-8"));
      os.close();

      InputStream in = urlConnection.getInputStream();

      Scanner scanner = new Scanner(in);
      scanner.useDelimiter("\\A");

      boolean hasInput = scanner.hasNext();
      if (hasInput) {
        return scanner.next();
      } else {
        return null;
      }

    }catch(Exception e){
      Log.v(TAG, "---> Error al conectar con el HOST"+e.toString());
      return "";
    } finally {
      urlConnection.disconnect();
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
