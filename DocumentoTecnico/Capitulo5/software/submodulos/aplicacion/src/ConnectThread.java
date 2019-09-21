private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID);
            } catch (IOException e) {
                Log.d("valor", "---->  RFComm error: " + e.toString());
            }
            mmSocket = tmp;
        }

        public void run() {
            bluetoothAdapter.cancelDiscovery();

            try {
                Log.d("valor", "---->  Socket status: " + mmSocket.isConnected());
                mmSocket.connect();
            } catch (IOException connectException) {
                Log.d("valor", "---->  Unable to connect: " + connectException.toString());
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.d("valor", "---->  Close exception: " + closeException.toString());
                }
                return;
            }

            final InputStream mmInStream;
            final OutputStream mmOutStream;

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    bluetoothHandler.obtainMessage(handlerState, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }