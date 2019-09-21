public interface RestClient {
    @GET("nearbysearch/json")
    Call<PlacesFeed> getPlaces(@Query("location") String loc, @Query("radius") String radius,@Query("types") String types,@Query("key") String key);
    @GET("details/json")
    Call<PlaceFeed> getPlaceInfo(@Query("placeid") String id,@Query("fields") String fields, @Query("key") String key);
}