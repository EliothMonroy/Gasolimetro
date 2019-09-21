package com.ipn.mx.gasolimetro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.gson.Gson;
import com.ipn.mx.gasolimetro.datos.api.Constantes;
import com.ipn.mx.gasolimetro.datos.api.RestClient;
import com.ipn.mx.gasolimetro.datos.api.RestClientServer;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtils;
import com.ipn.mx.gasolimetro.datos.api.RetrofitUtilsServer;
import com.ipn.mx.gasolimetro.datos.modelos.DistanciaFeed;
import com.ipn.mx.gasolimetro.datos.modelos.Gasolinera;
import com.ipn.mx.gasolimetro.datos.modelos.GasolineraConsultaModel;
import com.ipn.mx.gasolimetro.datos.modelos.GasolinerasFeed;
import com.ipn.mx.gasolimetro.datos.modelos.InfoGasolineraServer;
import com.ipn.mx.gasolimetro.datos.modelos.InsigniasGasolinera;
import com.ipn.mx.gasolimetro.datos.modelos.PlaceFeed;
import com.ipn.mx.gasolimetro.datos.modelos.PlacesFeed;
import com.ipn.mx.gasolimetro.datos.modelos.ResponseGeneric;
import com.ipn.mx.gasolimetro.datos.modelos.Result;
import com.ipn.mx.gasolimetro.datos.modelos.ResultPlace;
import com.ipn.mx.gasolimetro.datos.modelos.Row;
import com.ipn.mx.gasolimetro.pruebaPath.FetchURL;
import com.ipn.mx.gasolimetro.pruebaPath.TaskLoadedCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarMapa extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, View.OnClickListener, TaskLoadedCallback {

    private GoogleMap mMap;
    // Atributos para lo relacionado con el posicionamiento.
    private LocationManager locationManager = null;
    //ESCOM
    private LatLng currentLocation = new LatLng(19.504507, -99.146911);
    private boolean moveCameraCurrentLocation = false;
    private static final int DEFAULT_ZOOM_LEVEL = 15;
    private int timeUpdateLocation = 10000;
    private float distanceUpateLocation = (float)10.0;
    Location location;
    Marker marcadorDestino=null;
    GasolineraConsultaModel destino, posibleDestino;
    private List<Result> results = new ArrayList<>();
    private List<GasolineraConsultaModel> resultsServer=new ArrayList<>();
    private GasolineraConsultaModel gasolineraSeleccionada;
    private List<Marker> marcadores=new ArrayList<>();
    RestClientServer restClientServer;
    RestClient restClient;
    private List<LatLng> gasolinera=new ArrayList<>();
    private ResultPlace resultPlace;
    private LatLng gas;
    private Result result;
    private List<Row> rows=new ArrayList<>();
    Activity activity;
    ResponseGeneric<InfoGasolineraServer> responseGeneric;
    InfoGasolineraServer infoGasolineraServer;


    private Polyline currentPolyline;

    //Slider
    private SlidingUpPanelLayout mLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Obtenemos el floating action button
        FloatingActionButton floatingActionButton=getActivity().findViewById(R.id.boton_navegacion);
        floatingActionButton.setOnClickListener(this);

        //Trabajamos con el slider
        mLayout=getActivity().findViewById(R.id.slider_principal);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Toast.makeText(getContext(),"Estoy en slide",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                //Toast.makeText(getContext(),"Cambie",Toast.LENGTH_SHORT).show();
            }
        });
        ///
        //Inicializamos cliente rest
        restClient = RetrofitUtils.getInstance().create(RestClient.class);
        //Aquí deberiamos de inicializar cliente para conectarnos server batiz
        restClientServer= RetrofitUtilsServer.getInstance().create(RestClientServer.class);

        activity=getActivity();


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        Log.d("onMapReady","Iniciando Servicio .....");
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                initLocationService();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(),
                                0x1);
                        initLocationService();
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }


    private void initLocationService() {
        this.locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Log.d("initLocationService", "Registrando Servicio....");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

                Log.d("initLocationService", "No hay permiso de algo");

                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
            } else {
                try {
                    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        Log.d("initLocationService:", "Soy gps");
                        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeUpdateLocation, distanceUpateLocation, this);
                        location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }else{
                        Log.d("initLocationService:", "Soy network");
                        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeUpdateLocation, distanceUpateLocation, this);
                        location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    //LatLng escom = new LatLng(19.504507, -99.146911);
                    //Aquí obtenemos la ubicación actual

                    this.mMap.setMyLocationEnabled(true);

                    if(location!=null){
                        Log.d("initLocationService:", "Location NO es nulo");
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }else{
                        Log.d("initLocationService:", "Location es nulo");
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    //mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    // Hacemos zoom, animating with a duration of 2 seconds.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM_LEVEL), 2000, null);
                    mMap.setOnMarkerClickListener(this);
                    //Vamos a encontrar las gasolineras cercanas
                    encontrarGasolineras();
                    Log.d("initLocationService:", "Acabe de registrar servicio");

                    //Toast.makeText(getContext(),"Inicie bien",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Log.d("initLocationService:", "Error en el try");
                    e.printStackTrace();
                }
            }
        }else{
            Log.d("initLocationService:", "Error raro");
            Toast.makeText(getContext(),"Error raro",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case 10:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    initLocationService();
                }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // This method is called when the location changes.
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        currentLocation = new LatLng(latitude, longitude);
        Log.e("onLocationChanged", "Latitud:" + latitude + " Longitud:" + longitude);
        /*if(moveCameraCurrentLocation) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentLocation)
                    .zoom(DEFAULT_ZOOM_LEVEL)
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }*/

        if(currentPolyline==null){
            mMap.clear();
            gasolinera.clear();
            results.clear();
            result=null;
            rows.clear();
            destino=null;
            marcadores.clear();
            resultPlace=null;
            encontrarGasolineras();
        }else{
            for(int i=0;i<marcadores.size();i++){
                if(Objects.equals(destino.getGasolineraId(), resultsServer.get(i).getGasolineraId())){
                    marcadorDestino=marcadores.get(i);
                    destino=resultsServer.get(i);
                    posibleDestino=resultsServer.get(i);
                }else{
                    gasolinera.remove(i);
                    marcadores.get(i).remove();
                    marcadores.remove(i);

                }
            }
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void encontrarGasolineras(){
        //Retrofit
        //Se guardan en results que es un list
        obtenerGasolinerasCercanasGoogle();

        //gasolinera = new LatLng(19.50213, -99.14932);
        //marcador=mMap.addMarker(new MarkerOptions().position(gasolinera).title(getResources().getString(R.string.texto_barra_inferior_nombre_gasolinera)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_gasolinera)));
        //marcador.setSnippet(getResources().getString(R.string.texto_barra_inferior_rating_gasolinera));
        //marcador.showInfoWindow();

    }

    public void obtenerGasolinerasCercanasGoogle(){
        results.clear();
        result=null;

        Call<PlacesFeed> call = restClient.getPlaces(locationToString(currentLocation), Constantes.RADIO,Constantes.TYPE,activity.getString(R.string.google_directions_key));

        call.enqueue(new Callback<PlacesFeed>() {
            @Override
            public void onResponse(Call<PlacesFeed> call, Response<PlacesFeed> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("obtenerGasolinerasCerca", "Consulta exitosa api google para encontrar gasolineras cercanas");
                        PlacesFeed data = response.body();
                        Log.d("response", "response: "+new Gson().toJson(response));
                        results=data.getResults();
                        obtenerGasolinerasServer();
                        break;
                    case 401:
                        Log.e("obtenerGasolinerasCerca", "error: No pude conectarme a google 401");
                        break;
                    default:
                        Log.e("obtenerGasolinerasCerca", "error: No pude conectarme, estoy en el default");
                        break;
                }
            }

            @Override
            public void onFailure(Call<PlacesFeed> call, Throwable t) {
                Log.e("encontrarGasolineras()", "error: "+t.toString());
            }
        });
    }

    public void obtenerGasolinerasServer(){


        //Variables para leer gasolineras del server
        List<Gasolinera> gasolineras=new ArrayList<>();
        GasolinerasFeed gasolinerasFeed=new GasolinerasFeed();

        Log.d("crearMarcadores", "Voy a crear "+results.size()+" marcadores");


        for (int i=0;i<results.size();i++){

            //Añadimos una gasolinera a la lista de gasolineras
            gasolineras.add(new Gasolinera(results.get(i).getPlaceId(),results.get(i).getGeometry().getLocation().getLat(),results.get(i).getGeometry().getLocation().getLng()));
        }

        //Metemos la lista de gasolineras al objeto feed
        gasolinerasFeed.setResults(gasolineras);
        //Llamado retrofit
        Call<List<GasolineraConsultaModel>> call = restClientServer.getGasolineras(gasolinerasFeed);

        call.enqueue(new Callback<List<GasolineraConsultaModel>>() {
            @Override
            public void onResponse(Call<List<GasolineraConsultaModel>> call, Response<List<GasolineraConsultaModel>> response) {
                //Log.d("obtenerGasolinerasSer", "response batiz: "+new Gson().toJson(call.request()));
                switch (response.code()) {
                    case 200:
                        Log.d("obtenerGasolinerasSer", "exito batiz");
                        Log.d("obtenerGasolinerasSer", "response batiz: "+new Gson().toJson(response));
                        resultsServer = response.body();
                        crearMarcadores();
                        break;
                    case 401:
                        Log.e("obtenerGasolinerasSer", "error batiz: No pude conectarme a server"+new Gson().toJson(response));
                        break;
                    default:
                        Log.e("obtenerGasolinerasSer", "error batiz: No pude conectarme, estoy en el default"+new Gson().toJson(response));
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<GasolineraConsultaModel>> call, Throwable t) {
                Log.e("obtenerGasolinerasSer", "error: "+t.toString());
            }
        });

    }

    public void crearMarcadores(){

        Log.d("crearMarcadores","Batiz devolvio: "+resultsServer.size());

        for(int i=0;i<resultsServer.size();i++){
            if(destino!=null){
                if(!(Objects.equals(destino.getGasolineraId(), resultsServer.get(i).getGasolineraId()))){

                    //Comparar

                    if(resultsServer.get(i).getExists()){
                        gasolinera.add(new LatLng(Double.valueOf(resultsServer.get(i).getLat()), Double.valueOf(resultsServer.get(i).getLon())));

                        //marcadores.add(mMap.addMarker(new MarkerOptions().position(gasolinera.get(i)).title(resultsServer.get(i).getNombreGasolinera()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_gasolinera))));
                        marcadores.add(mMap.addMarker(new MarkerOptions().position(gasolinera.get(i)).title(resultsServer.get(i).getNombreGasolinera()).icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_gas)))));

                        marcadores.get(i).setSnippet("Calificación: "+String.format ("%.2f", resultsServer.get(i).getCalificacion()));

                    }else{
                        gasolinera.add(new LatLng(Double.valueOf(resultsServer.get(i).getLat()), Double.valueOf(resultsServer.get(i).getLon())));

                        marcadores.add(mMap.addMarker(new MarkerOptions().position(gasolinera.get(i)).title("Sin información").icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_gas_rojo)))));

                        marcadores.get(i).setSnippet("Rating: Sin información");
                    }

                }
            }else{
                Log.d("crearMarcadores","Batiz devolvio: "+resultsServer.get(i).getExists());
                if(resultsServer.get(i).getExists()){
                    gasolinera.add(new LatLng(Double.valueOf(resultsServer.get(i).getLat()), Double.valueOf(resultsServer.get(i).getLon())));

                    marcadores.add(mMap.addMarker(new MarkerOptions().position(gasolinera.get(i)).title(resultsServer.get(i).getNombreGasolinera()).icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_gas)))));

                    marcadores.get(i).setSnippet("Calificación: "+String.format ("%.2f", resultsServer.get(i).getCalificacion()));

                }else{
                    gasolinera.add(new LatLng(Double.valueOf(resultsServer.get(i).getLat()), Double.valueOf(resultsServer.get(i).getLon())));

                    marcadores.add(mMap.addMarker(new MarkerOptions().position(gasolinera.get(i)).title("Sin información").icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_gas_rojo)))));

                    marcadores.get(i).setSnippet("Rating: Sin información");
                }
            }
        }

        //Añadimos por default un destino
        if(destino==null){
            seleccionarDestinoDefault();
        }else{
            encontrarCamino();
        }
    }


    public void seleccionarDestinoDefault(){
        onMarkerClick(marcadores.get(0));
        posibleDestino=resultsServer.get(0);
    }

    public String locationToString(LatLng actual){
        String resultado=actual.latitude+","+actual.longitude;
        return resultado;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("onMarkerClick", "Voy a mostrar la info de un marcador");
        for(int i=0;i<marcadores.size();i++){
            if (marker.equals(marcadores.get(i)))
            {
                marcadorDestino=marcadores.get(i);
                gas=marcadores.get(i).getPosition();
                gasolineraSeleccionada=resultsServer.get(i);
                posibleDestino=resultsServer.get(i);
                //Validación:

                if(gasolineraSeleccionada.getNombreGasolinera()!=null){
                    ((TextView)activity.findViewById(R.id.nombre_gasolinera)).setText(gasolineraSeleccionada.getNombreGasolinera());
                    //Aquí hay que poner rating
                    mostrarRating(resultsServer.get(i).getCalificacion());
                }else{
                    ((TextView)activity.findViewById(R.id.nombre_gasolinera)).setText("Sin información");
                    //Aquí hay que poner rating
                    mostrarRating(0.0);
                }

                obtenerInfoGasolinera();

                //Toast.makeText(getContext(),"Seleccionaste una gasolinera",Toast.LENGTH_LONG).show();
                marcadorDestino.showInfoWindow();

                break;
            }
        }

        return true;
    }

    public void mostrarRating(Double rating){
        if (rating==0.0){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_0_of_5);
        }
        if (rating>0.0 && rating<=.5){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_0_5_of_5);
        }
        if (rating>0.5 && rating<=1.0){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_1_of_5);
        }
        if (rating>1.0 && rating<=1.5){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_1_5_of_5);
        }
        if (rating>1.5 && rating<=2.0){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_2_of_5);
        }
        if (rating>2.0 && rating<=2.5){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_2_5_of_5);
        }
        if (rating>2.5 && rating<=3.0){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_3_of_5);
        }
        if (rating>3.0 && rating<=3.5){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_3_5_of_5);
        }
        if (rating>3.5 && rating<=4.0){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_4_of_5);
        }
        if (rating>4.0 && rating<=4.5){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_4_5_of_5);
        }
        if (rating>4.5){
            ((ImageView)activity.findViewById(R.id.img_rating_gasolinera)).setImageResource(R.drawable.star_rating_5_of_5);
        }

        ((TextView)activity.findViewById(R.id.rating_gasolinera)).setText("Calificación: "+String.format("%.2f",rating));

    }


    public void obtenerInfoGasolinera(){
        resultPlace=null;

        Call<PlaceFeed> call = restClient.getPlaceInfo(gasolineraSeleccionada.getPlace_id(),Constantes.CAMPOS_PLACE,activity.getString(R.string.google_directions_key));

        call.enqueue(new Callback<PlaceFeed>() {
            @Override
            public void onResponse(Call<PlaceFeed> call, Response<PlaceFeed> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("obtenerInfoGasolinera", "Consulta exitosa api google para encontrar info gasolinera para el place_id: "+gasolineraSeleccionada.getPlace_id());
                        PlaceFeed data = response.body();
                        Log.d("obtenerInfoGasolinera", "response: "+new Gson().toJson(response));
                        resultPlace=data.getResultPlace();
                        mostrarDireccion();
                        obtenerDistanciaGasolinera();
                        obtenerInfoGasolineraServer();
                        break;
                    case 401:
                        Log.e("obtenerInfoGasolinera", "error: No pude conectarme a google 401");
                        break;
                    default:
                        Log.e("obtenerInfoGasolinera", "error: No pude conectarme, estoy en el default");
                        break;
                }
            }

            @Override
            public void onFailure(Call<PlaceFeed> call, Throwable t) {
                Log.e("encontrarGasolineras()", "error: "+t.toString());
            }
        });

    }

    public void mostrarDireccion(){
        ((TextView)activity.findViewById(R.id.direccion_gasolinera)).setText(resultPlace.getFormattedAddress());
    }

    public void obtenerDistanciaGasolinera(){
        rows.clear();

        Call<DistanciaFeed> call = restClient.getDistance(Constantes.UNITS,Constantes.LANGUAGE,locationToString(currentLocation),locationToString(gas),activity.getString(R.string.google_directions_key));

        call.enqueue(new Callback<DistanciaFeed>() {
            @Override
            public void onResponse(Call<DistanciaFeed> call, Response<DistanciaFeed> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("obtenerDistanciaGasolin", "Consulta exitosa api google para encontrar distancia y tiempo");
                        DistanciaFeed data = response.body();
                        Log.d("response", "response: "+new Gson().toJson(response));
                        rows=data.getRows();
                        mostrarDistanciaTiempo();
                        break;
                    case 401:
                        Log.e("obtenerDistanciaGasolin", "error: No pude conectarme a google 401");
                        break;
                    default:
                        Log.e("obtenerDistanciaGasolin", "error: No pude conectarme, estoy en el default");
                        break;
                }
            }

            @Override
            public void onFailure(Call<DistanciaFeed> call, Throwable t) {
                Log.e("encontrarGasolineras()", "error: "+t.toString());
            }
        });
    }

    public void obtenerInfoGasolineraServer(){
        //Aquí hay que hacer una petición al server de batiz

        Call<ResponseGeneric<InfoGasolineraServer>> call = restClientServer.obtenerInfoGasolinera(gasolineraSeleccionada.getGasolineraId());

        call.enqueue(new Callback<ResponseGeneric<InfoGasolineraServer>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<InfoGasolineraServer>> call, Response<ResponseGeneric<InfoGasolineraServer>> response) {
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        Log.d("obtenerInfoGasoliner", "Consulta exitosa api google para encontrar distancia y tiempo");
                        responseGeneric= response.body();
                        infoGasolineraServer=responseGeneric.getObject();
                        Log.d("response", "response: "+new Gson().toJson(response));
                        mostrarInfoGasolineraServer();
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        Log.e("obtenerInfoGasoliner", "error: No encontre la gasolinera");
                        break;
                    default:
                        Log.e("obtenerInfoGasoliner", "error: No pude conectarme, estoy en el default");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<InfoGasolineraServer>> call, Throwable t) {
                Log.e("obtenerInfoGasoliner", "error: "+t.toString());
            }
        });

    }

    public void mostrarInfoGasolineraServer(){
        LinearLayout layout=activity.findViewById(R.id.contenedor_insignias);

        if(infoGasolineraServer.getNumeroMediciones()!=null){
            ((TextView)activity.findViewById(R.id.total_mediciones_gasolinera)).setText("Total de mediciones hechas aquí: "+infoGasolineraServer.getNumeroMediciones());
        }else{
            ((TextView)activity.findViewById(R.id.total_mediciones_gasolinera)).setText("Total de mediciones hechas aquí: 0");
        }

        if(infoGasolineraServer.getInsigniasGasolinera()!=null){
            ((TextView)activity.findViewById(R.id.titulo_insignias)).setText("Insignias:");
            for (InsigniasGasolinera insigniasGasolinera:infoGasolineraServer.getInsigniasGasolinera()) {
                TextView textView=new TextView(activity);
                textView.setText(insigniasGasolinera.getCriterio());
                textView.setTextSize(activity.getResources().getDimension(R.dimen.text_size_insignia));
                textView.setTextColor(ContextCompat.getColor(activity, R.color.editTextColorBlack));
                //textView.setTextColor(getResources().getColor(R.color.editTextColorBlack));
                layout.addView(textView);
            }
        }else{
            ((TextView)activity.findViewById(R.id.titulo_insignias)).setText("Sin insignias");
        }

    }


    public void mostrarDistanciaTiempo(){
        ((TextView)activity.findViewById(R.id.distancia_gasolinera)).setText("Distancia: "+rows.get(0).getElements().get(0).getDistance().getText());
        ((TextView)activity.findViewById(R.id.tiempo_gasolinera)).setText("Tiempo llegada: "+rows.get(0).getElements().get(0).getDuration().getText());
    }


    @Override
    public void onClick(View v) {

        //Toast.makeText(getContext(), "Calculando dirección", Toast.LENGTH_LONG).show();

        if(currentPolyline!=null){
            currentPolyline.remove();
        }

        destino=posibleDestino;

        //Prueba camino
        encontrarCamino();

    }

    public void encontrarCamino(){
        new FetchURL(activity).execute(getUrl(currentLocation, marcadorDestino.getPosition(), "driving"), "driving");
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + activity.getString(R.string.google_directions_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {

        if (currentPolyline != null)
            currentPolyline.remove();
        Log.d("onTaskDone","Voy a empezar a dibujar");
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

    }

    private Bitmap getBitmap(int drawableRes) {
        //Drawable drawable = activity.getResources().getDrawable(drawableRes);
        Drawable drawable = ContextCompat.getDrawable(activity, drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
