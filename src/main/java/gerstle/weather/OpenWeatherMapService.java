package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherJSonService
{
    @GET("/data/2.5/weather?appid=12bfe356e180a322628ce16cfb5f85f0")
    Single<OpenWeatherMapFeed> getCurrentWeather(@Query("q")String location,
                                                 @Query("units")String units);
    @GET("/data/2.5/forecast?appid=12bfe356e180a322628ce16cfb5f85f0")
    Single<OpenWeatherMapForecast> getWeatherForecast(@Query("q") String location,
                                                     @Query("units")String units);
}
