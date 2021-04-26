package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherMapController<temp>
{

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    WeatherJSonService service = retrofit.create(WeatherJSonService.class);

    Single<OpenWeatherMapFeed> single = service.getCurrentWeather("New York");
    //no sapposed to but allowed for right now
    OpenWeatherMapFeed feed = single.blockingGet();

    double temp = feed.main.temp;
}
