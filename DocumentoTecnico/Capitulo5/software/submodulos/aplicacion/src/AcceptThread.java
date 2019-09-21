
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("G Pro Lite",BTMODULEUUID);
            } catch (Exception e) {
                Log.d("Valor", "----> Bluetooth error server: "+e.getMessage());
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
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
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }