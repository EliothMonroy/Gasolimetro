private void inicializarUbicacion() {
        this.locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
            } else {
                try {
                    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeUpdateLocation, distanceUpateLocation, this);
                        location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }else{
                        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeUpdateLocation, distanceUpateLocation, this);
                        location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                    this.mMap.setMyLocationEnabled(true);
                    if(location!=null){
                        
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM_LEVEL), 2000, null);
                    mMap.setOnMarkerClickListener(this);
                    encontrarGasolineras(currentLocation);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }