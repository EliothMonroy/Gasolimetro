public void obtenerGasolinerasCercanasGoogle(){
        Call<PlacesFeed> call = restClient.getPlaces(locationToString(currentLocation), Constantes.RADIO,Constantes.TYPE,getString(R.string.google_directions_key));

        call.enqueue(new Callback<PlacesFeed>() {
            @Override
            public void onResponse(Call<PlacesFeed> call, Response<PlacesFeed> response) {
                switch (response.code()) {
                    case 200:
                        PlacesFeed data = response.body();
                        results=data.getResults();
                        crearMarcadores();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailure(Call<PlacesFeed> call, Throwable t) {
            }
        });
    }